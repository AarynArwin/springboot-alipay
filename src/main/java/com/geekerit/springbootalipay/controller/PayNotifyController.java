package com.geekerit.springbootalipay.controller;

import com.alipay.api.internal.util.AlipaySignature;
import com.geekerit.springbootalipay.config.AlipayProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @ClassName PayNotifyController
 * @Description TODO
 * @Author Aaryn
 * @Date 2018/12/20 15:18
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/pay/alipay")
public class PayNotifyController {

    public static final Logger logger = LoggerFactory.getLogger(PayNotifyController.class);

    @Autowired
    private AlipayProperties alipayProperties;

    @RequestMapping(value = "/notifyUrl")
    public String notifyUrl(HttpServletRequest request) throws Exception{
        Map<String, String> map = new HashMap<>(16);
        Map<String, String[]> parameterMap = request.getParameterMap();
        StringBuilder notifyBuild = new StringBuilder("/****************************** alipay notify ******************************/\n");
        parameterMap.forEach((key, value) -> notifyBuild.append(key + "=" + value[0] + "\n"));
        logger.info(notifyBuild.toString());

        Iterator<String> iterator = parameterMap.keySet().iterator();
        for (Iterator iter = iterator; iterator.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = parameterMap.get(name);

            String valueStr = "";

            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");

            logger.info("该{}项下的获取的值为,{}", name, valueStr);
            map.put(name, valueStr);
        }
        boolean verifyResult = AlipaySignature.rsaCheckV1(map,
                alipayProperties.getPublicKey(),
                alipayProperties.getCharset(),
                alipayProperties.getSignType());
        logger.info("验签结果{}", verifyResult);

        if (verifyResult){
            String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
            logger.info("交易状态{}",tradeStatus);

            return "success";
        }
        return "fail";
    }

}
