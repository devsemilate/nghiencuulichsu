package s3.lamphan.nghiencuulichsu.mvp.presenters;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import s3.lamphan.nghiencuulichsu.mvp.models.Book;
import s3.lamphan.nghiencuulichsu.mvp.models.DownloadBook;
import s3.lamphan.nghiencuulichsu.mvp.views.IHandleDownloadView;

/**
 * Created by lam.phan on 11/8/2016.
 */
public class DownloadBookPresenter extends BasePresenter{
    public static final List<String> LINK_DOWNLOAD_SIGN = Arrays.asList("docs.googleusercontent.com/docs/");
    private static final int STATE_BREAK = -1;
    private DownloadManager downloadManager;
    private IHandleDownloadView downloadView;
    private List<Book> curDownloadBook = new ArrayList<>();

    public DownloadBookPresenter(Context context, IHandleDownloadView downloadView) {
        super(context);
        this.downloadView = downloadView;
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    public void startDownload(final Book book, String realLink)
    {
        if(book == null)
            return;
        // create download book
//        final DownloadBook downloadBook = new DownloadBook(book);
        // prepare request
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(realLink));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle(book.getName());
        request.setDescription(book.getAuthor());
        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, "test.pdf");
        // start
        long downloadReference = downloadManager.enqueue(request);
        book.setDownloadReference(downloadReference);
        this.downloadView.onStart(book, downloadReference);
        // status
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                while (book.getStatusDownload() != DownloadManager.STATUS_SUCCESSFUL
                        && book.getStatusDownload() != DownloadManager.STATUS_FAILED) {
                    subscriber.onNext(null);
                    // query status
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(book.getDownloadReference());
                    Cursor cursor = downloadManager.query(query);
                    cursor.moveToFirst();
                    int byteDownload = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    int byteTotal = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                    final int downloadProgress = (byteDownload * 100) / byteTotal;
//                    Log.d("Test", "download progress : " + downloadProgress);
//                    downloadBook.setProgress(downloadProgress);
                    book.setStatusDownload(cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)));
                    cursor.close();
                    // handle status
                    subscriber.onNext(book.getStatusDownload());
                    Log.d("Test", "status download : " + book.getStatusDownload());
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (downloadView != null) {
                                  switch (book.getStatusDownload()) {
                                      case DownloadManager.STATUS_RUNNING:
                                          downloadView.onProgress(book,
                                                  book.getDownloadReference(),
                                                  downloadProgress);
                                          break;
                                      case DownloadManager.STATUS_FAILED:
                                          downloadView.onFailed(book,
                                                  book.getDownloadReference());
                                          break;
                                      case DownloadManager.STATUS_PAUSED:
                                          downloadView.onPause(book,
                                                  book.getDownloadReference());
                                          break;
                                      case DownloadManager.STATUS_SUCCESSFUL:
                                          downloadView.onComplete(book,
                                                  book.getDownloadReference());
                                          break;
                                      default:
                                          break;
                                  }
                            }
                        }
                    });
//                    try {
//                        Thread.sleep(100);
//                    } catch (Exception ex)
//                    {
//
//                    }
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
          .subscribeOn(Schedulers.newThread())
          .subscribe(new Subscriber<Integer>() {
              @Override
              public void onCompleted() {

              }

              @Override
              public void onError(Throwable e) {

              }

              @Override
              public void onNext(Integer downloadProgress) {
//                  if (downloadView != null) {
//                      switch (book.getStatusDownload()) {
//                          case DownloadManager.STATUS_RUNNING:
//                              downloadView.onProgress(book,
//                                      book.getDownloadReference(),
//                                      downloadProgress);
//                              break;
//                          case DownloadManager.STATUS_FAILED:
//                              downloadView.onFailed(book,
//                                      book.getDownloadReference());
//                              break;
//                          case DownloadManager.STATUS_PAUSED:
//                              downloadView.onPause(book,
//                                      book.getDownloadReference());
//                              break;
//                          case DownloadManager.STATUS_SUCCESSFUL:
//                              downloadView.onComplete(book,
//                                      book.getDownloadReference());
//                              break;
//                          default:
//                              break;
//                      }
//                  }
              }
          });
    }

}
