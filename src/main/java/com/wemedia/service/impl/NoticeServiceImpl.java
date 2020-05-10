package com.wemedia.service.impl;

import com.wemedia.mapper.BizLinkMapper;
import com.wemedia.mapper.NoticeMapper;
import com.wemedia.model.BizLink;
import com.wemedia.model.Notice;
import com.wemedia.service.BizLinkService;
import com.wemedia.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl extends BaseServiceImpl<Notice> implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;
    @Override
    public List<Notice>  selectNotices(Notice notice) {
        return noticeMapper.selectNotices(notice);
    }
}
