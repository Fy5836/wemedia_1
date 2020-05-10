package com.wemedia.service;

import com.wemedia.model.Client;
import com.wemedia.model.User;
import java.util.List;

public interface ClientService {

    /**
     * 根据用户名查询用户
     * @param username
     * @return user
     */
    Client selectByUsername(String username);

    /**
     * 根据用户名查询用户
     * @param username
     * @param password
     * @return Client
     */
    Client clientLogin(String username, String password);

    /**
     * 注册用户
     * @param user
     * @return int
     */
    int register(Client user);

    /**
     * 更新最后登录时间
     * @param user
     */
    void updateLastLoginTime(Client user);

    /**
     * 根据条件查询用户列表
     * @param user
     * @return list
     */
    List<User> selectUsers(Client user);

    /**
     * 根据用户id查询用户
     * @param userId
     * @return Client
     */
    Client selectByUserId(String userId);

    /**
     * 根据用户id更新用户信息
     * @param user
     * @return int
     */
    int updateByUserId(Client user);

    /**
     * 根据用户id集合批量更新用户状态
     * @param userIds
     * @param status
     * @return int
     */
    int updateStatusBatch(List<String> userIds, Integer status);

}
