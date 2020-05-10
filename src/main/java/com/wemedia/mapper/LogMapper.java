package com.wemedia.mapper;

import com.wemedia.model.Log;
import com.wemedia.util.MyMapper;

import java.util.List;

public interface LogMapper extends MyMapper<Log> {

    /**
     * 根据user参数查询对应日志操作
     * @param log
     * @return list
     */
    List<Log> selectLog(Log log);

}