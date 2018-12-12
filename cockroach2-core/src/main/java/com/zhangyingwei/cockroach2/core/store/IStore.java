package com.zhangyingwei.cockroach2.core.store;

import com.zhangyingwei.cockroach2.session.response.CockroachResponse;

/**
 * @author zhangyw
 * @date: 2018/12/12
 * @desc:
 */
public interface IStore {
    void store(CockroachResponse response);
}
