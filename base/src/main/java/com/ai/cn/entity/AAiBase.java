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
 * AI
 * </p>
 *
 * @author kires
 * @since 2025-02-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="AAiBase  ", description="AI    ")
public class AAiBase implements Serializable {

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
    @ApiModelProperty(value = "    ")
    private String url;

    /**
     *
     */
    @ApiModelProperty(value = "  ")
    private String icon;

    /**
     *
     */
    @ApiModelProperty(value = "  ")
    private String label;

    /**
     *
     */
    @ApiModelProperty(value = "  ")
    private String description;

    /**
     *   ID
     */
    @ApiModelProperty(value = "  ID")
    private String categoryId;

    /**
     *    0-   1-
     */
    @ApiModelProperty(value = "   0-   1-  ")
    private Integer status;

    /**
     *        0  1
     */
    @ApiModelProperty(value = "       0  1 ")
    private Integer isInternal;

    private Integer sort;

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


    private Integer isOpen;


}
