package com.geekerit.springbootalipay;

import com.geekerit.springbootalipay.enums.SmsTypeEnum;
import com.geekerit.springbootalipay.enums.UserGiftMoneyEnum;
import com.geekerit.springbootalipay.utils.SmsUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 测试短信工具类
 * @author Aaryn
 */
class SmsTest {

    /**
     * 用户发送登录验证码
     * @throws Exception
     */
    @Test
    void login() throws Exception{
        String s = SmsUtil.sendMsgForLogin(1234, "17636620930");
        assertEquals("success",s);
    }

    /**
     * 用户首次登陆获得悦心礼金
     * @throws Exception
     */
    @Test
    void giftMoneyForLogin() throws Exception{
        String s = SmsUtil.sendMsgOfGiftMoney("17636620930", UserGiftMoneyEnum.LOGIN);
        assertEquals("success",s);
    }

    /**
     * 成功邀请好友成为付费用户
     * @throws Exception
     */
    @Test
    void gifyMoneyForInvite() throws Exception{
        String s = SmsUtil.sendMsgOfGiftMoney("17636620930", UserGiftMoneyEnum.INVITE);
        assertEquals("success",s);
    }

    /**
     * 盒子发出提醒
     * @throws Exception
     */
    @Test
    void boxSend() throws Exception{
        String s = SmsUtil.sendMsgOfBox("17636620930", SmsTypeEnum.BOX_EXPRESS_SEND);
        assertEquals("success",s);
    }

    /**
     * 盒子签收提醒
     * @throws Exception
     */
    @Test
    void boxSign() throws Exception{
        String s = SmsUtil.sendMsgOfBox("17636620930", SmsTypeEnum.BOX_EXPRESS_SIGN);
        assertEquals("success",s);
    }
}
