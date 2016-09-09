package wgz.com.cx_ga_project.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import wgz.com.cx_ga_project.R;

/**
 * Created by qwerr on 2016/8/4.
 */

public abstract class BaseFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutitem(),null);
        ButterKnife.bind(this, view);
        initview(view);

        return view;
    }

    public abstract void initview(View view);
    public abstract  int getLayoutitem();

}
