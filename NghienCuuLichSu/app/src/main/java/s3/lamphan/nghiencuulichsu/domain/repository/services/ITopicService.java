package s3.lamphan.nghiencuulichsu.domain.repository.services;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import s3.lamphan.nghiencuulichsu.domain.repository.restModels.TopicRestModel;

/**
 * Created by lam.phan on 10/31/2016.
 */
public interface ITopicService {
    @GET("v1/data/topic")
    Observable<TopicRestModel> getTopics(
        @Query("where") String whereBranchId
    );

}
