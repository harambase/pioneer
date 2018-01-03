package com.harambase.common;

import com.harambase.support.util.FileWriterUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class UploadFile {

    /**
     * @param file    待上传文件
     * @param dirPath 文件服务器子目录
     * @return
     */
    public static String uploadFileToPath(MultipartFile file, String dirPath) throws IOException {

        String fileName = file.getOriginalFilename();

        // 源文件目录
        String dirName = FileWriterUtil.getSecondPathByHashCode(Config.SERVER_PATH + dirPath, fileName);

        // 获取当前文件存放路径
        String filePath = FileWriterUtil.getSingleFileDirName(fileName, dirName);

        // 根据文件名生成唯一文件路径
        File imgFile = new File(filePath);

        // 写入文件到实际路径
        String imgPath = filePath.substring(Config.SERVER_PATH.length(), filePath.length());
        file.transferTo(imgFile);

        return imgPath;
    }
}
