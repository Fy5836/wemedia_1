package com.wemedia.service.impl;

import com.wemedia.mapper.BizLinkMapper;
import com.wemedia.model.BizLink;
import com.wemedia.service.BizLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BizLinkServiceImpl extends BaseServiceImpl<BizLink> implements BizLinkService {
    @Autowired
    private BizLinkMapper linkMapper;
    @Override
    public List<BizLink>  selectLinks(BizLink bizLink) {
        return linkMapper.selectLinks(bizLink);
    }

    @Override
    public int deleteBatch(Integer[] ids) {
        return linkMapper.deleteBatch(ids);
    }

}
