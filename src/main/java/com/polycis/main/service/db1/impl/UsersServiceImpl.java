package com.polycis.main.service.db1.impl;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.polycis.main.common.ApiResult;
import com.polycis.main.common.CommonCode;
import com.polycis.main.common.interceptor.RequestHolder;
import com.polycis.main.entity.Users;
import com.polycis.main.entity.admin.OssAdmin;
import com.polycis.main.mapper.db1.UsersMapper;
import com.polycis.main.mapper.db3.DevDataWarnMapper;
import com.polycis.main.service.db1.IUsersService;
import com.polycis.main.client.redis.RedisFeignClient;
import com.polycis.main.service.db3.IMybatisPlusDB3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static com.polycis.main.common.CommonSubCode.ISV_UPLOAD_FAIL;

/*
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2018-11-29
*/
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {


    @Autowired
    private RedisFeignClient redisFeignClient;


/*
     * 查看是否是有此账户
     *
     * @param uss
     * @return
*/
    @Override
    public Users isUser(Users uss) {
        // 是否启用  1启用 0未启用
        uss.setIsDelete(1);
        uss.setIsStart(1);
        List<Users> users = usersMapper.selectList(new EntityWrapper<Users>(uss));
        if (null != users && !users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }

    @Override
    public Users setByUserid(Integer usid) {
        return this.selectById(usid);
    }


    @Override
    public Boolean updataUser(Users us) {
        return this.updataUser(us);
    }

    @Override
    public Page<Users> listuserByPage(int cupage, int cusize, Users us) {


        Page<Users> page = new Page<Users>(cupage, cusize);
        EntityWrapper<Users> wrus = new EntityWrapper<Users>();
        if (null != us.getName()) {
            wrus.like("name", us.getName(), SqlLike.DEFAULT);
            wrus.or().like("loginname", us.getName(), SqlLike.DEFAULT);
        }
        if (us.getOrg() != 1) {
            wrus.and().eq("org", us.getOrg());
        }
        //  wrus.setEntity(new Users());

        wrus.and().eq("is_delete", 1);

        wrus.orderBy("id desc");
        return this.selectPage(page, wrus);
    }


    @Override
    public Boolean checkLoginNameUnique(String loginName) {
        Users users = new Users();
        users.setIsDelete(1);
        users.setLoginname(loginName);
        List<Users> list = usersMapper.selectList(new EntityWrapper<Users>(users));
        if (list != null && list.size() > 0) {
            return false;
        }
        return true;
    }

    @Override
    public Users selectAdminByOrg(Integer orgId) {
        Users users = new Users();
        users.setOrg(orgId);
     //   users.setRole("sys");
        List<Users> list = this.selectList(new EntityWrapper<Users>(users));
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Autowired
    private IMybatisPlusDB3Service iMybatisPlusDB3Service;


    @Autowired
    private UsersMapper  usersMapper;

    @Autowired
    private DevDataWarnMapper devDataWarnMapper;


    /*@Resource(name = "db1tx")
    private DataSourceTransactionManager tx1;

    @Resource(name = "db2tx")
    private DataSourceTransactionManager tx2;
    @Override
    //@CustomTransaction(value={"db1tx","db2tx"})
    // @Transactional
    public void insertTest(Users users) {
        TransactionStatus transactionStatus1=null;
        TransactionStatus transactionStatus2=null;
        try {
            transactionStatus1 = tx1
                    .getTransaction(new DefaultTransactionDefinition());
            usersMapper.insertTest();
            tx1.commit(transactionStatus1);

            transactionStatus2 = tx2
                    .getTransaction(new DefaultTransactionDefinition());

            devDataWarnMapper.insertTest();


            tx2.commit(transactionStatus2);
        } catch (Exception e) {
            tx1.rollback(transactionStatus1);
            tx2.rollback(transactionStatus2);
            e.printStackTrace();
        }
        // iMybatisPlusDB3Service.test();
    }
*/
    @Override
    public void insertTest2(Users users) {
            usersMapper.insertTest();
            devDataWarnMapper.insertTest();
    }


    public static final String WINDOWS_PROFILES_PATH = "C:/super_meeting/profiles/";
    public static final String LINUX_PROFILES_PATH = "/root/super_meeting/profiles/";

    @Override
    public Users selectbytokne(String accessToken) {
        List<Users> users = this.selectList(new EntityWrapper<Users>().eq("token", accessToken));
        if (!users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }
}
