package com.geekerit.springbootalipay.enums;

/**
 * @author Aaryn
 */

public enum UserGiftMoneyEnum {
    /**
     * 首次登陆系统领取悦心礼金
     */
    LOGIN(1, "首次登陆系统领取悦心礼金"),
    /**
     * 评论商品获取悦心礼金
     */
    COMMENT(2, "评论商品获取悦心礼金"),
    /**
     * 邀请好友成功入会领取
     */
    INVITE(3, "邀请好友成功入会领取");

    private int typeId;

    private String description;

    UserGiftMoneyEnum(int typeId, String description) {
        this.typeId = typeId;
        this.description = description;
    }

    public int getTypeId() {
        return typeId;
    }
}
