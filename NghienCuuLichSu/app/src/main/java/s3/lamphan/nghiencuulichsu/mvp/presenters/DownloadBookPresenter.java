package s3.lamphan.nghiencuulichsu.mvp.presenters;

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
    private List<DownloadBook> curDownloadBook = new ArrayList<>();

    public DownloadBookPresenter(Context context, IHandleDownloadView downloadView) {
        super(context);
        this.downloadView = downloadView;
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    public void startDownload(Book book, String realLink)
    {
        if(book == null)
            return;
        // create download book
        final DownloadBook downloadBook = new DownloadBook(book);
        // prepare request
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(realLink));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle(book.getName());
        request.setDescription(book.getAuthor());
        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, "test.pdf");
        // start
        downloadBook.setDownloadReference(downloadManager.enqueue(request));
        this.downloadView.onStart(downloadBook.getBook(), downloadBook.getDownloadReference());
        // status
//        Observable.create(new Observable.OnSubscribe<DownloadBook>() {
//            @Override
//            public void call(Subscriber<? super DownloadBook> subscriber) {
//                Log.d("Test", "call................... " + downloadBook.getState());
//                while (downloadBook.getState() != STATE_BREAK) {
//                    subscriber.onNext(null);
//                    // query status
//                    DownloadManager.Query query = new DownloadManager.Query();
//                    query.setFilterById(downloadBook.getDownloadReference());
//                    Cursor cursor = downloadManager.query(query);
//                    cursor.moveToFirst();
//                    int byteDownload = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
//                    int byteTotal = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
//                    int downloadProgress = (byteDownload * 100) / byteTotal;
//                    downloadBook.setProgress(downloadProgress);
//                    downloadBook.setState(cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)));
//                    cursor.close();
//                    // handle status
//                    subscriber.onNext(downloadBook);
//                    if(downloadBook.getState() == DownloadManager.STATUS_SUCCESSFUL
//                            || downloadBook.getState() == DownloadManager.STATUS_FAILED)
//                    {
//                        downloadBook.setState(STATE_BREAK);
//                    }
//                    Log.d("Test", "state................... " + downloadBook.getState());
//                }
//            }
//        }).observeOn(AndroidSchedulers.mainThread())
//          .subscribeOn(Schedulers.io())
//          .subscribe(new Subscriber<DownloadBook>() {
//              @Override
//              public void onCompleted() {
//
//              }
//
//              @Override
//              public void onError(Throwable e) {
//
//              }
//
//              @Override
//              public void onNext(DownloadBook downloadBook) {
//                  Log.d("Test", "onNext downloadbook : " + new Gson().toJson(downloadBook));
//                  if (downloadView != null) {
//                      Log.d("Test", "onNext downloadbook state : " + downloadBook.getState());
//                      switch (downloadBook.getState()) {
//                          case DownloadManager.STATUS_RUNNING:
//                              Log.d("Test", "state running ............... ");
//                              downloadView.onProgress(downloadBook.getBook(),
//                                      downloadBook.getDownloadReference(),
//                                      downloadBook.getProgress());
//                              break;
//                          case DownloadManager.STATUS_FAILED:
//                              downloadView.onFailed(downloadBook.getBook(),
//                                      downloadBook.getDownloadReference());
//                              break;
//                          case DownloadManager.STATUS_PAUSED:
//                              downloadView.onPause(downloadBook.getBook(),
//                                      downloadBook.getDownloadReference());
//                              break;
//                          case DownloadManager.STATUS_SUCCESSFUL:
//                              downloadView.onComplete(downloadBook.getBook(),
//                                      downloadBook.getDownloadReference());
//                              break;
//                          default:
//                              break;
//                      }
//                  }
//              }
//          });
    }

}
