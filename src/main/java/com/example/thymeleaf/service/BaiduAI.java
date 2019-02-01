package com.example.thymeleaf.service;

import com.baidu.aip.nlp.AipNlp;
import com.example.thymeleaf.util.AppUtils;
import com.google.common.collect.Lists;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 参考地址：https://cloud.baidu.com/doc/NLP/NLP-API.html#.E8.BF.94.E5.9B.9E.E6.A0.BC.E5.BC.8F
 * Created by lixiaoming on 2018/12/6.
 */
@Service
public class BaiduAI {

    private static Log logger = LogFactory.getLog(BaiduAI.class);

    //设置APPID/AK/SK
    public static final String APP_ID = "15075087";
    public static final String API_KEY = "5pwYRMuEcbH0YqAeWpsUhuFB";
    public static final String SECRET_KEY = "0KzGXeGHBqp2RQxkYoErSYdiZ3YDCP4G";

    private AipNlp client ;

    BaiduAI(){
        client = new AipNlp(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
    }

    /**
     *
     * @param sentence
     * @return
     */
    public List<String> wordsTag(String sentence){
        JSONObject res = client.lexer(sentence, null);
        if(!res.has("items")){
            logger.info("key not found,3 seconds try again ,res:"+res.toString(2));
            AppUtils.sleep(3);
            res = client.lexer(sentence, null);
        }
        //final String jsonStr = res.toString(2);
        JSONArray items = res.getJSONArray("items");
        List words = Lists.newArrayList();
        for(Object item:items){
            JSONObject jItem = (JSONObject)item;
            String strItem = jItem.getString("item");
            //pos=a,v
            String pos = jItem.getString("pos");
            if((pos.startsWith("a")||pos.startsWith("v"))&&
            strItem.length()>1){
                //不返回长度为1的词
                words.add(strItem);
            }
        }
        return words;
    }


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
        String text = "技术分析是指研究过去金融市场的资讯（主要是经由使用图表）来预测价格的";
        JSONObject res = client.lexer(text, null);
        final String jsonStr = res.toString(2);
        JSONArray items = res.getJSONArray("items");
        List words = Lists.newArrayList();
        for(Object item:items){
            JSONObject jItem = (JSONObject)item;
            String strItem = jItem.getString("item");
            words.add(strItem);
        }
        System.out.println(jsonStr);

    }
}
