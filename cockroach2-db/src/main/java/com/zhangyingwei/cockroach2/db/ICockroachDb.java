package com.zhangyingwei.cockroach2.db;

import com.zhangyingwei.cockroach2.utils.ListComparator;
import com.zhangyingwei.cockroach2.utils.ListFilter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangyw
 * @date: 2019/1/11
 * @desc:
 */
public interface ICockroachDb {
    /**
     * 累加器
     * 每调用一次，累加器的值会加一
     * @param key
     * @return
     */
    public Long accumulator(String key);

    /**
     * 减少
     * @param key
     * @return
     */
    public Long subtract(String key);

    /**
     * 累加器
     * 每调用一次，累加器的值会加一
     * @param key
     * @return
     */
    public Long getAcc(String key);

    /**
     * 键值对
     * @param key
     * @param value
     */
    public void put(String key,Object value);

    /**
     * 与旧值做对比，符合条件的插入库中
     * @param key
     * @param value
     * @param comparator
     */
    public void put(String key,Object value, Comparator comparator);

    /**
     * 添加值到list中
     * @param key
     * @param value
     */
    void putInList(String key,Object value);

    /**
     * 替换list中的值
     * @param key
     * @param value
     */
    void replaceInList(String key, Object value, Comparator comparator);

    /**
     * 根据 key 获取 value
     * @param key
     * @param <T>
     * @return
     */
    public <T>T get(String key);


    /**
     * 获取list
     * @param key
     * @return
     */
    public <T> List<T> getList(String key);

    /**
     * 获取list并排序
     * @param key
     * @param comparator
     * @return
     */
    public <T>List<T> getList(String key,Comparator<T> comparator);

    /**
     * 获取list并filter
     * @param key
     * @param filter
     * @return
     */
    public <T>List<T> getList(String key, ListFilter<T> filter);

    /**
     * 获取list并 filter 然后 排序
     * @param key
     * @param comparator
     * @return
     */
    public <T>List<T> getList(String key,ListFilter<T> filter, Comparator<T> comparator);
}
