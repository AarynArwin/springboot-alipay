package com.geekerit.springbootalipay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.geekerit.springbootalipay.config.AlipayProperties;
import com.geekerit.springbootalipay.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @ClassName WapPayController
 * @Description TODO
 * @Author Aaryn
 * @Date 2018/12/19 17:43
 * @Version 1.0
 */
@Controller
public class WapPayController {

//    public static final Logger logger = LoggerFactory.getLogger(WapPayController.class);

    @Autowired
    private AlipayProperties alipayProperties;

    @Autowired
    private AlipayClient alipayClient;

    @RequestMapping(value = "/pay")
    public void pay(HttpServletResponse httpResponse) throws Exception{
        //创建API对应的request
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
        //在公共参数中设置回跳和通知地址
        alipayRequest.setReturnUrl(alipayProperties.getReturnUrl());
        alipayRequest.setNotifyUrl(alipayProperties.getNotifyUrl());
        // 设置业务参数
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setBody("这一杯 谁不爱");
        model.setSubject("瑞幸咖啡");
        model.setOutTradeNo(DateUtil.nowTimeString());
        model.setTimeoutExpress("30m");
        model.setTotalAmount("10.00");
        //填充业务参数
        alipayRequest.setBizModel(model);
        String form="";
        try {
            //调用SDK生成表单
            form = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + alipayProperties.getCharset());
        //直接将完整的表单html输出到页面
        httpResponse.getWriter().write(form);
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

    @RequestMapping(value = "/returnUrl")
    public String returnUrl(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,String> map = new HashMap<>(16);

        Map<String, String[]> parameterMap = request.getParameterMap();
        Iterator<String> iterator = parameterMap.keySet().iterator();
        for (Iterator iter = iterator; iterator.hasNext();) {
            String name = (String)iter.next();
            String[] values = parameterMap.get(name);

            String valueStr = "";

            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]: valueStr + values[i] + ",";
            }
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            map.put(name,valueStr);
        }
        boolean verifyResult = AlipaySignature.rsaCheckV1(map,
                alipayProperties.getPublicKey(),
                alipayProperties.getCharset(),
                alipayProperties.getSignType());

        if (verifyResult){
            String outTradeNo = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
//            logger.info("商品订单号{}", outTradeNo);

            //支付宝交易号
            String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
//            logger.info("交易号{}",tradeNo);
            return "wayPaySuccess";
        }

        return "wayPayFail";
    }
}
