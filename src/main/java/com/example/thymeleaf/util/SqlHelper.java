package com.example.thymeleaf.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jodd.http.HttpRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by lixiaoming on 17/3/14.
 */
public class SqlHelper {

    private static Logger logger = LoggerFactory.getLogger(SqlHelper.class);

    public static String proxySql(String sql) throws Exception {
        long t1 = System.currentTimeMillis();
        String salt = "aming";
        String tageTime = TimeUtils.getCurDay();
        String expMd5 = DigestUtils.md5Hex(sql + salt + tageTime);//expMd5
        Map postMap = new HashMap();
        postMap.put("querySql",sql);
        postMap.put("tagTime",tageTime);
        postMap.put("expMd5",expMd5);
        String base = "http://bquant.xyz/tools/sec_sql_proxy.php";
        HttpRequest httpRequest = HttpRequest.post(base);
        httpRequest.form(postMap);
        final byte[] bytes = httpRequest.send().bodyBytes();
        long gap = System.currentTimeMillis() - t1;
        logger.info("proxySql cost ["+gap+"]");
        return new String(bytes,"utf8");
    }

    /**
     * 转换成
     * INSERT INTO `t_article`(`comment`,`title`,`url`,`seed_id`,`status`," +
     "`click_times`,`img`,`author`) VALUES ('?','?','?',?,'?',0,'?','?')";
     * @param tableName
     * @param params
     * @return
     */
    public static String insertTable(String tableName,Map<String,Object> params){
        StringBuilder buffer = new StringBuilder();
        buffer.append("INSERT INTO `");
        buffer.append(tableName);
        buffer.append("`(");
        Set<String> columns = params.keySet();
        StringBuilder values = new StringBuilder();
        for(String column:columns){
            buffer.append("`");
            buffer.append(column);
            buffer.append("`,");
            Object value = params.get(column);
            if(value instanceof String){
                values.append("'");
                values.append(escapeInput((String) value));
                values.append("',");
            }else if(value instanceof Date){
                String date = TimeUtils.getDate((Date)value,TimeUtils.FULL_TIME_FORMAT_NO_SPLIT);
                values.append("'");
                values.append(date);
                values.append("',");
            }else if(value instanceof Integer){
                values.append(value);
                values.append(",");
            }else if (value==null){
                values.append("NULL,");
            }else {
                values.append(value);
                values.append(",");
            }
        }
        buffer.deleteCharAt(buffer.length()-1);
        buffer.append(") VALUES (");
        values.deleteCharAt(values.length()-1);
        values.append(")");
        buffer.append(values);
        return buffer.toString();
    }

    private static String escapeInput(String input){
        if(input.contains("'")){
            input = input.replace("'","\\'");
        }
        return input;
    }

    //UPDATE  `u342382313_mydb`.`t_store_msg` SET  `msg_content` =  'testmsgcontent2' WHERE  `t_store_msg`.`msg_id` =2;

    public static String updateTable(String tableName,Map<String,Object> setMap,Map<String,Object> whereMap){
        StringBuilder buffer = new StringBuilder();
        buffer.append("UPDATE `");
        buffer.append(tableName);
        buffer.append("` SET ");

        equalExpress(setMap, buffer);
        buffer.append(" WHERE ");
        equalExpressWhere(whereMap, buffer);
        return buffer.toString();
    }

    public static String queryTable(String tableName,Map<String,Object> whereMap){
        StringBuilder buffer = new StringBuilder();
        buffer.append("select * from  `");
        buffer.append(tableName);

        buffer.append("` WHERE ");
        equalExpressWhere(whereMap, buffer);
        return buffer.toString();
    }

    private static void equalExpress(Map<String, Object> setMap, StringBuilder buffer) {
        Set<String> columns = setMap.keySet();
        for(String column:columns){
            buffer.append("`");
            buffer.append(column);
            buffer.append("`=");
            Object value = setMap.get(column);
            if(value instanceof String){
                buffer.append("'");
                buffer.append(value);
                buffer.append("',");
            }else if(value instanceof Date){
                String date = TimeUtils.getDate((Date)value,TimeUtils.FULL_TIME_FORMAT_NO_SPLIT);
                buffer.append("'");
                buffer.append(date);
                buffer.append("',");
            }else if(value instanceof Integer){
                buffer.append(value);
                buffer.append(",");
            }
        }
        buffer.deleteCharAt(buffer.length()-1);
    }

    private static void equalExpressWhere(Map<String, Object> setMap, StringBuilder buffer) {
        Set<String> columns = setMap.keySet();
        for(String column:columns){
            buffer.append("`");
            buffer.append(column);
            buffer.append("`=");
            Object value = setMap.get(column);
            if(value instanceof String){
                buffer.append("'");
                buffer.append(value);
                buffer.append("' and ");
            }else if(value instanceof Date){
                String date = TimeUtils.getDate((Date)value,TimeUtils.FULL_TIME_FORMAT_NO_SPLIT);
                buffer.append("'");
                buffer.append(date);
                buffer.append("' and ");
            }else if(value instanceof Integer){
                buffer.append(value);
                buffer.append(" and ");
            }
        }
        buffer.delete(buffer.length()-4,buffer.length());
    }

    /**
     * 将sql中的?替换为变量
     * @param sqlTemplate
     * @param args
     * @return
     */
    public static String parseSql(String sqlTemplate, Object ...args) {
        StringBuilder buffer = new StringBuilder();
        for(int i=0,j=0;i<sqlTemplate.length();i++){
            char c = sqlTemplate.charAt(i);
            if(c=='?'){
                buffer.append(args[j++]);
            }else{
                buffer.append(c);
            }
        }
        return buffer.toString();
    }

    public static JSONObject getOneRecord(String sql) throws Exception{
        String reponse = SqlHelper.proxySql(sql);
        if(reponse.startsWith("[")){
            JSONArray jsonArray = JSONObject.parseArray(reponse);
            for(int i=0;i<jsonArray.size();i++){
                JSONObject json = jsonArray.getJSONObject(i);
                return json;
            }
        }else{
            logger.warn("not expect response [" + reponse + "]");
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        String sql = "select title,click_times from t_article order by click_times desc limit 10";
        //String sql = "delete  from `t_chapter`  ";
        String result = SqlHelper.proxySql(sql);
        System.out.println("["+result+"]");
        JSONArray jsonArray = JSONArray.parseArray(result);
        for(Object jsonObject:jsonArray){
            System.out.println("AAA:"+jsonObject);
        }
    }
}
