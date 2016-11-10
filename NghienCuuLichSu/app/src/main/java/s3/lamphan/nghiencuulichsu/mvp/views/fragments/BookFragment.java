package s3.lamphan.nghiencuulichsu.mvp.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
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
import s3.lamphan.nghiencuulichsu.mvp.models.Book;
import s3.lamphan.nghiencuulichsu.mvp.models.Branch;
import s3.lamphan.nghiencuulichsu.mvp.models.Topic;
import s3.lamphan.nghiencuulichsu.mvp.presenters.BasePresenter;
import s3.lamphan.nghiencuulichsu.mvp.presenters.BookPresenter;
import s3.lamphan.nghiencuulichsu.mvp.presenters.DownloadBookPresenter;
import s3.lamphan.nghiencuulichsu.mvp.presenters.TopicPresenter;
import s3.lamphan.nghiencuulichsu.mvp.views.IHandleDownloadView;
import s3.lamphan.nghiencuulichsu.mvp.views.IMainView;
import s3.lamphan.nghiencuulichsu.ui.adapter.BookAdapter;
import s3.lamphan.nghiencuulichsu.utils.RecyclerViewScrollBehavior;

/**
 * Created by lam.phan on 11/7/2016.
 */
public class BookFragment extends BaseFragment implements IHandleDownloadView {
    public static final String BRANCH_BUNDLE = "BRANCH_BUNDLE";
    private Branch branch;
    private BookPresenter bookPresenter;
    private List<Book> bookList = new ArrayList<>();
    private BookAdapter bookAdapter;
    private LinearLayoutManager linearLayoutManager;
    private IMainView container;
    private DownloadBookPresenter downloadBookPresenter;

    @Bind(R.id.rvBook)
    RecyclerView rvBook;

    public static BookFragment createNewInstance(Branch branch)
    {
        BookFragment bookFragment = new BookFragment();
        Bundle branchBundle = new Bundle();
        branchBundle.putParcelable(BRANCH_BUNDLE, Parcels.wrap(branch));
        bookFragment.setArguments(branchBundle);

        return bookFragment;
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
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        linearLayoutManager = new LinearLayoutManager(this.getContext());
        rvBook.setLayoutManager(linearLayoutManager);
        bookAdapter = new BookAdapter(this.getContext(), bookList, new BookAdapter.IBookItemClickListener() {
            @Override
            public void onBookItemClick(Book book) {
                if(container != null)
                {
                    presentDownloadBookView(book);
                }
            }
        });
        rvBook.setAdapter(bookAdapter);
        rvBook.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (RecyclerViewScrollBehavior.isScrollingOverMiddle(linearLayoutManager, bookList.size())) {
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

        bookPresenter = new BookPresenter(this.getContext(), BookPresenter.DEFAULT_PAGE_SIZE,
                BookPresenter.DEFAULT_START_PAGE);
        addPresenter(bookPresenter);
        downloadBookPresenter = new DownloadBookPresenter(this.getContext(), this);
        addPresenter(downloadBookPresenter);

        if(branch != null) {
            getBooks(branch.getId());
        }
    }

    public void getBooks(String branchId)
    {
        Log.d("Test", "get book...................");
        showLoading();
        bookPresenter.resetPage(BasePresenter.DEFAULT_PAGE_SIZE, BasePresenter.DEFAULT_START_PAGE);
        bookPresenter.getBookList(branchId, new IBaseCallback<Book>() {
            @Override
            public void success(List<Book> results) {
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
        bookPresenter.getBookList(branchId, new IBaseCallback<Book>() {
            @Override
            public void success(List<Book> results) {
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

    public void refreshTopicListView(List<Book> newBookData)
    {
        bookList.clear();
        bookList.addAll(newBookData);
        bookAdapter.notifyDataSetChanged();
    }

    public void addMoreTopicList(List<Book> moreBookData)
    {
        bookList.addAll(moreBookData);
        bookAdapter.notifyDataSetChanged();
    }

    public void presentDownloadBookView(Book book) {
        FragmentManager fm = getFragmentManager();
        DownloadBookDialogFm downloadBookDialogFm = DownloadBookDialogFm.getInstance(book);
        downloadBookDialogFm.setTargetFragment(BookFragment.this, 300);
        downloadBookDialogFm.show(fm, book.getName());
    }

    /* IHandleDownloadView */

    @Override
    public void onFailed(Book book, long downloadId) {
        Log.d("Test", "onFailed download book : " + new Gson().toJson(book));
    }

    @Override
    public void onPause(Book book, long downloadId) {
        Log.d("Test", "onPause download book : " + new Gson().toJson(book));
    }

    @Override
    public void onComplete(Book book, long downloadId) {
        Log.d("Test", "onComplete download book : " + new Gson().toJson(book));
    }

    @Override
    public void onProgress(Book book, long downloadId, int progress) {
        Log.d("Test", "onProgress download book : " + progress);
    }

    @Override
    public void onStart(Book book, long downloadId) {
        Log.d("Test", "onstart download book : " + new Gson().toJson(book));
    }

    @Override
    public void getLinkDownload(Book book, String url) {
        Log.d("Test", "get download link : " + url);
        downloadBookPresenter.startDownload(book, url);
    }

    /* end IHandleDownloadView */
}
