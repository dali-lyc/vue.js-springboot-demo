package com.ly.milestone.utils;

import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;

/**
 * Created by lyc28724 on 2017/3/30.
 */
public abstract class WebUtils {

    /**
     * 模拟post请求方法，请求参数为map
     *
     * @param url
     * @param params
     */
    public static String postByMapParameters(String url, Map<String, String> params) {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        Set<String> keySet = params.keySet();
        try {
            NameValuePair[] postData = new NameValuePair[keySet.size()];
            int postDataIndex = 0;
            for (String key : keySet) {
                postData[postDataIndex++] = new NameValuePair(key, params.get(key));
            }
            postMethod.getParams().setContentCharset(Constants.CHARSET_UTF8);
            postMethod.addParameters(postData);
            httpClient.executeMethod(postMethod);
            if (postMethod.getStatusCode() == HttpStatus.SC_OK) {
                String clientUri = new String(postMethod.getResponseBodyAsString());
                return clientUri;
            } else {
                return null;
            }
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            postMethod.releaseConnection();
        }
        return null;
    }

    /**
     * 模拟get请求方法
     *
     * @param url
     * @return
     */
    public static String httpClientGet(String url) {
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(url);
        try {
            httpClient.executeMethod(getMethod);
            if (getMethod.getStatusCode() == HttpStatus.SC_OK) {
                String response = new String(getMethod.getResponseBodyAsString());
                return response;
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            getMethod.releaseConnection();
        }
        return null;
    }

    /**
     * 模拟post请求方法，请求参数为json
     *
     * @param url
     * @param params
     * @return
     */
    public static String postByJsonParameters(String url, JSONObject params) {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        Set<String> keySet = params.keySet();
        try {
            NameValuePair[] postData = new NameValuePair[keySet.size()];
            int postDataIndex = 0;
            for (String key : keySet) {
                postData[postDataIndex++] = new NameValuePair(key, params.getString(key));
            }
            postMethod.getParams().setContentCharset(Constants.CHARSET_UTF8);
            postMethod.addParameters(postData);
            httpClient.executeMethod(postMethod);
            if (postMethod.getStatusCode() == HttpStatus.SC_OK) {
                String response = new String(postMethod.getResponseBodyAsString());
                return response;
            } else {
                return null;
            }
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            postMethod.releaseConnection();
        }
        return null;
    }

    public static String postByJsonString(String url, String jsonParams) {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        try {
            StringRequestEntity entity = new StringRequestEntity(jsonParams, "application/json", Constants.CHARSET_UTF8);
            postMethod.setRequestEntity(entity);
            httpClient.executeMethod(postMethod);
            if (postMethod.getStatusCode() == HttpStatus.SC_OK) {
                String response = new String(postMethod.getResponseBodyAsString());
                return response;
            } else {
                return null;
            }
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            postMethod.releaseConnection();
        }
        return null;
    }

    /**
     * 加密
     *
     * @param str
     * @param method
     * @return
     */
    public static String encode(String str, String method) {
        MessageDigest md;
        String encodeStr = null;
        try {
            md = MessageDigest.getInstance(method);
            md.update(str.getBytes());
            encodeStr = new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    /**
     * 新增cookie
     *
     * @param response
     * @param name
     * @param value
     * @param path
     * @param maxAge
     */
    public static void addCookie(HttpServletResponse response, String name, String value, String path, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 根据cookie的名称获取cookie的值
     *
     * @param request
     * @param name
     * @return
     */
    public static String getCookieValueByName(HttpServletRequest request, String name) {
        if (null != request.getCookies()) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 根据cookie的名称获取cookie
     *
     * @param request
     * @param name
     * @return
     */
    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        if (null != request.getCookies()) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    /**
     * 根据名称清除cookie
     *
     * @param request
     * @param response
     * @param name
     * @param path
     */
    public static void clearCookieByName(HttpServletRequest request, HttpServletResponse response, String name, String path) {
        if (null != request.getCookies()) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(name)) {
                    cookie.setMaxAge(0);
                    cookie.setPath(path);
                    response.addCookie(cookie);
                    return;
                }
            }
        }
    }

    /**
     * 清除cookie
     *
     * @param response
     * @param cookie
     * @param path
     */
    public static void clearCookie(HttpServletResponse response, Cookie cookie, String path) {
        cookie.setMaxAge(0);
        cookie.setPath(path);
        response.addCookie(cookie);
    }
}
