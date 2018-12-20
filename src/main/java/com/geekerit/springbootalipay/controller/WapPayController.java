package com.geekerit.springbootalipay.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.geekerit.springbootalipay.config.AlipayProperties;
import com.geekerit.springbootalipay.constants.AlipayConstants;
import com.geekerit.springbootalipay.domain.AlipayRefundDTO;
import com.geekerit.springbootalipay.utils.DateUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @ClassName WapPayController
 * @Description
 * @Author Aaryn
 * @Date 2018/12/19 17:43
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "/pay")
@Api(value = "手机网站支付")
public class WapPayController {

    public static final Logger logger = LoggerFactory.getLogger(WapPayController.class);

    @Autowired
    private AlipayProperties alipayProperties;

    @Autowired
    private AlipayClient alipayClient;

    @RequestMapping(method = RequestMethod.GET)
    public void pay(HttpServletResponse httpResponse) throws Exception {
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
        String form = "";
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
    @ResponseBody
    public String returnUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> map = new HashMap<>(16);

        Map<String, String[]> parameterMap = request.getParameterMap();
        Iterator<String> iterator = parameterMap.keySet().iterator();
        for (Iterator iter = iterator; iterator.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = parameterMap.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");

            map.put(name, valueStr);
        }
        boolean verifyResult = AlipaySignature.rsaCheckV1(map,
                alipayProperties.getPublicKey(),
                alipayProperties.getCharset(),
                alipayProperties.getSignType());
        logger.info("验签结果{}", verifyResult);
        if (verifyResult) {
            String outTradeNo = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            logger.info("商品订单号{}", outTradeNo);

            //支付宝交易号
            String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
            logger.info("交易号{}", tradeNo);
            return "wayPaySuccess";
        }

        return "wayPayFail";
    }

    @RequestMapping(value = "/info/{outTradeNo}",method = RequestMethod.POST)
    @ResponseBody
    public void getTradeInfo(@PathVariable(value = "outTradeNo") String outTradeNo) throws Exception{
        //创建API对应的request类
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        // 设置业务参数
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(outTradeNo);
        request.setBizModel(model);
        //通过alipayClient调用API，获得对应的response类
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        logger.info("订单查询内容{}",response.getBody());
        //根据response中的结果继续业务逻辑处理
        String msg = response.getMsg();
        if (!msg.equals(AlipayConstants.QUERY_MSG_SUCCESS)){
            logger.info("订单查询失败，检查是否存在此订单信息");
        }
        logger.info("订单查询成功，开始处理业务");

    }

    @RequestMapping(value = "/refund",method = RequestMethod.POST)
    @ResponseBody
    public String refundTrade(@RequestBody AlipayRefundDTO alipayRefundDTO) throws Exception{
        AlipayTradeRefundRequest refundRequest = new AlipayTradeRefundRequest();

        AlipayTradeRefundModel refundModel = new AlipayTradeRefundModel();

        refundModel.setOutTradeNo(alipayRefundDTO.getOutTradeNo());
        refundModel.setTradeNo(alipayRefundDTO.getTradeNo());
        refundModel.setOutRequestNo(alipayRefundDTO.getOutRequestNo());
        refundModel.setRefundAmount(alipayRefundDTO.getRefundAmount());

        AlipayTradeRefundResponse refundResponse = alipayClient.execute(refundRequest);

        if (null != refundResponse){
            logger.info("退款信息为",refundResponse.getBody());
        }

        return refundResponse.getBody();
    }



}
