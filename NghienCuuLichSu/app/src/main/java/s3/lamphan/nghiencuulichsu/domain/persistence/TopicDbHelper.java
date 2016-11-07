package s3.lamphan.nghiencuulichsu.domain.persistence;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Func1;
import s3.lamphan.nghiencuulichsu.domain.persistence.models.TopicRealmObject;
import s3.lamphan.nghiencuulichsu.domain.persistence.rx.RealmObserable;
import s3.lamphan.nghiencuulichsu.mvp.models.Topic;

/**
 * Created by lam.phan on 11/7/2016.
 */
public class TopicDbHelper {
    private Context context;

    public TopicDbHelper(Context context) {
        this.context = context;
    }

    public Observable<Boolean> addNewTopics(final List<Topic> topicList)
    {
        return RealmObserable.object(context, new Func1<Realm, RealmObject>() {
            @Override
            public RealmObject call(Realm realm) {
                for(Topic topic : topicList)
                {
                    TopicRealmObject topicRO = TopicRealmObject.convertFromTopicModel(topic);
                    realm.copyToRealmOrUpdate(topicRO);
                }
                return new TopicRealmObject();
            }
        }).map(new Func1<RealmObject, Boolean>() {
            @Override
            public Boolean call(RealmObject realmObject) {
                return true;
            }
        });
    }

    public Observable<List<Topic>> getTopicByBranchId(final String branchId)
    {
        return RealmObserable.result(context, new Func1<Realm, RealmResults<TopicRealmObject>>() {
            @Override
            public RealmResults<TopicRealmObject> call(Realm realm) {
                return realm.where(TopicRealmObject.class)
                        .equalTo("branch", branchId)
                        .findAll();
            }
        }).map(new Func1<RealmResults<TopicRealmObject>, List<Topic>>() {
            @Override
            public List<Topic> call(RealmResults<TopicRealmObject> topicRealmObjects) {
                List<Topic> result = new ArrayList<Topic>();
                for(TopicRealmObject tro : topicRealmObjects)
                {
                    result.add(TopicRealmObject.convertToTopicModel(tro));
                }
                return result;
            }
        });
    }
}
