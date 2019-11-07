/*
 *  Engine3D Technologies Co., Ltd. Copyright 2019,  All rights reserved
 */

package cn.ikangxu.boot.header.common.request.net;

import cn.ikangxu.boot.header.RootAttribute;
import cn.ikangxu.boot.header.RootHeader;
import cn.ikangxu.boot.header.common.Encoding;
import cn.ikangxu.boot.header.common.PropertiesConst;
import cn.ikangxu.boot.header.util.MapUtils;
import cn.ikangxu.boot.header.util.ObjectUtils;
import cn.ikangxu.boot.header.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.DependsOn;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author kangxu [xukang@engine3d.com]
 * @version v1.0
 * @className HttpURLTemplate
 * @description
 * @date 2019/11/7 15:33
 */
@Slf4j
@DependsOn({"springContextUtils", "propertiesConst"})
public class HttpURLTemplate {

    public String getEntity(String requestUrl, Map<String, Object> params) {
        return httpRequestEntity(requestUrl, "GET", toParamString(params), null);
    }

    public String postEntity(String requestUrl, Map<String, Object> params) {
        if (requestUrl.startsWith("https")) {
            return httpsRequestEntity(requestUrl, params, String.valueOf(PropertiesConst.NET_CHARSET));
        } else {
            return httpRequestEntity(requestUrl, "POST", toParamString(params), "application/x-www-form-urlencoded");
        }

    }

    private String httpRequestEntity(String requestUrl, String requestMethod, String outputStr, String contentType) {

        String retStr = "";
        try {
            if ("GET".equals(requestMethod.toUpperCase()) && outputStr != null) {
                requestUrl = requestUrl + "?" + outputStr;
            }

            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(Boolean.valueOf((String) PropertiesConst.NET_DO_OUTPUT));
            conn.setDoInput(Boolean.valueOf((String) PropertiesConst.NET_DO_INPUT));
            conn.setUseCaches(Boolean.valueOf((String) PropertiesConst.NET_USE_CACHES));

            conn.setConnectTimeout(Integer.valueOf((String) PropertiesConst.NET_CONNECT_TIMEOUT));
            conn.setReadTimeout(Integer.valueOf((String) PropertiesConst.NET_READ_TIMEOUT));

            // 传入头信息
            String[] keys = RootAttribute.list();
            for (String key : keys) {
                String val = RootHeader.get(key);

                conn.setRequestProperty(key, val);
            }

            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("Content-Type", contentType);

            // 当outputStr不为null时向输出流写数据
            if ("POST".equals(requestMethod.toUpperCase()) && null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes(String.valueOf(PropertiesConst.NET_CHARSET)));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, String.valueOf(PropertiesConst.NET_CHARSET));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer buffer = new StringBuffer();
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            retStr = buffer.toString();

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return retStr;
    }

    private String httpsRequestEntity(String url, Map<String, Object> map, String charset) {
        return null;
    }

    private String toParamString(Map<String, Object> params) {

        String retStr = null;

        StringBuffer urlBuffer = new StringBuffer();
        if (MapUtils.isNotEmpty(params)) {
            for (String key : params.keySet()) {
                urlBuffer.append("&").append(key).append("=").append(URLEncoder.encode(String.valueOf(params.get(key))));
            }
            retStr = urlBuffer.toString().substring(1);
        }

        return retStr;

    }

}
