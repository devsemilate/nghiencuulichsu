package s3.lamphan.nghiencuulichsu.domain.persistence.rx;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;
import s3.lamphan.nghiencuulichsu.domain.persistence.NCLSMigration;

/**
 * Created by lam.phan on 10/31/2016.
 */
public abstract class OnSubscribeRealm<T> implements Observable.OnSubscribe<T> {
    private final Context context;
    private final String fileName;

    private final List<Subscriber<? super T>> subscribers = new ArrayList<>();
    private final Object lock = new Object();
    private final AtomicBoolean canceled = new AtomicBoolean();

    public OnSubscribeRealm(Context context) {
        this(context, null);
    }

    public OnSubscribeRealm(Context context, String fileName) {
        this.context = context.getApplicationContext();
        this.fileName = fileName;
    }

    @Override
    public void call(Subscriber<? super T> subscriber) {
        synchronized (lock){
            boolean isCanceled = canceled.get();
            if(!isCanceled && !subscribers.isEmpty()){
                subscriber.add(newUnsubscribeAction(subscriber));
                subscribers.add(subscriber);
                return;
            } else if(isCanceled){
                return;
            }
        }

        subscriber.add(newUnsubscribeAction(subscriber));
        subscribers.add(subscriber);

        RealmConfiguration.Builder builder = new RealmConfiguration.Builder(context)
                .schemaVersion(NCLSMigration.VERSION)
                .migration(new NCLSMigration());
        if(fileName != null){
            builder.name(fileName);
        }

        Realm realm = Realm.getInstance(builder.build());

        T object = null;
        boolean error = false;
        try {
            if(!canceled.get()) {
                realm.beginTransaction();
                object = execute(realm);
                if(object != null && !canceled.get()){
                    realm.commitTransaction();
                } else {
                    realm.cancelTransaction();
                }
            }
        } catch (Exception ex){
            realm.cancelTransaction();
            sendOnError(ex);
            error = true;
        }

        if(object != null && !error && !canceled.get()) {
            sendOnNext(object);
        }

        try {
            realm.close();
        } catch (RealmException ex){
            sendOnError(ex);
            error = true;
        }
        if(!error) {
            sendOnComplete();
        }
        canceled.set(false);
    }

    private void sendOnNext(T object){
        for(Subscriber subscriber : subscribers)
        {
            subscriber.onNext(object);
        }
    }

    private void sendOnError(Throwable error){
        for(Subscriber subscriber : subscribers)
        {
            subscriber.onError(error);
        }
    }

    private void sendOnComplete(){
        for(Subscriber subscriber : subscribers)
        {
            subscriber.onCompleted();
        }
    }

    private Subscription newUnsubscribeAction(final Subscriber<? super T> subscriber) {
        return Subscriptions.create(new Action0() {
            @Override
            public void call() {
               synchronized (lock) {
                   subscribers.remove(subscriber);
                   if (subscribers.isEmpty()) {
                       canceled.set(true);
                   }
               }
            }
        });
    }

    public abstract T execute(Realm realm);
}
