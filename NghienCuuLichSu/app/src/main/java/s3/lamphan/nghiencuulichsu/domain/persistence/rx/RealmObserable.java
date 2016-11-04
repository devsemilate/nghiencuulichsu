package s3.lamphan.nghiencuulichsu.domain.persistence.rx;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * Created by lam.phan on 10/31/2016.
 */
public class RealmObserable {
    public static <T extends RealmObject> Observable<T> object(Context context, final Func1<Realm, T> func1){
        return Observable.create(new OnSubscribeRealm<T>(context){
            @Override
            public T execute(Realm realm) {
                return func1.call(realm);
            }
        });
    }

    public static <T extends RealmObject> Observable<RealmList<T>> list(Context context, final Func1<Realm, RealmList<T>> func1){
        return Observable.create(new OnSubscribeRealm<RealmList<T>>(context) {
            @Override
            public RealmList<T> execute(Realm realm) {
                return func1.call(realm);
            }
        });
    }

    public static <T extends RealmObject> Observable<RealmResults<T>> result(Context context,
                                         final Func1<Realm, RealmResults<T>> func1){
        return Observable.create(new OnSubscribeRealm<RealmResults<T>>(context) {
            @Override
            public RealmResults<T> execute(Realm realm) {
                return func1.call(realm);
            }
        });
    }
}
