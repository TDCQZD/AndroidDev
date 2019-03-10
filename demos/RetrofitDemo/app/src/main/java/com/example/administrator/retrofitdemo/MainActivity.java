package com.example.administrator.retrofitdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.retrofitdemo.Bean.Blog;
import com.example.administrator.retrofitdemo.Bean.Contributor;
import com.example.administrator.retrofitdemo.Bean.Result;
import com.example.administrator.retrofitdemo.Bean.User;
import com.example.administrator.retrofitdemo.UI.API;
import com.example.administrator.retrofitdemo.view.StringConverterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Retrofit mRetrofit, retrofit, tretrofit, gsonRetrofit, mRetrofitlogging;
    private API mAPI, api, tapi, gsonAPI, mAPIlogging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Gson gson = new GsonBuilder()
                //配置Gson
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();
        gsonRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                //转换器，可以接收自定义的Gson，当然也可以不传
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mAPI = mRetrofit.create(API.class);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://123.160.220.46:12305/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(API.class);
        tretrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.3.177/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        tapi = tretrofit.create(API.class);
        gsonAPI = gsonRetrofit.create(API.class);

        /*
        logging日志
         */

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        mRetrofitlogging = new Retrofit.Builder()
//                .addCallAdapterFactory(CustomCallAdapterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mAPIlogging = mRetrofitlogging.create(API.class);
    }

    /**
     * 添加请求头
     *
     * @param v
     */
    public void Headers(View v) {
        Call<List<Contributor>> call = mAPIlogging.contributorsAndAddHeader("square", "retrofit");
        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                List<Contributor> contributorList = response.body();
                for (Contributor contributor : contributorList) {
                    Log.d("TAG", "login:" + contributor.getLogin() + "  contributions:" + contributor.getContributions());
                }
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {

            }
        });
    }

    /*
    自定义OkhttpClient输出日志
     */
    public void logging(View view) {

        Call<ResponseBody> mCall = mAPIlogging.getUsers();
        mCall.enqueue(new Callback<ResponseBody>() {//异步请求
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String result = null;
                    try {
                        result = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.i("TAG", "logging:" + result);
                    Toast.makeText(MainActivity.this, "logging:" + result, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void customCallAdapter(View view) {
        Toast.makeText(MainActivity.this, "自定义CallAdapter", Toast.LENGTH_SHORT).show();

    }

    public void RxJava(View view) {
//        Toast.makeText(MainActivity.this, "RxJava", Toast.LENGTH_SHORT).show();

        mAPIlogging.contributorsByRxJava("square", "retrofit")
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Contributor>>() {
                    @Override
                    public void accept(List<Contributor> contributors) throws Exception {
                        for (Contributor contributor : contributors) {
                            Log.d("TAG", "login:" + contributor.getLogin() + "  contributions:" + contributor.getContributions());
                        }
                    }
                });

    }

    public void customConverter(View view) {
//        Toast.makeText(MainActivity.this, "自定义Converter", Toast.LENGTH_SHORT).show();
        /*
        addConverterFactory是有先后顺序的，如果有多个ConverterFactory都支持同一种类型，那么就是只有第一个才会被使用.
         */
        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://localhost:4567/")
                .baseUrl("https://api.github.com/")
                // 如是有Gson这类的Converter 一定要放在其它前面
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API mAPI = retrofit.create(API.class);

        Call<String> call = mAPI.getBlog(2);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("TAG", "自定义Converter-->" + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    public void getGsonData(View view) {
        Blog blog = new Blog();
        blog.content = "新建的Blog";
        blog.title = "测试";
        blog.author = "怪盗kidou";
        Call<Result<Blog>> mCall = gsonAPI.createBlog(blog);

        mCall.enqueue(new Callback<Result<Blog>>() {
            @Override
            public void onResponse(Call<Result<Blog>> call, Response<Result<Blog>> response) {
                // 已经转换为想要的类型了
                Result<Blog> result = response.body();
                Log.i("TAG", "Retrofit Converter 序列化-->" + result);
            }

            @Override
            public void onFailure(Call<Result<Blog>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    /*
    简单GET请求-无参
     */
    public void getRequest(View view) {
        Call<ResponseBody> mCall = mAPI.getUsers();
        mCall.enqueue(new Callback<ResponseBody>() {//异步请求
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String result = null;
                    try {
                        result = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.i("TAG", "简单GET:" + result);
                    Toast.makeText(MainActivity.this, "GET:" + result, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });


    }

    /*
    1个参数的Get请求
    注意区别Query和Path的不同
     */
    public void getParmameter_1_Request(View v) {
        Call<ResponseBody> mCall = mAPI.getQueryUserId("1");
        Call<ResponseBody> call = mAPI.getPathUserId("1");
        mCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String result = null;
                    try {
                        result = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.i("TAG", "1个参数的GET_Query_成功:" + result);
                    Toast.makeText(MainActivity.this, "GET:" + result, Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        String error = response.errorBody().string();
                        Log.i("TAG", "1个参数的GET_Query_失败:" + error);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(MainActivity.this, "请求出错", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String result = null;
                    try {
                        result = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.i("TAG", "1个参数的GET_Path_成功:" + result);
                    Toast.makeText(MainActivity.this, "GET:" + result, Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        String error = response.errorBody().string();
                        Log.i("TAG", "1个参数的GET_Path_失败:" + error);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(MainActivity.this, "请求出错", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /*
    2个参数的GET_Path请求
     */
    public void getParmameter_2_Request(View v) {
        Call<ResponseBody> mCall = mAPI.getUserparamter("mojombo", "followers");
        mCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String result = null;
                    try {
                        result = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.i("TAG", "2个参数的GET_Path_成功:" + result);
                    Toast.makeText(MainActivity.this, "GET:" + result, Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        String error = response.errorBody().string();
                        Log.i("TAG", "2个参数的GET_Path_失败:" + error);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(MainActivity.this, "请求出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });


    }

    /*
    多个参数的GET_query请求
    注：多个Path可以参照上面两个参数的Path
     */
    public void getQueryParmameter(View v) {

        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("q", "retrofit");
        queryMap.put("since", "2016-03-29");
        queryMap.put("page", "1");
        queryMap.put("per_page", "3");
        Call<ResponseBody> mCall = mAPI.queryRetrofitByGetCallMap(queryMap);
        mCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String result = null;
                    try {
                        result = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.i("TAG", "多个参数的GET_query_map_成功:" + result);
                    Toast.makeText(MainActivity.this, "GET:" + result, Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        String error = response.errorBody().string();
                        Log.i("TAG", "多个参数的GET_query_map_失败:" + error);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(MainActivity.this, "请求出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
        ;
        Call<ResponseBody> call = mAPI.queryRetrofitByGetCall("retrofit", "2016-03-29", 1, 3);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String result = null;
                    try {
                        result = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.i("TAG", "多个参数的GET_query_成功:" + result);
                    Toast.makeText(MainActivity.this, "GET:" + result, Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        String error = response.errorBody().string();
                        Log.i("TAG", "多个参数的GET_query_map_失败:" + error);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(MainActivity.this, "请求出错", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void http(View v) {
        Call<ResponseBody> mCall = mAPI.httpRequest("1");
        mCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String result = null;
                    try {
                        result = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.i("TAG", "HTTP_GET请求_成功:" + result);
                    Toast.makeText(MainActivity.this, "HTTP_GET:" + result, Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        String error = response.errorBody().string();
                        Log.i("TAG", "HTTP_GET请求_失败:" + error);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(MainActivity.this, "请求出错", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
        new Thread() {
            public void run() {
//                Looper.prepare();
                Call<User> call = mAPI.http_postRequest("EDIT GmbH", "EDIT GmbH");
                try {
                    Response<User> mUserResponse = call.execute();
                    if (mUserResponse.isSuccessful()) {
                        String result = mUserResponse.body().toString();
                        Log.i("TAG", "HTTP_POST请求-成功" + result);
                    } else {
                        String result = mUserResponse.errorBody().string();
                        Log.i("TAG", "HTTP_POST请求_失败:" + result);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void postFieldParmameter(View v) {

        new Thread() {
            public void run() {
//                Looper.prepare();
                User user = new User();
                user.setName("EDIT GmbH");
                user.setCompany("EDIT GmbH");
                Call<User> call = mAPI.addUser(user);
//                Call<User> call = mAPI.addUser("EDIT GmbH", "EDIT GmbH");

                try {
                    Response<User> mUserResponse = call.execute();
                    if (mUserResponse.isSuccessful()) {
                        String result = mUserResponse.body().toString();
                        Log.i("TAG", "POST请求结果_成功" + result);
                    } else {
                        String result = mUserResponse.errorBody().string();
                        Log.i("TAG", "POST请求结果_失败:" + result);
//                        Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Call<ResponseBody> mCall1 = mAPI.postName("Bob Smith", "Jane Doe");
                try {
                    Response<ResponseBody> bodyResponse = mCall1.execute();
                    if (bodyResponse.isSuccessful()) {
                        String result = bodyResponse.body().toString();
                        Log.i("TAG", "POST_Body请求结果_成功" + result);
                    } else {
                        String result = bodyResponse.errorBody().string();
                        Log.i("TAG", "POST_Body请求结果_失败:" + result);
//                        Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();

    }

    public void postFile(View view) throws IOException {
        new Thread() {
            public void run() {
                try {
                    network();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private void network() throws IOException {
        MediaType textType = MediaType.parse("text/plain");
        RequestBody name = RequestBody.create(textType, "Carson");
        RequestBody age = RequestBody.create(textType, "24");
        RequestBody file = RequestBody.create(MediaType.parse("application/octet-stream"), "这里是模拟文件的内容");
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "test.txt", file);

        Map<String, RequestBody> params = new HashMap<>();
        params.put("name", name);
        params.put("age", age);

        Call<ResponseBody> call = mAPI.upload(file, params);
        Response<ResponseBody> mResponseBody = call.execute();

        Call<ResponseBody> call1 = mAPI.fileUpload(name, age, filePart);
        Response<ResponseBody> mResponseBody1 = call1.execute();

        Call<ResponseBody> call2 = mAPI.fileUploadMap(params, filePart);
        Response<ResponseBody> mResponseBody2 = call2.execute();

        Call<ResponseBody> call3 = mAPI.fileUploadMap2(params);
        Response<ResponseBody> mResponseBody3 = call3.execute();

        if (mResponseBody.isSuccessful()) {

            String result = mResponseBody.body().toString();
            Log.i("TAG", "POST_File请求结果_成功" + result);
        } else {
            String result = mResponseBody.errorBody().string();
            Log.i("TAG", "POST_File请求结果_失败:" + result);

        }
        if (mResponseBody1.isSuccessful()) {

            String result = mResponseBody1.body().toString();
            Log.i("TAG", "POST_File请求结果_成功" + result);
        } else {
            String result = mResponseBody1.errorBody().string();
            Log.i("TAG", "POST_File请求结果_失败:" + result);

        }
        if (mResponseBody2.isSuccessful()) {

            String result = mResponseBody2.body().toString();
            Log.i("TAG", "POST_File2请求结果_成功" + result);
        } else {
            String result = mResponseBody2.errorBody().string();
            Log.i("TAG", "POST_File2请求结果_失败:" + result);

        }
        if (mResponseBody3.isSuccessful()) {

            String result = mResponseBody3.body().toString();
            Log.i("TAG", "POST_File3请求结果_成功" + result);
        } else {
            String result = mResponseBody3.errorBody().string();
            Log.i("TAG", "POST_File3请求结果_失败:" + result);

        }
    }


    /**
     * 中盾测试接口数据
     *
     * @param view
     * @throws IOException
     */
    public void certAuth(View view) throws IOException {

        new Thread() {
            public void run() {
                try {
                    net();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private void net() throws IOException {
        MediaType textType = MediaType.parse("text/plain");
        String signdata1 = "MIIEjQYJKoZIhvcNAQcCoIIEfjCCBHoCAQExCzAJBgUrDgMCGgUAMCMGCSqGSIb3DQEHAaAWBBQxLjc0ODcwOTAxMjk1MDAzMzhFOKCCA0AwggM8MIICpaADAgECAhBgNrLIkVAtlwgYwmtNsB8FMA0GCSqGSIb3DQEBBQUAMEUxCzAJBgNVBAYTAkNOMQswCQYDVQQIEwJITjELMAkGA1UEBxMCWloxDTALBgNVBAoTBEhOQ0ExDTALBgNVBAMTBEhOQ0EwHhcNMTMxMTA0MDc1OTE4WhcNMTgxMTAzMDc1OTE4WjBNMQswCQYDVQQGEwJDTjEqMCgGA1UECgwh5rKz5Y2X55yB5pWw5a2X6K+B5Lmm6K6k6K+B5Lit5b+DMRIwEAYDVQQDDAnmooHli4fliKkwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAL7YkMPD7gUizUEvsIzMHnA2COjLn50+bb/XFTI9m+OSV/pV0cv3rKRxZEi9f8Qrcg5MeVQwHjoTtoxezaZTqnwHZhJVT+TRmQukHft0L4adPBpmHEWnCNn0toj9a0oJC2jVO0KFgs5CaZxjRU/qdjM9izyqbPqQshUd4tft9YU7AgMBAAGjggEjMIIBHzAfBgNVHSMEGDAWgBR0AuzIGvvkQefc7sN45BG+FU1zIzAMBgNVHRMEBTADAQEAMIHBBgNVHR8EgbkwgbYwOaA3oDWkMzAxMQswCQYDVQQGEwJDTjEQMA4GA1UECwwHQUREMUNSTDEQMA4GA1UEAwwHY3JsMjI0NTB5oHegdYZzbGRhcDovLzIxOC4yOC4xNi4xMDE6Mzg5L0NOPWNybDIyNDUsT1U9QUREMUNSTCxDPUNOP2NlcnRpZmljYXRlUmV2b2NhdGlvbkxpc3Q/YmFzZT9vYmplY3RjbGFzcz1jUkxEaXN0cmlidXRpb25Qb2ludDALBgNVHQ8EBAMCBsAwHQYDVR0OBBYEFLqIuOhFrzCrHMIrzFzRzwDpjbxaMA0GCSqGSIb3DQEBBQUAA4GBAEOjtDay5ST2BEyn9wjFA4bAqZBr8/JpLb3ukOGsu7MQhqOmOwBjLnChUj0QOWevo14L+Ea/EwQ57fyRKXkGL4wHKyiwGe60ntKL9F0Hjc4JiNoZocY6DDmF4d0e0TGj1NHf6aqe6J6GF65/zJ+kEHPpe12MFr1TDwUh2ZYbCv++MYH+MIH7AgEBMFkwRTELMAkGA1UEBhMCQ04xCzAJBgNVBAgTAkhOMQswCQYDVQQHEwJaWjENMAsGA1UEChMESE5DQTENMAsGA1UEAxMESE5DQQIQYDayyJFQLZcIGMJrTbAfBTAJBgUrDgMCGgUAMA0GCSqGSIb3DQEBAQUABIGArzb65GxkvBEyf3GFmCO4fNCV+ln8vRYcU82rMesLaV9jbTLyKYtrRTv8MT0NjEO6vEnIx6d20BWsst1IxctfpMdS2qNNYZVtCG0vJf2LHS0BcQT7VJ2AWhuW70ehROiWingS7gcblihaHYX1aLeURhqXxknIkjZKDVabB0tCX/g=";
        String signcert1 = "MIIDPDCCAqWgAwIBAgIQYDayyJFQLZcIGMJrTbAfBTANBgkqhkiG9w0BAQUFADBFMQswCQYDVQQGEwJDTjELMAkGA1UECBMCSE4xCzAJBgNVBAcTAlpaMQ0wCwYDVQQKEwRITkNBMQ0wCwYDVQQDEwRITkNBMB4XDTEzMTEwNDA3NTkxOFoXDTE4MTEwMzA3NTkxOFowTTELMAkGA1UEBhMCQ04xKjAoBgNVBAoMIeays+WNl+ecgeaVsOWtl+ivgeS5puiupOivgeS4reW/gzESMBAGA1UEAwwJ5qKB5YuH5YipMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+2JDDw+4FIs1BL7CMzB5wNgjoy5+dPm2/1xUyPZvjklf6VdHL96ykcWRIvX/EK3IOTHlUMB46E7aMXs2mU6p8B2YSVU/k0ZkLpB37dC+GnTwaZhxFpwjZ9LaI/WtKCQto1TtChYLOQmmcY0VP6nYzPYs8qmz6kLIVHeLX7fWFOwIDAQABo4IBIzCCAR8wHwYDVR0jBBgwFoAUdALsyBr75EHn3O7DeOQRvhVNcyMwDAYDVR0TBAUwAwEBADCBwQYDVR0fBIG5MIG2MDmgN6A1pDMwMTELMAkGA1UEBhMCQ04xEDAOBgNVBAsMB0FERDFDUkwxEDAOBgNVBAMMB2NybDIyNDUweaB3oHWGc2xkYXA6Ly8yMTguMjguMTYuMTAxOjM4OS9DTj1jcmwyMjQ1LE9VPUFERDFDUkwsQz1DTj9jZXJ0aWZpY2F0ZVJldm9jYXRpb25MaXN0P2Jhc2U/b2JqZWN0Y2xhc3M9Y1JMRGlzdHJpYnV0aW9uUG9pbnQwCwYDVR0PBAQDAgbAMB0GA1UdDgQWBBS6iLjoRa8wqxzCK8xc0c8A6Y28WjANBgkqhkiG9w0BAQUFAAOBgQBDo7Q2suUk9gRMp/cIxQOGwKmQa/PyaS297pDhrLuzEIajpjsAYy5woVI9EDlnr6NeC/hGvxMEOe38kSl5Bi+MBysosBnutJ7Si/RdB43OCYjaGaHGOgw5heHdHtExo9TR3+mqnuiehheuf8yfpBBz6XtdjBa9Uw8FIdmWGwr/vg==";
        String random1 = "8.026430743576467E8";
        random1 = "11";
        signcert1 = "MIIChTCCAimgAwIBAgIFAQABCZswDAYIKoEcz1UBg3UFADCBgDELMAkGA1UEBhMCQ04xDjAMBgNVBAgMBUh1bmFuMREwDwYDVQQHDAhDaGFuZ3NoYTEvMC0GA1UECgwmRG9uZ0ZhbmcgRWxlY3Ryb25pYyBDZXJ0aWZpY2F0ZSBDZW50ZXIxHTAbBgNVBAMMFERGQ0EgUHViaWxjIFJvb3QgU00yMB4XDTE3MDYzMDA4Mjg0OFoXDTE4MDYzMDA4Mjg0OFowXjELMAkGA1UEBhMCQ04xDzANBgNVBAgMBuays+WNlzEPMA0GA1UEBwwG6YOR5beeMQ0wCwYDVQQKDAR0ZXN0MQ4wDAYDVQQLDAV0ZXN0MTEOMAwGA1UEAwwFdGVzdDIwWTATBgcqhkjOPQIBBggqgRzPVQGCLQNCAAQw3mY7YQuIIqe3+khIXs6y+k6V2Nf+MTAtcuxxXXBC5aHna52tAOoq4OaCCNK5uTsEOQaXwEqvDTJ1hBcanYpGo4GuMIGrMAwGBCoDBAcEBDEwNDEwCQYDVR0TBAIwADAdBgNVHQ4EFgQUvTt13kZrNBepJN0mFrj/lrxCEvUwHwYDVR0jBBgwFoAUkbQhfgToQj3/3CzzBvWTGUrXjnIwQAYDVR0fBDkwNzA1oDOgMYYvaHR0cDovL3B1YmxpYy50b3BvbmVjYS5jb206ODQ0Ni9sZGFwL3NtMmNybC5jcmwwDgYDVR0PAQH/BAQDAgbAMAwGCCqBHM9VAYN1BQADSAAwRQIhAPw6QQdIbFYyudXIAEc5Rj7Q3RKIm1VzkRy9ywl0uL6kAiAiU3Ur40OT7VZOZ1JouodWicHz1cE54Fc8+Pn5EFsNOA==";
//        signdata1 = "MEYCIQCyjU+5W1dKrnJFl1uh2uGXn25C5sGnJG704gDUWtXD3AIhALt+aUJbU1zr7lAk6twhlET5/4yXMakpjDxfa/v7r8b/";

        RequestBody appcode = RequestBody.create(textType, "121");
        RequestBody sourcedata = RequestBody.create(textType, random1);
        RequestBody signdata = RequestBody.create(textType, signdata1);
        RequestBody signcert = RequestBody.create(textType, signcert1);
        Map<String, RequestBody> params = new HashMap<>();
        params.put("appcode", appcode);
        params.put("sourcedata", sourcedata);
        params.put("signdata", signdata);
        params.put("signcert", signcert);


        Call<ResponseBody> call3 = api.certAuth(params);
        Response<ResponseBody> mResponseBody3 = call3.execute();
        if (mResponseBody3.isSuccessful()) {

            String result = mResponseBody3.body().string();
            Log.i("TAG", "认证接口_成功-->" + result);
        } else {
            String result = mResponseBody3.errorBody().string();
            Log.i("TAG", "认证接口_失败-->" + result);

        }
    }

    public void add(View view) throws IOException {
        MediaType textType = MediaType.parse("text/plain");
        String bus = "1001";
        String name = "张三";
        String identified = "1111122";
        String zhanghao = "zhangsan";
        String tel = "111";
        String type = "1";
        String certSN = "abffadfjal";
        String certDN = "CN=张三,C=CN";

        RequestBody appcode = RequestBody.create(textType, "121");
        RequestBody busBody = RequestBody.create(textType, bus);
        RequestBody nameBody = RequestBody.create(textType, name);
        RequestBody identifiedBody = RequestBody.create(textType, identified);
        RequestBody zhanghaoBody = RequestBody.create(textType, zhanghao);
        RequestBody telBody = RequestBody.create(textType, tel);
        RequestBody typeBody = RequestBody.create(textType, type);
        RequestBody certSNBody = RequestBody.create(textType, certSN);
        RequestBody certDNBody = RequestBody.create(textType, certDN);

        final Map<String, RequestBody> params = new HashMap<>();
        params.put("appcode", appcode);
        params.put("bus", busBody);
        params.put("name", nameBody);
        params.put("identified", identifiedBody);
        params.put("zhanghao", zhanghaoBody);
        params.put("tel", telBody);
        params.put("type", typeBody);
        params.put("certSN", certSNBody);
        params.put("certDN", certDNBody);
        new Thread() {
            public void run() {
                try {
                    net1(params);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

    private void net1(Map<String, RequestBody> params) throws IOException {
        Call<ResponseBody> call3 = api.add(params);
        Response<ResponseBody> mResponseBody3 = call3.execute();
        if (mResponseBody3.isSuccessful()) {

            String result = mResponseBody3.body().string();
            Log.i("TAG", "添加授权用户_成功-->" + result);
        } else {
            String result = mResponseBody3.errorBody().string();
            Log.i("TAG", "添加授权用户_失败-->" + result);

        }
    }

    public void update(View view) throws IOException {
        MediaType textType = MediaType.parse("text/plain");

        String bus = "1002";
        String name = "张三";
        String identified = "11111";
        String zhanghao = "zhangsan111";
        String tel = "111";
        String type = "1";
        String certSN = "abffadfjalaaaa";
        String certDN = "CN=张三,C=CN";
        String userId = "2ea2e0595f524d189ad5680931687480";

        RequestBody appcode = RequestBody.create(textType, "121");
        RequestBody busBody = RequestBody.create(textType, bus);
        RequestBody nameBody = RequestBody.create(textType, name);
        RequestBody identifiedBody = RequestBody.create(textType, identified);
        RequestBody zhanghaoBody = RequestBody.create(textType, zhanghao);
        RequestBody telBody = RequestBody.create(textType, tel);
        RequestBody typeBody = RequestBody.create(textType, type);
        RequestBody certSNBody = RequestBody.create(textType, certSN);
        RequestBody certDNBody = RequestBody.create(textType, certDN);
        RequestBody userIdBody = RequestBody.create(textType, userId);

        final Map<String, RequestBody> params = new HashMap<>();
        params.put("appcode", appcode);
        params.put("bus", busBody);
        params.put("name", nameBody);
        params.put("identified", identifiedBody);
        params.put("zhanghao", zhanghaoBody);
        params.put("tel", telBody);
        params.put("type", typeBody);
        params.put("certSN", certSNBody);
        params.put("certDN", certDNBody);
        params.put("userId", userIdBody);
        new Thread() {
            public void run() {
                try {
                    net2(params);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }


    private void net2(Map<String, RequestBody> params) throws IOException {
        Call<ResponseBody> call3 = api.update(params);
        Response<ResponseBody> mResponseBody3 = call3.execute();
        if (mResponseBody3.isSuccessful()) {
            String result = mResponseBody3.body().string();
            Log.i("TAG", "更新用户_成功-->" + result);
        } else {
            String result = mResponseBody3.errorBody().string();
            Log.i("TAG", "更新用户_失败-->" + result);

        }
    }
}
