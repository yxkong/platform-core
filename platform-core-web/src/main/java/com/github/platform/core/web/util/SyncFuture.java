package com.github.platform.core.web.util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

/**
 * @author wangxiaozhou
 * @create 2023/2/24 上午11:30
 * @desc SyncFuture
 */
public class SyncFuture<V> implements Future {

    private Supplier<V> supplier;

    public SyncFuture(Supplier<V> supplier) {
        this.supplier = supplier;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return true;
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        return supplier.get();
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return supplier.get();
    }
}
