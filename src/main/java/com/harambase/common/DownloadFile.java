package com.harambase.common;

import com.harambase.common.constant.FlagDict;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class DownloadFile {
    public static void downloadFile(String fileName, String filePath, HttpServletResponse response) {

        File file = null;
        if (StringUtils.isNotEmpty(filePath)) {
            file = new File(Config.serverPath + filePath);
        }

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName);//uri.substring(uri.lastIndexOf("/"), uri.length()) + ".csv\"");
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        OutputStream outputStream = null;
        try {

            FileInputStream fileInputStream = new FileInputStream(filePath);
            outputStream = new BufferedOutputStream(response.getOutputStream());
            byte[] bytes = new byte[2048];
            int length;
            while ((length = fileInputStream.read(bytes)) > 0) {
                outputStream.write(bytes, 0, length);
            }
            fileInputStream.close();
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (file.exists())
                file.delete();
        }

    }
}
