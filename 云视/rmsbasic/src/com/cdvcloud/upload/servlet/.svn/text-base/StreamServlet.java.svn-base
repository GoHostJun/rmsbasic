package com.cdvcloud.upload.servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.cdvcloud.upload.config.Configurations;
import com.cdvcloud.upload.util.IoUtil;

/**
 * File reserved servlet, mainly reading the request parameter and its file
 * part, stored it.
 */
public class StreamServlet extends HttpServlet {
	private static final long serialVersionUID = -8619685235661387895L;
	/** when the has increased to 10kb, then flush it to the hard-disk. */
	static final int BUFFER_LENGTH = 10240;
	static final String START_FIELD = "start";
	public static final String CONTENT_RANGE_HEADER = "content-range";
	private static final Logger logger = Logger.getLogger(StreamServlet.class);

	@Override
	public void init() throws ServletException {
	}

	/**
	 * Lookup where's the position of this file?
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doOptions(req, resp);

		final String token = req.getParameter(TokenServlet.TOKEN_FIELD);
		final String size = req.getParameter(TokenServlet.FILE_SIZE_FIELD);
		final String fileName = req.getParameter(TokenServlet.FILE_NAME_FIELD);
		final PrintWriter writer = resp.getWriter();
		JSONObject json = new JSONObject();
		long start = 0;
		boolean success = true;
		String message = "";
		try {
			File f = IoUtil.getTokenedFile(token);
			start = f.length();
			/** file size is 0 bytes. */
			if (token.endsWith("_0") && "0".equals(size) && 0 == start) {
				// f.renameTo(IoUtil.getFile(fileName, null));
				String newFileName = newFileName(fileName, token);
				File dst = IoUtil.getFile(newFileName, token);
				IoUtil.map.remove(token);
				IoUtil.mapFileName.put(token, newFileName);
				dst.delete();
				f.renameTo(dst);
				logger.info("GET方式，token: " + token + ", 原名: " + fileName + "" + ",新名称：" + newFileName + "");
			}
		} catch (FileNotFoundException fne) {
			message = "Error: " + fne.getMessage();
			success = false;
		} finally {
			try {
				if (success)
					json.put(START_FIELD, start);
				json.put(TokenServlet.SUCCESS, success);
				json.put(TokenServlet.MESSAGE, message);
			} catch (JSONException e) {
			}
			writer.write(json.toString());
			IoUtil.close(writer);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doOptions(req, resp);

		final String token = req.getParameter(TokenServlet.TOKEN_FIELD);
		final String fileName = req.getParameter(TokenServlet.FILE_NAME_FIELD);
		Range range = IoUtil.parseRange(req);
		OutputStream out = null;
		InputStream content = null;
		final PrintWriter writer = resp.getWriter();

		JSONObject json = new JSONObject();
		long start = 0;
		boolean success = true;
		String message = "";
		File f = IoUtil.getTokenedFile(token);
		try {
			if (f.length() != range.getFrom()) {
				/** drop this uploaded data */
				throw new StreamException(StreamException.ERROR_FILE_RANGE_START);
			}

			out = new FileOutputStream(f, true);
			content = req.getInputStream();
			int read = 0;
			final byte[] bytes = new byte[BUFFER_LENGTH];
			while ((read = content.read(bytes)) != -1)
				out.write(bytes, 0, read);

			start = f.length();
		} catch (StreamException se) {
			success = StreamException.ERROR_FILE_RANGE_START == se.getCode();
			message = "Code: " + se.getCode();
			logger.error("上传出错:" + message);
		} catch (FileNotFoundException fne) {
			message = "Code: " + StreamException.ERROR_FILE_NOT_EXIST;
			success = false;
			logger.error("上传出错:" + message);
		} catch (IOException io) {
			message = "IO Error: " + io.getMessage();
			success = false;
			logger.error("上传出错:" + message);
		} finally {
			IoUtil.close(out);
			IoUtil.close(content);

			/** rename the file */
			if (range.getSize() == start) {
				/** fix the `renameTo` bug */
				String newFileName = newFileName(fileName, token);
				File dst = IoUtil.getFile(newFileName, token);
				IoUtil.map.remove(token);
				IoUtil.mapFileName.put(token, newFileName);
				dst.delete();
				f.renameTo(dst);
				logger.info("POST方式，token: " + token + ", 原名: " + fileName + "" + ",新名称：" + newFileName + "");

				/** if `STREAM_DELETE_FINISH`, then delete it. */
				if (Configurations.isDeleteFinished()) {
					dst.delete();
				}
			}
			try {
				if (success)
					json.put(START_FIELD, start);
				json.put(TokenServlet.SUCCESS, success);
				json.put(TokenServlet.MESSAGE, message);
			} catch (JSONException e) {
			}

			writer.write(json.toString());
			IoUtil.close(writer);
		}
	}

	/**
	 * 生成新的文件路径，在原文件名后添加token
	 * 
	 * @param fileName
	 *            \xxx\文件名_时间戳.后缀名
	 * @param token
	 * @return
	 */
	public String newFileName(String fileName, String token) {
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		String name = fileName.substring(fileName.lastIndexOf(File.separator) + 1, fileName.lastIndexOf("."));
		String parentFilePath = fileName.substring(0, fileName.lastIndexOf(File.separator) + 1);
		String strTime = "";
		// strTime = "_" + System.currentTimeMillis();
		name = UUID.randomUUID().toString();
		String newFileName = parentFilePath + name + strTime + suffix;
		return newFileName;
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Range,Content-Type");
		resp.setHeader("Access-Control-Allow-Origin", Configurations.getCrossOrigins());
		resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
	}

	@Override
	public void destroy() {
		super.destroy();
	}

}