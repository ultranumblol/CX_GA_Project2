package wgz.com.cx_ga_project.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.adapter.chatAdapter.ChatAdapter;
import wgz.com.cx_ga_project.base.BaseActivity;

public class ChatActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.btn_set_mode_voice)
    Button btnSetModeVoice;
    @Bind(R.id.btn_set_mode_keyboard)
    Button btnSetModeKeyboard;
    @Bind(R.id.et_sendmessage)
    EditText etSendmessage;
    @Bind(R.id.edittext_layout)
    RelativeLayout edittextLayout;
    @Bind(R.id.btn_more)
    Button btnMore;
    @Bind(R.id.btn_send)
    Button btnSend;
    @Bind(R.id.rl_bottom)
    LinearLayout rlBottom;
    @Bind(R.id.view_photo)
    LinearLayout viewPhoto;
    @Bind(R.id.view_camera)
    LinearLayout viewCamera;
    @Bind(R.id.view_location)
    LinearLayout viewLocation;
    @Bind(R.id.custom_botton)
    LinearLayout customBotton;
    @Bind(R.id.view_file)
    LinearLayout viewFile;
    @Bind(R.id.view_audio)
    LinearLayout viewAudio;
    @Bind(R.id.view_video)
    LinearLayout viewVideo;
    @Bind(R.id.view_location_video)
    LinearLayout viewLocationVideo;
    @Bind(R.id.ll_btn_container)
    LinearLayout llBtnContainer;
    @Bind(R.id.more)
    LinearLayout more;
    @Bind(R.id.bar_bottom)
    LinearLayout barBottom;
    @Bind(R.id.pb_load_more)
    ProgressBar pbLoadMore;
    @Bind(R.id.list)
    EasyRecyclerView recyclerview;
    @Bind(R.id.root_layout)
    RelativeLayout rootview;
    private ChatAdapter adapter;
    private InputMethodManager manager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    public void initView() {
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        toolbar.setTitle("指挥通讯");
        toolbar.setSubtitle("警情2134323");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter = new ChatAdapter(this));
        adapter.addAll(initData());
        recyclerview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (more.getVisibility()==View.VISIBLE){
                    more.setVisibility(View.GONE);
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        recyclerview.scrollToPosition(adapter.getCount()-1);

    }

    private List<Map<String, Object>> initData() {
        List<Map<String, Object>> datas = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            Map<String, Object> map = new HashMap<>();
            if (i % 2 == 0) {
                map.put("from", "send");
                map.put("type", "msg");

            } else {
                map.put("from", "recieve");
                map.put("type", "msg");

            }
            datas.add(map);

        }
        return datas;
    }

    /**
     * 隐藏软键盘
     */
    private void hideKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_set_mode_voice, R.id.btn_set_mode_keyboard, R.id.btn_more, R.id.btn_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_set_mode_voice:
                break;
            case R.id.btn_set_mode_keyboard:
                break;
            case R.id.btn_more:
                //llBtnContainer.setVisibility(llBtnContainer.getVisibility()==View.GONE?View.VISIBLE:View.GONE);
                if (more.getVisibility() == View.GONE) {
                    System.out.println("more gone");
                    hideKeyboard();
                    more.setVisibility(View.VISIBLE);
                    llBtnContainer.setVisibility(View.VISIBLE);

                } else {
                        more.setVisibility(View.GONE);
                }
                break;
            case R.id.btn_send:
                break;
        }
    }
}
