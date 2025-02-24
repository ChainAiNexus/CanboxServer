package com.ai.cn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nft.cn.util.LocalDateToLongSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * Ai
 * </p>
 *
 * @author kires
 * @since 2025-02-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="AAiCategory  ", description="Ai  ")
public class AAiCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     *
     */
    @ApiModelProperty(value = "  ")
    private String name;

    /**
     *
     */
    @ApiModelProperty(value = "  ")
    private Integer sort;

    /**
     *
     */
    @ApiModelProperty(value = "   ")
    private Integer status;

    /**
     *        0  1
     */
    @ApiModelProperty(value = "       0  1 ")
    private Integer isInternal;

    private String url;

    /**
     *
     */
    @ApiModelProperty(value = "    ")
    @JsonSerialize(using = LocalDateToLongSerializer.class)
    private LocalDateTime createTime;

    /**
     *
     */
    @ApiModelProperty(value = "    ")
    @JsonSerialize(using = LocalDateToLongSerializer.class)
    private LocalDateTime updateTime;


}
