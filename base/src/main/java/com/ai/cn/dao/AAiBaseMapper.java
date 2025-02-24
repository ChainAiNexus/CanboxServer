package com.ai.cn.dao;

import com.ai.cn.entity.AAiBase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * AI     Mapper
 * </p>
 *
 * @author kires
 * @since 2025-02-11
 */
public interface AAiBaseMapper extends BaseMapper<AAiBase> {

    /**
     *
     * @param categoryId    ID
     * @param name
     * @return List<AAiBase>
     */
    List<AAiBase> getAaiBase(@Param("categoryId") Long categoryId,@Param("name") String name);

}
