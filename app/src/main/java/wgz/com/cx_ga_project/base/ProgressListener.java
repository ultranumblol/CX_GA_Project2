package wgz.com.cx_ga_project.base;

/**
 * Created by wgz on 2016/11/9.
 */

public interface ProgressListener {
    void onProgress(long hasWrittenLen, long totalLen, boolean hasFinish);
}
