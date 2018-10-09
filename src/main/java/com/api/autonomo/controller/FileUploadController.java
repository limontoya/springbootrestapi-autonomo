package com.api.autonomo.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {
	
	private static String UPLOAD_DIR;
	
	public FileUploadController() {
		UPLOAD_DIR = "uploads";
	}
	
	/**
	 * Uploads the file sent and makes a copy in Server's hard drive using _saveFile_ method
	 * @param file File to be uploaded
	 * @param request Http Servlet request
	 * @return
	 */
	public String upload(MultipartFile file, HttpServletRequest request) {
		try {
			String fileName = file.getOriginalFilename();
			
			String path = request.getServletContext().getRealPath("") + UPLOAD_DIR + File.separator + fileName;
			
			saveFile(file.getInputStream(), path);
			
			return path;
			
		}catch (Exception e) {
			return e.getMessage();
		}
	}
	
	/**
	 * Copies the uploaded file into the Server's context path 
	 * @param inputStream
	 * @param path
	 */
	private void saveFile(InputStream inputStream, String path) {
		try {
			OutputStream outputStream = new FileOutputStream(new File(path));
			
			int len = 0;
			byte [] bytes = new byte[1024];
			
			while ((len = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, len);
			}
			
			outputStream.flush();
			outputStream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
