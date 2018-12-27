package com.geekerit.springbootalipay.utils;

/**
 * 短信类型
 * @author Aaryn
 */
public class SmsType {

    //**************************************验证码短信**************************************//

    // 登录验证码（悦心盒子）
    public static final String YZ_LOGIN = "YZ_LOGIN";

    // 登录验证码(会员体系)
    public static final String YZ_LOGIN_MEMBER = "YZ_LOGIN_MEMBER";

    //**************************************通知短信**************************************//
    // ---------------1.登录系统后的短信通知---------------
    // 通知短信（首次登录系统获得50元悦心礼金）
    public static final String TZ_LOGIN_GIFTMONEY = "TZ_LOGIN_GIFTMONEY";
    // 通知短信(邀请好友入会，好友缴纳押金后邀请人获得礼金通知)
    public static final String TZ_LOGIN_GIFTMONEY_INVITE = "TZ_LOGIN_GIFTMONEY_INVITE";
    // 通知短信（注册后未成为会员）
    public static final String TZ_LOGIN_MEMBER = "TZ_LOGIN_MEMBER";
    // 通知短信（注册后生成支付押金订单未付费）
    public static final String TZ_LOGIN_NOPAY = "TZ_LOGIN_NOPAY";

    // ---------------2.开始服务后的短信通知---------------
    // 通知短信（悦心盒子发出时间前七天发出确认地址的通知短信）
    public static final String TZ_BOX_CONFIRM_ADDRESS = "TZ_BOX_CONFIRM_ADDRESS";
    // 通知短信（悦心盒子发货后）
    public static final String TZ_BOX_EXPRESS = "TZ_BOX_EXPRESS";
    // 通知短信（悦心盒子签收后）
    public static final String TZ_BOX_SIGN= "TZ_BOX_SIGN";
    // 通知短信（悦心盒子评论并生成支付订单后十分钟内未支付）
    public static final String TZ_BOX_PAY= "TZ_BOX_PAY";
    // 通知短信（悦心盒子签收七天后通知预约快递退回盒子）
    public static final String TZ_BOX_BACK= "TZ_BOX_BACK";
    // 通知短信（悦心盒子成功退回---无异常）
    public static final String TZ_BOX_FINISH_SUCCESS= "TZ_BOX_FINISH_SUCCESS";
    // 通知短信（悦心盒子成功退回---存在异常）
    public static final String TZ_BOX_FINISH_EXCEPTION= "TZ_BOX_FINISH_EXCEPTION";

    // ---------------3.其他类型的短信通知---------------
    // 通知短信（用户退回申请受理通知）
    public static final String TZ_OTHER_USERQUIT= "TZ_OTHER_USERQUIT";

    // ---------------4.群发类型的短信通知---------------
    // 群发短信通知服务变更
    public static final String QF_MANY_CHANGE = "QF_MANY_CHANGE";


    //***************************营销短信***************************//
    // 生日营销短信通知（发放购物券）
    public static final String YX_BIRTHDAY = "YX_BIRTHDAY";
    // 促销短信
    public static final String YX_SALES = "YX_SALES";





}
