package com.wemedia.mapper;

import com.wemedia.model.BizDislike;
import com.wemedia.model.BizLove;
import com.wemedia.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BizDislikeMapper extends MyMapper<BizDislike>{
    BizDislike checkDislike(@Param("disId") Integer disId, @Param("userIp") String userIp);
}