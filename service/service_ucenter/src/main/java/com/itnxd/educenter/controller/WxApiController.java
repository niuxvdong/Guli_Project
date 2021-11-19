package com.itnxd.educenter.controller;

import com.google.gson.Gson;
import com.itnxd.commonutils.JwtUtils;
import com.itnxd.educenter.entity.UcenterMember;
import com.itnxd.educenter.service.UcenterMemberService;
import com.itnxd.educenter.utils.ConstantWxUtils;
import com.itnxd.educenter.utils.HttpClientUtils;
import com.itnxd.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.Query;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author ITNXD
 * @create 2021-11-11 20:47
 */
@CrossOrigin
//@Controller//注意这里没有配置 @RestController
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    private UcenterMemberService memberService;

    // 微信扫码后的请求回调方法
    @GetMapping("/callback")
    public String callback(String code, String state) {
        try {
            // 1. code: 每次都是随机的，类似验证码 state：原样传递
            //System.out.println("code:" + code + "\n state:" + state);

            // 2. 通过code请求微信固定地址，得到access_token,openid
            String baseAccessTokenUrl =
                    "https://api.weixin.qq.com/sns/oauth2/access_token" +
                            "?appid=%s" +
                            "&secret=%s" +
                            "&code=%s" +
                            "&grant_type=authorization_code";
            // 拼接三个参数
            String accessTokenUrl = String.format(baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code);
            // 请求 accessTokenUrl  使用httpclient
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            // 将json转化为 key-value 形式 方便获取 access_token openid
            Gson gson = new Gson();
            HashMap map = gson.fromJson(accessTokenInfo, HashMap.class);
            String accessToken = (String) map.get("access_token");
            String openid = (String) map.get("openid");

            // 4. 将扫码人信息加入数据库
            // 判断数据库有无相同信息
            UcenterMember member = memberService.getOpenIdMember(openid);
            if(member == null){
                // 3. 通过 access_token openid 来请求固定地址获取到用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
                String userInfo = HttpClientUtils.get(userInfoUrl);
                HashMap userMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) userMap.get("nickname");
                String headimgurl = (String) userMap.get("headimgurl");

                member = new UcenterMember();
                member.setOpenid(openid).setNickname(nickname).setAvatar(headimgurl);
                // 保存后实体类对象自动更新相关信息，因此if结束member一定不为空
                memberService.save(member);
            }
            // 使用jwt根据微信信息生成token
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());

            // 5. 回到首页面
            return "redirect:http://localhost:3000?token=" + jwtToken;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "登录失败！");
        }
    }

    // 生成微信二维码
    @GetMapping("/login")
    public String getWxCode() {
        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        // 回调地址
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
        } catch (UnsupportedEncodingException e) {
            throw new GuliException(20001, e.getMessage());
        }
        // 防止csrf攻击（跨站请求伪造攻击）
        //String state = UUID.randomUUID().toString().replaceAll("-", "");//一般情况下会使用一个随机数
        //String state = "imhelen";//为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名
        //System.out.println("state = " + state);
        // 采用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置
        //键："wechar-open-state-" + httpServletRequest.getSession().getId()
        //值：satte
        //过期时间：30分钟

        //生成qrcodeUrl
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "itnxd");
        return "redirect:" + qrcodeUrl;
    }
}
