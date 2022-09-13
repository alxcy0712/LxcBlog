package com.example.lxcblog.controller.blog;

import com.example.lxcblog.Utils.FileUtils;
import com.example.lxcblog.Utils.Smell;
import com.example.lxcblog.entity.Blog.Article;
import com.example.lxcblog.entity.Result;
import com.example.lxcblog.mapper.Blog.ArticleMapper;

import com.example.lxcblog.service.blog.ESArticleService;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.search.profile.ProfileShardResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author liuxiaochen
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class ArticleController {

    private final ArticleMapper articleMapper;
    private final RestHighLevelClient client;
    private final ESArticleService service;

    @Autowired
    public ArticleController(ArticleMapper articleMapper , RestHighLevelClient client , ESArticleService service) {
        this.articleMapper = articleMapper;
        this.client = client;
        this.service = service;
    }

    @Autowired
    private FileUtils fileUtils;



    @GetMapping("/getArticleMessage")
    public Result<?> articleResult(@RequestParam("page") String page) {
        int p = Integer.parseInt(page);
        int fromPage = (p - 1) * 7;
        List<Article> articleList = articleMapper.getSevenInfo(fromPage);
        if (articleList.size() != 0) {
            return Result.success(articleList);
        } else {
            return Result.error("0", "页码错误", null);
        }
    }

    @GetMapping("/getArticle")
    public Result<?> getArticle(@RequestParam("id") String id) {
        long p = Long.parseLong(id);
        List<Article> article = articleMapper.getText(p);
        if (article.size() != 0) {
            return Result.success(article);
        } else {
            return Result.error("0", "未查询到id为" + id + "的文章", null);
        }
    }

    @GetMapping("/getArticleNumber")
    public int getArticleNumber(){
        return articleMapper.getArticleNumber();
    }

    @PostMapping("/addArticle")
    public Result<?> addArticle(@RequestBody Article article) {
        /**
         * 生成文章的uid
         */
        long userUid = article.getUserUid();
        long textUid = Smell.getUid();
        if (textUid == 0L) {
            return Result.error("0", "机器时间错误", null);
        } else if (userUid == 0) {
            return Result.error("0", "用户未登录", null);
        }
        article.setTextUid(textUid);
        String text = article.getText().substring(1, article.getText().length() - 1);

        try {
            service.existIndex();
            service.addArticle(article);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int finalArticle = articleMapper.addText(textUid, userUid, text, article.getTitle());
        if (finalArticle == 0) {
            return Result.error("0", "上传错误", null);
        } else {
            return Result.success(article);
        }
    }


    @PostMapping("/uploadImg")
    public Result<?> uploadImg(MultipartHttpServletRequest files) {
        if (files == null) {
            System.out.println("error");
            return Result.error("0", "文件上传错误", null);
        }
        List<MultipartFile> list = files.getFiles("image");
        //获取用户的uid
        String userUid = files.getHeader("user");
        if(userUid == null){
            return Result.error("0", "用户未登录", null);
        }
        return Result.success(fileUtils.upload(list , userUid));
    }

    @PutMapping("/updateText")
    public Result<?> updateText(@RequestBody Article article){
        String text = article.getText();
        long textUid = article.getTextUid();
        return Result.success(articleMapper.updateText(text,textUid));
    }


    @GetMapping("/searchArticle")
    public Result<?> searchArticle(@RequestParam String text){
       List<Object> list = new ArrayList<>();
        try {
            list = service.searchDocument(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.success(list);
    }

}
