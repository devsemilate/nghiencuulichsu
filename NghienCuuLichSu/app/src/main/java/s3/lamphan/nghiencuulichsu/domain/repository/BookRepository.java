package s3.lamphan.nghiencuulichsu.domain.repository;

import android.util.Log;

import com.google.gson.Gson;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import s3.lamphan.nghiencuulichsu.domain.repository.gateways.BaseGateWay;
import s3.lamphan.nghiencuulichsu.domain.repository.gateways.IGateWay;
import s3.lamphan.nghiencuulichsu.domain.repository.restModels.BookRestModel;
import s3.lamphan.nghiencuulichsu.domain.repository.services.IBookService;
import s3.lamphan.nghiencuulichsu.mvp.models.Book;

/**
 * Created by lam.phan on 11/7/2016.
 */
public class BookRepository extends BaseRepository{
    private static final String BOOK_BRANCH_ID = "b2";
    private IBookService service;

    public BookRepository() {
        super(new BaseGateWay());
        init();
    }

    public BookRepository(IGateWay baseGateWay) {
        super(baseGateWay);
        init();
    }

    public void init()
    {
        if(retrofit != null)
        {
            service = retrofit.create(IBookService.class);
        }
    }

    public void getBookList(final int pageSize, final int curPage,
                            final IBaseCallback<Book> callback,
                            final IDataLogicCallback bookLogicCallback)
    {
        if(service == null || compositeSubscription == null)
        {
            callback.error("Service null or CompositeSubscription null");
            return;
        }

        final Observable<BookRestModel> call = service.getBooks("branch='"+BOOK_BRANCH_ID+"'", pageSize, curPage);
        Subscription subscription = call.observeOn(AndroidSchedulers.mainThread())
                                        .subscribeOn(Schedulers.newThread())
                                        .subscribe(new Subscriber<BookRestModel>() {
                                            @Override
                                            public void onCompleted() {

                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                Log.d("Test", "get book Error : " + e.getMessage());
                                                if (callback != null) {
                                                    callback.error(e.getMessage());
                                                }
                                            }

                                            @Override
                                            public void onNext(BookRestModel bookRestModel) {
                                                Log.d("Test", "get book list ok : " + new Gson().toJson(bookRestModel));
                                                if (callback != null) {
                                                    callback.success(Book.convertFromRestModel(bookRestModel));
                                                }
                                                if(bookLogicCallback != null
                                                        && (pageSize * (curPage+1) >= bookRestModel.getTotalObjects()))
                                                {
                                                    bookLogicCallback.overFlow();
                                                }
                                            }
                                        });
        this.compositeSubscription.add(subscription);
    }
}
