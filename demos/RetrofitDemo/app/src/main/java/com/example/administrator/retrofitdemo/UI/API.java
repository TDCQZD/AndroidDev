package com.example.administrator.retrofitdemo.UI;

import com.example.administrator.retrofitdemo.Bean.Blog;
import com.example.administrator.retrofitdemo.Bean.Contributor;
import com.example.administrator.retrofitdemo.Bean.Result;
import com.example.administrator.retrofitdemo.Bean.User;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * @Description:Retrofit网络请求的接口
 * @author: Administrator
 * @Date: 2017/9/13 9:22
 * 拼接后的Url:https://api.github.com/
 */

public interface API {
    /**********************************************************************************************
     注解的学习使用
     第一类：HTTP请求方法
     @GET
     @POST
     @PUT
     @DELETE
     @PATCH
     @HEAD
     @OPTIONS
     @HTTP
     第二类：标记类
     @FormUrlEncoded
     @Multipart
     @Streaming
     第三类：参数类
     @Header
     @Headers
     @Body
     @Field
     @FieldMap
     @Part
     @PartMap
     @Path
     @Query
     @QueryMap
     @Url
     **********************************************************************************************/
    /*
    无参请求
    拼接后的Url:https://api.github.com/users/emails
     */
    @GET("users/emails")
    Call<ResponseBody> getUsers();

    /*
    1个参数请求
     */
    //https://api.github.com/users?id=1
    @GET("users")
    Call<ResponseBody> getQueryUserId(@Query("id") String id);

    //拼接后的Url:https://api.github.com/users/1
    @GET("users/{id}")
    Call<ResponseBody> getPathUserId(@Path("id") String id);

    /*
    两个参数请求
   https://api.github.com/users/mojombo/followers
     */
    @GET("users/{login}/{followers_url}")
    Call<ResponseBody> getUserparamter(@Path("login") String login, @Path("followers_url") String followers_url);

    /*
    多个拼接参数的GET
    https://api.github.com/search/repositories?q=retrofit&since=2016-03-29&page=1&per_page=3
     */
    @GET("search/repositories")
    Call<ResponseBody> queryRetrofitByGetCall(@Query("q") String owner,
                                              @Query("since") String time,
                                              @Query("page") int page,
                                              @Query("per_page") int per_Page);

    @GET("search/repositories")
    Call<ResponseBody> queryRetrofitByGetCallMap(@QueryMap Map<String, String> map);

    /*
    POST请求
     */
    /*
    上传表单文件 FormUrlEncoded
  注：  @Body 以 Post方式 传递 自定义数据类型 给服务器 传递非表单数据 不能使用 @FormUrlEncoded
     */

    @POST("user/edit")
    Call<User> addUser (@Body User user);

    @FormUrlEncoded
    @POST("user/edit")
    Call<User> addUser (@Field("name") String name,@Field("company") String company);



    @FormUrlEncoded
    @POST("list")
    Call<ResponseBody> postName (@Field("name") String... names);

    /*
    上传File文件 Multipart
    @Part支持三种类型：RequestBody okhttp3.MultipartBody.Part 、任意类型。
    除  okhttp3.MultipartBody.Part 以外，
    其它类型都必须带上表单字段({ okhttp3.MultipartBody.Part} 中已经包含了表单字段的信息）。
    @PartMap支持一个Map作为参数，支持 { RequestBody } 类型， 如果有其它的类型，会被{@link retrofit2.Converter}转换。所以文件只能用 @Part MultipartBody.Part
     */

    @Multipart//官方PartMap用法
    @POST("upload")
    Call<ResponseBody> upload(@Part("file") RequestBody file,@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("upload")
    Call<ResponseBody> fileUpload(@Part("name") RequestBody name, @Part("age") RequestBody age, @Part MultipartBody.Part file);


    @Multipart
    @POST("upload")
    Call<ResponseBody> fileUploadMap(@PartMap Map<String, RequestBody> args, @Part MultipartBody.Part file);

    @Multipart
    @POST("upload")
    Call<ResponseBody> fileUploadMap2(@PartMap Map<String, RequestBody> args);

    /*
    HTTP请求的使用
     */
    @HTTP(method = "GET", path = "users/{id}", hasBody = false)
    Call<ResponseBody> httpRequest(@Path("id") String id);

    @FormUrlEncoded
    @HTTP(method = "POST", path = "user/edit", hasBody = true)
    Call<User> http_postRequest(@Field("name") String name,@Field("company") String company);

    /*
    添加请求头
     */
    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: RetrofitBean-Sample-App",
            "name:ljd"
    })
    @GET("repos/{owner}/{repo}/contributors")
    Call<List<Contributor>> contributorsAndAddHeader(@Path("owner") String owner,@Path("repo") String repo);

    /**
     *Retrofit高级使用
     * GSON RxJava 自定义Converter 自定义CallAdapter
     * 注：gson只是解析数据的一种，还可以使用fastjson
     */

    @POST("user")
    Call<Result<Blog>> createBlog(@Body Blog blog);

    @GET("user/{id}")
    Call<String> getBlog(@Path("id") int id);

    @GET("repos/{owner}/{repo}/contributors")
    Observable<List<Contributor>> contributorsByRxJava(@Path("owner") String owner, @Path("repo") String repo);




    /*
    中盾示例测试，传递多个参数
     */
    @Multipart
    @POST("MIAServer/CertAuth")
    Call<ResponseBody> certAuth (@PartMap Map<String, RequestBody> args);

    @Multipart
    @POST("MIAServer/AuthUser")
    Call<ResponseBody> add (@PartMap Map<String, RequestBody> args);
    @Multipart
    @POST("MIAServer/servlet/AuthUserService")
    Call<ResponseBody> update (@PartMap Map<String, RequestBody> args);


}
