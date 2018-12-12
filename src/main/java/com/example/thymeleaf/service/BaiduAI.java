package com.example.thymeleaf.service;

import com.baidu.aip.nlp.AipNlp;
import org.json.JSONObject;

/**
 * 参考地址：https://cloud.baidu.com/doc/NLP/NLP-API.html#.E8.BF.94.E5.9B.9E.E6.A0.BC.E5.BC.8F
 * Created by lixiaoming on 2018/12/6.
 */
public class BaiduAI {
    //设置APPID/AK/SK
    public static final String APP_ID = "15075087";
    public static final String API_KEY = "5pwYRMuEcbH0YqAeWpsUhuFB";
    public static final String SECRET_KEY = "0KzGXeGHBqp2RQxkYoErSYdiZ3YDCP4G";

    public static void main(String[] args) {
        // 初始化一个AipNlp
        AipNlp client = new AipNlp(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        //client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
        //client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        //System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        // 调用接口
        String text = "百度是一家高科技公司";
        JSONObject res = client.lexer(text, null);
        System.out.println(res.toString(2));

    }
}
