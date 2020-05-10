package com.wemedia.service.impl;

import com.wemedia.mapper.BizDislikeMapper;
import com.wemedia.model.BizDislike;
import com.wemedia.service.BizDislikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BizDislikeServiceImpl extends BaseServiceImpl<BizDislike> implements BizDislikeService {

    @Autowired
    private BizDislikeMapper dislikeMapper;
    @Override
    public BizDislike checkDislike(Integer disId, String userIp) {
        return dislikeMapper.checkDislike(disId, userIp) ;
    }
}
