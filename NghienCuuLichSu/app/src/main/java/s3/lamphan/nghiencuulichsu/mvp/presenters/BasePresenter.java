package s3.lamphan.nghiencuulichsu.mvp.presenters;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import s3.lamphan.nghiencuulichsu.domain.repository.BaseRepository;

/**
 * Created by lam.phan on 11/2/2016.
 */
public class BasePresenter {
    public static enum Error
    {
        UNKNOWN,
        NETWORK_CONNECT
    }
    protected static final int END_PAGE = -1;
    public static final int DEFAULT_PAGE_SIZE = 5;
    public static final int DEFAULT_START_PAGE = 0;

    private Context context;
    private List<BaseRepository> repositories = new ArrayList<>();
    protected CompositeSubscription compositeSubscription;
    private ConnectivityManager connectivityManager;

    public BasePresenter(Context context) {
        this.context = context;
        compositeSubscription = new CompositeSubscription();
        connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public void addRepository(BaseRepository repository)
    {
        this.repositories.add(repository);
    }

    public void release()
    {
        compositeSubscription.unsubscribe();
        for(BaseRepository baseRepository : repositories)
        {
            baseRepository.releaseCompositeSubscription();
        }
    }

    protected void checkConnectionNetwork(final ICheckConnectNetworkCallback callback)
    {
        Log.d("Test", "start check connect");
        Subscription subscription = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                boolean available = networkInfo != null && networkInfo.isConnectedOrConnecting();
                subscriber.onNext(available);
                subscriber.onCompleted();
            }
        }).observeOn(AndroidSchedulers.mainThread())
          .subscribeOn(Schedulers.io())
          .subscribe(new Subscriber<Boolean>() {
              @Override
              public void onCompleted() {

              }

              @Override
              public void onError(Throwable e) {
                callback.notAvailable();
              }

              @Override
              public void onNext(Boolean available) {
                  Log.d("Test", "network available : " + available);
                  if(callback != null)
                  {
                      if(available)
                      {
                          callback.available();
                      } else {
                          callback.notAvailable();
                      }
                  }
              }
          });

        if(compositeSubscription != null)
        {
            compositeSubscription.add(subscription);
        }
    }

    public interface ICheckConnectNetworkCallback
    {
        void available();
        void notAvailable();
    }
}
