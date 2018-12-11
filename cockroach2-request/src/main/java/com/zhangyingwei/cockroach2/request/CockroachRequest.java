package com.zhangyingwei.cockroach2.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangyw
 * @date: 2018/12/11
 * @desc:
 */
@Builder
public class CockroachRequest {
    @Setter
    private String url;
    @Setter
    private Map<String, String> params;
    @Getter @Setter
    private RequestHeader headers = new RequestHeader();
    @Getter @Setter
    private RequestType requestType;


    public String getUrl() {
        String paramsContent = params.entrySet().stream().map(entity -> {
            return entity.getKey().concat("=").concat(entity.getValue());
        }).reduce((left, right) -> {
            return left.concat("&").concat(right);
        }).get();
        return this.url.concat(paramsContent);
    }
}
