package recstation.lkk.com.recstation.util;

import android.content.Context;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import recstation.lkk.com.recstation.application.DemoApplication;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by hubk on 2018/2/26.
 */

public class HKEapiManager {

    public DemoApi demoApi;
    public Retrofit retrofit;
    public SharedPreferencesUtils preferences;

    private HKEapiManager(Context context) {
        OkHttpClient client;
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        client = new okhttp3.OkHttpClient.Builder()
                .addInterceptor(logging)
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();

        this.retrofit = new Retrofit.Builder().baseUrl(URLConfig.HEAD_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        this.demoApi = retrofit.create(DemoApi.class);

        this.preferences = SharedPreferencesUtils.getInstence();
    }

    private static HKEapiManager hkEapiManager;

    public static HKEapiManager getInstances() {
        if (hkEapiManager == null) {
            hkEapiManager = new HKEapiManager(DemoApplication.getInstance().getApplicationContext());
        }
        return hkEapiManager;
    }


}
