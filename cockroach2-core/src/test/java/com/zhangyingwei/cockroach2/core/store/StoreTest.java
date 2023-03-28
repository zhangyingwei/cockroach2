package com.zhangyingwei.cockroach2.core.store;

import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import com.zhangyingwei.cockroach2.session.response.CockroachResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import static org.junit.Assert.*;

/**
 * @author zhangyw
 * @date: 2018/12/13
 * @desc:
 */
@Slf4j
public class StoreTest implements IStore{
    @Override
    public void store(CockroachResponse response) {
        response.select("body > main > div > article > section > a").stream().forEach(ele -> {
            log.info(ele.attr("title"));
        });
    }

    @Override
    public void faild(CockroachResponse response) {
        // failed
    }
}