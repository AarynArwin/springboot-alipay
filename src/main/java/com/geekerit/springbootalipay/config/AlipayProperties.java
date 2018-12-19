package com.geekerit.springbootalipay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @ClassName AlipayProperties
 * @Description TODO
 * @Author Aaryn
 * @Date 2018/12/18 17:20
 * @Version 1.0
 */
@Component
@PropertySource(value = "classpath:application-dev.yml")
@ConfigurationProperties(prefix = "pay.alipay")
@Data
public class AlipayProperties {

    private String gatewayUrl;

    private String appId;

    private String privateKey;

    private String publicKey;

    private String returnUrl;

    private String notifyUrl;

    private String charset;

    private String format;

    private String signType;


}




