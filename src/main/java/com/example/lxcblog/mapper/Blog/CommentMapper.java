package com.example.lxcblog.mapper.Blog;


import com.example.lxcblog.entity.Blog.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("commentMapper")
public interface CommentMapper {


    /**
     * 获取评论总数
     * @param textUid 文章id
     * @return int
     */
    Integer getCount(long textUid);

    /**
     * 获取子评论总数
     * @param textUid 文章id
     * @param commentUid 评论id
     * @return int
     */
    Integer getChildCount(long textUid , long commentUid);

    /**
     * 获取顶级评论信息
     * @param textUid 什么文章
     * @param start 一次不能获取太多，点击更多后加载更多
     * @return 评论信息
     */
    List<Comment> getTopComment(long textUid , int start);

    /**
     * 获取子评论
     * @param textUid 什么文章
     * @param rootUid 回复的顶级评论
     * @param start 加载更多
     * @return 子评论信息
     */
    List<Comment> getChildComment(long textUid , long rootUid , int start);

    Integer addComment2Text(long commentUid , long textUid , String myComment , long userUid);

    Integer addComment2TopComment(long commentUid , long textUid , long topCommentUid , String myComment , long userUid);

    Integer addComment2ChildComment(long commentUid , long textUid  , long topCommentUid , long parentCommentUid , String myComment , long userUid);
}
