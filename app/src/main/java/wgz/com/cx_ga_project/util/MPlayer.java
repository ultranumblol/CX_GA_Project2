package wgz.com.cx_ga_project.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.media.AudioTrack;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Date;

/**
 * Created by wgz on 2016/11/29.
 */

public class MPlayer {
    private int mNativeContext = 0;
    private int mPicWidth;
    private int mPicHeight;
    private SurfaceHolder mSurfaceHolder;
    private static final String TAG = "ImosPlayer";
    private Context cnt;
    private Bitmap mFrameBmp;
    private int mFrameWidth;
    private int mFrameHeight;
    private AudioTrack mAudioTrack;
    private boolean mbRequireSnatch = false;
    private boolean mbPause = false;
    private boolean mbIsStart = false;
    private String mPlaySession;
    private Rect mSrcRect = null;
    private Rect mDstRect = null;
    private Handler mAudioHandler;
    private Handler mVideoHandler;
    private Bitmap mFrameCache;
    private MPlayer.AudioThread mAudioThread;
    private MPlayer.VideoThread mVideoThread;
    private MPlayer.OnPlayListener mPlayerCallback;
    private Paint mPaint = new Paint();
    private PaintFlagsDrawFilter mDrawFilter = new PaintFlagsDrawFilter(0, 3);
    private boolean mMute = true;
    private boolean mbRecording = false;
    private String mRecordingPath = "";
    private FileOutputStream mRecordingFos = null;
    private static final int MESSAGE_VIDEO_ID = 1;
    private static final int MESSAGE_AUDIO_ID = 2;

    public MPlayer() {
    }

    protected void finalize() throws Throwable {
        Log.d("ImosPlayer", "Player finalize");
        super.finalize();
        if(this.mAudioHandler != null) {
            this.mAudioHandler.getLooper().quit();
            this.mAudioHandler = null;
        }

        if(this.mVideoHandler != null) {
            this.mVideoHandler.getLooper().quit();
            this.mVideoHandler = null;
        }

        if(this.mVideoThread != null && this.mVideoThread.isAlive()) {
            this.mVideoThread.interrupt();
            this.mVideoThread = null;
        }

        if(this.mAudioThread != null && this.mAudioThread.isAlive()) {
            this.mAudioThread.interrupt();
            this.mAudioThread = null;
        }

        if(null != this.mAudioTrack) {
            this.mAudioTrack.stop();
            this.mAudioTrack.release();
        }

        if(this.mFrameBmp != null) {
            this.mFrameBmp.recycle();
            this.mFrameBmp = null;
        }

        if(this.mFrameCache != null) {
            this.mFrameCache.recycle();
            this.mFrameCache = null;
        }

    }

    private void speak(short[] x, int n) {
        if(null == this.mAudioThread) {
            this.mAudioThread = new MPlayer.AudioThread();
            this.mAudioThread.start();
        }

        if(null != this.mAudioHandler && !this.mMute) {
            Message msg = new Message();
            msg.obj = x.clone();
            this.mAudioHandler.sendMessage(msg);
        }

    }

    private void render(byte[] data, int width, int height) {
        if(null == this.mVideoThread) {
            this.mVideoThread = new MPlayer.VideoThread();
            this.mVideoThread.start();
            if(null != this.mPlayerCallback) {
                this.mPlayerCallback.onStartPlay();
            }
        }

        if(null != this.mVideoHandler) {
            Message msg = new Message();
            msg.what = 1;
            MPlayer.VideoFrame v = new MPlayer.VideoFrame();
            v.mWidth = width;
            v.mHeight = height;
            v.mData = data;
            v.ts = System.currentTimeMillis();
            msg.obj = v;
            this.mVideoHandler.sendMessage(msg);
        }

        Object data1 = null;
    }

    public void changeDisplaySize(int w, int h) {
        if(this.mDstRect != null) {
            this.mDstRect.set(0, 0, w, h);
        } else {
            this.mDstRect = new Rect(0, 0, w, h);
        }

    }

    public void setPlayerCb(MPlayer.OnPlayListener pcb) {
        this.mPlayerCallback = pcb;
    }

    public String snatch(String path) {
        Bitmap bmp = this.snatch();
        if(null == bmp) {
            return null;
        } else {
            if(null == path) {
                path = this.getSnatchPath();
            }

            try {
                File e = new File(path);
                e.createNewFile();
                FileOutputStream fos = new FileOutputStream(e);
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
                return path;
            } catch (Exception var5) {
                var5.printStackTrace();
                return null;
            }
        }
    }

    private String getSnatchPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals("mounted");
        if(!sdCardExist) {
            return "";
        } else {
            sdDir = Environment.getExternalStorageDirectory();
            String path = sdDir.toString();
            path = path + "/uniview/media/snatch/";
            Date dt = new Date();
            int year = dt.getYear() + 1900;
            int month = dt.getMonth() + 1;
            String txt_month = month < 10?"0" + String.valueOf(month):String.valueOf(month);
            int day = dt.getDate();
            String txt_day = day < 10?"0" + String.valueOf(day):String.valueOf(day);
            path = path + year + txt_month + txt_day;
            path = path + "/";
            File dstDir = new File(path);
            if(!dstDir.exists()) {
                dstDir.mkdirs();
            }

            path = path + dt.getTime() + ".jpg";
            Log.d("ImosPlayer", path);
            return path;
        }
    }

    private Bitmap snatch() {
        int times = 0;
        if(this.mFrameCache != null) {
            this.mFrameCache.recycle();
            this.mFrameCache = null;
        }

        if(this.mbPause) {
            if(null != this.mFrameBmp) {
                this.mFrameCache = Bitmap.createBitmap(this.mFrameBmp);
            }
        } else {
            this.mbRequireSnatch = true;

            while(this.mbRequireSnatch) {
                if(null != this.mFrameCache) {
                    return this.mFrameCache;
                }

                try {
                    Thread.sleep(50L);
                } catch (Exception var3) {
                    var3.printStackTrace();
                }

                ++times;
                if(times > 40) {
                    Log.i("ImosPlayer", "times up");
                    return null;
                }
            }
        }

        return this.mFrameCache;
    }

    public String startRecord(String path) throws IOException {
        if(!this.mbIsStart) {
            return "";
        } else {
            if(null == path) {
                path = this.getRecordPath();
            }

            File f = new File(path);
            if(!f.exists()) {
                f.createNewFile();
            }

            this.mRecordingFos = new FileOutputStream(path);
            this.mRecordingPath = path;
            this.mbRecording = true;
            return path;
        }
    }

    public String stopRecord() {
        if(!this.mbRecording) {
            return "";
        } else {
            this.mbRecording = false;

            try {
                this.mRecordingFos.close();
            } catch (Exception var2) {
                ;
            }

            this.mRecordingFos = null;
            return this.mRecordingPath;
        }
    }

    private String getRecordPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals("mounted");
        if(!sdCardExist) {
            return "";
        } else {
            sdDir = Environment.getExternalStorageDirectory();
            String path = sdDir.toString();
            path = path + "/uniview/media/video/";
            Date dt = new Date();
            int year = dt.getYear() + 1900;
            int month = dt.getMonth() + 1;
            String txt_month = month < 10?"0" + String.valueOf(month):String.valueOf(month);
            int day = dt.getDate();
            String txt_day = day < 10?"0" + String.valueOf(day):String.valueOf(day);
            path = path + year + txt_month + txt_day;
            path = path + "/";
            File dstDir = new File(path);
            if(!dstDir.exists()) {
                dstDir.mkdirs();
            }

            path = path + dt.getTime() + ".ts";
            Log.d("ImosPlayer", path);
            return path;
        }
    }

    public void AVInitialize(SurfaceHolder holder) {
        this.nativeInitialize();
        this.mSurfaceHolder = holder;
        this.mDstRect = holder.getSurfaceFrame();
    }

    public void AVStartPlay() {
        if(this.AVIsPlaying().booleanValue()) {
            this.AVStopPlay();
        }

        this.mbIsStart = true;
        this.nativeStartPlay();
    }

    public void AVStopPlay() {
        this.nativeStopPlay();
        this.mbIsStart = false;
        if(this.mAudioHandler != null) {
            this.mAudioHandler.getLooper().quit();
            this.mAudioHandler = null;
        }

        if(this.mVideoHandler != null) {
            this.mVideoHandler.getLooper().quit();
            this.mVideoHandler = null;
        }

        if(this.mVideoThread != null && this.mVideoThread.isAlive()) {
            this.mVideoThread.interrupt();
            this.mVideoThread = null;
        }

        if(this.mAudioThread != null && this.mAudioThread.isAlive()) {
            this.mAudioThread.interrupt();
            this.mAudioThread = null;
        }

        if(null != this.mAudioTrack) {
            this.mAudioTrack.stop();
            this.mAudioTrack.release();
            this.mAudioTrack = null;
        }

        if(this.mFrameBmp != null) {
            this.mFrameBmp.recycle();
            this.mFrameBmp = null;
        }

        if(this.mFrameCache != null) {
            this.mFrameCache.recycle();
            this.mFrameCache = null;
        }

    }

    public void AVFinalize() {
        this.nativeFinalize();
        if(this.mAudioHandler != null) {
            this.mAudioHandler.getLooper().quit();
            this.mAudioHandler = null;
        }

        if(this.mVideoHandler != null) {
            this.mVideoHandler.getLooper().quit();
            this.mVideoHandler = null;
        }

        if(this.mVideoThread != null && this.mVideoThread.isAlive()) {
            this.mVideoThread.interrupt();
            this.mVideoThread = null;
        }

        if(this.mAudioThread != null && this.mAudioThread.isAlive()) {
            this.mAudioThread.interrupt();
            this.mAudioThread = null;
        }

        if(null != this.mAudioTrack) {
            this.mAudioTrack.stop();
            this.mAudioTrack.release();
        }

        if(this.mFrameBmp != null) {
            this.mFrameBmp.recycle();
            this.mFrameBmp = null;
        }

        if(this.mFrameCache != null) {
            this.mFrameCache.recycle();
            this.mFrameCache = null;
        }

    }

    public void AVStreaming(byte[] buf, int len) throws IOException {
        if(null != buf) {
            if(this.mbRecording && null != this.mRecordingFos) {
                this.mRecordingFos.write(buf, 0, len);
            }

            this.nativeStreaming(buf, len);
        }
    }

    public Boolean AVIsPlaying() {
        return Boolean.valueOf(this.mbIsStart);
    }

    public String getPlaySession() {
        return this.mPlaySession;
    }

    public void setPlaySession(String playSession) {
        this.mPlaySession = playSession;
    }

    public void setMute(boolean mute) {
        this.mMute = mute;
    }


    private native void nativeInitialize() throws RuntimeException;

    private native void nativeStartPlay();

    private native void nativeStopPlay();

    private native void nativeFinalize();

    private native void nativeStreaming(byte[] var1, int var2);

    static {
        try {
            System.loadLibrary("imosavcodec");
        } catch (Exception var2) {
            Log.e("JavaImosPlayer", "loadLibrary imosplayer failed");
        }

        try {
            System.loadLibrary("imosplayer");
        } catch (Exception var1) {
            Log.e("JavaImosPlayer", "loadLibrary imosplayer failed");
        }

    }

    class VideoThread extends Thread {
        VideoThread() {
        }

        public void run() {
            Looper.prepare();
            MPlayer.this.mVideoHandler = new Handler() {
                public void handleMessage(Message msg) {
                    MPlayer.VideoFrame v = (MPlayer.VideoFrame)msg.obj;
                    int width = v.mWidth;
                    int height = v.mHeight;
                    byte[] data = v.mData;
                    if(System.currentTimeMillis() - v.ts > 400L) {
                        this.removeMessages(1);
                        Log.e("ImosPlayer", "Message cost:" + (System.currentTimeMillis() - v.ts));
                    } else {
                        if(width != MPlayer.this.mFrameWidth || height != MPlayer.this.mFrameHeight) {
                            if(null != MPlayer.this.mFrameBmp) {
                                MPlayer.this.mFrameBmp.recycle();
                            }

                            MPlayer.this.mFrameWidth = width;
                            MPlayer.this.mFrameHeight = height;
                            MPlayer.this.mSrcRect = new Rect(0, 0, MPlayer.this.mFrameWidth, MPlayer.this.mFrameHeight);
                            MPlayer.this.mPaint.setAntiAlias(true);
                            MPlayer.this.mPaint.setFilterBitmap(true);
                            MPlayer.this.mFrameBmp = Bitmap.createBitmap(MPlayer.this.mFrameWidth, MPlayer.this.mFrameHeight, Bitmap.Config.ARGB_8888);
                        }

                        if(null == MPlayer.this.mFrameBmp) {
                            MPlayer.this.mFrameWidth = 0;
                            MPlayer.this.mFrameHeight = 0;
                        } else {
                            MPlayer.this.mFrameBmp.copyPixelsFromBuffer(ByteBuffer.wrap(data, 0, MPlayer.this.mFrameWidth * MPlayer.this.mFrameHeight * 4));
                            if(MPlayer.this.mbRequireSnatch) {
                                MPlayer.this.mFrameCache = Bitmap.createBitmap(MPlayer.this.mFrameBmp);
                                if(null != MPlayer.this.mFrameCache) {
                                    MPlayer.this.mbRequireSnatch = false;
                                }
                            }

                            Canvas cvs = null;

                            try {
                                if(null != MPlayer.this.mSurfaceHolder) {
                                    cvs = MPlayer.this.mSurfaceHolder.lockCanvas();
                                    if(null != cvs) {
                                        cvs.setDrawFilter(MPlayer.this.mDrawFilter);
                                        cvs.drawBitmap(MPlayer.this.mFrameBmp, MPlayer.this.mSrcRect, MPlayer.this.mDstRect, MPlayer.this.mPaint);
                                        MPlayer.this.mSurfaceHolder.unlockCanvasAndPost(cvs);
                                    }
                                }
                            } catch (Exception var8) {
                                var8.printStackTrace();
                                if(cvs != null && null != MPlayer.this.mSurfaceHolder) {
                                    MPlayer.this.mSurfaceHolder.unlockCanvasAndPost(cvs);
                                }
                            }

                            Object data1 = null;
                        }
                    }
                }
            };
            Looper.loop();
        }
    }

    class AudioThread extends Thread {
        AudioThread() {
        }

        public void run() {
            Looper.prepare();
            if(null == MPlayer.this.mAudioTrack) {
                int bufsize = AudioTrack.getMinBufferSize(8000, 2, 2);
                MPlayer.this.mAudioTrack = new AudioTrack(3, 8000, 2, 2, bufsize, 1);
                MPlayer.this.mAudioTrack.play();
            }

            MPlayer.this.mAudioHandler = new Handler() {
                public void handleMessage(Message msg) {
                    short[] x = (short[])((short[])msg.obj);
                    if(null != MPlayer.this.mAudioTrack) {
                        MPlayer.this.mAudioTrack.write(x, 0, x.length);
                    }

                    Object x1 = null;
                }
            };
            Looper.loop();
        }
    }

    public interface OnPlayListener {
        void onStartPlay();
    }

    private class VideoFrame {
        public int mWidth;
        public int mHeight;
        public byte[] mData;
        public long ts;

        private VideoFrame() {
            this.mWidth = 0;
            this.mHeight = 0;
            this.mData = null;
            this.ts = 0L;
        }
    }
}
