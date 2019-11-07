/*
 *  Engine3D Technologies Co., Ltd. Copyright 2019,  All rights reserved
 */

package cn.ikangxu.boot.header.common.request.httpclient;

import cn.ikangxu.boot.header.RootAttribute;
import cn.ikangxu.boot.header.RootHeader;
import cn.ikangxu.boot.header.common.Encoding;
import cn.ikangxu.boot.header.common.PropertiesConst;
import cn.ikangxu.boot.header.util.ListUtils;
import cn.ikangxu.boot.header.util.MapUtils;
import cn.ikangxu.boot.header.util.ObjectUtils;
import cn.ikangxu.boot.header.util.SpringContextUtils;
import com.google.common.base.Charsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.DependsOn;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author kangxu [xukang@engine3d.com]
 * @version v1.0
 * @className HttpClientUtils
 * @description
 * @date 2019/11/7 9:04
 */
@Slf4j
@DependsOn({"springContextUtils", "propertiesConst"})
public class HttpClientTemplate {

    private static final RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(Integer.valueOf((String) PropertiesConst.HTTP_CLIENT_CONNECT_TIMEOUT))
            .setConnectionRequestTimeout(Integer.valueOf((String) PropertiesConst.HTTP_CLIENT_CONNECTION_REQUEST_TIMEOUT))
            .setSocketTimeout(Integer.valueOf((String) PropertiesConst.HTTP_CLIENT_SOCKET_TIMEOUT))
            .build();

    /**
     * GET请求
     *
     * @param requestUrl 请求Url
     * @param params     请求参数
     * @return
     */
    public String getEntity(String requestUrl, Map<String, String> params) {

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;

        try {
            httpClient = createHttpClient();
            HttpGet httpGet = new HttpGet();
            httpGet.setConfig(requestConfig);
            // 构造头信息
            // 传入头信息
            String[] keys = RootAttribute.list();
            for (String key : keys) {
                String val = RootHeader.get(key);

                httpGet.addHeader(key, val);
            }

            // 构造参数
            List<NameValuePair> nameValuePairs = this.buildParams(params);

            String param = URLEncodedUtils.format(nameValuePairs, String.valueOf(PropertiesConst.HTTP_CLIENT_CHARSET));
            httpGet.setURI(URI.create(requestUrl + "?" + param));

            response = httpClient.execute(httpGet);

            return buildResponse(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                this.release(response, httpClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * POST请求
     *
     * @param requestUrl 请求Url
     * @param params     请求参数
     * @return
     */
    public String postEntity(String requestUrl, Map<String, String> params) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;

        try {
            httpClient = createHttpClient();
            HttpPost httpPost = new HttpPost(requestUrl);
            httpPost.setConfig(requestConfig);

            // 传入头信息
            String[] keys = RootAttribute.list();
            for (String key : keys) {
                String val = RootHeader.get(key);

                httpPost.addHeader(key, val);
            }

            // 构造参数
            List<NameValuePair> nameValuePairs = this.buildParams(params);

            if (ListUtils.isNotEmpty(nameValuePairs)) {
                //装填参数
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs, String.valueOf(PropertiesConst.HTTP_CLIENT_CHARSET));
                //设置参数到请求对象中
                httpPost.setEntity(entity);
            }

            response = httpClient.execute(httpPost);

            return buildResponse(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                this.release(response, httpClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void release(CloseableHttpResponse httpResponse,
                                CloseableHttpClient httpClient) throws IOException {
        // 释放资源
        if (httpResponse != null) {
            httpResponse.close();
        }
        if (httpClient != null) {
            httpClient.close();
        }
    }

    private List<NameValuePair> buildParams(Map<String, String> params) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();

        if (MapUtils.isNotEmpty(params)) {
            Set<Map.Entry<String, String>> set = params.entrySet();
            for (Map.Entry<String, String> entry : set) {
                nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        return nameValuePairs;
    }

    private String buildResponse(CloseableHttpResponse response) throws IOException {
        String result = "";
        if (response != null && response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, String.valueOf(PropertiesConst.HTTP_CLIENT_CHARSET));
            }
        }
        return result;
    }

    private static CloseableHttpClient createHttpClient() throws KeyManagementException, NoSuchAlgorithmException {
        SSLContext sslcontext = createIgnoreVerifySSL();

        // 设置协议http和https对应的处理socket链接工厂的对象
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslcontext))
                .build();

        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

        HttpClients.custom().setConnectionManager(connManager);

        //创建自定义的httpclient对象
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();

        return client;
    }

    private static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");
        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        sc.init(null, new TrustManager[]{trustManager}, null);
        return sc;
    }

    public String postFileMultiPart(String url, Map<String, ContentBody> reqParam) throws ClientProtocolException, IOException {
        return postFileMultiPart(null, url, reqParam);
    }

    public String postFileMultiPart(Map<String, String> header, String url, Map<String, ContentBody> reqParam) throws ClientProtocolException, IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            // 创建httpget.
            HttpPost httPost = new HttpPost(url);

            if (header != null) {
                for (Map.Entry<String, String> param : header.entrySet()) {
                    httPost.addHeader(param.getKey(), param.getValue());
                }
            }

            // 传入头信息
            String[] keys = RootAttribute.list();
            for (String key : keys) {
                String val = RootHeader.get(key);

                httPost.addHeader(key, val);
            }

            httPost.setConfig(requestConfig);

            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            for (Map.Entry<String, ContentBody> param : reqParam.entrySet()) {
                multipartEntityBuilder.addPart(param.getKey(), param.getValue());
            }
            HttpEntity reqEntity = multipartEntityBuilder.build();
            httPost.setEntity(reqEntity);

            // 执行post请求.
            CloseableHttpResponse response = httpclient.execute(httPost);

            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // 打印响应内容
                    return EntityUtils.toString(entity, Charset.forName("UTF-8"));
                }
            } finally {
                response.close();

            }
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
