package com.api.autonomo.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class FileDepotProperties {

	private String uploadDirectory;
	private String notAvailable;
	private String contentType;

	public String getUploadDirectory() {
		return uploadDirectory;
	}

	public void setUploadDirectory(String uploadDirectory) {
		this.uploadDirectory = uploadDirectory;
	}

	public String getNotAvailable() {
		return notAvailable;
	}

	public void setNotAvailable(String notAvailable) {
		this.notAvailable = notAvailable;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
