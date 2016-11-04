package s3.lamphan.nghiencuulichsu.mvp.presenters;

import android.content.Context;

import java.util.List;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by lam.phan on 11/4/2016.
 */
public class ParseContentPresenter extends BasePresenter{
    private CompositeSubscription compositeSubscription;

    public ParseContentPresenter(Context context) {
        super(context);

        compositeSubscription = new CompositeSubscription();
    }

    public String parseContentOfUrl(String urlContent, List<String> tagContent)
    {
        
    }
}
