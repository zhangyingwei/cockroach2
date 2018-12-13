package com.zhangyingwei.cockroach2.core.store;

import com.zhangyingwei.cockroach2.session.response.CockroachResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author zhangyw
 * @date: 2018/12/13
 * @desc:
 */
@Slf4j
public class HtmlTitleStore implements IStore {

    @Override
    public void store(CockroachResponse response) {
        if (response.isSuccess()) {
            log.info("title({})",response.select("title").text());
        } else {
            log.info("task error");
        }
    }
}
