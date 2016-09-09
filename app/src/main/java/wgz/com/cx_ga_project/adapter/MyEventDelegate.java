package wgz.com.cx_ga_project.adapter;

import android.view.View;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by wgz on 2016/8/8.
 */

public interface MyEventDelegate {
    void addData(int length);
    void clear();

    void stopLoadMore();
    void pauseLoadMore();
    void resumeLoadMore();

    void setMore(View view, MyRecyclerArrayAdapter.OnLoadMoreListener listener);
    void setNoMore(View view);
    void setErrorMore(View view);
}
