package com.geekerit.springbootalipay.enums;

/**
 * @author Aaryn
 */
public enum  AlipayEnum {
    /**
     * 手机网站支付
     */
    WAPPAY("QUICK_WAP_WAY","手机网站支付");
    /**
     * 支付宝平台的产品码
     */
    private String title;
    /**
     * 描述
     */
    private String description;

    private AlipayEnum(String title,String description){
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }}
