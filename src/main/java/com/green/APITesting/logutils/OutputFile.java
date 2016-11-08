package com.green.APITesting.logutils;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class OutputFile {

    public boolean isAppend = false;

    /**
     * @param dst     目标路径
     * @param content 输出的内容	输出返回的文件内容到目标文件
     */
    public synchronized void writeFile(String dst, String content) {

        OutputStream os = null;
        OutputStreamWriter fos = null;
        BufferedWriter bw = null;
        try {
            os = new FileOutputStream(dst, isAppend);
            fos = new OutputStreamWriter(os, "gbk");
            bw = new BufferedWriter(fos);
            if (content != null)
                bw.write(content);
            bw.flush();
            bw.close();
            fos.close();
            os.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
