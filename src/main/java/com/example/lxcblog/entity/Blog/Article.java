package com.example.lxcblog.entity.Blog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author liuxiaochen
 */
@Data
public class Article implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    private long textUid;

    private long userUid;

    private String title;

    private String text;

    private String userNickName;

    private int likeCount;

    private int commentCount;

    private List<MultipartFile> images;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date buildTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updateTime;


    @Override
    public String toString() {
        return "textUid = " + this.textUid + " userUid = " + this.userUid + " title = " + this.title + " buildTime = " + this.buildTime;
    }
}
