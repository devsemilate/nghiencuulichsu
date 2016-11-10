package s3.lamphan.nghiencuulichsu.mvp.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import s3.lamphan.nghiencuulichsu.R;
import s3.lamphan.nghiencuulichsu.mvp.models.Book;
import s3.lamphan.nghiencuulichsu.mvp.presenters.DownloadBookPresenter;
import s3.lamphan.nghiencuulichsu.mvp.views.IHandleDownloadView;

/**
 * Created by lam.phan on 11/8/2016.
 */
public class DownloadBookDialogFm extends DialogFragment{
    public static final String BOOK_BUNDLE = "BOOK_BUNDLE";
    private Book book;
    private IHandleDownloadView iHandleDownloadView;

    @Bind(R.id.wvDownloadBook)
    WebView wvDownloadBook;

    public static DownloadBookDialogFm getInstance(Book book)
    {
        DownloadBookDialogFm dialogFm = new DownloadBookDialogFm();
        Bundle args = new Bundle();
        args.putParcelable(BOOK_BUNDLE, Parcels.wrap(book));
        dialogFm.setArguments(args);
        return dialogFm;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle != null)
        {
            book = Parcels.unwrap(bundle.getParcelable(BOOK_BUNDLE));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getDialog().setCanceledOnTouchOutside(true);

        View view = inflater.inflate(R.layout.dialog_fm_download_book, container, false);
        ButterKnife.bind(this, view);

        // setup
        wvDownloadBook.getSettings().setUseWideViewPort(true);
        wvDownloadBook.getSettings().setLoadWithOverviewMode(true);
        wvDownloadBook.getSettings().setMinimumFontSize(10);
        wvDownloadBook.getSettings().setJavaScriptEnabled(true);
        wvDownloadBook.setInitialScale(1);
        wvDownloadBook.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("Test", "catch url : " + url);
                if(catchedDownloadUrl(url))
                {
                    closeDialogAndDispatchLink(url);
                    return false;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        wvDownloadBook.loadUrl(book.getDownloadUrl());
    }

    public boolean catchedDownloadUrl(String url)
    {
        for(String sign : DownloadBookPresenter.LINK_DOWNLOAD_SIGN)
        {
            if(url.contains(sign))
            {
                return true;
            }
        }

        return false;
    }

    public void closeDialogAndDispatchLink(String url)
    {
        iHandleDownloadView = (IHandleDownloadView) getTargetFragment();
        if(iHandleDownloadView != null)
        {
            iHandleDownloadView.getLinkDownload(book, url);
        }
        this.dismiss();
    }
}
