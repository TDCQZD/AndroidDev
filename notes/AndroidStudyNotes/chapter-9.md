OkHttp基本使用

一、依赖库

```
compile 'com.squareup.okhttp3:okhttp:3.9.0'
```

测试库依赖：

```
testCompile 'com.squareup.okhttp3:mockwebserver:3.9.0'
```

一种用于测试HTTP，HTTPS和HTTP / 2客户端库。

二、使用步骤：

1、配置OkHttpClient

2、构建Request,并进行Url请求

3、封装Request为Call

4、请求响应Reponse,并处理返回信息。

注：post请求时，需要使用Xxxbody进行数据处理。

```
   FormBody：表单

   MultipartBody：文件上传
```

三、请求方法

#### GET

```
OkHttpClient client = new OkHttpClient();

String run(String url) throws IOException {
  Request request = new Request.Builder()
      .url(url)
      .build();

  Response response = client.newCall(request).execute();
  return response.body().string();
}
```

#### POST

```
public static final MediaType JSON
    = MediaType.parse("application/json; charset=utf-8");

OkHttpClient client = new OkHttpClient();

String post(String url, String json) throws IOException {
  RequestBody body = RequestBody.create(JSON, json);
  Request request = new Request.Builder()
      .url(url)
      .post(body)
      .build();
  Response response = client.newCall(request).execute();
  return response.body().string();
}
```

四、通用网络框架封装

