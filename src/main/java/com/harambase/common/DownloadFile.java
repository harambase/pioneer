package com.harambase.common;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.OutputStream;

public class DownloadFile {

    public static void downloadFile(String fileName, String logicalPath, HttpServletResponse response) {
        String filePath = Config.serverPath + logicalPath;

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName);
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        OutputStream outputStream = null;
        FileInputStream fileInputStream = null;
        try {

            fileInputStream = new FileInputStream(filePath);
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
        }
    }

}
