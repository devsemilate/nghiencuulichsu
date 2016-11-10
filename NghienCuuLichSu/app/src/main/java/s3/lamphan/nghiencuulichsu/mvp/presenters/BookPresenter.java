package s3.lamphan.nghiencuulichsu.mvp.presenters;

import android.content.Context;
import android.util.Log;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import s3.lamphan.nghiencuulichsu.domain.persistence.BookDbHelper;
import s3.lamphan.nghiencuulichsu.domain.repository.BookRepository;
import s3.lamphan.nghiencuulichsu.domain.repository.IBaseCallback;
import s3.lamphan.nghiencuulichsu.domain.repository.IDataLogicCallback;
import s3.lamphan.nghiencuulichsu.mvp.models.Book;
import s3.lamphan.nghiencuulichsu.mvp.models.Topic;

/**
 * Created by lam.phan on 11/7/2016.
 */
public class BookPresenter extends BasePresenter{
    private static Object lock = new Object();
    private BookRepository bookRepository;
    private BookDbHelper bookDbHelper;
    private int pageSize;
    private int curPage;
    private AtomicBoolean isGettingBook = new AtomicBoolean(); // false

    public BookPresenter(Context context, int pageSize, int curPage) {
        super(context);
        bookRepository = new BookRepository();
        addRepository(bookRepository);
        bookDbHelper = new BookDbHelper(context);
        this.pageSize = pageSize;
        this.curPage = curPage;
    }

    public void resetPage(int pageSize, int curPage)
    {
        this.pageSize = pageSize;
        this.curPage = curPage;
    }

    public void getBookList(final String branchId, final IBaseCallback<Book> callback)
    {
        Log.d("Test", "current book page : " + curPage);
        synchronized (lock) {
            if(isGettingBook.get())
                return;
            if (curPage == END_PAGE)
                return;
        }
        isGettingBook.set(true);
        Log.d("Test", "start get book page : " + curPage);
        this.checkConnectionNetwork(new ICheckConnectNetworkCallback() {
            @Override
            public void available() {
                bookRepository.getBookList(branchId, pageSize, curPage, new IBaseCallback<Book>() {
                    @Override
                    public void success(List<Book> results) {
                        if (callback != null) {
                            callback.success(results);
                            if (results.size() > 0) {
                                curPage++;
                            } else {
                                curPage = END_PAGE;
                            }
                        }

                        // save topic
                        saveBooks(results);

                        isGettingBook.set(false);
                    }

                    @Override
                    public void error(String message) {
                        getBookListFromDb(branchId, callback, Error.UNKNOWN);
                        curPage = END_PAGE;
                        isGettingBook.set(false);
                    }

                    @Override
                    public void error(Error error) {
                        getBookListFromDb(branchId, callback, error);
                        curPage = END_PAGE;
                        isGettingBook.set(false);
                    }
                }, new IDataLogicCallback() {
                    @Override
                    public void overFlow() {
                        curPage = END_PAGE;
                    }
                });
            }

            @Override
            public void notAvailable() {
                Log.d("Test", "network not available......");
                getBookListFromDb(branchId, callback, Error.NETWORK_CONNECT);
                curPage = END_PAGE;
                isGettingBook.set(false);
            }
        });
    }

    public void getBookListFromDb(final String branchId, final IBaseCallback<Book> callback, final Error error)
    {
        if(curPage == 0)
            return;
        Subscription subscription = bookDbHelper.getBookList(branchId).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Action1<List<Book>>() {
                    @Override
                    public void call(List<Book> bookList) {
                        if(callback != null)
                        {
                            callback.success(bookList);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if(callback != null)
                        {
                            if(error != Error.UNKNOWN)
                            {
                                callback.error(error);
                            } else {
                                callback.error(throwable.getMessage());
                            }
                        }
                    }
                });
        compositeSubscription.add(subscription);
    }

    public void saveBooks(List<Book> bookList)
    {
        Subscription subscription = bookDbHelper.addNewBooks(bookList).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
        compositeSubscription.add(subscription);
    }

}
