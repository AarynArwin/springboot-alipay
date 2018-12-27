package com.geekerit.springbootalipay.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.geekerit.springbootalipay.enums.SmsRedisExpireEnum;
import com.geekerit.springbootalipay.enums.SmsTypeEnum;
import com.geekerit.springbootalipay.enums.UserGiftMoneyEnum;

/**
 * 短信发送工具类
 *
 * @author Aaryn
 */
public class SmsUtil {

    /**
     * 阿里云短信平台的开发者信息
     */
    private static final String ACCESS_KEY_ID = "LTAIt9PlF9nfkwaX";
    private static final String ACCESS_KEY_SECRET = "WrygkrI5W9w8rGYuiVxEwUQ2TNEYb9";

    /**
     * 产品名称:云通信短信API产品,开发者无需替换
     */
    private static final String PRODUCT = "Dysmsapi";
    /**
     * 产品域名,开发者无需替换
     */
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";
    /**
     * 短信接口连接超时时间
     */
    private static final String TIMEOUT_LIMIT = "10000";
    /**
     * 短信签名(悦心盒子)
     */
    private static final String SIGN_NAME = "悦心盒子";
    /**
     * 短信发送标识（成功）
     */
    private static final String SEND_FLAG_SUCCESS = "success";
    /**
     * 短信发送标识（失败）
     */
    private static final String SEND_FLAG_FAIL = "fail";
    /**
     * 公司客服联系方式
     */
    private static final String CONTACT = "0571-26280363";
    /**
     * 用户昵称设置为空
     */
    private static final String NICKNAME = "";

    private static IAcsClient sendMsgCommon() throws ClientException {
        // HttpURLConnection是基于HTTP协议的，其底层通过socket通信实现。如果不设置超时（timeout），
        // 在网络异常的情况下，可能会导致程序僵死而不继续往下执行。可以通过以下两个语句来设置相应的超时：
        // 可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", TIMEOUT_LIMIT);
        System.setProperty("sun.net.client.defaultReadTimeout", TIMEOUT_LIMIT);

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PRODUCT, DOMAIN);
        return new DefaultAcsClient(profile);
    }

    /**
     * 发送短信
     *
     * @return 发送短信标识（成功 success 失败 fail）
     * @throws ClientException 阿里云短信客户端异常
     */
    public static String sendSms(SmsData smsData) throws ClientException {
        // 参数校验,校验通过时拿到用户的联系方式
        String phone = checkSmsParam(smsData);
        IAcsClient iAcsClient = sendMsgCommon();
        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(SIGN_NAME);
        // 根据短信类型选择不同的参数
        chooseService(smsData, request);
        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = iAcsClient.getAcsResponse(request);
        if (sendSmsResponse != null) {
            return ("OK").equals(sendSmsResponse.getCode()) ? SEND_FLAG_SUCCESS : SEND_FLAG_FAIL;
        }
        return SEND_FLAG_FAIL;
    }

    /**
     * 发送短信时参数校验
     *
     * @param msg 短信发送参数对象
     * @return 用户手机号码
     */
    private static String checkSmsParam(SmsData msg) {
        if (null == msg) {
            throw new DisplayableException("短信参数缺失");
        }
        // 短信类型非空判断
        if (null == msg.getSmsTypeEnum()) {
            throw new DisplayableException("短信类型不能为空");
        }
        // 手机号码的非空判断
        if (null == msg.getPhone() || msg.getPhone().isEmpty() || "".equals(msg.getPhone())) {
            throw new DisplayableException("手机号码不可为空!");
        }
        return msg.getPhone();
    }

    /**
     * 选择短信模板并设置参数
     *
     * @param smsData 短信发送参数
     * @param request 发送短信请求
     * @return 封装后的短信请求
     */
    private static void chooseService(SmsData smsData, SendSmsRequest request) {
        // 短信验证码
        int code = smsData.getCode();
        // 选择相应的短信类型
        switch (smsData.getSmsTypeEnum()) {
            // 悦心盒子登录验证码
            case LOGIN:
                request.setTemplateCode(SmsTypeEnum.LOGIN.getTemplateCode());
                request.setTemplateParam("{\"code\":" + code + "}");
                break;
            // 发送悦心礼金
            case GIFTMONEY_FIRST_LOGIN:
                request.setTemplateCode(SmsTypeEnum.GIFTMONEY_FIRST_LOGIN.getTemplateCode());
                request.setTemplateParam("{\"nickName\":\"" + NICKNAME + "\"}");
                break;
            // 成功邀请好友后得到悦心礼金通知
            case GIFTMONEY_INVITE:
                request.setTemplateCode(SmsTypeEnum.GIFTMONEY_INVITE.getTemplateCode());
                request.setTemplateParam("{\"nickName\":\"" + NICKNAME + "\"}");
                break;
            //  用户注册未成为会员（后台进行提醒）
            case REGIST_NOTMEMBER:
                request.setTemplateCode(SmsTypeEnum.REGIST_NOTMEMBER.getTemplateCode());
                request.setTemplateParam("{\"nickName\":\"" + NICKNAME + "\"}");
                break;
            //  生成支付押金订单未付费
            case REGIST_NOPAY:
                request.setTemplateCode(SmsTypeEnum.REGIST_NOPAY.getTemplateCode());
                request.setTemplateParam("{\"nickName\":\"" + NICKNAME + "\",\"contact\":\"" + CONTACT + "\"}");
                break;
            // 发出盒子前七天提醒用户确认收货地址
            case BOX_CONFIRM_ADDRESS:
                request.setTemplateCode(SmsTypeEnum.BOX_CONFIRM_ADDRESS.getTemplateCode());
                request.setTemplateParam("{\"nickName\":\"" + NICKNAME + "\",\"contact\":\"" + CONTACT + "\"}");
                break;
            // 盒子发出通知用户
            case BOX_EXPRESS_SEND:
                request.setTemplateCode(SmsTypeEnum.BOX_EXPRESS_SEND.getTemplateCode());
                request.setTemplateParam("{\"nickName\":\"" + NICKNAME + "\",\"contact\":\"" + CONTACT + "\"}");
                break;
            // 盒子签收提醒
            case BOX_EXPRESS_SIGN:
                request.setTemplateCode(SmsTypeEnum.BOX_EXPRESS_SIGN.getTemplateCode());
                request.setTemplateParam("{\"nickName\":\"" + NICKNAME + "\",\"contact\":\"" + CONTACT + "\"}");
                break;
            // 盒子支付订单未支付提醒
            case BOX_ORDER_PAY:
                request.setTemplateCode(SmsTypeEnum.BOX_ORDER_PAY.getTemplateCode());
                request.setTemplateParam("{\"nickName\":\"" + NICKNAME + "\",\"contact\":\"" + CONTACT + "\"}");
                break;
            // 提醒用户退回盒子
            case BOX_EXPRESS_BACK:
                request.setTemplateCode(SmsTypeEnum.BOX_EXPRESS_BACK.getTemplateCode());
                request.setTemplateParam("{\"nickName\":\"" + NICKNAME + "\",\"contact\":\"" + CONTACT + "\"}");
                break;
            default:
                break;
        }
    }

    /**
     * 用户登录验证码
     *
     * @param code  生成的验证码
     * @param phone 用户手机号
     * @return 发送短信标识(success 成功 fail 失败)
     * @throws Exception 短信异常
     */
    public static String sendMsgForLogin(int code, String phone) throws Exception {
        SmsData smsData = new SmsData();
        smsData.setSmsTypeEnum(SmsTypeEnum.LOGIN);
        smsData.setPhone(phone);
        smsData.setCode(code);
        return sendSms(smsData);
    }

    /**
     * 发送悦心礼金后的短信提醒
     *
     * @param phone    用户手机号码
     * @param giftType 用户礼金获取类型
     * @throws Exception 短信发送异常
     */
    public static void sendMsgOfGiftMoney(String phone, UserGiftMoneyEnum giftType) throws Exception {
        SmsData smsData = new SmsData();
        smsData.setPhone(phone);
        // 用户首次登陆发送五十元悦心礼金的短信通知
        if (giftType == UserGiftMoneyEnum.LOGIN) {
            smsData.setSmsTypeEnum(SmsTypeEnum.GIFTMONEY_FIRST_LOGIN);
            // 用户成功邀请好友（好友缴纳押金后）
        } else {
            smsData.setSmsTypeEnum(SmsTypeEnum.GIFTMONEY_INVITE);
        }
        sendSms(smsData);
    }

    /**
     * 监控统一过期键前缀
     */
    private static final String REDIS_EXPIRE_KEY = "expire";

    /**
     * 根据redis过期键提醒用户相关业务（短信通知）
     *
     * @param redisKey 已过期的redis的键
     * @throws Exception 短信发送异常
     */
    public void chooseRedisExpire(String redisKey) throws Exception {
        String[] split = redisKey.split(":");
        // 设置的业务监控标识
        String flag = split[0];
        // 不相关的redis的过期键直接过滤掉
        if (!flag.equals(REDIS_EXPIRE_KEY)) {
            return;
        }
        // 短信提醒业务键的类型（区分不同的通知内容）
        String redisType = split[1];
        SmsRedisExpireEnum smsEnum = SmsRedisExpireEnum.getSmsEnum(redisType);

        if (null == smsEnum) {
            throw new DisplayableException("不合法的过期短信提醒，请检查业务类型");
        }
        // 真正的查询条件（一般是用户ID，可以查询出用户的联系方式用于短信提醒）
        String trueKey = split[2];
        // TODO 查询出用户的联系方式后进行短信发送
        //UserBasic userBasic = userBasicService.selectByUserId(Long.valueOf(trueKey));
        //String phone = userBasic.getPhone();
        // System.out.println("短信通知用户的手机号码为==" + phone);
        String smsType = "";

        SmsData smsData = new SmsData();
        // TODO  设置为查询出来的用户手机号码
        smsData.setPhone("");

        // 判断redis的键属于哪种情形，用于区分不同的短信内容
        switch (smsEnum) {
            // 用户押金通知（未成功支付开始计时10分钟，开始提示第一条。
            case ORDER_MEMBER_PAY:
                // TODO 当用户成为会员时不再发送短信
                smsData.setSmsTypeEnum(SmsTypeEnum.REGIST_NOPAY);
                break;
            // 盒子支付通知
            case ORDER_BOX_PAY:
                String boxId = split[3];
                // TODO 获取盒子主键后需要查询盒子的状态，如果盒子支付状态为已完成不再提醒用户
                smsData.setSmsTypeEnum(SmsTypeEnum.BOX_ORDER_PAY);
                break;
            // 用户确认收货地址的通知
            case USER_CONFIRM_ADDRESS:
                smsData.setSmsTypeEnum(SmsTypeEnum.BOX_EXPRESS_BACK);
                break;
            // 通知用户退回盒子的通知
            case USER_BOX_BACK:
                // 取出盒子的编号
                String boxBack = split[3];
                // 设置提醒标识，当标识为true时提醒用户退回盒子
                Boolean callFlag = true;
                /*
                 * TODO 根据盒子编号查询盒子
                 *  1. 首先查询盒子属于测量盒子还是试穿盒子
                 *  2. 判断盒子的状态是否已经是退回状态以及之后的状态
                 *  3. 如果是试穿盒子还需要判断用户是否已经全部留下商品
                 */
                // 短信通知用户进行盒子退还
                if (callFlag) {
                    smsData.setSmsTypeEnum(SmsTypeEnum.BOX_EXPRESS_BACK);
                }
                break;
            default:
                break;
        }
        sendSms(smsData);
    }

}
