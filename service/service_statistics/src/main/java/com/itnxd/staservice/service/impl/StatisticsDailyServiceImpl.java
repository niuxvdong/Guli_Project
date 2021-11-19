package com.itnxd.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itnxd.commonutils.R;
import com.itnxd.commonutils.RandomUtil;
import com.itnxd.staservice.client.UcenterClient;
import com.itnxd.staservice.entity.StatisticsDaily;
import com.itnxd.staservice.mapper.StatisticsDailyMapper;
import com.itnxd.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author ITNXD
 * @since 2021-11-17
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void registerCount(String day) {

        // 添加统计记录前，先将日期相同的记录删掉
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper);

        // 远程调用得到某一天注册人数
        R res = ucenterClient.countRegister(day);
        Integer countRegister = (Integer) res.getData().get("countRegister");

        // 将获取的数据添加到数据库中
        StatisticsDaily daily = new StatisticsDaily();
        daily.setRegisterNum(countRegister); // 注册人数
        daily.setDateCalculated(day); // 统计日期
        // 其他数据模拟即可
        daily.setVideoViewNum(RandomUtils.nextInt(100, 300));
        daily.setLoginNum(RandomUtils.nextInt(100, 300));
        daily.setCourseNum(RandomUtils.nextInt(100, 300));

        baseMapper.insert(daily);
    }

    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", begin, end);

        wrapper.select("date_calculated", type); // 只需要查询横纵两个数据即可

        List<StatisticsDaily> list = baseMapper.selectList(wrapper);

        // 将两部分数据转换为list集合便于封装为json数组
        List<String> date_calculatedList = new ArrayList<>();
        List<Integer> typeNumList = new ArrayList<>();

        for (StatisticsDaily sta : list) {
            date_calculatedList.add(sta.getDateCalculated());
            switch (type) {
                case "register_num":
                    typeNumList.add(sta.getRegisterNum());
                    break;
                case "login_num":
                    typeNumList.add(sta.getLoginNum());
                    break;
                case "video_view_num":
                    typeNumList.add(sta.getVideoViewNum());
                    break;
                case "course_num":
                    typeNumList.add(sta.getCourseNum());
                    break;
                default:
                    break;
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("date_calculatedList", date_calculatedList);
        map.put("typeNumList", typeNumList);
        return map;
    }
}
