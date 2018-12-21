package com.geekerit.springbootalipay.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Aaryn
 */
@Data
@ApiModel(value = "订单退款参数")
public class AlipayRefundDTO {

    @ApiModelProperty(value = "订单支付时传入的商户订单号,不能和 trade_no同时为空。")
    private String outTradeNo;

    @ApiModelProperty(value = "支付宝交易号，和商户订单号不能同时为空")
    private String tradeNo;

    @ApiModelProperty(value = "标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。")
    private String outRequestNo;

    @ApiModelProperty(value = "需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数")
    private String refundAmount;
}
