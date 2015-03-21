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
					throw new Exception("�û������������!");
				}
			} else {
				ftp.disconnect();
				throw new Exception("ip��˿ڴ���!");
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
						throw new Exception("����Ŀ¼ʧ��!");
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

			return ftp.storeFile(serverFileName, input);// �ϴ��ļ�

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

			if (downloadType == 0) {// ���ص����ļ�
				FTPFile[] files = ftp.listFiles(serverPathEncoding);
				if (files.length != 1) {
					System.out.println("û��" + serverPathEncoding + "����ļ�");
					return false;
				}
				outStream = new FileOutputStream(localFileEncoding
						+ files[0].getName());
				reMark = ftp.retrieveFile(serverPathEncoding, outStream);

			} else if (downloadType == 1) {// �����ļ����µ������ļ����������ļ���
				String[] s = serverFilePath.split("/");

				for (int i = 1; i < s.length; i++) {
					ftp.changeWorkingDirectory(s[i]);
				}

				String[] files2 = ftp.listNames(serverPathEncoding);

				if (files2 == null) {
					System.out.println(serverPathEncoding + "û�����·����û���ļ�");
					return false;
				}
				for (int i = 0; i < files2.length; i++) {
					File f = new File(localFilePath + "/" + files2[i]);
					outStream = new FileOutputStream(f);
					String name = new String(files2[i].getBytes("GBK"),
							"iso-8859-1");
					// �����ļ�����ʧ���򷵻�ʧ��
					if (!ftp.retrieveFile(serverPathEncoding + "/" + name,
							outStream)) {
						System.out.println(name + "---����ʧ�ܣ�");
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
			FTPFile[] files = ftp.listFiles(serverPathEncoding);// ���ж���
			if (files == null) {
				System.out.println(path + "���·�����ǿյ�");
			} else {
				for (int i = 0; i < files.length; i++) {

					FTPFile ss = files[i];
					list.add(ss.getName());
				}
			}
		} catch (Exception e) {
			System.out.println("�쳣");
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