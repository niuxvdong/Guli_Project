package com.itnxd.eduorder.client;

import com.itnxd.commonutils.UcenterMemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author ITNXD
 * @create 2021-11-15 15:15
 */
@Component
@FeignClient(value = "service-ucenter")
public interface UcenterClient {

    //根据用户id查询用户信息(评论模块和支付模块都会用到)
    @PostMapping("/educenter/member/getMemberInfoById/{memberId}")
    public UcenterMemberVo getMemberInfoById(@PathVariable("memberId") String memberId);
}
