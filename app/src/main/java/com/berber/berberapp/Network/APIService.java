package com.berber.berberapp.Network;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by gabotrugomez on 11/29/17.
 */

public interface APIService {
    @FormUrlEncoded
    @POST("user/email")
    Call<ResponseBody> sendOrderEmail(@Field("email") String email, @Field("cart") String cart, @Field("vendor_email") String vendorEmail);

    @FormUrlEncoded
    @POST("user/contact/email")
    Call<ResponseBody> sendContactEmail(@Field("email") String email, @Field("name") String name, @Field("message") String message);
}
