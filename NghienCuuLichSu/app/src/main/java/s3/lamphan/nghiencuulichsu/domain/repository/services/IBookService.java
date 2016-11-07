package s3.lamphan.nghiencuulichsu.domain.repository.services;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import s3.lamphan.nghiencuulichsu.domain.repository.restModels.BookRestModel;
import s3.lamphan.nghiencuulichsu.domain.repository.restModels.TopicRestModel;

/**
 * Created by lam.phan on 11/7/2016.
 */
public interface IBookService {
    @GET("v1/data/book")
    Observable<BookRestModel> getBooks(
            @Query("where") String whereClause,
            @Query("pageSize") Integer pageSize,
            @Query("offset") Integer offset
    );
}
