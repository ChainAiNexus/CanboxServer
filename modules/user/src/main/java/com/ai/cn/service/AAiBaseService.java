package com.ai.cn.service;

import com.ai.cn.entity.AAiBase;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * AI
 * </p>
 *
 * @author kires
 * @since 2025-02-11
 */
public interface AAiBaseService extends IService<AAiBase> {

    /**
     *
     * @param categoryId    ID
     * @param name
     * @return List<AAiBase>
     */
    List<AAiBase> getAaiBase(Long categoryId,String name);

}
