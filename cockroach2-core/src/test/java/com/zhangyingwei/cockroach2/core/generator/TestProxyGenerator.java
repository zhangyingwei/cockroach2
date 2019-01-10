package com.zhangyingwei.cockroach2.core.generator;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.common.enmus.ProxyType;
import com.zhangyingwei.cockroach2.common.generators.ICGenerator;
import com.zhangyingwei.cockroach2.http.proxy.ProxyInfo;

/**
 * @author zhangyw
 * @date: 2019/1/10
 * @desc:
 */
public class TestProxyGenerator implements ICGenerator<ProxyInfo> {
    @Override
    public ProxyInfo generate(Task task) {
        return new ProxyInfo(null, null, ProxyType.HTTP);
    }
}
