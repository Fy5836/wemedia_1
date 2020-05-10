package com.wemedia.service;

import com.wemedia.model.BizDislike;

public interface BizDislikeService extends BaseService<BizDislike> {
    BizDislike checkDislike(Integer disId, String userIp);
}
