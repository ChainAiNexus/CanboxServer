package com.ai.cn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
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
 * @since 2025-02-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SSysConfig  ", description="      ")
public class SSysConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     *   key
     */
    @ApiModelProperty(value = "  key")
    private String configKey;

    /**
     *
     */
    @ApiModelProperty(value = "   ")
    private String configValue;

    /**
     *
     */
    @ApiModelProperty(value = "  ")
    private String remark;

    /**
     *
     */
    @ApiModelProperty(value = "    ")
    private LocalDateTime createTime;

    /**
     *
     */
    @ApiModelProperty(value = "    ")
    private LocalDateTime updateTime;


}
