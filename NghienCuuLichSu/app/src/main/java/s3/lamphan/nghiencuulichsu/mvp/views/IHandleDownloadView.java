package s3.lamphan.nghiencuulichsu.mvp.views;

import s3.lamphan.nghiencuulichsu.mvp.models.Book;

/**
 * Created by lam.phan on 11/8/2016.
 */
public interface IHandleDownloadView {
    void getLinkDownload(Book book, String url);
    void onStart(Book book,long downloadId);
    void onProgress(Book book, long downloadId, int progress);
    void onComplete(Book book, long downloadId);
    void onPause(Book book, long downloadId);
    void onFailed(Book book, long downloadId);
}
