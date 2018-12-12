package com.zhangyingwei.cockroach2.session.request;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.common.enmus.RequestType;
import com.zhangyingwei.cockroach2.common.exception.CockroachUrlNotValidException;
import com.zhangyingwei.cockroach2.queue.ICQueue;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author zhangyw
 * @date: 2018/12/11
 * @desc:
 */
public class CockroachRequest {
    @Getter
    private Task task;
    @Setter @Getter
    private ICQueue queue;
    private RequestHeader header;

    public CockroachRequest(Task task) throws CockroachUrlNotValidException {
        this.task = task;
        if (null == task.getUrl()) {
            throw new CockroachUrlNotValidException(task.getUrl());
        }
        this.header = new RequestHeader();
    }

    public String getUrl() {
        if (RequestType.GET.equals(this.task.getRequestType())) {
            String paramsContent = this.task.getParams().entrySet().stream().map(entity -> {
                return entity.getKey().concat("=").concat(entity.getValue());
            }).reduce((left, right) -> {
                return left.concat("&").concat(right);
            }).get();
            return this.task.getUrl().concat(paramsContent);
        }else {
            return this.getUrl();
        }
    }

    public RequestType getRequestType() {
        return task.getRequestType();
    }

    public RequestHeader getHeaders() {
        return this.header;
    }

    public Map<String,String> getParams() {
        return task.getParams();
    }
}
