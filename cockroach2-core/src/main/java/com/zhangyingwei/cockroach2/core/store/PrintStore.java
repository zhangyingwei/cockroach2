package com.zhangyingwei.cockroach2.core.store;

import com.zhangyingwei.cockroach2.session.response.CockroachResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author zhangyw
 * @date: 2018/12/12
 * @desc:
 */
@Slf4j
public class PrintStore implements IStore {
    @Override
    public void store(CockroachResponse response) {
        log.info("responst: " + response.getContent().charset("UTF-8").string());
    }

    @Override
    public void faild(CockroachResponse response) {
        log.info("task faild");
    }
}
