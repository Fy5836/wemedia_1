package com.wemedia.service.impl;

import com.github.pagehelper.util.StringUtil;
import com.wemedia.mapper.AttachmentMapper;
import com.wemedia.model.Attachment;
import com.wemedia.model.BizArticle;
import com.wemedia.service.AttachmentService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class AttachmentServiceImpl extends BaseServiceImpl<Attachment> implements AttachmentService {

    Logger logger = LoggerFactory.getLogger(AttachmentServiceImpl.class);
    @Autowired
    private AttachmentMapper attachmentMapper;

    private String rootpath = "D:/images/";

    @Override
    public Attachment upload(MultipartFile file, BizArticle bizArticle) throws IllegalStateException, IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM");
        File root = new File(rootpath, simpleDateFormat.format(new Date()));
        File savepath = new File(root, bizArticle.getTitle());

        if (!savepath.exists()) {
            savepath.mkdirs();
        }
        String fileName = file.getOriginalFilename();
        if (!StringUtil.isEmpty(fileName)) {
            String suffix = FilenameUtils.getExtension(fileName);
            String newFileName = UUID.randomUUID().toString().toLowerCase() + "." + suffix;
            File targetFile = new File(savepath, newFileName);
            file.transferTo(targetFile);
            Attachment attachment = new Attachment();
            attachment.setAttachmentName(file.getOriginalFilename());
            attachment.setAttachmentPath(targetFile.getAbsolutePath().replace("\\", "/").replace(rootpath, ""));
            attachment.setAttachmentShuffix(suffix);
            attachment.setAttachmentSize(file.getSize());
            attachment.setAttachmentType(file.getContentType());
            attachment.setUploadTime(new Date());
            attachment.setArticleId(bizArticle.getId());
            return attachment;
        }
        return null;
    }

    public boolean saveAttachment(MultipartFile filePath, BizArticle bizArticle) throws IOException {

        Attachment attachment = attachmentMapper.selectByArticleId(bizArticle.getId());
        if ("".equals(attachment) || attachment == null) {
            logger.info("添加新的附件信息");
            attachment = upload(filePath, bizArticle);
            attachmentMapper.insert(attachment);
            return true;
        } else {
            attachmentMapper.delete(attachment);
            logger.info("删除原附件信息，添加新的附件");
            attachment = upload(filePath, bizArticle);
            attachmentMapper.insert(attachment);
            return false;
        }
    }

    /**
     * 写文件 方法
     *
     * @param response
     * @param file
     * @throws IOException
     */
    public void writefile(HttpServletResponse response, File file) {
        ServletOutputStream sos = null;
        FileInputStream aa = null;
        try {
            aa = new FileInputStream(file);
            sos = response.getOutputStream();
            // 读取文件字节码
            byte[] data = new byte[(int) file.length()];
            IOUtils.readFully(aa, data);
            // 将文件流输出到浏览器
            IOUtils.write(data, sos);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                sos.close();
                aa.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
