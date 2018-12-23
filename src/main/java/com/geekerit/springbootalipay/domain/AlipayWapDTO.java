package com.geekerit.springbootalipay.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 支付宝手机网站支付参数
 * @author Aaryn
 */
@Data
@ApiModel(value = "支付宝手机网站支付参数")
public class AlipayWapDTO {
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题",required = true)
    private String subject;
    /**
     * 商品订单
     */
    @ApiModelProperty(value = "商品订单",required = true)
    private String outTradeNo;
    /**
     * 总金额
     */
    @ApiModelProperty(value = "总金额",required = true)
    private Double totalAmount;
    /**
     * 商品详细描述
     */
    @ApiModelProperty(value = "商品详细描述")
    private String body;
    /**
     * 商品过期时间
     * 该笔订单允许的最晚付款时间，逾期将关闭交易。
     * 取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。
     * 该参数数值不接受小数点， 如 1.5h，可转换为 90m。注：若为空，则默认为15d。
     */
    @ApiModelProperty(value = "商品过期时间",notes = "取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。")
    private String timeoutExpire;
    /**
     * 绝对过期时间
     * 绝对超时时间，格式为yyyy-MM-dd HH:mm。
     * 注：
     * 1）以支付宝系统时间为准；
     * 2）如果和timeout_express参数同时传入，以time_expire为准。
     */
    @ApiModelProperty(value = "绝对过期时间",notes = "格式为yyyy-MM-dd HH:mm;如果和timeout_express参数同时传入，以time_expire为准。")
    private String timeExpire;
    /**
     *商品主类型：
     *  0—虚拟类商品，
     *  1—实物类商品注：虚拟类商品不支持使用花呗渠道
     */
    @ApiModelProperty(value = "商品主类型",notes = "0—虚拟类商品，1—实物类商品注：虚拟类商品不支持使用花呗渠道")
    private String goodsType;
}
