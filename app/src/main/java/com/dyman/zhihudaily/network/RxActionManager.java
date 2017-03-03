package com.dyman.zhihudaily.network;

import rx.Subscription;

/**
 * Created by dyman on 2017/3/3.
 */

public interface RxActionManager<T> {

    void add(T tag, Subscription subscription);
    void remove(T tag);

    void cancel(T tag);

    void cancelAll();
}
