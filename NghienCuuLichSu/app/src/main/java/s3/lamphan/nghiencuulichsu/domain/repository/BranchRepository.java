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
import s3.lamphan.nghiencuulichsu.domain.repository.restModels.BranchRestModel;
import s3.lamphan.nghiencuulichsu.domain.repository.services.IBranchService;
import s3.lamphan.nghiencuulichsu.mvp.models.Branch;

/**
 * Created by lam.phan on 11/3/2016.
 */
public class BranchRepository extends BaseRepository{
    private IBranchService service;
    public BranchRepository()
    {
        super(new BaseGateWay());
        init();
    }
    public BranchRepository(IGateWay baseGateWay) {
        super(baseGateWay);
        init();
    }

    private void init()
    {
        if(retrofit != null)
        {
            service = retrofit.create(IBranchService.class);
        }
    }

    public void getBranchs(final IBaseCallback<Branch> callback)
    {
        if(service == null || compositeSubscription == null)
        {
            callback.error("Service or CompositeSubscription null");
            return;
        }

        Log.d("Test", "execute getbranchs");
        Observable<BranchRestModel> call = this.service.getBranchs();
        Subscription subscription = call.observeOn(AndroidSchedulers.mainThread())
                                        .subscribeOn(Schedulers.newThread())
                                        .subscribe(new Subscriber<BranchRestModel>() {
                                            @Override
                                            public void onCompleted() {

                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                Log.d("Test", "getBranch error : " + e.getMessage());
                                                callback.error(e.getMessage());
                                            }

                                            @Override
                                            public void onNext(BranchRestModel restModel) {
                                                Log.d("Test", "getBranch ok : " + new Gson().toJson(restModel));
                                                callback.success(Branch.convertFromRestModel(restModel));
                                            }
                                        });

        this.compositeSubscription.add(subscription);
    }

    @Override
    public void releaseCompositeSubscription() {
        super.releaseCompositeSubscription();
    }
}
