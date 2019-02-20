/*
 * Copyright (c) CFCA 2016.
 */

package recstation.lkk.com.recstation.util;


import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    Observable<String> login(@Url String url, @Field("USERNAME") String userName, @Field("PASSWORD") String password);

    @FormUrlEncoded
    @POST
    Observable<String> getdingdanlist(@Url String url, @Field("currentPage") String currentPage, @Field("USERNAME") String userName);
    @FormUrlEncoded
    @POST
    Observable<String> jieshoudindan(@Url String url,  @Field("RETRIEVEORDER_ID") String RETRIEVEORDER_ID,  @Field("STATUS") String STATUS,  @Field("SUSERNAME") String SUSERNAME,  @Field("APPRAISE") String APPRAISE);
    @FormUrlEncoded
    @POST
    Observable<String> chexiaodindan(@Url String url,  @Field("RETRIEVEORDER_ID") String RETRIEVEORDER_ID,  @Field("STATUS") String STATUS);

    @FormUrlEncoded
    @POST
    Observable<String> getyzm(@Url String url, @Field("phoneNumbers") String phoneNumbers, @Field("templateCode") String templateCode);

    @FormUrlEncoded
    @POST
    Observable<String> register(@Url String url, @Field("USERNAME") String USERNAME, @Field("PASSWORD") String PASSWORD, @Field("APPSMS_ID") String APPSMS_ID, @Field("APPSMS_CODE") String APPSMS_CODE);

    @FormUrlEncoded
    @POST
    Observable<String> setpwd(@Url String url, @Field("USERNAME") String USERNAME, @Field("PASSWORD") String PASSWORD, @Field("APPSMS_ID") String APPSMS_ID, @Field("APPSMS_CODE") String APPSMS_CODE);

    @FormUrlEncoded
    @POST
    Observable<String> checksms(@Url String url, @Field("USERNAME") String USERNAME, @Field("APPSMS_ID") String APPSMS_ID, @Field("APPSMS_CODE") String APPSMS_CODE);

    @FormUrlEncoded
    @POST
    Observable<String> yuyuehuishou(@Url String url, @Field("USERNAME") String USERNAME, @Field("RETRIEVETYPE_ID") String RETRIEVETYPE_ID, @Field("PROVINCE") String PROVINCE, @Field("CITY") String CITY, @Field("AREA") String AREA, @Field("ADDRESS") String ADDRESS, @Field("MOBILE") String MOBILE, @Field("YDATE") String YDATE, @Field("YTIME") String YTIME);

    @GET
    Observable<String> index(@Url String url);

    @FormUrlEncoded
    @POST
    Observable<String> recperson(@Url String url, @Field("currentPage") String currentPage);

    @FormUrlEncoded
    @POST
    Observable<String> querydingdan(@Url String url, @Field("currentPage") String currentPage, @Field("USERNAME") String USERNAME, @Field("STATUS") String STATUS, @Field("SUSERNAME") String SUSERNAME, @Field("PROVINCE") String PROVINCE, @Field("CITY") String CITY, @Field("AREA") String AREA);

    @FormUrlEncoded
    @POST
    Observable<String> findAdress(@Url String url, @Field("USERNAME") String USERNAME);


    @FormUrlEncoded
    @POST
    Observable<String> addAdress(@Url String url, @Field("DEFT") String DEFT, @Field("USERNAME") String USERNAME, @Field("NAME") String NAME, @Field("MOBILE") String MOBILE, @Field("PROVINCE") String PROVINCE, @Field("CITY") String CITY, @Field("AREA") String AREA, @Field("ADDRESS") String ADDRESS);

    @FormUrlEncoded
    @POST
    Observable<String> editAdress(@Url String url, @Field("DEFT") String DEFT, @Field("USERADDRESS_ID") String USERADDRESS_ID, @Field("USERNAME") String USERNAME, @Field("NAME") String NAME, @Field("MOBILE") String MOBILE, @Field("PROVINCE") String PROVINCE, @Field("CITY") String CITY, @Field("AREA") String AREA, @Field("ADDRESS") String ADDRESS);

    @FormUrlEncoded
    @POST
    Observable<String> delAdress(@Url String url, @Field("USERADDRESS_ID") String USERADDRESS_ID);

    @Multipart
    @POST
    Observable<String> BusinessCertificate(@Url String url, @Part List<MultipartBody.Part> partList, @Part("USERNAME") RequestBody USERNAME, @Part("MOBILE") RequestBody MOBILE, @Part("LEGALNAME") RequestBody LEGALNAME, @Part("REGISTERADDRESS") RequestBody REGISTERADDRESS, @Part("PUBLICACCOUNT") RequestBody PUBLICACCOUNT, @Part("PROVINCE") RequestBody PROVINCE, @Part("CITY") RequestBody CITY, @Part("AREA") RequestBody AREA, @Part("LON") RequestBody LON, @Part("LAT") RequestBody LAT);
    @Multipart
    @POST
    Observable<String> editPic(@Url String url, @Part List<MultipartBody.Part> partList, @Part("USERNAME") RequestBody USERNAME);
    @FormUrlEncoded
    @POST
    Observable<String> sendPost(@Url String url,@FieldMap Map<String, String> paramsMap );



    @GET
    Observable<String> sign1(@Url String url, @Query("RsaCode") String originText, @HeaderMap Map<String, String> headers);


    @FormUrlEncoded
    @POST
    Observable<String> update(@Url String url, @Field("RsaCode") String versinfoType, @HeaderMap Map<String, String> headers);

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
