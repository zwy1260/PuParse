package com.utils;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

public class pupuParseUrl {

    private  String name;      // 商品名
    private  double price;    // 价格
    private  double market_price;    // 原价
    private  String content;  //详细信息
    private  String spec;     // 规格
    private  LinkedTreeMap data;

    public void parsePuPu() throws IOException {
        //获得请求  https://j1.pupuapi.com/client/product/storeproduct/detail/831b632e-12bd-4c23-a6fd-a18749d8d508/188d7a73-f518-418e-a928-e293f4b17556
        String url="https://j1.pupuapi.com/client/product/storeproduct/detail/831b632e-12bd-4c23-a6fd-a18749d8d508/188d7a73-f518-418e-a928-e293f4b17556";
        //解析网页
        Connection.Response res = Jsoup.connect(url)
                .header("Accept", "*/*")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                .header("Content-Type", "application/json;charset=UTF-8")
                .timeout(10000).ignoreContentType(true).execute();
        //创建gson对象
        Gson gson = new Gson();
        //对获取到的数据进行转换
        HashMap hashMap = gson.fromJson(res.body(), HashMap.class);
        //获取data里面的值
        data = (LinkedTreeMap) hashMap.get("data");
        //获取对应数据
        name= (String) data.get("name");
        spec=(String) data.get("spec");
        price=(Double) data.get("price")/100;
        market_price=(Double) data.get("market_price")/100;
        content=(String) data.get("share_content");
    }
    public void show() throws InterruptedException, IOException {
        parsePuPu();
        //打印
        System.out.println("-----------------------商品："+name+"-----------------------");
        System.out.println("规格："+spec);
        System.out.println("价格："+price);
        System.out.println("原价/折扣价："+market_price+"/"+price);
        System.out.println("详细信息："+content);
        System.out.println("--------------------"+name+"的价格波动--------------------");
        timing();
    }
    public void timing() throws IOException, InterruptedException {
        //每3秒抓取一次商品信息
        while (true){
            parsePuPu();
            System.out.println("当前时间为"+ LocalDateTime.now()+" ,"+name+":价格为"+price);
            Thread.sleep(3000);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new pupuParseUrl().show();
    }
}
