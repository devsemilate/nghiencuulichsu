package s3.lamphan.nghiencuulichsu.mvp.views;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import s3.lamphan.nghiencuulichsu.R;
import s3.lamphan.nghiencuulichsu.domain.repository.IBaseCallback;
import s3.lamphan.nghiencuulichsu.mvp.models.Topic;
import s3.lamphan.nghiencuulichsu.mvp.presenters.BasePresenter;
import s3.lamphan.nghiencuulichsu.mvp.presenters.ParseContentPresenter;

/**
 * Created by lam.phan on 11/4/2016.
 */
public class ContentActivity extends BaseActivity {
    public static final String TOPIC = "TOPIC";

    private Topic topic;
    private ParseContentPresenter parseContentPresenter;

    @Bind(R.id.toolbarContent)
    Toolbar toolbarContent;
    @Bind(R.id.wvContent)
    WebView wvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        ButterKnife.bind(this);

        setSupportActionBar(toolbarContent);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        // setup webview
        WebSettings settings = wvContent.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setMinimumFontSize(30);
        wvContent.setInitialScale(1);
        wvContent.setWebViewClient(new WebViewClient());

        Bundle args = getIntent().getExtras();
        if(args != null) {
            topic = Parcels.unwrap(args.getParcelable(TOPIC));
            getSupportActionBar().setTitle(topic.getName());
        }

        parseContentPresenter = new ParseContentPresenter(this);
        addPresenter(parseContentPresenter);
        getContent();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId)
        {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    private void getContent()
    {
        showLoading();
        parseContentPresenter.parseContentOfUrl(topic.getContentUrl(),
                parseContentPresenter.parseContentTags(topic.getContentTags()),
                new IBaseCallback<String>() {
                    @Override
                    public void success(List<String> results) {
                        if(results != null) {
                            wvContent.loadData(results.get(0),
                                    "text/html; charset=UTF-8", null);
                        }
                        hideLoading();
                    }

                    @Override
                    public void error(String message) {
                        hideLoading();
                    }

                    @Override
                    public void error(BasePresenter.Error error) {
                        hideLoading();
                    }
                });
    }

}
