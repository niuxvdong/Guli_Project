package com.itnxd.educenter.service;

import com.itnxd.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itnxd.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author ITNXD
 * @since 2021-11-10
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    void register(RegisterVo registerVo);

    UcenterMember getOpenIdMember(String openid);

    Integer countRegisterDay(String day);
}
