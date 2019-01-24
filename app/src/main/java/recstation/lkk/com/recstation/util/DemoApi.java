/*
 * Copyright (c) CFCA 2016.
 */

package recstation.lkk.com.recstation.util;


import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by wufan on 16/3/25.
 * 与BankDemo进行交互请求类，使用 retrofit 进行网络请求
 */
public interface DemoApi {

    @FormUrlEncoded
    @POST
    Observable<String> login(@Url String url, @Field("USERNAME") String userName,@Field("PASSWORD") String password);

    @FormUrlEncoded
    @POST
    Observable<String> getyzm(@Url String url, @Field("phoneNumbers") String phoneNumbers,@Field("templateCode") String templateCode);

     @FormUrlEncoded
    @POST
    Observable<String> register(@Url String url, @Field("USERNAME") String USERNAME,@Field("PASSWORD") String PASSWORD,@Field("APPSMS_ID") String APPSMS_ID,@Field("APPSMS_CODE") String APPSMS_CODE);

    @FormUrlEncoded
    @POST
    Observable<String> setpwd(@Url String url, @Field("USERNAME") String USERNAME,@Field("PASSWORD") String PASSWORD,@Field("APPSMS_ID") String APPSMS_ID,@Field("APPSMS_CODE") String APPSMS_CODE);

    @FormUrlEncoded
    @POST
    Observable<String> checksms(@Url String url, @Field("USERNAME") String USERNAME,@Field("APPSMS_ID") String APPSMS_ID,@Field("APPSMS_CODE") String APPSMS_CODE);

    @GET
    Observable<String> index(@Url String url);



    @GET
    Observable<String> sign1(@Url String url, @Query("RsaCode") String originText, @HeaderMap Map<String,String> headers);


    @FormUrlEncoded
    @POST

    Observable<String> update(@Url String url, @Field("RsaCode") String versinfoType,@HeaderMap Map<String,String> headers);
    @FormUrlEncoded
    @POST
    Observable<ResponseBody> updateAdvertisement(@Url String url, @Field("RsaCode") String Type);
    @GET
    Observable<ResponseBody> downloadAdvertisement(@Url String url);

//    @FormUrlEncoded
//    @POST
//    Observable<String> requesCornerMark(@Url String url, @Field("RsaCode") String serial);
//    @FormUrlEncoded
//    @POST
//    Observable<String> updateResIDSecond2(@Url String url, @Field("RsaCode") String rsaCode);
//
//    @FormUrlEncoded
//    @POST
//    Observable<String> updateResIDFirst(@Url String url, @Field("serial") String serial, @Field("pushId") String pushId,  @Field("deviceType") String deviceType);

    @FormUrlEncoded
    @POST
    Observable<String> updateResID(@Url String url, @Field("RsaCode") String rsaCode);
    @POST
    Observable<String> delMsgContent(@Url String url);
}
