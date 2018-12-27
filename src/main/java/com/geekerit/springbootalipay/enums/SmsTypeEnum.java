package com.geekerit.springbootalipay.enums;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Aaryn
 */
@ApiModel(value = "短信发送类型")
public enum SmsTypeEnum {
    /**
     * 登录
     */
    LOGIN("SMS_144943098", "登录"),
    /**
     * 首次登录系统获得50元悦心礼金
     */
    GIFTMONEY_FIRST_LOGIN("SMS_144943114", "首次登录系统获得50元悦心礼金"),
    /**
     * 邀请好友入会，好友缴纳押金后邀请人获得礼金通知
     */
    GIFTMONEY_INVITE("SMS_148591035", "邀请好友入会，好友缴纳押金后邀请人获得礼金通知"),
    /**
     * 注册后未成为会员
     */
    REGIST_NOTMEMBER("SMS_144943104", "注册后未成为会员"),
    /**
     * 注册后生成支付押金订单未付费
     */
    REGIST_NOPAY("SMS_148610973", "注册后生成支付押金订单未付费"),
    /**
     * 悦心盒子发出时间前七天发出确认地址的通知短信
     */
    BOX_CONFIRM_ADDRESS("SMS_148610984", "悦心盒子发出时间前七天发出确认地址的通知短信"),
    /**
     * 悦心盒子发货后
     */
    BOX_EXPRESS_SEND("SMS_148591045", "悦心盒子发货后"),
    /**
     * 悦心盒子签收后
     */
    BOX_EXPRESS_SIGN("SMS_149100850", "悦心盒子签收后"),
    /**
     * 悦心盒子评论并生成支付订单后十分钟内未支付
     */
    BOX_ORDER_PAY("SMS_144943147", "悦心盒子评论并生成支付订单后十分钟内未支付"),
    /**
     * 悦心盒子签收七天后通知预约快递退回盒子
     */
    BOX_EXPRESS_BACK("SMS_148610997", "悦心盒子签收七天后通知预约快递退回盒子");

    /**
     * 短信模板ID
     */
    private String templateCode;
    /**
     * 功能描述
     */
    private String description;

    SmsTypeEnum(String templateCode, String description) {
        this.templateCode = templateCode;
        this.description = description;
    }

    public String getTemplateCode() {
        return templateCode;
    }

}
