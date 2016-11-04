package s3.lamphan.nghiencuulichsu.mvp.presenters;

import android.content.Context;

import s3.lamphan.nghiencuulichsu.domain.repository.BranchRepository;
import s3.lamphan.nghiencuulichsu.domain.repository.IBaseCallback;
import s3.lamphan.nghiencuulichsu.mvp.models.Branch;

/**
 * Created by lam.phan on 11/3/2016.
 */
public class BranchPresenter extends BasePresenter{
    private BranchRepository branchRepository;

    public BranchPresenter(Context context) {
        super(context);
        branchRepository = new BranchRepository();
        addRepository(branchRepository);
    }

    public void getBranchs(final IBaseCallback<Branch> callback)
    {
        this.checkConnectionNetwork(new ICheckConnectNetworkCallback() {
            @Override
            public void available() {
                if(callback != null)
                {
                    branchRepository.getBranchs(callback);
                }
            }

            @Override
            public void notAvailable() {
                if(callback != null)
                {
                    callback.error("connection error");
                }
            }
        });
    }
}
