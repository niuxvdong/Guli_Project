package com.itnxd.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itnxd.commonutils.JwtUtils;
import com.itnxd.commonutils.MD5;
import com.itnxd.educenter.entity.UcenterMember;
import com.itnxd.educenter.entity.vo.RegisterVo;
import com.itnxd.educenter.mapper.UcenterMemberMapper;
import com.itnxd.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itnxd.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author ITNXD
 * @since 2021-11-10
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String login(UcenterMember member) {

        // 获取手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "登录失败！");
        }
        // 判断手机号
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        if (StringUtils.isEmpty(ucenterMember)) {
            throw new GuliException(20001, "登录失败！");
        }
        // 判断密码(需要md5加密后再和数据库比对，md5加密不可逆，无法解密，除非暴力破解)
        if(!MD5.encrypt(password).equals(ucenterMember.getPassword())){
            throw new GuliException(20001, "登录失败！");
        }
        // 判断用户是否禁用
        if(ucenterMember.getIsDisabled()){
            throw new GuliException(20001, "登录失败！");
        }
        // 登录成功，返回token
        return JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
    }

    // 还是使用redis对验证码进行处理
    @Override
    public void register(RegisterVo registerVo) {
        // 获取注册数据
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        // 非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(code)) {
            throw new GuliException(20001, "注册失败！");
        }
        // 验证码判断
        String redisCode = stringRedisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)){
            throw new GuliException(20001, "注册失败！");
        }
        // 判断手机号是否注册
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0){
            throw new GuliException(20001, "注册失败！");
        }

        UcenterMember member = new UcenterMember();
        member.setMobile(mobile).setNickname(nickname)
                .setPassword(MD5.encrypt(password)).setIsDisabled(false)
                .setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(member);
    }

    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
