package com.geekerit.springbootalipay.controller;

import com.geekerit.springbootalipay.config.AlipayProperties;
import com.geekerit.springbootalipay.utils.AlipayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Aaryn
 */
@RestController
@RequestMapping(value = "/pay/alipay")
public class PayNotifyController {

    public static final Logger logger = LoggerFactory.getLogger(PayNotifyController.class);

    @Autowired
    private AlipayProperties alipayProperties;
    @Autowired
    private AlipayUtil alipayUtil;

    @RequestMapping(value = "/notifyUrl")
    public String notifyUrl(HttpServletRequest request) throws Exception{
        boolean verifyResult = alipayUtil.checkSign(request);
        if (verifyResult){
            String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
            logger.info("交易状态{}",tradeStatus);
            return "success";
        }
        return "fail";
    }




}
