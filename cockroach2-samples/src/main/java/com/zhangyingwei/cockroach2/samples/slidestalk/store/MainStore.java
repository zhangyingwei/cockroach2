package com.zhangyingwei.cockroach2.samples.slidestalk.store;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.core.store.IStore;
import com.zhangyingwei.cockroach2.session.response.CockroachResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangyw
 * @date: 2018/12/14
 * @desc:
 */
@Slf4j
public class MainStore implements IStore {
    private IStore categoriesStore = new CategoriesStore();
    @Override
    public void store(CockroachResponse response) {
        if (response.isGroup("ss")) {
            response.select(".slta-dropdown-menu").select("li > a").stream()
                    .forEach(element -> {
                        String url = element.attr("href");
                        String type = element.text();
                        Task task = new Task("https://www.slidestalk.com".concat(url), "ss.categories")
                                .higherPriorityBy(response.getTask());
                        task.setData(type);
                        response.getQueue().add(task);
                    });
        } else {
            this.categoriesStore.store(response);
        }
    }
}
