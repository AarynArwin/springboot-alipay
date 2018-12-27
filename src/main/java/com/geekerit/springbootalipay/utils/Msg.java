package com.geekerit.springbootalipay.utils;

import com.geekerit.springbootalipay.enums.SmsTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 短信请求体封装
 * @author Aaryn
 */
@Data
@ApiModel(value = "发送短信参数")
public class Msg {

    @ApiModelProperty(value = "短信所需变量")
    private SmsData smsData;

    @ApiModelProperty(value = "短信类型")
    private String type;

}
