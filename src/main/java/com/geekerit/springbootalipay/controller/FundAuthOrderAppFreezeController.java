package com.geekerit.springbootalipay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayFundAuthOrderAppFreezeModel;
import com.alipay.api.request.AlipayFundAuthOrderAppFreezeRequest;
import com.alipay.api.response.AlipayFundAuthOrderAppFreezeResponse;
import com.geekerit.springbootalipay.config.AlipayProperties;
import com.geekerit.springbootalipay.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author Aaryn
 */
@Controller
public class FundAuthOrderAppFreezeController {
    private static final Logger logger = LoggerFactory.getLogger(FundAuthOrderAppFreezeController.class);

    @Autowired
    private AlipayClient alipayClient;
    @Autowired
    private AlipayProperties alipayProperties;

    @RequestMapping(value = "/auth",method = RequestMethod.GET)
    public String fundAuthOrderAppFreeze(Model model) throws AlipayApiException {
        AlipayFundAuthOrderAppFreezeRequest request = new AlipayFundAuthOrderAppFreezeRequest();
        AlipayFundAuthOrderAppFreezeModel authModel = new AlipayFundAuthOrderAppFreezeModel();
        authModel.setOrderTitle("支付宝预授权");
        //替换为实际订单号
        authModel.setOutOrderNo("2018077735255938023");
        //替换为实际请求单号，保证每次请求都是唯一的
        authModel.setOutRequestNo(DateUtil.nowTimeString());
        //payee_user_id,Payee_logon_id不能同时为空
        authModel.setPayeeUserId(alipayProperties.getPayeeUserId());
        authModel.setPayeeLogonId("Payee_logon_id");
        //PRE_AUTH_ONLINE为固定值，不要替换
        authModel.setProductCode("PRE_AUTH_ONLINE");
        authModel.setAmount("0.02");
        //需要支持信用授权，该字段必传
        //outStoreAlias将在用户端信用守护、支付信息、账单详情页展示
        authModel.setExtraParam("{\"category\":\"POSTPAY_CLOTHING\",\"outStoreCode\":\"charge001\",\"outStoreAlias\":\"信用免押测试\"}");
        //选填字段，指定支付渠道
        //authModel.setEnablePayChannels("[{\"payChannelType\":\"PCREDIT_PAY\"},{\"payChannelType\":\"MONEY_FUND\"},{\"payChannelType\":\"CREDITZHIMA\"}]");
        request.setBizModel(authModel);
        //异步通知地址，必填，该接口只通过该参数进行异步通知
        request.setNotifyUrl(alipayProperties.getNotifyUrl());
        //注意这里是sdkExecute，可以获取签名参数
        AlipayFundAuthOrderAppFreezeResponse response = alipayClient.sdkExecute(request);
        if (response.isSuccess()) {
            System.out.println("调用成功");
            //签名后的参数，直接入参到
            logger.info("response: {}" + response.getBody());
            model.addAttribute("orderStr",response.getBody());
        } else {
            System.out.println("调用失败");
        }

        return "authTradePay";
    }
}
