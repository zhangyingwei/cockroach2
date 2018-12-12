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
        if (response.isSuccess()) {
            try {
                System.out.println("responst: " + response.getContent().string());
            } catch (IOException e) {
                System.out.println("get string error: " + e.getLocalizedMessage());
            }
        } else {
            System.out.println("task error");
        }
    }
}
