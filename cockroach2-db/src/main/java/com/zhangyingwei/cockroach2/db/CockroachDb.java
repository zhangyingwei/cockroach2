package com.zhangyingwei.cockroach2.db;

import com.zhangyingwei.cockroach2.common.utils.IdUtils;
import com.zhangyingwei.cockroach2.utils.ListComparator;
import com.zhangyingwei.cockroach2.utils.ListFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author zhangyw
 * @date: 2019/1/11
 * @desc:
 */
public class CockroachDb implements ICockroachDb{
    private CockroachDbManager dbManager = new CockroachDbManager();

    private ExecutorService readWorkers = Executors.newFixedThreadPool(10, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName("cockroach-db-"+ IdUtils.getId(CockroachDb.class.getName()));
            thread.setDaemon(true);
            return thread;
        }
    });

    @Override
    public Long accumulator(String key) {
        return dbManager.accumulator(key);
    }

    @Override
    public Long subtract(String key) {
        return this.dbManager.subtract(key);
    }

    @Override
    public Long getAcc(String key) {
        Future<Long> future = readWorkers.submit(() -> {
            return this.dbManager.getAcc(key);
        });
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    @Override
    public void put(String key,Object value) {
        dbManager.put(key, value);
    }

    @Override
    public void put(String key,Object value, Comparator comparator) {
        this.dbManager.put(key, value, comparator);
    }

    @Override
    public void putInList(String key, Object value) {
        this.dbManager.putInList(key, value);
    }


    @Override
    public void replaceInList(String key, Object value, Comparator comparator) {
        this.dbManager.replaceInList(key, value, comparator);
    }

    @Override
    public <T>T get(String key) {
        Future<T> future = readWorkers.submit(() -> {
            return (T) this.dbManager.get(key);
        });
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T>List<T> getList(String key) {
        Future<List<T>> future = readWorkers.submit(() -> {
            return (List<T>)this.dbManager.getList(key);
        });
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return new ArrayList<T>();
    }

    @Override
    public <T>List<T> getList(String key,Comparator<T> comparator) {
        Future<List<T>> future = readWorkers.submit(() -> {
            return (List<T>) this.dbManager.getList(key, comparator);
        });
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return new ArrayList<T>();
    }

    @Override
    public <T>List<T> getList(String key,ListFilter<T> filter) {
        Future<List<T>> future = readWorkers.submit(() -> {
            return (List<T>) this.dbManager.getList(key, filter);
        });
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return new ArrayList<T>();
    }

    @Override
    public <T>List<T> getList(String key,ListFilter<T> filter, Comparator<T> comparator) {
        Future<List<T>> future = readWorkers.submit(() -> {
            return (List<T>) this.getList(key, filter, comparator);
        });
        try {
            return future.get();
        } catch (InterruptedException |ExecutionException e) {
            e.printStackTrace();
        }
        return new ArrayList<T>();
    }
}
