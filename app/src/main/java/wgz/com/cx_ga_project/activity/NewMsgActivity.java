package wgz.com.cx_ga_project.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jude.easyrecyclerview.EasyRecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import rx.Subscriber;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.util.RxUtil;
import wgz.datatom.com.utillibrary.util.LogUtil;

public class NewMsgActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.id_newmsg_lv)
    EasyRecyclerView idNewmsgLv;
    @Bind(R.id.content_new_msg)
    RelativeLayout rootview;
    @Bind(R.id.testpic)
    ImageView testpic;

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_msg;
    }

    @Override
    public void initView() {
        toolbar.setTitle("新消息");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       /* app.apiService.detrixPic("8a7b0444-ad65-4af8-93d8-ae0f990016e7", "0", "200")
                .compose(RxUtil.applySchedulers())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("pic error: " + e.toString());
                    }

                    @Override
                    public void onNext(ResponseBody s) {
                        Bitmap btp = BitmapFactory.decodeStream(s.byteStream());
                        testpic.setImageBitmap(btp);

                    }
                });*/

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
