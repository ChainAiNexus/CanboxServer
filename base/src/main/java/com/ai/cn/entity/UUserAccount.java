package com.ai.cn.entity;

import java.math.BigDecimal;
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
 *
 * </p>
 *
 * @author kires
 * @since 2025-02-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UUserAccount  ", description="      ")
public class UUserAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     *
     */
    @ApiModelProperty(value = "    ")
    private String userAddress;

    /**
     *
     */
    @ApiModelProperty(value = "  ")
    private BigDecimal amount;

    /**
     *
     */
    @ApiModelProperty(value = "  ")
    private BigDecimal freezeAmount;

    /**
     *
     */
    @ApiModelProperty(value = " ")
    private BigDecimal totalAmount;

    /**
     *
     */
    @ApiModelProperty(value = "  ")
    private String coinName;

    /**
     * 
     */
    @JsonSerialize(using = LocalDateToLongSerializer.class)
    private LocalDateTime createTime;

    /**
     *
     */
    @JsonSerialize(using = LocalDateToLongSerializer.class)
    @ApiModelProperty(value = "    ")
    private LocalDateTime updateTime;


}
