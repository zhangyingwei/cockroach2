package com.zhangyingwei.cockroach2.session.response;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.queue.ICQueue;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangyw
 * @date: 2018/12/11
 * @desc:
 */
@Slf4j
@RequiredArgsConstructor
public class CockroachResponse {
    @Getter @Setter
    private boolean success;
    @Getter@Setter
    private int code;
    @Getter
    @NonNull
    private Task task;
    @Getter @Setter
    private ICQueue queue;
    @Getter@Setter
    private CockroachResponseContent content;
    @Getter @Setter
    private ResponseHeaders headers;
}