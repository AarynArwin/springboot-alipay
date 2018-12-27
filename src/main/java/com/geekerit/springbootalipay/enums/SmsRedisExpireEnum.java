package com.geekerit.springbootalipay.enums;

/**
 * @author Aaryn
 */

public enum SmsRedisExpireEnum {
    /**
     * 用户支付会费
     */
    ORDER_MEMBER_PAY("paydeposit", 60 * 10L, "用户支付会费"),
    /**
     * 用户支付盒子
     */
    ORDER_BOX_PAY("paybox", 60 * 10L, "用户支付盒子"),
    /**
     * 用户盒子发货前七天确认地址
     * TODO 提醒用户确认地址的时间待修改
     */
    USER_CONFIRM_ADDRESS("boxconfirm", 10L, "用户盒子发货前七天确认地址"),
    /**
     * 提醒用户退回盒子
     */
    USER_BOX_BACK("boxback", 60 * 60 * 24 * 6L, "提醒用户退回盒子");

    private String prefix;

    private Long expireTime;

    private String description;

    SmsRedisExpireEnum(String prefix, Long expireTime, String description) {
        this.prefix = prefix;
        this.expireTime = expireTime;
        this.description = description;
    }

    public String getPrefix() {
        return prefix;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    /**
     * 获取前缀获取枚举对象
     * @param prefix 前缀
     * @return       该枚举对象
     */
    public static SmsRedisExpireEnum getSmsEnum(String prefix){
        for (SmsRedisExpireEnum smsRedisExpireEnum : SmsRedisExpireEnum.values()) {
            if (prefix.equals(smsRedisExpireEnum.getPrefix())){
                return smsRedisExpireEnum;
            }
        }
        return null;
    }
}
