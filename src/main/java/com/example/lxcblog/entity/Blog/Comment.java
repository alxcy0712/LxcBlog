package com.example.lxcblog.entity.Blog;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Comment {

    private long userUid;
    private String userName;
    private long textUid;
    private long commentUid;
    private long commentRootUid;
    private long commentParentUid;
    private String commentParentName;
    private String comment;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date commentTime;
    private boolean show;
    private int childCommentPage;
    private List<Object> childComment;
    private String myComment;
}
