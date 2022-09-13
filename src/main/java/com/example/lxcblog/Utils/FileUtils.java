package com.example.lxcblog.Utils;

import com.example.lxcblog.entity.Result;
import lombok.Data;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Data
@Service
public class FileUtils {
    @Value("${file.hostname}")
    private String HOSTNAME;
    @Value("${file.port}")
    private int PORT;
    @Value("${file.user}")
    private String USER;
    @Value("${file.password}")
    private String PASSWORD;
    @Value("${file.uploadUrl}")
    private String uploadPath;

    private FTPClient ftpClient;


    private boolean connectServer(String ip, int port, String user, String pwd) {
        ftpClient = new FTPClient();
        boolean isSuccess = false;
        try {
            ftpClient.connect(ip, port);
            isSuccess = ftpClient.login(user, pwd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    public String getSuffix(String name) {
        StringBuilder sb = new StringBuilder();
        for (int i = name.length() - 1; i >= 0 && name.charAt(i) != '.'; i--) {
            sb.append(name.charAt(i));
        }
        return sb.reverse().toString();
    }


    public Result<?> upload(List<MultipartFile> multipartFiles, String userUid) {
        // 上传的图片路径 , 是否成功
        List<String> list = new ArrayList<>();

        if (connectServer(HOSTNAME, PORT, USER, PASSWORD)) {
            // 连接到ftp服务器
            try {
                // 将FTP文件传输模式切换为为 binary
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                // 设置为被动模式
                ftpClient.enterLocalPassiveMode();
                // 该用户下的文件夹
                ftpClient.changeWorkingDirectory(uploadPath);
                ftpClient.makeDirectory(userUid);
                ftpClient.changeWorkingDirectory(userUid);
                // 存放图片
                for (int i = 0; i < multipartFiles.size(); i++) {
                    // 设置文件名
                    String name = System.currentTimeMillis() + "-" + i + "." + getSuffix(multipartFiles.get(i).getOriginalFilename());
                    InputStream file = multipartFiles.get(i).getInputStream();
                    BufferedInputStream in = new BufferedInputStream(file);
                    ftpClient.storeFile(name, in);
                    list.add(ftpClient.printWorkingDirectory() + "/" + name);
                }
                ftpClient.logout();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return Result.error("0", "连接FTP数据库错误", null);
        }
        return Result.success("1", "上传成功", list);
    }
}
