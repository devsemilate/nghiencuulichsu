package s3.lamphan.nghiencuulichsu.mvp.views.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import s3.lamphan.nghiencuulichsu.R;
import s3.lamphan.nghiencuulichsu.mvp.presenters.BasePresenter;

/**
 * Created by lam.phan on 11/4/2016.
 */
public class BaseFragment extends Fragment{
    private Dialog loadingDialog;
    private ImageView imvLoading;
    private AnimationDrawable loadingAnim;
    private RelativeLayout rlDialogOutline;
    private List<BasePresenter> presenterList = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        loadingDialog = new Dialog(context);
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingDialog.setContentView(R.layout.dialog_loading);
        loadingDialog.setCanceledOnTouchOutside(false);
        imvLoading = (ImageView) loadingDialog.findViewById(R.id.imvLoading);
        imvLoading.setBackgroundResource(R.drawable.loading);
        loadingAnim = (AnimationDrawable)imvLoading.getBackground();
        rlDialogOutline = (RelativeLayout) loadingDialog.findViewById(R.id.rlDialogOutline);
    }

    protected void showLoading()
    {
        if(rlDialogOutline != null)
        {
            rlDialogOutline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideLoading();
                }
            });
        }
        loadingDialog.show();
        if(loadingAnim != null)
        {
            loadingAnim.start();
        }
    }

    protected void hideLoading()
    {
        loadingDialog.hide();
        if(loadingAnim != null)
        {
            loadingAnim.stop();
        }
    }

    public void addPresenter(BasePresenter presenter)
    {
        presenterList.add(presenter);
    }

    @Override
    public void onStop() {
        super.onStop();

        for(BasePresenter presenter : presenterList)
        {
            presenter.release();
        }
    }
}
