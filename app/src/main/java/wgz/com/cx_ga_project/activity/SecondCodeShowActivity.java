package wgz.com.cx_ga_project.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import wgz.com.cx_ga_project.R;
import wgz.com.cx_ga_project.base.BaseActivity;

public class SecondCodeShowActivity extends BaseActivity {


    @Bind(R.id.id_tv_secoudtext)
    TextView idTvSecoudtext;
    @Bind(R.id.activity_second_code_show)
    RelativeLayout rootview;
    @Bind(R.id.codebitmap)
    ImageView codebitmap;

    @Override
    public int getLayoutId() {
        return R.layout.activity_second_code_show;
    }

    @Override
    public void initView() {
        setTitle("扫码结果");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String str = bundle.getString("result");
       // Bitmap bitmap = Bitmap.createBitmap((Bitmap) bundle.getParcelable("bitmap"));
        idTvSecoudtext.setText(str);
        idTvSecoudtext.setAutoLinkMask(Linkify.WEB_URLS);
        idTvSecoudtext.setMovementMethod(LinkMovementMethod.getInstance());



       /* if (bitmap!=null){
            codebitmap.setImageBitmap(bitmap);
        }*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.saomiao, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivityForResult(new Intent(SecondCodeShowActivity.this, SaoYiSaoActivity.class), 6);

        }
        if (id == android.R.id.home) {
            onBackPressed();
        }


        return super.onOptionsItemSelected(item);
    }
}
