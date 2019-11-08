/*
 * Engine3D Technologies Co., Ltd. Copyright 2019, All rights reserved
 */

package cn.ikangxu.boot.header.common.request;

/**
 *
 * @className HttpMethod
 * @description HTTP请求类型
 * @author kangxu [xukang@engine3d.com]
 * @date 2019/3/7 11:25
 * @version v1.0
 */
public interface HttpMethod {

    public final static String GET = "GET";
    public final static String HEAD = "HEAD";
    public final static String POST = "POST";
    public final static String PUT = "PUT";
    public final static String PATCH = "PATCH";
    public final static String DELETE = "DELETE";
    public final static String OPTIONS = "OPTIONS";
    public final static String TRACE = "TRACE";

}
