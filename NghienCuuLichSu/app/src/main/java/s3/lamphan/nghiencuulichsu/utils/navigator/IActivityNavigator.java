package s3.lamphan.nghiencuulichsu.utils.navigator;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by lam.phan on 11/2/2016.
 */
public interface IActivityNavigator {
    void goToActivity(AppCompatActivity fromActivity, Intent target, boolean finish);
    void backToActivity(AppCompatActivity fromActivity, Intent target);
    void addFragment(FragmentManager fragmentManager, int containerId,
                     Fragment target, boolean overrideAnim);
    void replaceFragment(FragmentManager fragmentManager, int containerId,
                         Fragment target, boolean overrideAnim);
}
