package com.wemedia.mapper;

import com.wemedia.model.Notice;
import com.wemedia.model.User;
import com.wemedia.util.MyMapper;
import org.aspectj.weaver.ast.Not;

import java.util.List;
import java.util.Map;

public interface NoticeMapper extends MyMapper<Notice> {
    /**
     * 根据公告参数查询公告列表
     * @param notice
     * @return list
     */
    List<Notice> selectNotices(Notice notice);

    /**
     * 显示最近一周的公告信息
     */
    List<Notice> NoticesForWeek();


    /**
     * 根据公告ID查询用户
     * @param id
     * @return notice
     */
    Notice selectByNoticeId(Integer id);


    /**
     * 根据公告id更新用户信息
     * @param notice
     * @return int
     */
    int updateByNoticeId(Notice notice);
}
