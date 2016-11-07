package s3.lamphan.nghiencuulichsu.mvp.presenters;

import android.content.Context;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import s3.lamphan.nghiencuulichsu.domain.repository.IBaseCallback;

/**
 * Created by lam.phan on 11/4/2016.
 */
public class ParseContentPresenter extends BasePresenter{
    private static final String CONTENT_TAG_SPLIT = ",";

    public ParseContentPresenter(Context context) {
        super(context);

        compositeSubscription = new CompositeSubscription();
    }

    public void parseContentOfUrl(final String urlContent, final List<String> tagContents,
                                  final IBaseCallback<String> callback)
    {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                StringBuffer result = new StringBuffer();
                try {
                    Document doc = Jsoup.connect(urlContent).get();
                    for (String tag : tagContents) {
                        Log.d("Test", "parse tag : " + tag);
                        Elements contents = doc.select(tag);
                        Log.d("Test", "tag content : " + contents.outerHtml());
                        result.append(contents.outerHtml());
                    }
                } catch (Exception ex) {

                }

                subscriber.onNext(result.toString());
                subscriber.onCompleted();
            }
        }).observeOn(AndroidSchedulers.mainThread())
          .subscribeOn(Schedulers.newThread())
          .subscribe(new Subscriber<String>() {
              @Override
              public void onCompleted() {

              }

              @Override
              public void onError(Throwable e) {

              }

              @Override
              public void onNext(String result) {
                  Log.d("Test", "content parse : " + result);
                    if(callback != null)
                    {
                        callback.success(Arrays.asList(result));
                    }
              }
          });

        compositeSubscription.add(subscription);
    }

    public List<String> parseContentTags(String contentTags)
    {
        Log.d("Test", "contentTags : " + contentTags);
        String[] test = contentTags.split(CONTENT_TAG_SPLIT);
        Log.d("Test", "contentTags at 0 : " + test[0]);
        return Arrays.asList(contentTags.split(CONTENT_TAG_SPLIT));
    }
}
