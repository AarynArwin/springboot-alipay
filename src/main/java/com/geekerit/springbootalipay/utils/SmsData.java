package com.geekerit.springbootalipay.utils;

import com.geekerit.springbootalipay.enums.SmsTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 短信发送参数
 * @author Aaryn
 */
@Data
@ApiModel(value = "短信发送参数")
public class SmsData {
    /**
     *  验证码
     */
    @ApiModelProperty(value = "验证码")
    private int code;
    /**
     * 用户手机号码
     */
    @ApiModelProperty(value = "用户手机号码")
    private String phone;
    /**
     * 快递单号
     */
    @ApiModelProperty(value = "快递单号")
    private String express;

    @ApiModelProperty(value = "短信类型")
    private SmsTypeEnum smsTypeEnum;
}
