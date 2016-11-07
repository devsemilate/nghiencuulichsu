package s3.lamphan.nghiencuulichsu.mvp.presenters;

import android.content.Context;
import android.util.Log;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import s3.lamphan.nghiencuulichsu.domain.persistence.TopicDbHelper;
import s3.lamphan.nghiencuulichsu.domain.repository.IBaseCallback;
import s3.lamphan.nghiencuulichsu.domain.repository.IDataLogicCallback;
import s3.lamphan.nghiencuulichsu.domain.repository.TopicRepository;
import s3.lamphan.nghiencuulichsu.mvp.models.Topic;

/**
 * Created by lam.phan on 11/2/2016.
 */
public class TopicPresenter extends BasePresenter{
    private static Object lock = new Object();
    private TopicRepository topicRepository;
    private TopicDbHelper topicDbHelper;
    private int pageSize;
    private int curPage;
    private boolean isGettingTopic = false;

    public TopicPresenter(Context context, int pageSize, int curPage) {
        super(context);
        topicRepository = new TopicRepository();
        addRepository(topicRepository);
        topicDbHelper = new TopicDbHelper(context);
        this.pageSize = pageSize;
        this.curPage = curPage;
    }

    public void resetPage(int pageSize, int curPage)
    {
        this.pageSize = pageSize;
        this.curPage = curPage;
    }

    public void getTopicsByBranchId(final String branchId, final IBaseCallback<Topic> callback)
    {
        Log.d("Test", "current topic page : " + curPage);
        synchronized (lock) {
            if(isGettingTopic)
                return;
            if (curPage == END_PAGE)
                return;
        }
        isGettingTopic = true;
        Log.d("Test", "start get topic page : " + curPage);
        this.checkConnectionNetwork(new ICheckConnectNetworkCallback() {
            @Override
            public void available() {
                topicRepository.getListTopic(branchId, pageSize, curPage, new IBaseCallback<Topic>() {
                    @Override
                    public void success(List<Topic> results) {
                        if (callback != null) {
                            callback.success(results);
                            if (results.size() > 0) {
                                curPage++;
                            } else {
                                curPage = END_PAGE;
                            }
                        }

                        // save topic
                        saveTopics(results);

                        isGettingTopic = false;
                    }

                    @Override
                    public void error(String message) {
                        getTopicListFromDb(branchId, callback, Error.UNKNOWN);
                        curPage = END_PAGE;
                        isGettingTopic = false;
                    }

                    @Override
                    public void error(Error error) {
                        getTopicListFromDb(branchId, callback, error);
                        curPage = END_PAGE;
                        isGettingTopic = false;
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
                getTopicListFromDb(branchId, callback, Error.NETWORK_CONNECT);
                curPage = END_PAGE;
                isGettingTopic = false;
            }
        });
    }

    public void getTopicListFromDb(String branchId, final IBaseCallback<Topic> callback, final Error error)
    {
        if(curPage == 0)
            return;
        Subscription subscription = topicDbHelper.getTopicByBranchId(branchId).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Action1<List<Topic>>() {
                    @Override
                    public void call(List<Topic> topicList) {
                        if(callback != null)
                        {
                            callback.success(topicList);
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

    public void saveTopics(List<Topic> topicList)
    {
        Subscription subscription = topicDbHelper.addNewTopics(topicList).observeOn(AndroidSchedulers.mainThread())
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
