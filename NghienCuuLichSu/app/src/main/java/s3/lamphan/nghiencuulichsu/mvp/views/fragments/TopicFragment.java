package s3.lamphan.nghiencuulichsu.mvp.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import s3.lamphan.nghiencuulichsu.R;
import s3.lamphan.nghiencuulichsu.domain.repository.IBaseCallback;
import s3.lamphan.nghiencuulichsu.mvp.models.Branch;
import s3.lamphan.nghiencuulichsu.mvp.models.Topic;
import s3.lamphan.nghiencuulichsu.mvp.presenters.BasePresenter;
import s3.lamphan.nghiencuulichsu.mvp.presenters.TopicPresenter;
import s3.lamphan.nghiencuulichsu.mvp.views.IMainView;
import s3.lamphan.nghiencuulichsu.ui.adapter.TopicAdapter;
import s3.lamphan.nghiencuulichsu.utils.RecyclerViewScrollBehavior;

/**
 * Created by lam.phan on 11/4/2016.
 */
public class TopicFragment extends BaseFragment{
    public static final String BRANCH_BUNDLE = "BRANCH_BUNDLE";
    private Branch branch;
    private TopicPresenter topicPresenter;
    private List<Topic> topicList = new ArrayList<>();
    private TopicAdapter topicAdapter;
    private LinearLayoutManager linearLayoutManager;
    private IMainView container;

    @Bind(R.id.rvTopic)
    RecyclerView rvTopic;

    public static TopicFragment createNewInstance(Branch branch)
    {
        TopicFragment topicFragment = new TopicFragment();
        Bundle branchBundle = new Bundle();
        branchBundle.putParcelable(BRANCH_BUNDLE, Parcels.wrap(branch));
        topicFragment.setArguments(branchBundle);

        return topicFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        container = (IMainView) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle branchBundle = this.getArguments();
        if(branchBundle != null)
        {
            branch = Parcels.unwrap(branchBundle.getParcelable(BRANCH_BUNDLE));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        linearLayoutManager = new LinearLayoutManager(this.getContext());
        rvTopic.setLayoutManager(linearLayoutManager);
        topicAdapter = new TopicAdapter(this.getContext(), topicList, new TopicAdapter.ITopicItemClickListener() {
            @Override
            public void onClick(Topic selectedTopic) {
                if(container != null)
                {
                    container.presentContentView(selectedTopic);
                }
            }
        });
        rvTopic.setAdapter(topicAdapter);
        rvTopic.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (RecyclerViewScrollBehavior.isScrollingOverMiddle(linearLayoutManager, topicList.size())) {
                    if (branch != null) {
                        loadMore(branch.getId());
                    }
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        topicPresenter = new TopicPresenter(this.getContext(), TopicPresenter.DEFAULT_PAGE_SIZE,
                TopicPresenter.DEFAULT_START_PAGE);
        addPresenter(topicPresenter);

        if(branch != null) {
            getTopic(branch.getId());
        }
    }

    public void getTopic(String branchId)
    {
        Log.d("Test", "get topic...................");
        showLoading();
        topicPresenter.resetPage(BasePresenter.DEFAULT_PAGE_SIZE, BasePresenter.DEFAULT_START_PAGE);
        topicPresenter.getTopicsByBranchId(branchId, new IBaseCallback<Topic>() {
            @Override
            public void success(List<Topic> results) {
                Log.d("Test", "get topic success : " + new Gson().toJson(results));
                refreshTopicListView(results);
                hideLoading();
            }

            @Override
            public void error(String message) {
                Log.d("Test", "get topic error : " + message);
                hideLoading();
            }

            @Override
            public void error(BasePresenter.Error error) {
                hideLoading();
            }
        });
    }

    public void loadMore(String branchId)
    {
        Log.d("Test", "load more..................");
        topicPresenter.getTopicsByBranchId(branchId, new IBaseCallback<Topic>() {
            @Override
            public void success(List<Topic> results) {
                Log.d("Test", "get topic success : " + new Gson().toJson(results));
                addMoreTopicList(results);
                hideLoading();
            }

            @Override
            public void error(String message) {
                Log.d("Test", "get topic error : " + message);
                hideLoading();
            }

            @Override
            public void error(BasePresenter.Error error) {
                hideLoading();
            }
        });
    }

    public void refreshTopicListView(List<Topic> newTopicData)
    {
        topicList.clear();
        topicList.addAll(newTopicData);
        topicAdapter.notifyDataSetChanged();
    }

    public void addMoreTopicList(List<Topic> moreTopicData)
    {
        topicList.addAll(moreTopicData);
        topicAdapter.notifyDataSetChanged();
    }
}
