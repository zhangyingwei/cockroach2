package com.zhangyingwei.cockroach2.session.request;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.common.enmus.RequestType;
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

    public CockroachRequest(Task task)  {
        this.task = task;
        this.header = new RequestHeader();
    }

    public String getUrl() {
        if (RequestType.GET.equals(this.task.getRequestType())) {
            String paramsContent = this.task.getParams().entrySet().stream()
                    .map(entity -> entity.getKey().concat("=").concat(entity.getValue()))
                    .reduce((left, right) -> left.concat("&").concat(right)).orElse("");
            if (paramsContent == null || paramsContent.length() > 0) {
                return this.task.getUrl().concat("?").concat(paramsContent);
            } else {
                return this.task.getUrl();
            }
        }else {
            return this.task.getUrl();
        }
    }

    public RequestType getRequestType() {
        return task.getRequestType();
    }

    public RequestHeader getHeader() {
        return this.header;
    }

    public Map<String,String> getParams() {
        return task.getParams();
    }
}
