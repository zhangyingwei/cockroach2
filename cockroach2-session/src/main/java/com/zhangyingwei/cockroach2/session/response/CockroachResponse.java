package com.zhangyingwei.cockroach2.session.response;

import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.queue.ICQueue;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author zhangyw
 * @date: 2018/12/11
 * @desc:
 */
@Slf4j
public class CockroachResponse {
    @Getter @Setter
    private boolean success;
    @Getter@Setter
    private int code;
    @Getter @Setter
    private Task task;
    @Getter @Setter
    private ICQueue queue;
    @Getter@Setter
    private CockroachResponseContent content;
    @Getter @Setter
    private CockroachResponseHeaders headers;

    public CockroachResponse(CockroachResponseContent content, CockroachResponseHeaders header, Integer code, Boolean success) {
        this.task = task;
        this.content = content;
        this.headers = header;
        this.code = code;
        this.success = success;
    }


    /**
     * 使用 css 选择器进行选择
     * @param cssSelector
     * @return
     */
    public Elements select(String cssSelector) {
        return this.content.toDocument().select(cssSelector);
    }

    /**
     * 使用 xparh 选择器
     * @param xpath
     * @return
     * @throws XpathSyntaxErrorException
     */
    //TODO 不好使
    private Elements xpath(String xpath) throws XpathSyntaxErrorException {
        List<Element> elements = this.content.toXDocument().selN(xpath).stream().map(node -> node.getElement()).collect(Collectors.toList());
        return new Elements(Optional.ofNullable(elements).orElse(new ArrayList<>()));
    }

    public Boolean isGroup(String group) {
        return this.task.getGroup().equals(group);
    }

    public Boolean isGroupStartWith(String group) {
        return this.task.getGroup().startsWith(group);
    }

    public Boolean isGroupEndWith(String group) {
        return this.task.getGroup().endsWith(group);
    }

    public Boolean isGroupContains(String group) {
        return this.task.getGroup().contains(group);
    }

    public void close() {
        try {
            this.getContent().getInputStream().close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}