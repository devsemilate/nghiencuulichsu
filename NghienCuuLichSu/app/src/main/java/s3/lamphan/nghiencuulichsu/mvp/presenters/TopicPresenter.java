package s3.lamphan.nghiencuulichsu.mvp.presenters;

import android.content.Context;

import s3.lamphan.nghiencuulichsu.domain.repository.IBaseCallback;
import s3.lamphan.nghiencuulichsu.domain.repository.TopicRepository;
import s3.lamphan.nghiencuulichsu.mvp.models.Topic;

/**
 * Created by lam.phan on 11/2/2016.
 */
public class TopicPresenter extends BasePresenter{
    private TopicRepository topicRepository;

    public TopicPresenter(Context context) {
        super(context);
        topicRepository = new TopicRepository();
        addRepository(topicRepository);
    }

    public void getTopicsByBranchId(final String branchId,
                                    final IBaseCallback<Topic> callback)
    {
        topicRepository.getListTopic(branchId, callback);
//        this.checkConnectionNetwork(new ICheckConnectNetworkCallback() {
//            @Override
//            public void available() {
//                topicRepository.getListTopic(branchId, callback);
//            }
//
//            @Override
//            public void notAvailable() {
//                callback.error("network error");
//            }
//        });
    }
}
