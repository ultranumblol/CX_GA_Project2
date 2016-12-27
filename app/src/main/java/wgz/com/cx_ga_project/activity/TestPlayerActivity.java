package wgz.com.cx_ga_project.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.uniview.airimos.Player;
import com.uniview.airimos.listener.OnDragReplayListener;
import com.uniview.airimos.listener.OnGetAlarmListener;
import com.uniview.airimos.listener.OnLoginListener;
import com.uniview.airimos.listener.OnPtzCommandListener;
import com.uniview.airimos.listener.OnPushAlarmListener;
import com.uniview.airimos.listener.OnQueryReplayListener;
import com.uniview.airimos.listener.OnQueryResourceListener;
import com.uniview.airimos.listener.OnStartLiveListener;
import com.uniview.airimos.listener.OnStartReplayListener;
import com.uniview.airimos.manager.ServiceManager;
import com.uniview.airimos.obj.AlarmInfo;
import com.uniview.airimos.obj.QueryCondition;
import com.uniview.airimos.obj.RecordInfo;
import com.uniview.airimos.obj.ResourceInfo;
import com.uniview.airimos.parameter.LoginParam;
import com.uniview.airimos.parameter.PtzCommandParam;
import com.uniview.airimos.parameter.PushAlarmParam;
import com.uniview.airimos.parameter.QueryReplayParam;
import com.uniview.airimos.parameter.QueryResourceParam;
import com.uniview.airimos.parameter.StartLiveParam;
import com.uniview.airimos.parameter.StartReplayParam;
import com.uniview.airimos.service.KeepaliveService;
import com.uniview.airimos.thread.RecvStreamThread;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import wgz.com.cx_ga_project.R;
import wgz.datatom.com.utillibrary.util.LogUtil;

/**
 * Created by wgz on 2016/12/26.
 */

public class TestPlayerActivity extends Activity implements View.OnClickListener, KeepaliveService.OnKeepaliveListener, OnLoginListener {
    private static final String TAG = "TestPlayerActivity";
    @Bind(R.id.fab2)
    FloatingActionButton fab2;
    @Bind(R.id.tips)
    TextView tips;
    @Bind(R.id.fab_down)
    FloatingActionButton fabDown;
    @Bind(R.id.fab_right)
    FloatingActionButton fabRight;
    @Bind(R.id.fab_up)
    FloatingActionButton fabUp;
    @Bind(R.id.fab_left)
    FloatingActionButton fabLeft;
    private Player mPlayer;
    private SurfaceView mSurfaceView;
    private Button mBtnStartLive;
    private Button mBtnStopLive;
    private Button mBtnStartReplay;
    private Button mBtnStopReplay;
    private Button mBtnDragReplay;
    private Button mBtnSnatch;
    private Button mBtnRecord;
    private Button mBtnStopRecord;
    private Button mBtnMute;
    private Button mBtnQueryRes;
    private Button mBtnGetAlarm;
    private Button mBtnPushAlarm;
    private EditText mEditCamCode;
    private EditText mEditReplayBeginTime;
    private EditText mEditReplayEndTime;
    private KeepaliveService mService = null;
    private boolean mBound = false;
    private boolean mRequireLogout = false;
    private RecvStreamThread mRecvStreamThread = null;

    private Button mPtzControl;
    private String mCameraCode = "";
    private boolean isplaying = false;
    private boolean ischanging = false;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            KeepaliveService.KeepaliveBinder binder = (KeepaliveService.KeepaliveBinder) service;
            mService = binder.getService();
            mService.start(TestPlayerActivity.this);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testplayer);
        ButterKnife.bind(this);
        //SurfaceView用于渲染
        dologin();
        Intent intent = getIntent();
        mCameraCode = intent.getStringExtra("camid");
        //mCameraCode = "53230101001130039082_1";//内网摄像头测试id


        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);

        //监听SurfaceView的变化
        mSurfaceView.getHolder().addCallback(new surfaceCallback());


        mBtnStartLive = (Button) findViewById(R.id.btn_start_live);
        mBtnStopLive = (Button) findViewById(R.id.btn_stop_live);
        mBtnStartReplay = (Button) findViewById(R.id.btn_start_replay);
        mBtnStopReplay = (Button) findViewById(R.id.btn_stop_replay);
        mBtnDragReplay = (Button) findViewById(R.id.btn_drag_replay);
        mEditReplayBeginTime = (EditText) findViewById(R.id.edit_replay_begin_time);
        mEditReplayEndTime = (EditText) findViewById(R.id.edit_replay_end_time);
        mBtnSnatch = (Button) findViewById(R.id.btn_snatch);
        mPtzControl = (Button) findViewById(R.id.ptzControl);
        mBtnRecord = (Button) findViewById(R.id.btn_record);
        mBtnStopRecord = (Button) findViewById(R.id.btn_stop_record);
        mBtnMute = (Button) findViewById(R.id.btn_mute);
        mBtnGetAlarm = (Button) findViewById(R.id.btn_get_alarm);
        mBtnPushAlarm = (Button) findViewById(R.id.btn_push_alarm);

        mBtnQueryRes = (Button) findViewById(R.id.btn_query_res);
        mEditCamCode = (EditText) findViewById(R.id.edit_camera_code);


        //初始化一个Player对象
        mPlayer = new Player();
        mPlayer.AVInitialize(mSurfaceView.getHolder());
    }

    private void dologin() {
        // 设置登录参数
        LoginParam params = new LoginParam();
        params.setServer("53.20.31.5");
        params.setPort(52060);
        params.setUserName("loadmin");
        params.setPassword("Cxsgaj123456");

        /*params.setServer("60.12.249.169");
        params.setPort(52060);
        params.setUserName("test10");
        params.setPassword("123abc");*/

        //调用登录接口
        ServiceManager.login(params, TestPlayerActivity.this);

    }

    @Override
    protected void onStart() {

        //启动保活服务。若不想用提供的保活服务类，可通过每十秒调用保活接口ServiceManager.keepalive实现保活
        if (!mBound) {
            Intent intent = new Intent(this, KeepaliveService.class);
            startService(intent);
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }

        mBtnStartLive.setOnClickListener(this);
        mBtnStopLive.setOnClickListener(this);
        mBtnStartReplay.setOnClickListener(this);
        mBtnStopReplay.setOnClickListener(this);
        mBtnDragReplay.setOnClickListener(this);
        mBtnSnatch.setOnClickListener(this);
        mBtnQueryRes.setOnClickListener(this);
        mPtzControl.setOnClickListener(this);
        mBtnRecord.setOnClickListener(this);
        mBtnStopRecord.setOnClickListener(this);
        mBtnMute.setOnClickListener(this);
        mBtnGetAlarm.setOnClickListener(this);
        mBtnPushAlarm.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fabRight.setOnClickListener(this);
        fabLeft.setOnClickListener(this);
        fabDown.setOnClickListener(this);
        fabUp.setOnClickListener(this);

        //模拟一个查询回放起始和结束的时间
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String begin = formatter.format(now) + " 00:00:00";
        String end = formatter.format(now) + " 23:59:59";
        mEditReplayBeginTime.setText(begin);
        mEditReplayEndTime.setText(end);


        super.onStart();
    }

    @Override
    protected void onStop() {
        //解除保活服务的绑定
        if (mBound) {
            unbindService(mConnection);
            mBound = false;

            if (mRequireLogout) {
                Intent serviceIntent = new Intent(TestPlayerActivity.this, KeepaliveService.class);
                stopService(serviceIntent);
            }
        }

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        //销毁Player
        if (null != mPlayer) {
            mPlayer.AVFinalize();
        }

        //退出登录，一般在用户手动退出时调用，这里只作演示
        ServiceManager.logout(null);
        super.onDestroy();
    }

    /**
     * 启动实况
     *
     * @param cameraCode 摄像机编码
     */
    public void startLive(String cameraCode) {
        try {
            //启动实况的结果监听
            OnStartLiveListener listener = new OnStartLiveListener() {
                @Override
                public void onStartLiveResult(long errorCode, String errorDesc, String playSession) {
                    //将播放会话设给Player
                    mPlayer.setPlaySession(playSession);

                    //如果已经在播放，则先停掉
                    if (mRecvStreamThread != null) {
                        mPlayer.AVStopPlay();
                        mRecvStreamThread.interrupt();
                        mRecvStreamThread = null;
                    }

                    //启动播放解码
                    mPlayer.AVStartPlay();

                    //收流线程启动
                    mRecvStreamThread = new RecvStreamThread(mPlayer, playSession);
                    mRecvStreamThread.start();
                }

            };

            //设置启动实况的参数
            StartLiveParam p = new StartLiveParam();
            p.setCameraCode(cameraCode);
            p.setUseSecondStream(true); //使用辅流
            p.setBitrate(32 * 8);   //64KB的码率
            p.setFramerate(12);     //25帧率
            p.setResolution(2);     //4CIF分辨率

            //启动实况
            ServiceManager.startLive(p, listener);

            fab2.setImageResource(R.drawable.ic_stop_white_48dp);
            isplaying = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopLive() {
        //停止实况，第二个参数是null表示不接收结果
        ServiceManager.stopLive(mPlayer.getPlaySession(), null);


        //收流线程退出
        if (mRecvStreamThread != null) {
            mRecvStreamThread.interrupt();
        }

        //停止Player播放解码
        mPlayer.AVStopPlay();
        fab2.setImageResource(R.drawable.ic_play_arrow_white_36dp);
        isplaying = false;
    }

    public void pushAlarm(String cameraCode) {

        PushAlarmParam param = new PushAlarmParam();
        param.setCameraCode(cameraCode);
        param.setAlarmType(401);
        param.setAlarmDesc("推送告警测试");

        try {
            OnPushAlarmListener listener = new OnPushAlarmListener() {
                @Override
                public void onPushAlarmResult(long errorCode, String errorDesc) {
                    if (0 != errorCode) {
                        Toast.makeText(TestPlayerActivity.this, errorDesc, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(TestPlayerActivity.this, "告警推送成功", Toast.LENGTH_SHORT).show();
                    }
                }
            };
            //推送告警接口
            ServiceManager.pushAlarmMessage(param, listener);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getAlarm(int timeout) {
        try {
            OnGetAlarmListener listener = new OnGetAlarmListener() {
                @Override
                public void onGetAlarmResult(long errorCode, String errorDesc, List<AlarmInfo> alarmList) {
                    if (null == alarmList) {
                        return;
                    }
                    if (errorCode != 0) {
                        Toast.makeText(TestPlayerActivity.this, errorDesc, Toast.LENGTH_SHORT).show();
                    } else {
                        int size = alarmList.size();
                        StringBuffer stringBuffer = new StringBuffer();
                        for (int i = 0; i < size; i++) {
                            AlarmInfo alarmInfo = alarmList.get(i);
                            stringBuffer.append(alarmInfo.getAlarmSrcCode() + ",");
                            stringBuffer.append(alarmInfo.getAlarmSrcName() + ",");
                            stringBuffer.append(alarmInfo.getAlarmType() + ",");
                            stringBuffer.append(alarmInfo.getAlarmDesc() + ",");
                            stringBuffer.append(alarmInfo.getAlarmTime() + "\n");
                        }

                        Log.i(TAG, "告警信息:" + stringBuffer.toString());
                    }
                }
            };

            //获取告警
            ServiceManager.getAlarmMessage(timeout, listener);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 云台控制
     */
    public void ptzCommand(String cameraCode, int directionCode) {

        //云台命令参数
        PtzCommandParam param = new PtzCommandParam();
        param.setCameraCode(cameraCode);
        param.setCmd(directionCode);
        param.setSpeed1(5);
        param.setSpeed2(5);

        try {
            OnPtzCommandListener listener = (errorCode, errorDesc) -> {
                if (errorCode != 0) {
                    //Toast.makeText(PtzActivity.this, errorDesc, Toast.LENGTH_SHORT).show();
                }
            };
            //云台控制接口
            ServiceManager.ptzCommand(param, listener);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

        if (fab2.equals(v)) {
            //String cameraCode = mEditCamCode.getText().toString();
            if (!isplaying) {
                startLive(mCameraCode);
            } else {
                stopLive();
            }

        } else if (mBtnSnatch.equals(v)) {
            //抓拍图片，返回路径
            String path = mPlayer.snatch(null);
            if (null != path) {
                Toast.makeText(TestPlayerActivity.this, path, Toast.LENGTH_SHORT).show();
            }
        } else if (fabUp.equals(v)) {
            if (isplaying)
            {   if (!ischanging){
                ptzCommand(mCameraCode, PtzCommandParam.PTZ_CMD.TILTUP);
                LogUtil.d("up");
                ischanging=true;
            }   else {
                ptzCommand(mCameraCode, PtzCommandParam.PTZ_CMD.ALLSTOP);
                LogUtil.d("stopPtz");
                ischanging=false;
            }

            }


        } else if (fabDown.equals(v)) {
            if (isplaying)
            {   if (!ischanging){
                ptzCommand(mCameraCode, PtzCommandParam.PTZ_CMD.TILTDOWN);
                LogUtil.d("dowm");
                ischanging=true;
            }   else {
                ptzCommand(mCameraCode, PtzCommandParam.PTZ_CMD.ALLSTOP);
                LogUtil.d("stopPtz");
                ischanging=false;
            }

            }

        } else if (fabLeft.equals(v)) {
            if (isplaying)
            {   if (!ischanging){
                ptzCommand(mCameraCode, PtzCommandParam.PTZ_CMD.PANLEFT);
                LogUtil.d("left");
                ischanging=true;
            }   else {
                ptzCommand(mCameraCode, PtzCommandParam.PTZ_CMD.ALLSTOP);
                LogUtil.d("stopPtz");
                ischanging=false;
            }

            }
        } else if (fabRight.equals(v)) {
            if (isplaying)
            {   if (!ischanging){
                ptzCommand(mCameraCode, PtzCommandParam.PTZ_CMD.PANRIGHT);
                LogUtil.d("right");
                ischanging=true;
            }   else {
                ptzCommand(mCameraCode, PtzCommandParam.PTZ_CMD.ALLSTOP);
                LogUtil.d("stopPtz");
                ischanging=false;
            }

            }

        } else if (mBtnQueryRes.equals(v)) {
            //查询资源参数
            QueryResourceParam params = new QueryResourceParam("", "", new QueryCondition(0, 200, true));

            //查询资源结果监听
            OnQueryResourceListener listener = new OnQueryResourceListener() {
                @Override
                public void onQueryResourceResult(long errorCode, String errorDesc, List<ResourceInfo> resList) {
                    if (null == resList) {
                        return;
                    }

                    StringBuffer strBuf = new StringBuffer();
                    int size = resList.size();
                    for (int i = 0; i < size; i++) {
                        strBuf.append(resList.get(i).getResCode() + ",");
                        strBuf.append(resList.get(i).getResName() + ",");
                        strBuf.append(resList.get(i).getResType() + ",");
                        strBuf.append(resList.get(i).getResSubType() + ",");
                        strBuf.append(resList.get(i).getIsOnline() + "\n");

                        if (resList.get(i).getResType() == ResourceInfo.ResType.CAMERA && resList.get(i).getIsOnline()) {
                            mEditCamCode.setText(resList.get(i).getResCode());
                        }
                    }

                    Log.d(TAG, strBuf.toString());

                }
            };

            //查询资源接口调用
            ServiceManager.queryResource(params, listener);
        } else if (mBtnStartReplay.equals(v)) {

            final String cameraCode = mEditCamCode.getText().toString();
            String beginTime = mEditReplayBeginTime.getText().toString();
            String endTime = mEditReplayEndTime.getText().toString();

            //查询回放记录参数
            QueryReplayParam p = new QueryReplayParam(cameraCode, beginTime, endTime, new QueryCondition(0, 100, true));

            //查询回放记录结果监听
            OnQueryReplayListener queryListener = new OnQueryReplayListener() {
                @Override
                public void onQueryReplayResult(long errorCode, String errorDesc, List<RecordInfo> recordList) {
                    if (recordList == null || recordList.size() <= 0) {
                        Log.d(TAG, "There is no record");
                        return;
                    }


                    //取第一条回放记录测试回放
                    RecordInfo firstRecord = recordList.get(0);

                    //启动回放的参数
                    StartReplayParam p = new StartReplayParam();
                    p.setCameraCode(cameraCode);
                    p.setRecodeInfo(firstRecord);
                    p.setBitrate(64 * 8);  //64KB码率
                    p.setFramerate(20);     //20帧率
                    p.setResolution(2);     //4CIF分辨率


                    OnStartReplayListener listener = new OnStartReplayListener() {
                        @Override
                        public void onStartReplayResult(long errorCode, String errorDesc, String playSession) {
                            //设播放会话给Player
                            mPlayer.setPlaySession(playSession);

                            //先停掉已有的播放
                            if (mRecvStreamThread != null) {
                                mPlayer.AVStopPlay();
                                mRecvStreamThread.interrupt();
                                mRecvStreamThread = null;
                            }

                            //启动播放解码
                            mPlayer.AVStartPlay();

                            //启动收流线程
                            mRecvStreamThread = new RecvStreamThread(mPlayer, playSession);
                            mRecvStreamThread.start();
                        }
                    };

                    //启动回放
                    ServiceManager.startReplay(p, listener);
                }
            };

            //先查询指定时间段内有的回放记录
            ServiceManager.queryReplay(p, queryListener);
        } else if (mBtnStopReplay.equals(v)) {
            //停止回放
            ServiceManager.stopReplay(mPlayer.getPlaySession(), null);

            //停止收流线程
            if (mRecvStreamThread != null) {
                mRecvStreamThread.interrupt();
                mRecvStreamThread = null;
            }

            //停止播放解码
            mPlayer.AVStopPlay();
        } else if (mPtzControl.equals(v)) {
            Intent intent = new Intent();
            intent.setClass(TestPlayerActivity.this, CamPlayerActivity.class);
            startActivity(intent);
        } else if (mBtnDragReplay.equals(v)) {
            //拖动回放，"2016-01-10 12:00:00"为要播放的时间，具体能用的时间在queryReplay中返回
            ServiceManager.dragReplay(mPlayer.getPlaySession(), "2016-01-10 12:00:00", new OnDragReplayListener() {
                @Override
                public void onDragReplayResult(long errorCode, String errorDesc) {

                }
            });
        } else if (mBtnRecord.equals(v)) {
            try {
                String path = mPlayer.startRecord(null);

                if (null != path) {
                    Toast.makeText(TestPlayerActivity.this, "录像开始：" + path, Toast.LENGTH_SHORT).show();
                }
            } catch (IOException ex) {

            }
        } else if (mBtnStopRecord.equals(v)) {
            String path = mPlayer.stopRecord();
            if (null != path) {
                Toast.makeText(TestPlayerActivity.this, "录像完成：" + path, Toast.LENGTH_SHORT).show();
            }
        } else if (mBtnMute.equals(v)) {

            // false为开启声音，若要静音传true
            mPlayer.setMute(false);
        } else if (mBtnGetAlarm.equals(v)) {
            // 查询告警为阻塞调用，有告警会立即返回。如果需要一直查询，可以放在线程中连续调用
            getAlarm(10000);
        } else if (mBtnPushAlarm.equals(v)) {
            String cameraCode = mEditCamCode.getText().toString();
            pushAlarm(cameraCode);
        }

    }

    /**
     * 保活失败响应，退出
     */
    public void onKeepaliveFailed() {

        mRequireLogout = true;
        Toast.makeText(TestPlayerActivity.this, "保活失败，已退出", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onLoginResult(long errorCode, String errorDesc) {
        //成功为0，其余为失败错误码
        if (errorCode == 0) {
            LogUtil.d("连接成功！可以查看摄像头");
            tips.setText("连接成功！可以查看摄像头 camid:" + mCameraCode);
        } else {
            //Toast.makeText(LoginActivity.this, "登录失败：" + errorCode + "," + errorDesc, Toast.LENGTH_LONG).show();
            LogUtil.d("连接失败");
            tips.setText("连接失败！请稍后查看");
        }
    }


    class surfaceCallback implements SurfaceHolder.Callback {

        public void surfaceCreated(SurfaceHolder holder) {
            Log.d(TAG, "===== surfaceCreated =====");
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Log.d(TAG, "===== surfaceChanged =====");
            if (mPlayer != null) {
                mPlayer.changeDisplaySize(width, height);
            }

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder arg0) {
            Log.d(TAG, "===== surfaceDestroyed =====");
        }
    }
}
