package com.lxq.batch.module.impl;
import java.io.File;
import java.io.FileInputStream;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.lxq.batch.module.interf.AbstractModule;

public class I18N extends AbstractModule{

    private static ResourceBundle rb;
    
    @Override
    public void load() throws Exception {

        // 获得缺省的系统区域 
        Locale locale = Locale.getDefault();
        // 获得资源文件 
        
        File file = new File(this.getPath() + this.getConfigFile().replace("locale", locale.toString()));
        if(!file.exists()){
            file = new File(this.getPath() + this.getConfigFile().replace("_locale", ""));
        }
        
        rb = new PropertyResourceBundle(new FileInputStream(file));
    } 
    
    public static String getString(String key){
        return rb.getString(key);
    }
}