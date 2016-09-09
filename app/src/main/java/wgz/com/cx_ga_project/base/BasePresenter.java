package wgz.com.cx_ga_project.base;

/**
 * Created by wgz on 2016/7/25.
 */

public abstract class BasePresenter<T> {
    public T mView;
    public  void setView(T mView){
        this.mView = mView;


    }
    public void Destroy(){
        mView = null;

    }

}
