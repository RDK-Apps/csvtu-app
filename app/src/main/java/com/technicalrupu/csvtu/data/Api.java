package com.technicalrupu.csvtu.data;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {
    @GET("getCourses.php")
    Call<Course> getCourses();

     @FormUrlEncoded
    @POST("getSubjects.php")
    Call<ArrayList<Subject>> getSubjects(
            @Field("cname") String cid,@Field("semoryear") String semoryear
    );
    @FormUrlEncoded
    @POST("getNotes.php")
    Call<Notes> getNotes(
            @Field("sname") String sname,
            @Field("cname") String cname,
            @Field("semoryear") String semoryear
    );

    @FormUrlEncoded
    @POST("getVideos.php")
    Call<Notes> getVideos(
            @Field("sname") String sname,
            @Field("cname") String cname,
            @Field("semoryear") String semoryear
    );

    @FormUrlEncoded
    @POST("getPYQ.php")
    Call<Notes> getPYQ(
            @Field("sname") String sname,
            @Field("cname") String cname,
            @Field("semoryear") String semoryear
    );
    @FormUrlEncoded
    @POST("getSyllabus.php")
    Call<ArrayList<Syllabus>> getSyllabus(
            @Field("cname") String cname,
            @Field("semoryear") String semoryear
    );
    @FormUrlEncoded
    @POST("getResults.php")
    Call<ArrayList<Result>> getResults(
            @Field("cname") String cid,@Field("semoryear") String semoryear
    );
    @FormUrlEncoded
    @POST("getSlider.php")
    Call<ArrayList<Slider>> getSlider(
            @Field("cname") String cname,
            @Field("semoryear") String semoryear
    );
}
