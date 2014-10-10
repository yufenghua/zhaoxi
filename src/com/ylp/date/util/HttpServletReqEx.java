package com.ylp.date.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.ylp.date.controller.ControlUtil;
import com.ylp.date.login.Login;
import com.ylp.date.mgr.user.impl.User;
import com.ylp.date.server.Server;

public class HttpServletReqEx implements HttpServletRequest {
	private HttpServletRequest req;
	private Map<String, String> params = new HashMap<String, String>();
	private List<FileItem> items;

	public HttpServletReqEx(HttpServletRequest req) {
		this.req = req;
		if (ServletFileUpload.isMultipartContent(req)) {
			FileItemFactory factory = new DiskFileItemFactory();// 为该请求创建一个DiskFileItemFactory对象，通过它来解析请求。执行解析后，所有的表单项目都保存在一个List中。
			ServletFileUpload upload = new ServletFileUpload(factory);
			try {
				items = upload.parseRequest(req);
			} catch (FileUploadException e) {
				Server.getInstance().handleException(e);
			}
			Iterator<FileItem> itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				// 检查当前项目是普通表单项目还是上传文件。
				if (item.isFormField()) {// 如果是普通表单项目，显示表单内容。
					params.put(item.getFieldName(), item.getString());
				}
			}
		} else {
			Enumeration<String> parameterNames = req.getParameterNames();
			while (parameterNames.hasMoreElements()) {
				String string = (String) parameterNames.nextElement();
				params.put(string, req.getParameter(string));
			}
		}
	}

	public List<FileItem> getItems() {
		if (items == null) {
			return null;
		}
		return Collections.unmodifiableList(items);
	}

	@Override
	public AsyncContext getAsyncContext() {
		return req.getAsyncContext();
	}

	@Override
	public Object getAttribute(String arg0) {
		return req.getAttribute(arg0);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return req.getAttributeNames();
	}

	@Override
	public String getCharacterEncoding() {
		return req.getCharacterEncoding();
	}

	@Override
	public int getContentLength() {
		return req.getContentLength();
	}

	@Override
	public String getContentType() {
		return req.getContentType();
	}

	@Override
	public DispatcherType getDispatcherType() {
		return req.getDispatcherType();
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return req.getInputStream();
	}

	@Override
	public String getLocalAddr() {
		// TODO Auto-generated method stub
		return req.getLocalAddr();
	}

	@Override
	public String getLocalName() {
		// TODO Auto-generated method stub
		return req.getLocalName();
	}

	@Override
	public int getLocalPort() {
		// TODO Auto-generated method stub
		return req.getLocalPort();
	}

	@Override
	public Locale getLocale() {
		// TODO Auto-generated method stub
		return req.getLocale();
	}

	@Override
	public Enumeration<Locale> getLocales() {
		// TODO Auto-generated method stub
		return req.getLocales();
	}

	@Override
	public String getParameter(String arg0) {
		// TODO Auto-generated method stub
		return params.get(arg0);
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		// TODO Auto-generated method stub
		return req.getParameterMap();
	}

	@Override
	public Enumeration<String> getParameterNames() {
		// TODO Auto-generated method stub
		return req.getParameterNames();
	}

	@Override
	public String[] getParameterValues(String arg0) {
		// TODO Auto-generated method stub
		return req.getParameterValues(arg0);
	}

	@Override
	public String getProtocol() {
		// TODO Auto-generated method stub
		return req.getProtocol();
	}

	@Override
	public BufferedReader getReader() throws IOException {
		// TODO Auto-generated method stub
		return req.getReader();
	}

	@Override
	public String getRealPath(String arg0) {
		// TODO Auto-generated method stub
		return req.getRealPath(arg0);
	}

	@Override
	public String getRemoteAddr() {
		// TODO Auto-generated method stub
		return req.getRemoteAddr();
	}

	@Override
	public String getRemoteHost() {
		// TODO Auto-generated method stub
		return req.getRemoteHost();
	}

	@Override
	public int getRemotePort() {
		// TODO Auto-generated method stub
		return req.getRemotePort();
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String arg0) {
		// TODO Auto-generated method stub
		return req.getRequestDispatcher(arg0);
	}

	@Override
	public String getScheme() {
		// TODO Auto-generated method stub
		return req.getScheme();
	}

	@Override
	public String getServerName() {
		// TODO Auto-generated method stub
		return req.getServerName();
	}

	@Override
	public int getServerPort() {
		// TODO Auto-generated method stub
		return req.getServerPort();
	}

	@Override
	public ServletContext getServletContext() {
		// TODO Auto-generated method stub
		return req.getServletContext();
	}

	@Override
	public boolean isAsyncStarted() {
		// TODO Auto-generated method stub
		return req.isAsyncStarted();
	}

	@Override
	public boolean isAsyncSupported() {
		// TODO Auto-generated method stub
		return req.isAsyncSupported();
	}

	@Override
	public boolean isSecure() {
		// TODO Auto-generated method stub
		return req.isSecure();
	}

	@Override
	public void removeAttribute(String arg0) {
		req.removeAttribute(arg0);

	}

	@Override
	public void setAttribute(String arg0, Object arg1) {
		req.setAttribute(arg0, arg1);

	}

	@Override
	public void setCharacterEncoding(String arg0)
			throws UnsupportedEncodingException {
		req.setCharacterEncoding(arg0);

	}

	@Override
	public AsyncContext startAsync() {
		// TODO Auto-generated method stub
		return req.startAsync();
	}

	@Override
	public AsyncContext startAsync(ServletRequest arg0, ServletResponse arg1) {
		// TODO Auto-generated method stub
		return req.startAsync(arg0, arg1);
	}

	@Override
	public boolean authenticate(HttpServletResponse arg0) throws IOException,
			ServletException {
		// TODO Auto-generated method stub
		return req.authenticate(arg0);
	}

	@Override
	public String getAuthType() {
		// TODO Auto-generated method stub
		return req.getAuthType();
	}

	@Override
	public String getContextPath() {
		// TODO Auto-generated method stub
		return req.getContextPath();
	}

	@Override
	public Cookie[] getCookies() {
		// TODO Auto-generated method stub
		return req.getCookies();
	}

	@Override
	public long getDateHeader(String arg0) {
		// TODO Auto-generated method stub
		return req.getDateHeader(arg0);
	}

	@Override
	public String getHeader(String arg0) {
		// TODO Auto-generated method stub
		return req.getHeader(arg0);
	}

	@Override
	public Enumeration<String> getHeaderNames() {
		// TODO Auto-generated method stub
		return req.getHeaderNames();
	}

	@Override
	public Enumeration<String> getHeaders(String arg0) {
		// TODO Auto-generated method stub
		return req.getHeaders(arg0);
	}

	@Override
	public int getIntHeader(String arg0) {
		// TODO Auto-generated method stub
		return req.getIntHeader(arg0);
	}

	@Override
	public String getMethod() {
		// TODO Auto-generated method stub
		return req.getMethod();
	}

	@Override
	public Part getPart(String arg0) throws IOException, IllegalStateException,
			ServletException {
		// TODO Auto-generated method stub
		return req.getPart(arg0);
	}

	@Override
	public Collection<Part> getParts() throws IOException,
			IllegalStateException, ServletException {
		// TODO Auto-generated method stub
		return req.getParts();
	}

	@Override
	public String getPathInfo() {
		// TODO Auto-generated method stub
		return req.getPathInfo();
	}

	@Override
	public String getPathTranslated() {
		// TODO Auto-generated method stub
		return req.getPathTranslated();
	}

	@Override
	public String getQueryString() {
		// TODO Auto-generated method stub
		return req.getQueryString();
	}

	@Override
	public String getRemoteUser() {
		// TODO Auto-generated method stub
		return req.getRemoteUser();
	}

	@Override
	public String getRequestURI() {
		// TODO Auto-generated method stub
		return req.getRequestURI();
	}

	@Override
	public StringBuffer getRequestURL() {
		// TODO Auto-generated method stub
		return req.getRequestURL();
	}

	@Override
	public String getRequestedSessionId() {
		// TODO Auto-generated method stub
		return req.getRequestedSessionId();
	}

	@Override
	public String getServletPath() {
		// TODO Auto-generated method stub
		return req.getServletPath();
	}

	@Override
	public HttpSession getSession() {
		// TODO Auto-generated method stub
		return req.getSession();
	}

	@Override
	public HttpSession getSession(boolean arg0) {
		// TODO Auto-generated method stub
		return req.getSession(arg0);
	}

	@Override
	public Principal getUserPrincipal() {
		// TODO Auto-generated method stub
		return req.getUserPrincipal();
	}

	@Override
	public boolean isRequestedSessionIdFromCookie() {
		// TODO Auto-generated method stub
		return req.isRequestedSessionIdFromCookie();
	}

	@Override
	public boolean isRequestedSessionIdFromURL() {
		// TODO Auto-generated method stub
		return req.isRequestedSessionIdFromURL();
	}

	@Override
	public boolean isRequestedSessionIdFromUrl() {
		// TODO Auto-generated method stub
		return req.isRequestedSessionIdFromUrl();
	}

	@Override
	public boolean isRequestedSessionIdValid() {
		// TODO Auto-generated method stub
		return req.isRequestedSessionIdValid();
	}

	@Override
	public boolean isUserInRole(String arg0) {
		// TODO Auto-generated method stub
		return req.isUserInRole(arg0);
	}

	@Override
	public void login(String arg0, String arg1) throws ServletException {
		// TODO Auto-generated method stub
		req.login(arg0, arg1);
	}

	@Override
	public void logout() throws ServletException {
		// TODO Auto-generated method stub
		req.logout();
	}

}
