package com.itnxd.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.itnxd.commonutils.HttpUtils;
import com.itnxd.msmservice.service.MsmService;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ITNXD
 * @create 2021-11-10 14:17
 */
@Service
public class MsmServiceImpl implements MsmService {

    // 阿里云短信发送方法
    public boolean sendTest(String phone, Map<String, Object> param) {
        if(StringUtils.isEmpty(phone)) return false;
        DefaultProfile profile =
                DefaultProfile.getProfile("default", "LTAIq6nIPY09VROj",
                        "FQ7UcixT9wEqMv9F35nORPqKr8XkTF");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", phone);
        // 签名名称
        request.putQueryParameter("SignName", "我的谷粒在线教育网站");
        // 模板code
        request.putQueryParameter("TemplateCode", "SMS_180051135");
        // code转化为json传递
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));
        try {
            // 发送
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 由于阿里云短信需要审核验证，因此直接调用云市场的短信服务！
     * 地址：https://market.aliyun.com/products/57124001/cmapi00037170.html?spm=5176.2020520132.101.2.73737218vNa8ny#sku=yuncode3117000001
     *
     * @param phone 要发送的手机号码
     * @param code 生成的验证码
     * @return
     */
    @Override
    public boolean send(String phone, String code) {

        String host = "https://dfsns.market.alicloudapi.com";
        String path = "/data/send_sms";
        String method = "POST";
        String appcode = "cb39881c201a4782aabd634a95ab7589";

        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);

        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();

        bodys.put("content", "code:"+ code + ",expire_at:5");
        bodys.put("phone_number", phone);
        bodys.put("template_id", "TPL_0001");

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            return true;
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
