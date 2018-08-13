package org.kuska.weatherapp.util.rx;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2018/08/12
 */
public class TestAppSchedulers implements AppSchedulers {
    @Override
    public Scheduler ui() {
        return Schedulers.immediate();
    }

    @Override
    public Scheduler background() {
        return Schedulers.immediate();
    }

    @Override
    public Scheduler current() {
        return Schedulers.immediate();
    }
}
