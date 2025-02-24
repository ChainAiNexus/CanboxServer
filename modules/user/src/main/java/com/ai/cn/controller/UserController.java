package com.ai.cn.controller;

import com.ai.cn.entity.*;
import com.ai.cn.service.*;
import com.cxytiandi.encrypt.springboot.annotation.Decrypt;
import com.cxytiandi.encrypt.springboot.annotation.Encrypt;
import com.nft.cn.entity.*;
import com.nft.cn.form.GetInstalledAppsForm;
import com.nft.cn.form.LoginForm;
import com.nft.cn.form.RunBridgeForm;
import com.nft.cn.form.SearchAiBaseForm;
import com.nft.cn.service.*;
import com.nft.cn.util.BaseResult;
import com.nft.cn.util.GeneratorUtil;
import com.nft.cn.util.HttpUtil;
import com.nft.cn.util.StringUtils;
import com.nft.cn.vo.ExchangeFormDataVO;
import com.nft.cn.vo.ExchangeTargetVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author kires
 * @date 2025/1/21
 */
@Slf4j
@RestController
@Api(tags = {"      "})
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UUserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private I18nService i18nService;

    @Autowired
    private AAiBaseService aiBaseService;

    @Autowired
    private AAiCategoryService aiCategoryService;

    @Autowired
    private UUserAccountService userAccountService;
    @Encrypt
    @Decrypt
    @PostMapping(value = "login")
    @ApiOperation(value = "  ", notes = "   token")
    public BaseResult<Map<String,Object>> login(@RequestBody LoginForm form) {
        String key = null;
        try {
            key = "login." + form.getSign();
            boolean flag = false;
            //    
            do {
                //                       
                flag = stringRedisTemplate.opsForValue().setIfAbsent(key, "install");
            } while (!flag);
            stringRedisTemplate.expire(key, 10, TimeUnit.SECONDS);
            return userService.login(form);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("    --{}", e.getMessage());
            return BaseResult.fail(i18nService.getMessage("99999"));
        } finally {
            //   
            if (key != null) {
                stringRedisTemplate.delete(key);
                log.info("           ");
            }
        }
    }

    @GetMapping(value = "getCategoryList")
    @ApiOperation(value = "    ", notes = "   token")
    public BaseResult<List<AAiCategory>> getCategoryList(){
        List<AAiCategory> list = aiCategoryService.lambdaQuery()
                .eq(AAiCategory::getStatus, 0)
                .orderByAsc(AAiCategory::getSort)
                .list();
        return BaseResult.success(list);
    }

    @GetMapping(value = "getCategoryByIdList/{categoryId}")
    @ApiOperation(value = "    AI  ", notes = "   token")
    public BaseResult<List<AAiBase>> getCategoryByIdList(@PathVariable Long categoryId){
        List<AAiBase> list = aiBaseService.getAaiBase(categoryId,null);
        return BaseResult.success(list);
    }

    @GetMapping(value = "getCanUserAccount")
    @ApiOperation(value = "  Can    ", notes = "  token")
    public BaseResult<UUserAccount> getCanUserAccount(){
        UUser currentTokenUSer = userService.getCurrentTokenUSer();
        UUserAccount userAccount = userAccountService.lambdaQuery()
                .eq(UUserAccount::getUserAddress, currentTokenUSer.getUserAddress())
                .eq(UUserAccount::getCoinName, "CAN")
                .one();
        return BaseResult.success(userAccount);
    }

    @Autowired
    private SSysConfigService sysConfigService;

    @GetMapping(value = "getTokenContractAddress")
    @ApiOperation(value = "      ", notes = "  token")
    public BaseResult<String> getTokenContractAddress(){
        SSysConfig sysConfig = sysConfigService.lambdaQuery()
                .eq(SSysConfig::getConfigKey, "token_contract_address")
                .one();
        return BaseResult.success(sysConfig.getConfigValue());
    }



    @PostMapping(value = "searchAiBase")
    @ApiOperation(value = "  AI", notes = "   token")
    public BaseResult<List<AAiBase>> getCategoryByIdList(@RequestBody SearchAiBaseForm form){
        List<AAiBase> list = aiBaseService.getAaiBase(form.getCategoryId(),form.getName());
        return BaseResult.success(list);
    }



    @Autowired
    private PPledgeRecordService pledgeRecordService;

    @GetMapping(value = "getPledgeRecord")
    @ApiOperation(value = "      ", notes = "  token")
    public BaseResult<Map<String,Object>> getPledgeRecord(){
        UUser currentTokenUSer = userService.getCurrentTokenUSer();

        Map<String,Object> stringObjectMap = new HashMap<>();

        List<PPledgeRecord> list = pledgeRecordService.lambdaQuery()
                .eq(PPledgeRecord::getUserAddress, currentTokenUSer.getUserAddress())
                .orderByDesc(PPledgeRecord::getCreateTime)
                .list();
        stringObjectMap.put("list", list);


        BigDecimal reduce = list.stream().map(PPledgeRecord::getNum).reduce(BigDecimal.ZERO, BigDecimal::add);

        stringObjectMap.put("total", reduce);

        return BaseResult.success(stringObjectMap);
    }
}
