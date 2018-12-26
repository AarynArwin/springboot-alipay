package com.geekerit.springbootalipay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author Aaryn
 */
@Component
@PropertySource(value = "classpath:application-dev.yml")
@ConfigurationProperties(prefix = "pay.alipay")
@Data
public class AlipayProperties {
    /**
     * 支付网关
     */
    private String gatewayUrl;
    /**
     * APPID
     */
    private String appId;
    /**
     * 私钥
     */
    private String privateKey;
    /**
     * 公钥
     */
    private String publicKey;
    /**
     * 同步回调地址
     */
    private String returnUrl;
    /**
     * 异步回调地址
     */
    private String notifyUrl;
    /**
     * 编码格式
     */
    private String charset;
    /**
     * 格式化
     */
    private String format;
    /**
     * 加密方式
     */
    private String signType;
    /**
     * 商户PID
     */
    private String payeeUserId;

    private String pid;
}




