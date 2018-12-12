package com.zhangyingwei.cockroach2.http.proxy;

/**
 * @author zhangyw
 * @date: 2018/12/12
 * @desc:
 */
public interface ICHttpProxy {
    void add(ProxyInfo proxyInfo);
    ProxyInfo get();
    void delete(ProxyInfo proxyInfo);
    Integer avaliableSize();
    Boolean hasAvaliable();
}
