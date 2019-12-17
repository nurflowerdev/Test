package com.nurflower.test.utils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class LiveDataTestUtil {

    public static <T> T getValue(LiveData<List<T>> liveData) throws InterruptedException {
        final Object[] data = new Object[1];
        CountDownLatch latch = new CountDownLatch(1);

        Observer<List<T>> observer = new Observer<List<T>>() {
            @Override
            public void onChanged(List<T> ts) {
                data[0] = ts.get(0);
                latch.countDown();
                liveData.removeObserver(this);
            }
        };

        liveData.observeForever(observer);


        latch.await(2, TimeUnit.SECONDS);
        //noinspection unchecked
        return (T) data[0];
    }
}
