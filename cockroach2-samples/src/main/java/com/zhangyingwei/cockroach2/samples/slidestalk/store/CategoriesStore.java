package com.zhangyingwei.cockroach2.samples.slidestalk.store;

import com.zhangyingwei.cockroach2.common.Task;
import com.zhangyingwei.cockroach2.core.store.IStore;
import com.zhangyingwei.cockroach2.session.response.CockroachResponse;

/**
 * @author zhangyw
 * @date: 2018/12/14
 * @desc:
 */
public class CategoriesStore implements IStore {

    private IStore itemStore = new ItemStore();

    @Override
    public void store(CockroachResponse response) {
        if (response.isGroup("ss.categories")) {
            String type = response.getTask().getData();
            response.select(".slidecard").select(".slidecard-image > a").stream()
                    .forEach(element -> {
                        String url = element.attr("href");
                        Task task = new Task("https://www.slidestalk.com".concat(url), "ss.categories.item").lowerPriorityBy(response.getTask());
                        task.setData(type);
                        response.getQueue().add(task);
                    });
        } else {
            this.itemStore.store(response);
        }
    }

    @Override
    public void faild(CockroachResponse response) {}
}
