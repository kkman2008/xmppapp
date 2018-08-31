package com.jzh.news.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public UploadServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Start to receive file...");
		RequestContext req = new ServletRequestContext(request);
		if (FileUpload.isMultipartContent(req)) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload fileUpload = new ServletFileUpload(factory);
			fileUpload.setFileSizeMax(1024 * 1024 * 1024);

			List items = new ArrayList();
			try {
				items = fileUpload.parseRequest(request);
			} catch (Exception e) {
				e.printStackTrace(); 
			}

			Iterator it = items.iterator();
			while (it.hasNext()) {
				FileItem fileItem = (FileItem) it.next();
				if (fileItem.isFormField()) {
					System.out.println(fileItem.getFieldName() + " " + fileItem.getName() + " " + new String(fileItem.getString().getBytes("ISO-8859-1"), "GBK"));
				} else {
					System.out.println(fileItem.getFieldName() + " " + fileItem.getName() + " " + fileItem.isInMemory() + " " + fileItem.getContentType() + " " + fileItem.getSize());
					if (fileItem.getName() != null && fileItem.getSize() != 0) {
						File fullFile = new File(fileItem.getName());
						File newFile = new File("d:\\upload\\" + fullFile.getName());
						try {
							fileItem.write(newFile);
							System.out.println("File uploaded...");
						} catch (Exception e) {
							e.printStackTrace(); 
						}
					} else {
						System.out.println("no file choosen or empty file");
					}
				}
			}
		}
	}

}
