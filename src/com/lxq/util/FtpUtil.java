package com.lxq.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FtpUtil {

	FTPClient ftp = new FTPClient();
	private String server;
	private int port;
	private String userName;
	private String userPassword;
	private int fileType;

	public FtpUtil(){
	}

	public void getFtpConnection() throws Exception {
		try {
			ftp.connect(server, port);
			int reply = ftp.getReplyCode();
			if (FTPReply.isPositiveCompletion(reply)) {
				if (ftp.login(userName, userPassword)) {
					ftp.setControlEncoding("GBK");
					ftp.setFileType(fileType);
				} else {
					ftp.logout();
					throw new Exception("用户名或密码错误!");
				}
			} else {
				ftp.disconnect();
				throw new Exception("ip或端口错误!");
			}

		} catch (IOException e) {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
					f.printStackTrace();
				}
			}
			throw e;
		}
	}

	public boolean cd(String dir) throws Exception {
		try {
			if (dir.indexOf("/") == 0) {
				ftp.changeWorkingDirectory("/");
				dir = dir.replaceFirst("/", "");
			}
			String[] dirs = dir.split("/");
			for (int i = 0; i < dirs.length; i++) {

				if (!ftp.changeWorkingDirectory(dirs[i])) {
					if(ftp.makeDirectory(dirs[i])){
						ftp.changeWorkingDirectory(dirs[i]);
					}else{
						throw new Exception("创建目录失败!");
					}
				}
			}

		} catch (Exception e) {
			closeFtpConnection();
			throw e;
		}
		return true;
	}

	public void closeFtpConnection() {
		try {
			ftp.logout();
			ftp.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean uploadFile(String localFileName, String serverFileName) throws IOException {

		InputStream input = null;
		try {
			input = new FileInputStream(localFileName);

			return ftp.storeFile(serverFileName, input);// 上传文件

		} catch (FileNotFoundException e1) {
			throw e1;
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean downloadFile(int downloadType, String localFilePath,
			String serverFilePath) {

		boolean reMark = true;
		OutputStream outStream = null;
		try {
			String localFileEncoding = new String(
					localFilePath.getBytes("GBK"), "iso-8859-1");

			String serverPathEncoding = new String(serverFilePath
					.getBytes("GBK"), "iso-8859-1");

			if (downloadType == 0) {// 下载单个文件
				FTPFile[] files = ftp.listFiles(serverPathEncoding);
				if (files.length != 1) {
					System.out.println("没有" + serverPathEncoding + "这个文件");
					return false;
				}
				outStream = new FileOutputStream(localFileEncoding
						+ files[0].getName());
				reMark = ftp.retrieveFile(serverPathEncoding, outStream);

			} else if (downloadType == 1) {// 下载文件夹下的所有文件，不包含文件夹
				String[] s = serverFilePath.split("/");

				for (int i = 1; i < s.length; i++) {
					ftp.changeWorkingDirectory(s[i]);
				}

				String[] files2 = ftp.listNames(serverPathEncoding);

				if (files2 == null) {
					System.out.println(serverPathEncoding + "没有这个路径下没有文件");
					return false;
				}
				for (int i = 0; i < files2.length; i++) {
					File f = new File(localFilePath + "/" + files2[i]);
					outStream = new FileOutputStream(f);
					String name = new String(files2[i].getBytes("GBK"),
							"iso-8859-1");
					// 任意文件下载失败则返回失败
					if (!ftp.retrieveFile(serverPathEncoding + "/" + name,
							outStream)) {
						System.out.println(name + "---下载失败！");
						reMark = false;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			reMark = false;
		} finally {
			try {
				outStream.close();
				ftp.logout();
				if (ftp.isConnected()) {
					try {
						ftp.disconnect();
					} catch (IOException f) {
						reMark = false;
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				reMark = false;
			}
		}
		return reMark;
	}

	public ArrayList getFileList(String path) {
		ArrayList list = new ArrayList();
		try {
			String serverPathEncoding = new String(path.getBytes("GBK"),
					"iso-8859-1");
			FTPFile[] files = ftp.listFiles(serverPathEncoding);// 所有东西
			if (files == null) {
				System.out.println(path + "这个路径下是空的");
			} else {
				for (int i = 0; i < files.length; i++) {

					FTPFile ss = files[i];
					list.add(ss.getName());
				}
			}
		} catch (Exception e) {
			System.out.println("异常");
		}
		return list;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public int getFileType() {
		return fileType;
	}

	public void setFileType(int fileType) {
		this.fileType = fileType;
	}

	
}