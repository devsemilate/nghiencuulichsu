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
import s3.lamphan.nghiencuulichsu.domain.repository.restModels.TopicRestModel;
import s3.lamphan.nghiencuulichsu.domain.repository.services.ITopicService;
import s3.lamphan.nghiencuulichsu.mvp.models.Topic;

/**
 * Created by lam.phan on 10/31/2016.
 */
public class TopicRepository extends BaseRepository{
    private ITopicService service;

    public TopicRepository() {
        super(new BaseGateWay());
        init();
    }

    public TopicRepository(IGateWay baseGateWay) {
        super(baseGateWay);
        init();
    }

    private void init()
    {
        if(retrofit != null){
            service = retrofit.create(ITopicService.class);
        }
    }

    public void getListTopic(String branchId, final int pageSize, final int curPage,
                             final IBaseCallback<Topic> callback,
                             final IDataLogicCallback topicLogicCallback)
    {
        if(service == null || compositeSubscription == null)
        {
            callback.error("Service null or CompositeSubscription null");
            return;
        }

        final Observable<TopicRestModel> call = this.service.getTopics("branch='"+branchId+"'", pageSize, curPage);
        Log.d("Test", "call get topic : " + new Gson().toJson(call));
        Subscription subscription = call.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe(new Subscriber<TopicRestModel>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    Log.d("Test", "getTopic Error : " + e.getMessage());
                    callback.error(e.getMessage());
                }

                @Override
                public void onNext(TopicRestModel topicRestModel) {
                    Log.d("Test", "getTopic result : " + new Gson().toJson(topicRestModel));
                    callback.success(Topic.convertTopicFromRestModel(topicRestModel));
                    if (pageSize * (curPage + 1) >= topicRestModel.getTotalObjects()) {
                        if (topicLogicCallback != null) {
                            topicLogicCallback.overFlow();
                        }
                    }
                }
            });

        this.compositeSubscription.add(subscription);
    }

}
