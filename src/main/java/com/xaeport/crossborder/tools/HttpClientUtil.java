package com.xaeport.crossborder.tools;

import com.alibaba.druid.support.json.JSONUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;

/**
 * Created by Nano on 2017-4-06.
 */
@Component
public class HttpClientUtil {
    @Autowired
    private SystemSetting systemSetting;

    private Log log = LogFactory.getLog(this.getClass());

    /*
      get
     */
    public Object sendGet(String resful, String param) throws Exception {
        String result = "";
        String address = systemSetting.getHttpClinetUrl();
        String url = MessageFormat.format("{0}{1}", address, resful);
        HttpURLConnection connection = null;
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestProperty("Content-Type", " application/json");
            connection.setConnectTimeout(20000);
            connection.setReadTimeout(20000);
            connection.setRequestMethod("GET");
            connection.connect();
            int code = connection.getResponseCode();
            if (code == 200) {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            } else {
                in = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "utf-8"));
            }
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            connection.disconnect();
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return JSONUtils.parse(result);
    }

    /*
        post
     */
    public Object sendPost(String resful, String param) throws IOException {
        BufferedReader in = null;
        String result = "";
        String address = systemSetting.getHttpClinetUrl();
        String url = MessageFormat.format("{0}{1}", address, resful);
        HttpURLConnection connection = null;
        PrintWriter printWriter = null;
        try {
            URL realUrl = new URL(url);
            connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setConnectTimeout(20000);
            connection.setReadTimeout(20000);
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.connect();
            // 获取connection对象对应的输出流
            printWriter = new PrintWriter(connection.getOutputStream());
            printWriter.write(param);//param的参数 xx=xx&yy=yy
            printWriter.flush();

            int code = connection.getResponseCode();
            if (code == 200) {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            } else {
                in = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "utf-8"));
            }
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e2) {
            throw e2;
        } finally {
            connection.disconnect();
            try {
                if (printWriter != null) {
                    printWriter.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return JSONUtils.parse(result);
    }
    /*
    put
     */
    public Object sendPut(String resful, NameValuePair[] params) throws Exception {
        String address = systemSetting.getHttpClinetUrl();
        String url = MessageFormat.format("{0}{1}", address, resful);
        HttpClient htpClient = new HttpClient();
        PutMethod putMethod = new PutMethod(url);
        putMethod.addRequestHeader("Content-Type", "application/json");
        putMethod.addRequestHeader("Accept", "*/*");
        putMethod.setQueryString(params);
        String result = "";
        try {
            int statusCode = htpClient.executeMethod(putMethod);
            if (statusCode == 200) {
                log.debug("Put Success!");
            } else {
                log.error(" Error===" + statusCode);
            }
            byte[] responseBody = putMethod.getResponseBody();
            result = new String(responseBody);
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            putMethod.releaseConnection();
        }
        return JSONUtils.parse(result);
    }
}
