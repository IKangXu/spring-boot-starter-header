/*
 *  Engine3D Technologies Co., Ltd. Copyright 2019,  All rights reserved
 */

package cn.ikangxu.boot.header.common;

import cn.ikangxu.boot.header.util.ObjectUtils;
import cn.ikangxu.boot.header.util.SpringContextUtils;
import org.springframework.stereotype.Component;

/**
 * @author kangxu [xukang@engine3d.com]
 * @version v1.0
 * @className Properties
 * @description
 * @date 2019/11/7 16:21
 */
@Component("propertiesConst")
public class PropertiesConst {

    // 编码
    public static Object NET_CHARSET = SpringContextUtils.getProperty("cn.ikangxu.request.net.charset");
    // 以后就可以使用conn.getOutputStream().write()
    public static Object NET_DO_OUTPUT = SpringContextUtils.getProperty("cn.ikangxu.request.net.do-output");
    // 以后就可以使用conn.getInputStream().read()
    public static Object NET_DO_INPUT = SpringContextUtils.getProperty("cn.ikangxu.request.net.do-input");
    // 是否使用缓存
    public static Object NET_USE_CACHES = SpringContextUtils.getProperty("cn.ikangxu.request.net.use-caches");
    // 连接超时时间
    public static Object NET_CONNECT_TIMEOUT = SpringContextUtils.getProperty("cn.ikangxu.request.net.connect-timeout");
    // 读取超时时间
    public static Object NET_READ_TIMEOUT = SpringContextUtils.getProperty("cn.ikangxu.request.net.read-timeout");

    // 编码
    public static Object HTTP_CLIENT_CHARSET = SpringContextUtils.getProperty("cn.ikangxu.request.httpclient.charset");
    // 设置连接超时时间，单位毫秒。
    public static Object HTTP_CLIENT_CONNECT_TIMEOUT = SpringContextUtils.getProperty("cn.ikangxu.request.httpclient.connect-timeout");
    // 设置从connect Manager获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
    public static Object HTTP_CLIENT_CONNECTION_REQUEST_TIMEOUT = SpringContextUtils.getProperty("cn.ikangxu.request.httpclient.connection-request-timeout");
    // 请求获取数据的超时时间，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
    public static Object HTTP_CLIENT_SOCKET_TIMEOUT = SpringContextUtils.getProperty("cn.ikangxu.request.httpclient.socket-timeout");
    // 是否使用缓存
    public static Object HTTP_CLIENT_USE_CACHES = SpringContextUtils.getProperty("cn.ikangxu.request.httpclient.use-caches");

    static {
        if (!ObjectUtils.isNotEmpty(HTTP_CLIENT_CHARSET)) {
            HTTP_CLIENT_CHARSET = Encoding.UTF_8;
        }
        if (!ObjectUtils.isNotEmpty(HTTP_CLIENT_CONNECT_TIMEOUT)) {
            HTTP_CLIENT_CONNECT_TIMEOUT = 5000;
        }
        if (!ObjectUtils.isNotEmpty(HTTP_CLIENT_CONNECTION_REQUEST_TIMEOUT)) {
            HTTP_CLIENT_CONNECTION_REQUEST_TIMEOUT = 5000;
        }
        if (!ObjectUtils.isNotEmpty(HTTP_CLIENT_SOCKET_TIMEOUT)) {
            HTTP_CLIENT_SOCKET_TIMEOUT = 15000;
        }
        if (!ObjectUtils.isNotEmpty(HTTP_CLIENT_USE_CACHES)) {
            HTTP_CLIENT_USE_CACHES = false;
        }

        if (!ObjectUtils.isNotEmpty(NET_CHARSET)) {
            NET_CHARSET = Encoding.UTF_8;
        }
        if (!ObjectUtils.isNotEmpty(NET_DO_OUTPUT)) {
            NET_DO_OUTPUT = true;
        }
        if (!ObjectUtils.isNotEmpty(NET_DO_INPUT)) {
            NET_DO_INPUT = true;
        }
        if (!ObjectUtils.isNotEmpty(NET_USE_CACHES)) {
            NET_USE_CACHES = false;
        }
        if (!ObjectUtils.isNotEmpty(NET_CONNECT_TIMEOUT)) {
            NET_CONNECT_TIMEOUT = 5000;
        }
        if (!ObjectUtils.isNotEmpty(NET_READ_TIMEOUT)) {
            NET_READ_TIMEOUT = 5000;
        }
    }

}
