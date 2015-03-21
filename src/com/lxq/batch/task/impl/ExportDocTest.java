package com.lxq.batch.task.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;


public class ExportDocTest {
    
    public static void main(String[] args) {
        
        String destFile="D:\\11.doc";
        //#####################�����Զ������ݵ���Word�ĵ�#################################################
        StringBuffer fileCon=new StringBuffer();
        fileCon.append("               �Ŵ���            ��              317258963215223\n" +
                "2011     09        2013     07       3\n" +
                "    �����о�              ����\n" +
                "2013000001                             2013     07     08");
        fileCon.append("\n\r\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        
        new ExportDocTest().exportDoc(destFile, fileCon.toString());
        
        //##################����Wordģ�嵼������Word�ĵ�###################################################
        Map<String, String> map=new HashMap<String, String>();
        
        map.put("name", "Zues");
        //ע��biyezheng_moban.doc�ĵ�λ��,������ΪӦ�ø�Ŀ¼
        HWPFDocument document=new ExportDocTest().replaceDoc("d:/template.doc", map);
        ByteArrayOutputStream ostream = new ByteArrayOutputStream();
        try {
            document.write(ostream);
            //���word�ļ�
            OutputStream outs=new FileOutputStream(destFile);
            outs.write(ostream.toByteArray());
            outs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    
    /**
     * 
     * @param destFile
     * @param fileCon
     */
    public void exportDoc(String destFile,String fileCon){
        try {
            //doc content
            ByteArrayInputStream bais = new ByteArrayInputStream(fileCon.getBytes());
            POIFSFileSystem fs = new POIFSFileSystem();
            DirectoryEntry directory = fs.getRoot(); 
            directory.createDocument("WordDocument", bais);
            FileOutputStream ostream = new FileOutputStream(destFile);
            fs.writeFilesystem(ostream);
            bais.close();
            ostream.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * ��ȡwordģ�岢�滻����
     * @param srcPath
     * @param map
     * @return
     */
    public HWPFDocument replaceDoc(String srcPath, Map<String, String> map) {
        try {
            // ��ȡwordģ��
            FileInputStream fis = new FileInputStream(new File(srcPath));
            HWPFDocument doc = new HWPFDocument(fis);
            // ��ȡword�ı�����
            Range bodyRange = doc.getRange();
            // �滻�ı�����
            for (Map.Entry<String, String> entry : map.entrySet()) {
                bodyRange.replaceText("${" + entry.getKey() + "}", entry
                        .getValue());
            }
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}