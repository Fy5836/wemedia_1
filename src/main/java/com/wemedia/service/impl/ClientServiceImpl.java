package com.wemedia.service.impl;

import com.wemedia.mapper.ClientMapper;
import com.wemedia.model.Client;
import com.wemedia.model.User;
import com.wemedia.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientMapper clientMapper;

    @Override
    public Client clientLogin(String username, String password) {

        return clientMapper.clientLogin(username, password);
    }

    @Override
    public Client selectByUsername(String username) {
        return null;
    }

    @Override
    public int register(Client user) {
        return 0;
    }

    @Override
    public void updateLastLoginTime(Client user) {

    }

    @Override
    public List<User> selectUsers(Client user) {
        return null;
    }

    @Override
    public Client selectByUserId(String userId) {
        return null;
    }

    @Override
    public int updateByUserId(Client user) {
        return 0;
    }

    @Override
    public int updateStatusBatch(List<String> userIds, Integer status) {
        return 0;
    }
}