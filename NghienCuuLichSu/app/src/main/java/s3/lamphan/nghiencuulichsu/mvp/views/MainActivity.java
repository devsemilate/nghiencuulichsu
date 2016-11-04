package s3.lamphan.nghiencuulichsu.mvp.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;
import s3.lamphan.nghiencuulichsu.R;
import s3.lamphan.nghiencuulichsu.domain.persistence.rx.RealmObserable;
import s3.lamphan.nghiencuulichsu.domain.repository.IBaseCallback;
import s3.lamphan.nghiencuulichsu.domain.repository.TopicRepository;
import s3.lamphan.nghiencuulichsu.mvp.models.Branch;
import s3.lamphan.nghiencuulichsu.mvp.models.Topic;
import s3.lamphan.nghiencuulichsu.mvp.presenters.BranchPresenter;
import s3.lamphan.nghiencuulichsu.mvp.views.fragments.TopicFragment;
import s3.lamphan.nghiencuulichsu.ui.adapter.DrawerMenuAdapter;

public class MainActivity extends BaseActivity implements IMainView{
    @Bind(R.id.mDrawerLayout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.toolbarMain)
    Toolbar toolbarMain;
    @Bind(R.id.rvLeftDrawer)
    RecyclerView rvLeftDrawer;

    private ActionBarDrawerToggle mDrawerToggle;

    //Branch
    private BranchPresenter branchPresenter;
    private List<Branch> branchList;
    private DrawerMenuAdapter drawerMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbarMain);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbarMain,
                                                    R.string.open_drawer, R.string.close_drawer);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        //branch
        branchPresenter = new BranchPresenter(this);
        addPresenter(branchPresenter);
        branchList = new ArrayList<>();
        rvLeftDrawer.setLayoutManager(new LinearLayoutManager(this));
        drawerMenuAdapter = new DrawerMenuAdapter(this, branchList, new DrawerMenuAdapter.IDrawerItemClickListener() {
            @Override
            public void onSelected(Branch branchSelected) {
                mDrawerLayout.closeDrawers();
                getSupportActionBar().setTitle(branchSelected.getName());
                presentTopicFragment(branchSelected);
            }
        });
        rvLeftDrawer.setAdapter(drawerMenuAdapter);
        getBranchs();
    }

    public void getBranchs()
    {
        if(branchPresenter == null)
        {
            branchPresenter = new BranchPresenter(this);
        }

        Log.d("Test", "start get branchs");
        showLoading();
        branchPresenter.getBranchs(new IBaseCallback<Branch>() {
            @Override
            public void success(List<Branch> results) {
                branchList.clear();
                branchList.addAll(results);
                drawerMenuAdapter.notifyDataSetChanged();
                hideLoading();
            }

            @Override
            public void error(String message) {
                hideLoading();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void presentTopicFragment(Branch branchSelected)
    {
        TopicFragment topicFragment = TopicFragment.createNewInstance(branchSelected);
        replcaeFragment(R.id.flFragmentContainer, topicFragment, true);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /* IMainView */

    @Override
    public void presentContentView(Topic topic) {
        Intent presentContentViewIntent = new Intent(this, ContentActivity.class);
        Bundle args = new Bundle();
        args.putParcelable(ContentActivity.TOPIC, Parcels.wrap(topic));
        presentContentViewIntent.putExtras(args);
        goToActivity(presentContentViewIntent, false);
    }
    /* end IMainView */
}
