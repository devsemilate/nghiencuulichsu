package s3.lamphan.nghiencuulichsu.utils.navigator;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import s3.lamphan.nghiencuulichsu.R;

/**
 * Created by lam.phan on 11/2/2016.
 */
public class LeftRightNavigator implements IActivityNavigator{
    public LeftRightNavigator() {
    }

    @Override
    public void goToActivity(AppCompatActivity fromActivity, Intent target, boolean finish) {
        if(fromActivity != null)
        {
            fromActivity.startActivity(target);
            fromActivity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
            if(finish)
            {
                fromActivity.finish();
            }
        }
    }

    @Override
    public void backToActivity(AppCompatActivity fromActivity, Intent target) {
        if(fromActivity != null)
        {
            fromActivity.startActivity(target);
            fromActivity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            fromActivity.finish();
        }
    }

    @Override
    public void addFragment(FragmentManager fragmentManager, int containerId,
                            Fragment target, boolean overrideAnim) {
        if(fragmentManager != null) {
            FragmentTransaction fT = fragmentManager.beginTransaction();
            if(overrideAnim){
                fT.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out);
            }
            fT.add(containerId, target, "Topic");
            fT.addToBackStack("Topic");
            fT.commit();
        }
    }

    @Override
    public void replaceFragment(FragmentManager fragmentManager, int containerId,
                                Fragment target, boolean overrideAnim) {
        if(fragmentManager != null) {
            FragmentTransaction fT = fragmentManager.beginTransaction();
            if(overrideAnim){
                fT.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out);
            }
            fT.replace(containerId, target, "Topic");
            fT.addToBackStack("Topic");
            fT.commit();
        }
    }
}
