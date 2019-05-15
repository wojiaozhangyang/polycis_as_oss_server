package com.polycis.main.service.db3.impl;

import com.polycis.main.common.ApiResult;
import com.polycis.main.common.page.RequestVO;
import com.polycis.main.entity.db3.DevDataWarn;
import com.polycis.main.mapper.db3.DevDataWarnMapper;
import com.polycis.main.service.db3.IDevDataWarnService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qiaokai
 * @since 2019-04-25
 */
@Service
public class DevDataWarnServiceImpl extends ServiceImpl<DevDataWarnMapper, DevDataWarn> implements IDevDataWarnService {
    @Resource
    private DevDataWarnMapper warnMapper;

    @Override
    public ApiResult<Map<String, Object>> selectWarnInfo(RequestVO param) {
        int total = warnMapper.selectWarnInfoCount(param);
        List<Map<String,Object>> data = warnMapper.selectWarnInfo(param);
        Map<String,Object> map = new HashMap<>(16);
        map.put("list",data);
        map.put("total",total);
        ApiResult<Map<String,Object>> api = new ApiResult<>();
        api.setCode(10000);
        api.setData(map);
        return api;
    }

    @Override
    public ApiResult updateWarnRead(RequestVO param) {
        int size = warnMapper.updateWarnRead(param.getData());
        ApiResult api = new ApiResult();
        api.setCode(10000);
        api.setMsg("成功标记"+ size +"条数据");
        return api;
    }
}
