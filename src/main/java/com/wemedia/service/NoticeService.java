package com.wemedia.service;

import com.wemedia.model.BizLink;
import com.wemedia.model.Notice;

import java.util.List;

public interface NoticeService extends BaseService<Notice> {

    List<Notice> selectNotices(Notice notice);


}
