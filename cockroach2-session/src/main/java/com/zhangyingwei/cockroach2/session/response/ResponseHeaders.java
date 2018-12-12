package com.zhangyingwei.cockroach2.session.response;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyw
 * @date: 2018/12/12
 * @desc:
 */
@RequiredArgsConstructor
public class ResponseHeaders {
    @Getter @Setter @NonNull
    private Map<String, List<String>> headers;
}
