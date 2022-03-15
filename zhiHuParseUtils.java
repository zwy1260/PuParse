package com.utils;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class zhiHuParseUtils {
    private List data;          //json数据
    private String articleUrl;  //文章链接
    private String articleTitle;   //文章标题
    private LinkedTreeMap map;
    private LinkedTreeMap question;
    private String[] nameSplite;         //收藏夹名称
    private ArrayList<String> listOfLinks;  //收藏夹id

    public static void main(String[] args) throws Exception {
        //调用show方法展示结果
        new zhiHuParseUtils().show();
    }

    public void getMsg() throws IOException {
        //发请求抓取网页信息提取id和收藏夹名称
        String articleUrl = "https://www.zhihu.com/people/huang-mu-81-3/collections";
        Document document = Jsoup.parse(new URL(articleUrl), 3000);
        Elements links = document.select(".SelfCollectionItem-title");

        listOfLinks = new ArrayList<String>();
        //获取指定class对应的数据
        String Name = document.select(".SelfCollectionItem-title").text();
        //字符串分割
        nameSplite = Name.split(" ");
        //存储收藏夹id
        for (int k = 0; k < nameSplite.length; k++) {
            String href = links.get(k).attr("href").substring(12);
            listOfLinks.add(href);
        }
    }

    public void getArticle(String url) throws Exception {
        //发送请求获取数据
        String urls = "https://www.zhihu.com/api/v4/collections/" + url + "/items?offset=0&limit=20";
        Connection.Response res = Jsoup.connect(urls)
                .header("Accept", "*/*")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                .header("Content-Type", "application/json;charset=UTF-8")
                .method(Connection.Method.GET)
                .timeout(10000).ignoreContentType(true).execute();
        String resBody = res.body();
        //创建gson对象
        Gson gson = new Gson();
        //对获取到的数据进行转换
        HashMap hashMap = gson.fromJson(resBody, HashMap.class);
        data = (List) hashMap.get("data");
        //遍历输出该收藏夹里有的全部文章的链接和名称
        for (int i = 0; i < data.size(); i++) {
            LinkedTreeMap o = (LinkedTreeMap) data.get(i);
            map = (LinkedTreeMap) o.get("content");
            articleUrl = (String) map.get("url");
            question = (LinkedTreeMap) map.get("question");
            //判断该文章是回答问题的文章还是写的文章，并获取指定标题
            if (question == null) {
                articleTitle = (String) map.get("title");
            } else {
                articleTitle = (String) question.get("title");
            }
            System.out.println(articleUrl + "  " + articleTitle);
        }
    }

    public void show() throws Exception {
        //获取收藏夹信息
        getMsg();
        //遍历收藏夹内部数据
        for (int i = 0; i < nameSplite.length; i++) {
            System.out.println("---------------收藏夹：" + nameSplite[i] + "---------------");
            getArticle(listOfLinks.get(i));
        }
    }

}
