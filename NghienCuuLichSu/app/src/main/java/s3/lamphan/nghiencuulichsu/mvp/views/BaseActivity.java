package s3.lamphan.nghiencuulichsu.mvp.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import s3.lamphan.nghiencuulichsu.R;
import s3.lamphan.nghiencuulichsu.mvp.presenters.BasePresenter;
import s3.lamphan.nghiencuulichsu.utils.navigator.IActivityNavigator;
import s3.lamphan.nghiencuulichsu.utils.navigator.LeftRightNavigator;

/**
 * Created by lam.phan on 11/2/2016.
 */
public class BaseActivity extends AppCompatActivity{
    protected FragmentManager fragmentManager;
    protected IActivityNavigator iNavigator;
    protected Dialog loadingDialog;
    private List<BasePresenter> presenterList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentManager = getSupportFragmentManager();

        loadingDialog = new Dialog(this);
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingDialog.setContentView(R.layout.dialog_loading);
        loadingDialog.setCanceledOnTouchOutside(false);

    }

    protected void setNavigator(IActivityNavigator iActivityNavigator)
    {
        this.iNavigator = iActivityNavigator;
    }

    private IActivityNavigator getNavigator()
    {
        if(iNavigator == null)
        {
            // default
            iNavigator = new LeftRightNavigator();
        }

        return iNavigator;
    }

    public void goToActivity(Intent target, boolean finish)
    {
        getNavigator().goToActivity(this, target, finish);
    }

    public void backToActivity(Intent target)
    {
        getNavigator().backToActivity(this, target);
    }

    public void addFragment(int containerId, Fragment target, boolean overrideAnim)
    {
        getNavigator().addFragment(fragmentManager, containerId, target, overrideAnim);
    }

    public void replcaeFragment(int containerId, Fragment target, boolean overrideAnim)
    {
        getNavigator().replaceFragment(fragmentManager, containerId, target, overrideAnim);
    }

    protected void showLoading()
    {
        RelativeLayout rlDialogOutline = (RelativeLayout) loadingDialog.findViewById(R.id.rlDialogOutline);
        rlDialogOutline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideLoading();
            }
        });
        ImageView imvLoading = (ImageView) loadingDialog.findViewById(R.id.imvLoading);
        imvLoading.setBackgroundResource(R.drawable.loading);
        AnimationDrawable loadingDrawable = (AnimationDrawable)imvLoading.getBackground();
        loadingDialog.show();
        loadingDrawable.start();
    }

    protected void hideLoading()
    {
        ImageView imvLoading = (ImageView) loadingDialog.findViewById(R.id.imvLoading);
        imvLoading.setBackgroundResource(R.drawable.loading);
        AnimationDrawable loadingDrawable = (AnimationDrawable)imvLoading.getBackground();
        loadingDrawable.stop();
        loadingDialog.hide();
    }

    protected void addPresenter(BasePresenter presenter)
    {
        this.presenterList.add(presenter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        for(BasePresenter presenter : presenterList)
        {
            presenter.release();
        }
    }
}
