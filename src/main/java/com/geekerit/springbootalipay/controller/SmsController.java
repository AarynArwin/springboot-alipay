package com.geekerit.springbootalipay.controller;

import com.geekerit.springbootalipay.utils.Msg;
import com.geekerit.springbootalipay.utils.SmsUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aaryn
 */
@RestController
public class SmsController {

    @RequestMapping(value = "/message",method = RequestMethod.POST)
    @ApiOperation(value = "发送短信测试")
    public String sendMsgTest(@RequestBody Msg msg){
        String flag = "";
        try {
            flag = SmsUtil.sendSms(msg);
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
}
