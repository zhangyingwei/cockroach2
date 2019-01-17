package com.zhangyingwei.cockroach2.samples.proxysample;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.common.enmus.ProxyType;
import com.zhangyingwei.cockroach2.common.generators.ICGenerator;
import com.zhangyingwei.cockroach2.http.proxy.ProxyInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangyw
 * @date: 2019/1/17
 * @desc:
 */
@Slf4j
public class SelfProxyGenerator implements ICGenerator<ProxyInfo> {
    @Override
    public ProxyInfo generate(Task task) {
        ProxyInfo proxyInfo = new ProxyInfo("119.101.113.232", 9999, ProxyType.HTTP);
        log.info("{} do with proxy: {} - {}", task.getUrl(), proxyInfo, proxyInfo.valid(task));
        return proxyInfo;
    }
}
