package s3.lamphan.nghiencuulichsu.domain.repository;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.subscriptions.CompositeSubscription;
import s3.lamphan.nghiencuulichsu.domain.repository.gateways.IGateWay;

/**
 * Created by lam.phan on 10/31/2016.
 */
public class BaseRepository {
    protected IGateWay baseGateWay;
    protected Retrofit retrofit;
    protected CompositeSubscription compositeSubscription;

    public BaseRepository(IGateWay baseGateWay) {
        this.baseGateWay = baseGateWay;
        init();
    }

    private void init()
    {
        try {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    try {
                        Request request = chain.request().newBuilder()
                                .addHeader("Accept","application/json")
                                .addHeader("Content-Type","application/x-www-form-urlencoded; charset=utf-8")
                                .addHeader("application-id", baseGateWay.getApplicationId())
                                .addHeader("secret-key", baseGateWay.getSecretKey())
                                .build();
                        return chain.proceed(request);
                    } catch (SocketTimeoutException e){
                        e.printStackTrace();
                    }
                    return null;
                }
            }).addInterceptor(logging).build();
            Gson parseNullableGson = new GsonBuilder().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseGateWay.getEndpoint())
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(parseNullableGson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            compositeSubscription = new CompositeSubscription();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void releaseCompositeSubscription()
    {
        this.compositeSubscription.unsubscribe();
    }

}
