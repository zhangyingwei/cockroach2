package com.zhangyingwei.cockroach2.http.proxy;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.common.enmus.ProxyType;
import com.zhangyingwei.cockroach2.common.utils.IdUtils;
import lombok.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
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

    public boolean valid() {
        Socket testConnection = null;
        try {
            Proxy.Type type = null;
            switch (this.proxyType) {
                case HTTP:
                    type = Proxy.Type.HTTP;
                    break;
                case SOCKET5:
                    type = Proxy.Type.SOCKS;
                    break;
                default:
                    break;
            }
            testConnection = new Socket(new Proxy(type, new InetSocketAddress(this.ip, this.port)));
            testConnection.connect(new InetSocketAddress("8.8.8.8", 53));
            return true;
        } catch (Exception e) {
            return false;
        }finally {
            if (testConnection != null) {
                try {
                    testConnection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
