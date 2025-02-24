package com.ai.cn.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ai.cn.entity.UUser;
import com.ai.cn.dao.UUserMapper;
import com.ai.cn.entity.UUserAccount;
import com.ai.cn.exception.UnauthorizedException;
import com.nft.cn.form.LoginForm;
import com.ai.cn.service.I18nService;
import com.ai.cn.service.TokenService;
import com.ai.cn.service.UUserAccountService;
import com.ai.cn.service.UUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nft.cn.util.*;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.sol4k.Base58;
import org.sol4k.PublicKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 *             
 * </p>
 *
 * @author kires
 * @since 2025-02-11
 */
@Slf4j
@Service
public class UUserServiceImpl extends ServiceImpl<UUserMapper, UUser> implements UUserService {

    @Autowired
    private UUserService userService;

    @Autowired
    private I18nService i18nService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UUserAccountService userAccountService;

    @Override
    public UUser getCurrentTokenUSer() {
        try {
            Claims jsonObject = TokenUtil.getTokenUserInfo();

            if (null == jsonObject) {
                return null;
            }
            UUser uUser = JSON.parseObject(jsonObject.getSubject(), UUser.class);

            Long uUserId = uUser.getId();
            UUser userInfo = userService.getById(uUserId);

            if (!Optional.ofNullable(userInfo).isPresent()){
                return null;
            }
            /*if (userInfo.getStatus() == 1){
                throw new UnauthorizedException(i18nService.getMessage("10002"));
            }*/
            return userInfo;
        } catch (Exception e) {
            log.error("        :{},", e.getMessage(), e.getCause());
            return null;
        }
    }

    @Override
    public BaseResult<Map<String,Object>> login(LoginForm form) throws IOException {
        UUser uUser = userService.lambdaQuery()
                .eq(UUser::getUserAddress, form.getUserAddress())
                .one();

        //  hahs
        if (StringUtils.isEmpty(form.getSign()) || StringUtils.isEmpty(form.getSignMsg()) || StringUtils.isEmpty(form.getUserAddress())){
            return BaseResult.fail(i18nService.getMessage("99999"));
        }

        //    
        Boolean verifySignature = verifySignature(form.getSignMsg(), form.getSign(), form.getUserAddress());

        if (!verifySignature){
            return BaseResult.fail(i18nService.getMessage("99998"));
        }

        Map<String, Object> param = new HashMap<>();
        param.put("public_key",form.getUserAddress());
        param.put("signature",form.getSignature());
        param.put("network","solana");


        if (Optional.ofNullable(uUser).isPresent()){
            if (uUser.getStatus() == 1){
                return BaseResult.fail(i18nService.getMessage("99998"));
            }
            //    
            String token = tokenService.getToken(uUser.getId(),uUser);

            Map<String,Object> stringObjectMap = new HashMap<>();
            stringObjectMap.put("token",token);

            stringObjectMap.put("accessToken",accessToken);
            stringObjectMap.put("refreshToken",refreshToken);


            return BaseResult.success(stringObjectMap);
        }else {
            boolean flag = false;
            //                       
            UUser inviteUUser = null;


            //      
            UUser newUser = new UUser();

            newUser.setCreateTime(LocalDateTime.now()).setUserAddress(form.getUserAddress()).setUpdateTime(LocalDateTime.now());
            int result = baseMapper.insert(newUser);
            if(result==1){


                //    
                List<UUserAccount> uUserAccounts = new ArrayList<>();
                UUserAccount uUserAccount = new UUserAccount()
                        .setUserAddress(newUser.getUserAddress())
                        .setAmount(BigDecimal.ZERO)
                        .setCoinName("CAN")
                        .setCreateTime(LocalDateTime.now())
                        .setTotalAmount(BigDecimal.ZERO);
                uUserAccounts.add(uUserAccount);


                userAccountService.saveBatch(uUserAccounts);



                //  token  
                String token = tokenService.getToken(newUser.getId(),newUser);
                Map<String,Object> stringObjectMap = new HashMap<>();
                stringObjectMap.put("token",token);

                stringObjectMap.put("accessToken",accessToken);
                stringObjectMap.put("refreshToken",refreshToken);

                //TODO     

                return BaseResult.success(stringObjectMap);
            }else {
                return BaseResult.fail(i18nService.getMessage("99999"));
            }
        }
    }

    public static Boolean verifySignature(String message,String signature, String walletAddress) {
        byte[] messageBytes = message.getBytes();
        PublicKey publicKey = new PublicKey(walletAddress);
        byte[] signatureBytes = Base58.decode(signature);
        return publicKey.verify(signatureBytes, messageBytes);
    }

    @Override
    public UUser getTokenUSer() {
        try {
            Claims jsonObject = TokenUtil.getTokenUserInfo();

            if (null == jsonObject) {
                throw new UnauthorizedException(i18nService.getMessage("10002"));
            }
            UUser uUser = JSON.parseObject(jsonObject.getSubject(), UUser.class);

            Long uUserId = uUser.getId();
            UUser userInfo = userService.getById(uUserId);

            if (!Optional.ofNullable(userInfo).isPresent()){
                throw new UnauthorizedException(i18nService.getMessage("10002"));
            }
            if (userInfo.getStatus() == 1){
                throw new UnauthorizedException(i18nService.getMessage("10002"));
            }
            return userInfo;
        } catch (Exception e) {
            log.error("        :{},", e.getMessage(), e.getCause());
            throw new UnauthorizedException(i18nService.getMessage("10002"));
        }
    }


}
