package com.api;

import com.api.util.MD5Util;

import java.util.*;
import java.util.Map.Entry;


/**
 * Created by Administrator on 2017/1/4.
 */
public class ApiConfig {
    //api key
    private static final String appId = "wx9d01f11b0af24860";
    //api secret
    private static final String appSecret = "607558697c7cadabf55b32d1728e910b";
    //允许的时间差为5s
    private static final long diffTimestamp = 5;


    /**
     * 验证接口签名
     * @param parameter
     * @param clientSign
     * @return
     */
    public static boolean validateApiSign(SortedMap<Object,Object> parameter, String clientSign){
        //服务器根据秘钥生成签名
        String serverSign = createSign(parameter);
        //比较服务器、客户端签名
        if(serverSign.equals(clientSign)){
            return true;
        }
        return false;
    }

    /**
     * 验证接口请求时间
     * @param timestamp 请求的时间戳
     * @return
     */
    public static boolean validateApiTimestamp(String timestamp){
        //当前时间戳
        Long currentTimestamp = Long.valueOf(createTimestamp());
        //请求时间戳
        Long requestTimestamp = Long.valueOf(timestamp);
        //当前时间戳-请求时间戳
        if(currentTimestamp - requestTimestamp > diffTimestamp){
            return false;
        }
        return true;
    }

    /**
     * 生成签名
     * 生成规则:将请求的参数以key=value&key1=value1的形式进行拼接,最后拼接上秘钥进行MD5加密
     * @param parameters
     * @return
     */
    public static String createSign(SortedMap<Object,Object> parameters){
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        for(Entry<Object,Object> entry : parameters.entrySet()) {
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + appSecret);
        String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
        return sign;
    }

    /**
     * 创建随机字符串
     * @return
     */
    private static String createNonceStr(){
        return UUID.randomUUID().toString();
    }

    /**
     * 创建时间戳
     * @return
     */
    private static String createTimestamp(){
        return Long.toString((System.currentTimeMillis() / 1000));
    }

    public static void main(String[] args) {
        //客户端时间戳
        String clientTimestamp = createTimestamp();
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SortedMap<Object,Object> sortedMap = new TreeMap<Object,Object>();
        sortedMap.put("nonceStr",createNonceStr());
        sortedMap.put("timestamp",clientTimestamp);
        sortedMap.put("appId",appId);
        sortedMap.put("productId","100045");

        //客户端签名
        String clientSign = createSign(sortedMap);

        System.out.println(validateApiTimestamp(clientTimestamp));
        System.out.println(validateApiSign(sortedMap,clientSign));
    }
}
