package com.itnxd.educms.service.impl;

import com.itnxd.educms.entity.CrmBanner;
import com.itnxd.educms.mapper.CrmBannerMapper;
import com.itnxd.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author ITNXD
 * @since 2021-11-09
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    // 查询所有banner
    @Override
    public List<CrmBanner> selectAllBanner() {
        return baseMapper.selectList(null);
    }
}
