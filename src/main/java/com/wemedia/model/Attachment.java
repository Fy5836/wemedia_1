package com.wemedia.model;

import javax.persistence.*;
import java.util.Date;

/**
 * 上传附件表
 */
public class Attachment {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long attachmentId; //附件id
	

	private Integer articleId;    //文章ID

	private String attachmentName;  //附件名字
	

	private String attachmentPath;  //附件存储路径
	

	private Long attachmentSize; //附件大小
	

	private String attachmentType;  //附件类型
	

	private Date uploadTime;     //附件上传时间
	
	private String model;          //所属模块
	

	private String attachmentShuffix; //附件后缀

	public Long getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(Long attachmentId) {
		this.attachmentId = attachmentId;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public String getAttachmentPath() {
		return attachmentPath;
	}

	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}

	public Long getAttachmentSize() {
		return attachmentSize;
	}

	public void setAttachmentSize(Long attachmentSize) {
		this.attachmentSize = attachmentSize;
	}

	public String getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getAttachmentShuffix() {
		return attachmentShuffix;
	}

	public void setAttachmentShuffix(String attachmentShuffix) {
		this.attachmentShuffix = attachmentShuffix;
	}

	@Override
	public String toString() {
		return "Attachment{" +
				"attachmentId=" + attachmentId +
				", articleId=" + articleId +
				", attachmentName='" + attachmentName + '\'' +
				", attachmentPath='" + attachmentPath + '\'' +
				", attachmentSize=" + attachmentSize +
				", attachmentType='" + attachmentType + '\'' +
				", uploadTime=" + uploadTime +
				", model='" + model + '\'' +
				", attachmentShuffix='" + attachmentShuffix + '\'' +
				'}';
	}
}
