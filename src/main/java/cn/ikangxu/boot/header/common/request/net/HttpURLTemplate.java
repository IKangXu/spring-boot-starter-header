/*
 *  Engine3D Technologies Co., Ltd. Copyright 2019,  All rights reserved
 */

package cn.ikangxu.boot.header.common.request.net;

import cn.ikangxu.boot.header.RootAttribute;
import cn.ikangxu.boot.header.RootHeader;
import cn.ikangxu.boot.header.common.Encoding;
import cn.ikangxu.boot.header.common.PropertiesConst;
import cn.ikangxu.boot.header.common.request.HttpMethod;
import cn.ikangxu.boot.header.util.MapUtils;
import cn.ikangxu.boot.header.util.ObjectUtils;
import cn.ikangxu.boot.header.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.DependsOn;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
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
        if (requestUrl.startsWith("https")) {
            return httpsRequestEntity(requestUrl, HttpMethod.GET, toParamString(params), null);
        } else {
            return httpRequestEntity(requestUrl, HttpMethod.GET, toParamString(params), null);
        }
    }

    public String postEntity(String requestUrl, Map<String, Object> params) {
        if (requestUrl.startsWith("https")) {
            return httpsRequestEntity(requestUrl, HttpMethod.POST, toParamString(params), "application/x-www-form-urlencoded");
        } else {
            return httpRequestEntity(requestUrl, HttpMethod.POST, toParamString(params), "application/x-www-form-urlencoded");
        }
    }

    private String httpRequestEntity(String requestUrl, String requestMethod, String params, String contentType) {

        String retStr = "";
        try {
            if (HttpMethod.GET.equals(requestMethod.toUpperCase()) && params != null) {
                requestUrl = requestUrl + "?" + params;
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

            // 当params不为null时向输出流写数据
            if (HttpMethod.POST.equals(requestMethod.toUpperCase()) && null != params) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(params.getBytes(String.valueOf(PropertiesConst.NET_CHARSET)));
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

    private String httpsRequestEntity(String requestUrl, String requestMethod, String params, String contentType) {
        String result = "";
        try {
            if (HttpMethod.GET.equals(requestMethod.toUpperCase()) && params != null) {
                requestUrl = requestUrl + "?" + params;
            }

            //创建SSLContext
            SSLContext sslContext = SSLContext.getInstance("SSL");
            TrustManager[] tm = {new MyX509TrustManager()};
            //初始化
            sslContext.init(null, tm, new java.security.SecureRandom());

            //获取SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            conn.setDoOutput(Boolean.valueOf((String) PropertiesConst.NET_DO_OUTPUT));
            conn.setDoInput(Boolean.valueOf((String) PropertiesConst.NET_DO_INPUT));
            conn.setUseCaches(Boolean.valueOf((String) PropertiesConst.NET_USE_CACHES));

            conn.setConnectTimeout(Integer.valueOf((String) PropertiesConst.NET_CONNECT_TIMEOUT));
            conn.setReadTimeout(Integer.valueOf((String) PropertiesConst.NET_READ_TIMEOUT));

            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("Content-Type", contentType);

            //设置当前实例使用的SSLSoctetFactory
            conn.setSSLSocketFactory(ssf);

            conn.connect();
            //往服务器端写内容
            if (ObjectUtils.isNotEmpty(params)) {
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(params.getBytes(String.valueOf(PropertiesConst.NET_CHARSET)));
                outputStream.close();
            }

            //读取服务器端返回的内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, String.valueOf(PropertiesConst.NET_CHARSET));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer buffer = new StringBuffer();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line);
            }

            result = buffer.toString();

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
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
