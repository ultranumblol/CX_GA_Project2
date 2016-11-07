package wgz.com.cx_ga_project.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.functions.Action1;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.app;
import wgz.com.cx_ga_project.base.BaseActivity;
import wgz.com.cx_ga_project.entity.TypeOfAuth;
import wgz.com.cx_ga_project.util.RxUtil;
import wgz.com.cx_ga_project.util.SomeUtil;
import wgz.com.cx_ga_project.view.Mylayout;
import wgz.datatom.com.utillibrary.util.LogUtil;

public class SICInputActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.sic_input_recyclerview)
    EasyRecyclerView recyclerview;
    @Bind(R.id.content_sicinput2)
    RelativeLayout rootview;
    @Bind(R.id.upload_sic)
    FloatingActionButton uploadSic;
    @Bind(R.id.testrv)
    RecyclerView testrv;
    @Bind(R.id.testlv)
    ListView testlv;
    @Bind(R.id.tesllv)
    LinearLayout tesllv;
    //private SICInputAdapter adapter;
    private List<Map<String, Object>> mdata = new ArrayList<>();
    private Mylayout manager;
    private String typeid = "";
    private String typename = "";
    private List<TypeOfAuth.AuthRe> typedata = new ArrayList();
    private String authid = "";
    private String picids = "";
    private String videoids = "";
    // private HomeAdapter mAdapter;
    private listviewadapter listviewadapter;
    private HashMap<Integer, View> lmap = new HashMap<Integer, View>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_sicinput2;
    }

    @Override
    public void initView() {
        toolbar.setTitle("社会信息采集");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        typename = intent.getStringExtra("typename");
        typeid = intent.getStringExtra("typeid");
        toolbar.setSubtitle(typename);
        LogUtil.d("typename: " + typename);
        LogUtil.d("typeid: " + typeid);
        recyclerview.setLayoutManager(manager = new Mylayout(this));
        //recyclerview.setAdapter(adapter = new SICInputAdapter(this));
        testlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EditText ev = (EditText) view.findViewById(R.id.sic_input_content);
                ev.requestFocus();
            }
        });


        initData();
        RxView.clicks(uploadSic).throttleFirst(500, TimeUnit.MICROSECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        String[] typename = new String[typedata.size()];
                        final String[] typeid = new String[typedata.size()];
                        for (int i = 0; i < typedata.size(); i++) {
                            typename[i] = typedata.get(i).getValname();
                            typeid[i] = typedata.get(i).getValcode();

                        }
                        AlertDialog.Builder builder = new AlertDialog.Builder(SICInputActivity.this);
                        builder.setTitle("请选择数据权限")
                                .setSingleChoiceItems(typename, -1, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        authid = typeid[which];
                                        LogUtil.d("authID : " + authid);
                                    }
                                }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UploadInfo();
                            }
                        }).setNegativeButton("取消", null).show();


                    }
                });

    }

    private void UploadInfo() {
        Map<String, Object> paraValue = new HashMap<>();
        LogUtil.d("mdata.size :" + mdata.size());
        LogUtil.d("lmap.size :" + lmap.size());

        for (int i = 0; i < mdata.size(); i++) {
            try {

                View itemview = lmap.get(i);
                TextView titleview = (TextView) itemview.findViewById(R.id.sic_input_titleid);
                EditText contentview = (EditText) itemview.findViewById(R.id.sic_input_content);
                String key = titleview.getText().toString();
                String value = contentview.getText().toString();
                paraValue.put(key, value);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        paraValue.put("policeid", SomeUtil.getUserId());
        paraValue.put("addtime", SomeUtil.getSysTime());
        paraValue.put("authid", authid);


        LogUtil.d("sicinput json : " + SomeUtil.mapTojsonStr(paraValue));
        app.apiService.addSocialInfoTxt(typeid, SomeUtil.mapTojsonStr(paraValue), SomeUtil.getUserId()
                , picids, videoids)
                .compose(RxUtil.<String>applySchedulers())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.d("sicinputresult : " + s);
                    }
                });

    }

    private void initData() {
        app.apiService.getFieldNameByType(typeid)
                .compose(RxUtil.<String>applySchedulers())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        //adapter.addAll(mdata);
                        //testrv.setAdapter(mAdapter = new HomeAdapter());
                        // testlv.setAdapter(listviewadapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("sicinput error : " + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        //listviewadapter = new listviewadapter();
                        mdata = SomeUtil.JsonStrToListMap(s);
                        for (int i = 0; i < mdata.size(); i++) {
                            View view = LayoutInflater.from(SICInputActivity.this).inflate(R.layout.item_input_sic, null);
                            TextView tvname = (TextView) view.findViewById(R.id.sic_input_title);
                            TextView tvid = (TextView) view.findViewById(R.id.sic_input_titleid);
                            tvname.setText(mdata.get(i).get("zh_name").toString());
                            tvid.setText(mdata.get(i).get("en_name").toString());
                            tesllv.addView(view);
                        }




                        LogUtil.d("listmap : " + mdata.toString());

                    }
                });

        app.apiService.getTypeOfAuth()
                .compose(RxUtil.<TypeOfAuth>applySchedulers())
                .subscribe(new Subscriber<TypeOfAuth>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        SomeUtil.checkHttpException(getApplicationContext(), e, rootview);
                    }

                    @Override
                    public void onNext(TypeOfAuth typeOfAuth) {
                        typedata = typeOfAuth.getRes();
                        LogUtil.d("typeOfAuth : " + typedata.toString());
                    }
                });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    class listviewadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mdata.size();
        }

        @Override
        public Object getItem(int position) {
            return mdata.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder ViewHolder;

            convertView = LayoutInflater.from(SICInputActivity.this).inflate(R.layout.item_input_sic, null);
            ViewHolder = new ViewHolder(convertView);
            lmap.put(position, convertView);
            convertView = lmap.get(position);
            ViewHolder.title.setText(mdata.get(position).get("zh_name").toString());
            ViewHolder.id.setText(mdata.get(position).get("en_name").toString());


            return convertView;
        }

        //添加viewHolder
        class ViewHolder {
            TextView title, id;

            public ViewHolder(View convertView) {
                title = (TextView) convertView.findViewById(R.id.sic_input_title);

                id = (TextView) convertView.findViewById(R.id.sic_input_titleid);

            }

        }

    }


}
