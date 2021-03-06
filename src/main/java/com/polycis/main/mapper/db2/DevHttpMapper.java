package com.polycis.main.mapper.db2;

import com.baomidou.mybatisplus.annotations.DataSource;
import com.polycis.main.entity.db2.DevHttp;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qiaokai
 * @since 2019-04-22
 */
@Mapper
@DataSource(name="db2")
public interface DevHttpMapper extends BaseMapper<DevHttp> {

}
