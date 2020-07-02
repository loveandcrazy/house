package org.linlinjava.litemall.core.storage;

import ch.qos.logback.classic.Logger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class IpfsStorageService {
    public static String IpfsStorage(String method,String value){
        String result="";
        try {
            Process process = Runtime.getRuntime().exec("python ./pyIPFS.py " + method + value);
            //            process.waitFor();
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            result = input.readLine();
            input.close();
            ir.close();
            //            process.waitFor();
        } catch (IOException e) {
            System.out.println("调用python脚本并读取结果时出错：");
            System.out.println(e.getMessage());
        }
        return result;
    }
    public static String Add(String filePath){
        return IpfsStorage("Add", filePath);
    }
    public static String Cat(String filehash){
        return IpfsStorage("Cat", filehash);
    }
    public static String Delete(String filehash){
        return IpfsStorage("Delete", filehash);
    }

}

