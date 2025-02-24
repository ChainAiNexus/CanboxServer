package com.ai.cn.service.impl;

import com.ai.cn.entity.AAiBase;
import com.ai.cn.dao.AAiBaseMapper;
import com.ai.cn.service.AAiBaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * AI
 * </p>
 *
 * @author kires
 * @since 2025-02-11
 */
@Service
public class AAiBaseServiceImpl extends ServiceImpl<AAiBaseMapper, AAiBase> implements AAiBaseService {

    @Override
    public List<AAiBase> getAaiBase(Long categoryId, String name) {
        return baseMapper.getAaiBase(categoryId, name);
    }
}
