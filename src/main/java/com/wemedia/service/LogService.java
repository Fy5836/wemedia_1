package com.wemedia.service;

import com.wemedia.model.User;
import com.wemedia.vo.base.ResponseVo;

import java.util.List;

public interface LogService {

    void addLog(User loginUser,String ip,String type);
}
