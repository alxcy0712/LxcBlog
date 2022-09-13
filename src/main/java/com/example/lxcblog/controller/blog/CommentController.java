package com.example.lxcblog.controller.blog;


import com.example.lxcblog.Utils.Smell;
import com.example.lxcblog.entity.Blog.Comment;
import com.example.lxcblog.entity.Result;
import com.example.lxcblog.mapper.Blog.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class CommentController {

    private final CommentMapper commentMapper;

    @Autowired
    public CommentController(CommentMapper commentMapper){
        this.commentMapper = commentMapper;
    }


    /**
     * 获取文章评论数量
     * @param textUid 文章id
     * @return 数量
     */
    @GetMapping("/getCount")
    public Integer getCount(@RequestParam String textUid){
        return commentMapper.getCount(Long.parseLong(textUid));
    }

    @GetMapping("/getChildCount")
    public Integer getChildCount(@RequestParam String textUid , String commentUid){
        return commentMapper.getChildCount(Long.parseLong(textUid) , Long.parseLong(commentUid));
    }


    /**
     * 获取顶级评论
     * @param textUid 文章id
     * @param start 次数
     * @return 所有评论信息
     */
    @GetMapping("/getTopComment")
    public Result<?> getTopComment(@RequestParam String textUid , String start){
        List<Comment> list = commentMapper.getTopComment(Long.parseLong(textUid),Integer.parseInt(start)*5);
        return Result.success(list);
    }

    /**
     * 获得某一个评论的子评论
     * @param textUid
     * @param rootUid
     * @param start
     * @return
     */
    @GetMapping("/getChildComment")
    public Result<?> getChildComment(@RequestParam String textUid , String rootUid , String start){
        List<Comment> list =
                commentMapper.getChildComment(Long.parseLong(textUid), Long.parseLong(rootUid), (Integer.parseInt(start))*5);
        return Result.success(list);
    }


    @PostMapping("/addComment2Text")
    public Result<?> addComment2Text(@RequestParam String userUid , String textUid , String myComment){
        if(myComment == null || myComment.length() == 0){
            return Result.error("0" , "请输入文字" , null);
        }
        if(userUid == null || userUid.length() == 0){
            return Result.error("0" , "用户未登录" , null);
        }
        long commentUid = Smell.getUid();
        Integer cmt = commentMapper.addComment2Text(
                commentUid ,
                Long.parseLong(textUid),
                myComment,
                Long.parseLong(userUid)
                );
        return Result.success(cmt);
    }

    @PostMapping("/addComment2TopComment")
    public Result<?> addComment2TopComment(@RequestParam String userUid , String textUid ,String myComment , String topCommentUid){
        if(myComment == null || myComment.length() == 0){
            return Result.error("0" , "请输入文字" , null);
        }
        if(userUid == null || userUid.length() == 0){
            return Result.error("0" , "用户未登录" , null);
        }
        long commentUid = Smell.getUid();
        Integer cmt = commentMapper.addComment2TopComment(
                commentUid ,
                Long.parseLong(textUid),
                Long.parseLong(topCommentUid),
                myComment,
                Long.parseLong(userUid)
        );
        return Result.success(cmt);
    }

    @PostMapping("/addComment2ChildComment")
    public Result<?> addComment2ChildComment
            (@RequestParam
                     String userUid ,
                     String textUid ,
                     String myComment,
                     String topCommentUid,
                     String parentCommentUid){
        if(myComment == null || myComment.length() == 0){
            return Result.error("0" , "请输入文字" , null);
        }
        if(userUid == null || userUid.length() == 0){
            return Result.error("0" , "用户未登录" , null);
        }
        long commentUid = Smell.getUid();
        Integer cmt = commentMapper.addComment2ChildComment(
                commentUid,
                Long.parseLong(textUid),
                Long.parseLong(topCommentUid),
                Long.parseLong(parentCommentUid),
                myComment,
                Long.parseLong(userUid)
        );
        return Result.success(cmt);
    }


}
