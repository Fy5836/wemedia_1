package com.wemedia.mapper;

import com.wemedia.model.Attachment;
import com.wemedia.model.Notice;
import com.wemedia.util.MyMapper;

import java.util.List;

public interface AttachmentMapper extends MyMapper<Attachment> {

    /**
     * 根据文章ID查询附件
     * @param id
     * @return notice
     */
    Attachment selectByArticleId(Integer id);

}
