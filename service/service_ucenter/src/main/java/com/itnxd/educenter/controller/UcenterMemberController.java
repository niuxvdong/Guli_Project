package com.itnxd.educenter.controller;

import com.itnxd.commonutils.JwtUtils;
import com.itnxd.commonutils.R;
import com.itnxd.educenter.entity.UcenterMember;
import com.itnxd.educenter.entity.vo.RegisterVo;
import com.itnxd.commonutils.UcenterMemberVo;
import com.itnxd.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author ITNXD
 * @since 2021-11-10
 */
@CrossOrigin
//@RestController
@RequestMapping("/educenter/member")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    // 登录
    @PostMapping("/login")
    public R login(@RequestBody UcenterMember member){
        // 返回token，使用jwt生成
        String token = memberService.login(member);
        return R.ok().data("token", token);
    }

    // 注册
    @PostMapping("/register")
    public R register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    // 根据登录后生成的token获取用户信息用于页面的状态显示
    @GetMapping("/getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        // 使用jwt工具类从token获取
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        // 调用数据库通过id查具体信息
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("userInfo", member);
    }

    //根据用户id查询用户信息(评论模块和支付模块都会用到)
    @PostMapping("/getMemberInfoById/{memberId}")
    public UcenterMemberVo getMemberInfoById(@PathVariable String memberId){
        UcenterMember member = memberService.getById(memberId);
        UcenterMemberVo memberVo = new UcenterMemberVo();
        BeanUtils.copyProperties(member, memberVo);
        return memberVo;
    }

    // 查询某一天的注册人数
    @GetMapping("/countRegister/{day}")
    public R countRegister(@PathVariable String day){

        Integer count = memberService.countRegisterDay(day);

        return R.ok().data("countRegister", count);
    }
}

