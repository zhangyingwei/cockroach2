package com.zhangyingwei.cockroach2.db;

import com.zhangyingwei.cockroach2.utils.ListFilter;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * @author zhangyw
 * @date: 2019/1/11
 * @desc:
 */
public class CockroachDbManager implements ICockroachDb{
    private Map<String, Long> accumulatorTable = new ConcurrentHashMap<String, Long>();
    private Map<String, Object> objectTable = new ConcurrentHashMap<String, Object>();
    private Map<String, CopyOnWriteArrayList> listTable = new ConcurrentHashMap<String, CopyOnWriteArrayList>();

    /**
     * 累加器
     * 每调用一次，累加器的值会加一
     * @param key
     * @return
     */
    @Override
    public Long accumulator(String key) {
        Long count = accumulatorTable.getOrDefault(key, 0L);
        accumulatorTable.put(key, ++count);
        return accumulatorTable.get(key);
    }

    @Override
    public Long getAcc(String key) {
        return this.accumulatorTable.getOrDefault(key, 0L);
    }

    /**
     * 键值对
     * @param key
     * @param value
     */
    @Override
    public void put(String key,Object value) {
        this.objectTable.put(key, value);
    }

    /**
     * 与旧值做对比，符合条件的插入库中
     * @param key
     * @param value
     * @param comparator
     */
    @Override
    public void put(String key,Object value, Comparator comparator) {
        Object old = this.objectTable.get(key);
        if (old == null) {
            this.put(key, value);
        } else if (comparator.compare(old, value) > 0) {
            put(key, value);
        }
    }

    @Override
    public void putInList(String key, Object value) {
        CopyOnWriteArrayList list = this.listTable.getOrDefault(key, new CopyOnWriteArrayList());
        list.add(value);
        this.listTable.put(key, list);
    }

    /**
     * 根据 key 获取 value
     * @param key
     * @param <T>
     * @return
     */
    @Override
    public <T>T get(String key) {
        return (T) this.objectTable.get(key);
    }


    /**
     * 获取list
     * @param key
     * @return
     */
    @Override
    public <T>List<T> getList(String key) {
        return this.listTable.get(key);
    }

    /**
     * 获取list并排序
     * @param key
     * @param comparator
     * @return
     */
    @Override
    public <T>List<T> getList(String key,Comparator<T> comparator) {
        List<T> resultList = getList(key);
        return resultList.parallelStream().sorted(comparator).collect(Collectors.toList());
    }

    /**
     * 获取list并filter
     * @param key
     * @param filter
     * @return
     */
    @Override
    public <T>List<T> getList(String key,ListFilter<T> filter) {
        List<T> resultList = getList(key);
        return resultList.parallelStream().filter(filter::filter).collect(Collectors.toList());
    }

    /**
     * 获取list并 filter 然后 排序
     * @param key
     * @param comparator
     * @return
     */
    @Override
    public <T>List<T> getList(String key,ListFilter<T> filter, Comparator<T> comparator) {
        return getList(key,filter).parallelStream().sorted(comparator).collect(Collectors.toList());
    }
}
