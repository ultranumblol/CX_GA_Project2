package wgz.com.cx_ga_project.util;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wgz on 2016/10/11.
 */

public class RxUtil {

    private final static Observable.Transformer schedulersTransformer = observable -> ((Observable)  observable).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread());

    public static  <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) schedulersTransformer;
    }

}
