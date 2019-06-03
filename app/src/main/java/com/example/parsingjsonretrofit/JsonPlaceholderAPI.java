package com.example.parsingjsonretrofit;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface JsonPlaceholderAPI {

    /*generic
    @GET("posts")
    Call<List<Posts>> getPosts();*/

    //with Query
    @GET("posts")
    Call<List<Posts>> getPosts(
            @Query("userId") Integer[] userID,
            @Query("_sort") String sort,
            @Query("order") String order
    );

    //with QueryMap
    @GET("posts")
    Call<List<Posts>> getPosts(@QueryMap Map<String, String> parameters);

    //with Path
    @GET("posts/{id}/comments")
    Call<List<Comments>> getComments(@Path("id") int postId);

    //with Url
    @GET
    Call<List<Comments>> getComments(@Url String url);

}
