package com.wemedia.service;

import com.wemedia.model.BizLove;


public interface BizLoveService extends BaseService<BizLove> {
    BizLove checkLove(Integer bizId, String userIp);
}
