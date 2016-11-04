package s3.lamphan.nghiencuulichsu.domain.repository.services;

import retrofit2.http.GET;
import rx.Observable;
import s3.lamphan.nghiencuulichsu.domain.repository.restModels.BranchRestModel;

/**
 * Created by lam.phan on 11/3/2016.
 */
public interface IBranchService {
    @GET("v1/data/branch")
    Observable<BranchRestModel> getBranchs();
}
