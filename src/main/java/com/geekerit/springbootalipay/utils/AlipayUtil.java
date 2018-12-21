package com.geekerit.springbootalipay.utils;

import com.alipay.api.internal.util.AlipaySignature;
import com.geekerit.springbootalipay.config.AlipayProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 验签
 * @author Aaryn
 */
@Component
public class AlipayUtil {

    private static final Logger logger = LoggerFactory.getLogger(AlipayUtil.class);

    @Autowired
    private AlipayProperties alipayProperties;

    public boolean checkSign(HttpServletRequest request) throws Exception{
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

        return verifyResult;
    }
}
