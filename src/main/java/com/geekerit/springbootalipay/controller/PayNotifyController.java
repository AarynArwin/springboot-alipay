package com.geekerit.springbootalipay.controller;

import com.geekerit.springbootalipay.config.AlipayProperties;
import com.geekerit.springbootalipay.utils.AlipayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * @author Aaryn
 */
@RestController
@RequestMapping(value = "/pay/alipay")
public class PayNotifyController {

    private static final Logger logger = LoggerFactory.getLogger(PayNotifyController.class);

    private final AlipayUtil alipayUtil;

    @Autowired
    public PayNotifyController(AlipayUtil alipayUtil) {
        this.alipayUtil = alipayUtil;
    }

    @RequestMapping(value = "/notifyUrl")
    public String notifyUrl(HttpServletRequest request) throws Exception{
        boolean verifyResult = alipayUtil.checkSign(request);
        if (verifyResult){
            String tradeStatus = new String(request.getParameter("trade_status").getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8);
            logger.info("交易状态{}",tradeStatus);
            return "success";
        }
        return "fail";
    }




}
