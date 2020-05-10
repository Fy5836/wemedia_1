package com.wemedia;

import org.springframework.util.StreamUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class HttpTest {

    public static void main(String[] args) throws Exception {
        URL url = new URL("https://way.jd.com/turning/turning");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        //需要输出参数
        con.setDoOutput(true);
        //拼接参数信息
        StringBuilder params = new StringBuilder();
        params.append("info=").append("你是男是女").append("&loc=").append("广州市天河区").append("&userid=").append("222").append("&appkey=").append("e50b3303a2fa65774b440c0f084a82b9");
        con.getOutputStream().write(params.toString().getBytes("UTF-8"));
        con.connect();
        //获取响应信息
        String res = StreamUtils.copyToString(con.getInputStream(), Charset.forName("UTF-8"));
        System.out.println(res);
    }

}