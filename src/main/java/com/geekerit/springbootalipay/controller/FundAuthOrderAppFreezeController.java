package com.geekerit.springbootalipay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayFundAuthOrderAppFreezeModel;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.request.AlipayFundAuthOrderAppFreezeRequest;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.response.AlipayFundAuthOrderAppFreezeResponse;
import com.alipay.api.response.AlipayTradePayResponse;
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

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public String fundAuthOrderAppFreeze(Model model) throws AlipayApiException {
        AlipayFundAuthOrderAppFreezeRequest request = new AlipayFundAuthOrderAppFreezeRequest();
        AlipayFundAuthOrderAppFreezeModel authModel = new AlipayFundAuthOrderAppFreezeModel();
        authModel.setOrderTitle("支付宝预授权");
        //替换为实际订单号
        authModel.setOutOrderNo(DateUtil.nowTimeString());
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
        authModel.setEnablePayChannels("[{\"payChannelType\":\"CREDITZHIMA\"}]");
        request.setBizModel(authModel);
        //异步通知地址，必填，该接口只通过该参数进行异步通知
        request.setNotifyUrl(alipayProperties.getNotifyUrl());
        //注意这里是sdkExecute，可以获取签名参数
        AlipayFundAuthOrderAppFreezeResponse response = alipayClient.sdkExecute(request);
        if (response.isSuccess()) {
            System.out.println("调用成功");
            //签名后的参数，直接入参到
            logger.info("response: {}" + response.getBody());
            model.addAttribute("orderStr", response.getBody());
        } else {
            System.out.println("调用失败");
        }

        return "authTradePay";
    }


    @RequestMapping(value = "/tradePay",method = RequestMethod.POST)
    public void tradePay() throws AlipayApiException {
        AlipayTradePayRequest request = new AlipayTradePayRequest();

        AlipayTradePayModel model = new AlipayTradePayModel();
        // 预授权转支付商户订单号，为新的商户交易流水号；如果重试发起扣款，商户订单号不要变；
        model.setOutTradeNo("20180412100020088982");
        // 固定值PRE_AUTH_ONLINE
        model.setProductCode("PRE_AUTH_ONLINE");
        // 填写预授权冻结交易号
        model.setAuthNo("2018041210002001660228733635");
        // 解冻转支付标题，用于展示在支付宝账单中
        model.setSubject("预授权转支付测试");
        // 结算支付金额
        model.setTotalAmount("0.01");
        // 填写卖家支付宝账户pid
        model.setSellerId(alipayProperties.getPid());
        // 填写预授权用户uid，通过预授权冻结接口返回的payer_user_id字段获取
        //model.setBuyerId(pay_user_id);
        // 填写实际交易发生的终端编号，与预授权的outStoreCode保持一致即可
        model.setStoreId("test_store_id");
        // 可填写备注信息
        model.setBody("预授权解冻转支付测试");
        //必须使用COMPLETE,传入该值用户剩余金额会自动解冻
        model.setAuthConfirmMode("COMPLETE");
        request.setBizModel(model);
        //异步通知地址，必填，该接口只通过该参数进行异步通知
        request.setNotifyUrl(alipayProperties.getNotifyUrl());

        AlipayTradePayResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            System.out.println("调用成功");
            logger.info("response: {}" + response.getBody());
        } else {
            System.out.println("调用失败");
        }
    }
}
