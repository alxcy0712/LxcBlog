package com.example.lxcblog.mapper.Blog;


import com.example.lxcblog.entity.Blog.Article;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("articleMapper")
public interface ArticleMapper {
    /**
     * 获取首页中每一page的基础信息
     * @param fromPage page信息
     * @return 文章基本信息
     */
    List<Article> getSevenInfo(int fromPage);

    /**
     * 获取具体信息
     * @param id 文章id
     * @return 文章信息 包含text
     */
    List<Article> getText(long id);

    /**
     * 添加文章信息
     * @param textUid 文章uid
     * @param userUid 用户uid
     * @param text    文章具体内容
     * @param title   文章题目
     * @return  文章具体信息 返回的行数
     */
    int addText(long textUid ,long userUid , String text , String title);

    int getArticleNumber();

    int updateText(String text , long textUid);

}
