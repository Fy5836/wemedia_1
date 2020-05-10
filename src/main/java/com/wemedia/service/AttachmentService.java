package com.wemedia.service;

import com.wemedia.model.Attachment;
import com.wemedia.model.BizArticle;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public interface AttachmentService extends BaseService<Attachment> {

    /**
     * 上传文章附件信息
     * @param file
     * @param bizArticle
     * @return
     */
    Attachment upload(MultipartFile file, BizArticle bizArticle) throws IOException;

    /**
     * 保存附件
     * @param filePath
     * @param bizArticle
     */
    boolean saveAttachment(MultipartFile filePath, BizArticle bizArticle) throws IOException;

    /**
     * 写文件 方法
     *
     * @param response
     * @param file
     * @throws IOException
     */
   void writefile(HttpServletResponse response, File file);
}
