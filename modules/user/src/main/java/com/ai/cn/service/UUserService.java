package com.ai.cn.service;

import com.ai.cn.entity.UUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nft.cn.form.LoginForm;
import com.nft.cn.util.BaseResult;

import java.io.IOException;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author kires
 * @since 2025-02-11
 */
public interface UUserService extends IService<UUser> {

    /**
     *
     * @return UUser
     */
    UUser getCurrentTokenUSer();

    BaseResult<Map<String,Object>> login(LoginForm form) throws IOException;

    /**
     *
     * @return UUser
     */
    UUser getTokenUSer();

}
