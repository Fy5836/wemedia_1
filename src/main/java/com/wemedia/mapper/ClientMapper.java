package com.wemedia.mapper;

import com.wemedia.model.Client;
import com.wemedia.model.User;
import com.wemedia.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ClientMapper extends MyMapper<Client> {


    /**
     * 根据user参数查询用户列表
     * @param user
     * @return list
     */
    List<Client> selectUsers(Client user);

    /**
     * 根据用户名查询用户
     * @param username
     * @return Client
     */
    Client selectByUsername(String username);

    /**
     * 用户名密码验证  参数绑定
     * @param username
     * @param password
     * @return Client
     */
    Client clientLogin(@Param("username")String username, @Param("password")String password);
    /**
     * 根据用户ID查询用户
     * @param userId
     * @return user
     */
    Client selectByUserId(String userId);

    /**
     * 更新最后登录时间
     * @param user
     */
    void updateLastLoginTime(Client user);

    /**
     * 根据用户id更新用户信息
     * @param user
     * @return int
     */
    int updateByUserId(Client user);

    /**
     * 根据参数批量修改用户状态
     * @param params
     * @return int
     */
    int updateStatusBatch(Map<String, Object> params);
}