//package com.example.myapplication.Services;
//
//import com.example.myapplication.Models.LoginRequest;
//import com.example.myapplication.Models.User;
//import com.example.myapplication.Models.UserRespond;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//import retrofit2.Call;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//import retrofit2.http.Body;
//import retrofit2.http.Field;
//import retrofit2.http.FormUrlEncoded;
//import retrofit2.http.GET;
//import retrofit2.http.Header;
//import retrofit2.http.POST;
//
//public interface UserAPI {
//
//    Gson gson = new GsonBuilder()
//            .setDateFormat("yyyy-MM-dd HH:mm:ss")
//            .create();
//
//    UserAPI userAPI = new Retrofit.Builder()
//                    .baseUrl("http://169.254.76.104:3000/")
//                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .build().create(UserAPI.class);
//
////    @FormUrlEncoded
////    @POST("/api/users/login")
////    Call<User> login(@Field("username") String username, @Field("password") String password);
//    @POST("/api/users/login")
//    Call<UserRespond> login(@Body LoginRequest loginRequest);
//
//    @GET("/api/users/current")
//    Call<User> current(@Header("Authorization") String token);
//
//    @FormUrlEncoded
//    @POST("/api/users/register")
//    Call<UserRespond> signUp(@Field("username") String username, @Field("email") String email, @Field("password")String password);
//}
//


package com.example.myapplication.Services;

import com.example.myapplication.Models.LoginRequest;
import com.example.myapplication.Models.User;
import com.example.myapplication.Models.UserRespond;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

import java.io.IOException;

public interface UserAPI {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    // Interceptor để bỏ qua cache
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder()
                            .addHeader("Cache-Control", "no-cache")
                            .addHeader("Cache-Control", "no-store")
                            .build();
                    return chain.proceed(request);
                }
            })
            .build();

    UserAPI userAPI = new Retrofit.Builder()
            .baseUrl("http://169.254.76.104:3000/")
            .client(okHttpClient)  // sử dụng OkHttpClient với Interceptor
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(UserAPI.class);

    @POST("/api/users/login")
    Call<UserRespond> login(@Body LoginRequest loginRequest);

    @GET("/api/users/current")
    Call<User> current(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("/api/users/register")
    Call<UserRespond> signUp(@Field("username") String username, @Field("email") String email, @Field("password") String password);
}
