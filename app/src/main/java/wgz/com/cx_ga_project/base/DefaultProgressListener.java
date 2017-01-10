package wgz.com.cx_ga_project.base;

import wgz.com.cx_ga_project.bean.ChatUpProgress;
import wgz.com.cx_ga_project.bean.progress;
import wgz.datatom.com.utillibrary.util.LogUtil;

/**
 * Created by wgz on 2016/11/9.
 */

public class DefaultProgressListener implements ProgressListener {



    //多文件上传时，index作为上传的位置的标志
    private int mIndex;
    private int filepieces;

    public DefaultProgressListener(int mIndex,int filepieces) {
        this.mIndex = mIndex;
        this.filepieces = filepieces;
    }

    @Override
    public void onProgress(long hasWrittenLen, long totalLen, boolean hasFinish) {
        int percent = (int) (hasWrittenLen * 100 / totalLen);
        if (percent > 100) percent = 100;
        if (percent < 0) percent = 0;
        long progress = (hasWrittenLen * 100 / totalLen)/filepieces +(100/filepieces)*mIndex;
        RxBus.getDefault().post(new ChatUpProgress(progress+"%"));

    }
}
