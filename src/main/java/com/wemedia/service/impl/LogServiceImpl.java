package com.wemedia.service.impl;

import com.wemedia.mapper.LogMapper;
import com.wemedia.model.Log;
import com.wemedia.model.User;
import com.wemedia.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper logMapper;
    @Override
    public void addLog(User user, String ip, String type) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String now = simpleDateFormat.format(new Date());
        Log log = new Log();
        log.setUsername(user.getUsername());
        log.setIp(ip);
        log.setType(type);
        log.setTime(now);
        logMapper.insert(log);
    }
}

