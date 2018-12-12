package com.zhangyingwei.cockroach2.http.proxy;

import com.zhangyingwei.cockroach2.common.enmus.ProxyType;
import com.zhangyingwei.cockroach2.common.utils.IdUtils;
import lombok.*;

import java.util.Objects;

/**
 * @author zhangyw
 * @date: 2018/12/12
 * @desc:
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ProxyInfo {
    private Long id = IdUtils.getId("ProxyInfo");
    @NonNull
    private String ip;
    @NonNull private Integer port;
    private String username;
    private String password;
    @NonNull private ProxyType proxyType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProxyInfo proxyInfo = (ProxyInfo) o;
        return id.equals(proxyInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
