package s3.lamphan.nghiencuulichsu.domain.persistence;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Func1;
import s3.lamphan.nghiencuulichsu.domain.persistence.models.BranchRealmObject;
import s3.lamphan.nghiencuulichsu.domain.persistence.rx.RealmObserable;
import s3.lamphan.nghiencuulichsu.mvp.models.Branch;

/**
 * Created by lam.phan on 11/7/2016.
 */
public class BranchDbHelper {
    private Context context;
    public BranchDbHelper(Context context) {
        this.context = context;
    }

    public Observable<Boolean> addNewBranchs(final List<Branch> branchList)
    {
        return RealmObserable.object(context, new Func1<Realm, RealmObject>() {
            @Override
            public RealmObject call(Realm realm) {
//                realm.where(BranchRealmObject.class).findAll().deleteAllFromRealm();
                Log.d("Test", "save...........");
                for(Branch branch : branchList)
                {
                    BranchRealmObject input = BranchRealmObject.convertFromBranchModel(branch);
                    realm.copyToRealmOrUpdate(input);
                }
                Log.d("Test", "save finished.");
                return new BranchRealmObject();
            }
        }).map(new Func1<RealmObject, Boolean>() {
            @Override
            public Boolean call(RealmObject realmObject) {
                Log.d("Test", "save branch list ok");
                return true;
            }
        });
    }

    public Observable<List<Branch>> getBranchList()
    {
        return RealmObserable.result(context, new Func1<Realm, RealmResults<BranchRealmObject>>() {
            @Override
            public RealmResults<BranchRealmObject> call(Realm realm) {
                return realm.where(BranchRealmObject.class).findAll();
            }
        }).map(new Func1<RealmResults<BranchRealmObject>, List<Branch>>() {
            @Override
            public List<Branch> call(RealmResults<BranchRealmObject> ts) {
                List<Branch> result = new ArrayList<Branch>();
                for(BranchRealmObject r : ts)
                {
                    result.add(BranchRealmObject.convertToBranchModel(r));
                }
                Log.d("Test", "get branch list : " + new Gson().toJson(result));
                return result;
            }
        });
    }
}
