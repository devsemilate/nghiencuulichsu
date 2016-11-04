package s3.lamphan.nghiencuulichsu.mvp.views;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import s3.lamphan.nghiencuulichsu.R;
import s3.lamphan.nghiencuulichsu.mvp.models.Topic;

/**
 * Created by lam.phan on 11/4/2016.
 */
public class ContentActivity extends BaseActivity {
    public static final String TOPIC = "TOPIC";

    private Topic topic;

    @Bind(R.id.toolbarContent)
    Toolbar toolbarContent;
    @Bind(R.id.tvContent)
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        ButterKnife.bind(this);

        setSupportActionBar(toolbarContent);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        Bundle args = getIntent().getExtras();
        if(args != null) {
            topic = Parcels.unwrap(args.getParcelable(TOPIC));
            getSupportActionBar().setTitle(topic.getName());
        }
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
}
