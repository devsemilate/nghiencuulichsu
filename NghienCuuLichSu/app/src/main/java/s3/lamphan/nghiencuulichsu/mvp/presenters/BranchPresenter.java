package s3.lamphan.nghiencuulichsu.mvp.presenters;

import android.content.Context;
import android.util.Log;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import s3.lamphan.nghiencuulichsu.domain.persistence.BranchDbHelper;
import s3.lamphan.nghiencuulichsu.domain.repository.BranchRepository;
import s3.lamphan.nghiencuulichsu.domain.repository.IBaseCallback;
import s3.lamphan.nghiencuulichsu.mvp.models.Branch;

/**
 * Created by lam.phan on 11/3/2016.
 */
public class BranchPresenter extends BasePresenter{
    private BranchRepository branchRepository;
    private BranchDbHelper branchDbHelper;

    public BranchPresenter(Context context) {
        super(context);
        branchRepository = new BranchRepository();
        addRepository(branchRepository);
        branchDbHelper = new BranchDbHelper(context);
    }

    public void getBranchs(final IBaseCallback<Branch> callback)
    {
        this.checkConnectionNetwork(new ICheckConnectNetworkCallback() {
            @Override
            public void available() {
                if(callback != null)
                {
                    branchRepository.getBranchs(new IBaseCallback<Branch>() {
                        @Override
                        public void success(List<Branch> results) {
                            callback.success(results);
                            // save db
                            saveBranchListToDb(results);
                        }

                        @Override
                        public void error(String message) {
                            getBranchFromDb(callback, Error.UNKNOWN);
                        }

                        @Override
                        public void error(Error error) {
                            getBranchFromDb(callback, error);
                        }
                    });
                }
            }

            @Override
            public void notAvailable() {
                getBranchFromDb(callback, Error.NETWORK_CONNECT);
            }
        });
    }

    public void getBranchFromDb(final IBaseCallback<Branch> callback, final Error error)
    {
        Subscription subscription = branchDbHelper.getBranchList().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Action1<List<Branch>>() {
                    @Override
                    public void call(List<Branch> branchList) {
                        if(branchList.size() <= 0)
                        {
                            if(callback != null)
                            {
                                if(error != Error.UNKNOWN) {
                                    callback.error(error);
                                } else {

                                }
                            }
                        } else {
                            if(callback != null)
                            {
                                callback.success(branchList);
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if(callback != null)
                        {
                            if(error != Error.UNKNOWN) {
                                callback.error(error);
                            } else {
                                callback.error(throwable.getMessage());
                            }
                        }
                    }
                });

        compositeSubscription.add(subscription);
    }

    public void saveBranchListToDb(List<Branch> branchList)
    {
        Log.d("Test", "start save branch.....");
        Subscription subscription = branchDbHelper.addNewBranchs(branchList).observeOn(AndroidSchedulers.mainThread())
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
