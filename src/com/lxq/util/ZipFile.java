package com.lxq.util;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFile {
	public static void zip(String srcFile,String destFile) throws Exception {
		createZipFile(new File(srcFile),new File(destFile));
	}

	public static void createZipFile(File srcFile, File destFile) throws Exception {
		
		File f = new File(srcFile,"backup.log");
		f.createNewFile();
		
		ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(
				destFile)));
		createZipFile(srcFile,out);
		out.close();
	}

	private static void createZipFile(File srcFile,ZipOutputStream out) throws Exception {
		if (srcFile.isDirectory()) { // 判断是否为目录
			File[] fl = srcFile.listFiles();
			for (int i = 0; i < fl.length; i++) {
				createZipFile(fl[i],out);
			}
		} else { // 压缩目录中的所有文件
			out.putNextEntry(new ZipEntry(srcFile.getPath()));
			
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFile));
			int buf = 1024 * 4;
			byte[] data = new byte[buf];
			int count;
			while ((count = bis.read(data,0,buf)) != -1) {
				out.write(data,0 ,count);
			}
			bis.close();
		}
	}
	
	public static void clearDir(File path){
		File[] f = path.listFiles();
		for(int i = 0 ; i < f.length; i ++){
			if(f[i].isDirectory()){
				clearDir(f[i]);
			}else{
				f[i].delete();
			}
		}
	}

}