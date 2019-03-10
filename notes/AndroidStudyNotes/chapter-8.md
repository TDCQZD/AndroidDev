# Retrofit使用步骤：

**步骤1：**添加Retrofit库的依赖

```
compile 'com.squareup.retrofit2:retrofit:2.3.0'
```

**步骤2：**创建 接收服务器返回数据 的类

即创建数据Bean类

注：如果自定义数据Bean类需要Retrofit实例中配置数据解析器

```
Gson: com.squareup.retrofit2:converter-gson
Jackson: com.squareup.retrofit2:converter-jackson
```

**步骤3：**创建 用于描述网络请求 的接口

即定义带参数的API接口  
**步骤4：**创建 Retrofit 实例

```
  Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/") // 设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create())// 设置数据解析器（GSON、fastJson、自定义）
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())// 配置支持RxJava
                .build();
```

**a. 关于数据解析器（Converter）**

* Retrofit支持多种数据解析方式
* 使用时需要在Gradle添加依赖

| 数据解析器 | Gradle依赖 |
| :--- | :--- |
| Gson | com.squareup.retrofit2:converter-gson:2.0.2 |
| Jackson | com.squareup.retrofit2:converter-jackson:2.0.2 |
| Simple XML | com.squareup.retrofit2:converter-simplexml:2.0.2 |
| Protobuf | com.squareup.retrofit2:converter-protobuf:2.0.2 |
| Moshi | com.squareup.retrofit2:converter-moshi:2.0.2 |
| Wire | com.squareup.retrofit2:converter-wire:2.0.2 |
| Scalars | com.squareup.retrofit2:converter-scalars:2.0.2 |

### b. 关于网络请求适配器（CallAdapter）

* Retrofit支持多种网络请求适配器方式：guava、Java8和rxjava
  > 使用时如使用的是 `Android` 默认的 `CallAdapter`，则不需要添加网络请求适配器的依赖，否则则需要按照需求进行添加  
  > Retrofit 提供的 `CallAdapter`
* 使用时需要在Gradle添加依赖：

| 网络请求适配器 | Gradle依赖 |
| :--- | :--- |
| guava | com.squareup.retrofit2:adapter-guava:2.0.2 |
| Java8 | com.squareup.retrofit2:adapter-java8:2.0.2 |
| rxjava | com.squareup.retrofit2:adapter-rxjava:2.0.2 |

**步骤5：**创建 网络请求接口实例 并 配置网络请求参数

```
 api = retrofit.create(API.class);// 创建 网络请求接口 的实例
  Call<RequestBody> call = api.getUser();//对 发送请求 进行封装（如果有参数，需要配置参数）
```

**步骤6：**发送网络请求（异步 / 同步）并处理服务器返回的数据

> 封装了 数据转换、线程切换的操作

```
//发送网络请求(异步)
call.enqueue(new Callback<RequestBody>() {//异步
            @Override
            public void onResponse(Call<RequestBody> call, Response<RequestBody> response) { //请求成功时回调
                String result = response.body().toString();
                Log.i("TAG", "ResponseBody onResponse()--->" + result);
            }

            @Override
            public void onFailure(Call<RequestBody> call, Throwable t) { //请求失败时候的回调

            }
        });
// 发送网络请求（同步）
Response<Reception> response = call.execute()
```

# 源码解析：

Retrofit 源码解析

1、Retrofit通过动态代理实现我们指定接口的实例

`retrofit.create`的源码

```
#Retrofit


public <T> T create(final Class<T> service) {
    Utils.validateServiceInterface(service);
    if (validateEagerly) {
      eagerlyValidateMethods(service);
    }
    return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[] { service },
        new InvocationHandler() {
          private final Platform platform = Platform.get();

          @Override public Object invoke(Object proxy, Method method, @Nullable Object[] args)
              throws Throwable {
            // If the method is a method from Object then defer to normal invocation.
            if (method.getDeclaringClass() == Object.class) {
              return method.invoke(this, args);
            }
            if (platform.isDefaultMethod(method)) {
              return platform.invokeDefaultMethod(method, service, proxy, args);
            }
            ServiceMethod<Object, Object> serviceMethod =
                (ServiceMethod<Object, Object>) loadServiceMethod(method);
            OkHttpCall<Object> okHttpCall = new OkHttpCall<>(serviceMethod, args);
            return serviceMethod.callAdapter.adapt(okHttpCall);
          }
        });
  }
```

# Retrofit整体实现流程

** Retrofit的构建**

**Retrofit**通过构造者模式进行构建retrofit对象.

```
  retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .build();
```

Builder源码：

```
#Retrofit

public Builder() {
      this(Platform.get());
    }
public Builder client(OkHttpClient client) {
      return callFactory(checkNotNull(client, "client == null"));
    }
public Builder callFactory(okhttp3.Call.Factory factory) {
      this.callFactory = checkNotNull(factory, "factory == null");
      return this;
    }
 public Builder baseUrl(String baseUrl) {
      checkNotNull(baseUrl, "baseUrl == null");
      HttpUrl httpUrl = HttpUrl.parse(baseUrl);
      if (httpUrl == null) {
        throw new IllegalArgumentException("Illegal URL: " + baseUrl);
      }
      return baseUrl(httpUrl);
    }
public Builder baseUrl(HttpUrl baseUrl) {
      checkNotNull(baseUrl, "baseUrl == null");
      List<String> pathSegments = baseUrl.pathSegments();
      if (!"".equals(pathSegments.get(pathSegments.size() - 1))) {
        throw new IllegalArgumentException("baseUrl must end in /: " + baseUrl);
      }
      this.baseUrl = baseUrl;
      return this;
    }  
public Builder addCallAdapterFactory(CallAdapter.Factory factory) {
      adapterFactories.add(checkNotNull(factory, "factory == null"));
      return this;
    }
public Builder addConverterFactory(Converter.Factory factory) {
      converterFactories.add(checkNotNull(factory, "factory == null"));
      return this;
    }
public Builder callbackExecutor(Executor executor) {
      this.callbackExecutor = checkNotNull(executor, "executor == null");
      return this;
    }
public Retrofit build() {
      if (baseUrl == null) {
        throw new IllegalStateException("Base URL required.");
      }

      okhttp3.Call.Factory callFactory = this.callFactory;
      if (callFactory == null) {
        callFactory = new OkHttpClient();
      }

      Executor callbackExecutor = this.callbackExecutor;
      if (callbackExecutor == null) {
        callbackExecutor = platform.defaultCallbackExecutor();
      }

      // Make a defensive copy of the adapters and add the default Call adapter.
      List<CallAdapter.Factory> adapterFactories = new ArrayList<>(this.adapterFactories);
      adapterFactories.add(platform.defaultCallAdapterFactory(callbackExecutor));

      // Make a defensive copy of the converters.
      List<Converter.Factory> converterFactories = new ArrayList<>(this.converterFactories);

      return new Retrofit(callFactory, baseUrl, converterFactories, adapterFactories,
          callbackExecutor, validateEagerly);
    }
  }
```

* baseUrl必须指定；

* callFactory:配置网络连接。如果不设置callFactory，则默认直接`new OkHttpClient()`，可见如果你需要对okhttpclient进行详细的设置，需要构建`OkHttpClient`对象，然后传入；

* callbackExecutor， 用来将回调传递到UI线程。 利用platform对象，对平台进行判断，判断主要是利用`Class.forName("")`进行查找。 如果是Android平台，会自定义一个`Executor`对象，并且利用`Looper.getMainLooper()`实例化一个handler对象，在`Executor`内部通过`handler.post(runnable)。`

```
#Platform

private static Platform findPlatform() {
    try {
      Class.forName("android.os.Build");
      if (Build.VERSION.SDK_INT != 0) {
        return new Android();
      }
    } catch (ClassNotFoundException ignored) {
    }
    try {
      Class.forName("java.util.Optional");
      return new Java8();
    } catch (ClassNotFoundException ignored) {
    }
    return new Platform();
  }

static class Android extends Platform {
    @Override public Executor defaultCallbackExecutor() {
      return new MainThreadExecutor();
    }

    @Override CallAdapter.Factory defaultCallAdapterFactory(@Nullable Executor callbackExecutor) {
      if (callbackExecutor == null) throw new AssertionError();
      return new ExecutorCallAdapterFactory(callbackExecutor);
    }

    static class MainThreadExecutor implements Executor {
      private final Handler handler = new Handler(Looper.getMainLooper());

      @Override public void execute(Runnable r) {
        handler.post(r);
      }
    }
  }
```

* adapterFactories 这个对象主要用于对Call进行转化，基本上不需要我们自己去自定义。

* converterFactories，该对象用于转化数据，例如将返回的`responseBody`转化为对象等；当然不仅仅是针对返回的数据，还能用于一般备注解的参数的转化例如`@Body`标识的对象做一些操作。

**Call构建流程**

构造完成retrofit，就可以利用retrofit.create方法去构建接口的实例了，上面已经分析了这个环节利用了动态代理，而且我们也分析了具体的Call的构建流程在invoke方法中，下面看代码：

```
#Retrofit


public <T> T create(final Class<T> service) {
    Utils.validateServiceInterface(service);
    if (validateEagerly) {
      eagerlyValidateMethods(service);
    }
    return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[] { service },
        new InvocationHandler() {
          private final Platform platform = Platform.get();

          @Override public Object invoke(Object proxy, Method method, @Nullable Object[] args)
              throws Throwable {
            // If the method is a method from Object then defer to normal invocation.
            if (method.getDeclaringClass() == Object.class) {
              return method.invoke(this, args);
            }
            if (platform.isDefaultMethod(method)) {
              return platform.invokeDefaultMethod(method, service, proxy, args);
            }
            ServiceMethod<Object, Object> serviceMethod =
                (ServiceMethod<Object, Object>) loadServiceMethod(method);①
            OkHttpCall<Object> okHttpCall = new OkHttpCall<>(serviceMethod, args);②
            return serviceMethod.callAdapter.adapt(okHttpCall);③
          }
        });
  }
```

主要也就三行代码，

第一行是根据我们的method将其包装成ServiceMethod，①

第二行是通过ServiceMethod和方法的参数构造`retrofit2.OkHttpCall`对象，②

第三行是通过`serviceMethod.callAdapter.adapt()`方法，将`OkHttpCall`进行代理包装。③

* **第一步：ServiceMethod类**

```
#Retrofit class

  ServiceMethod<?, ?> loadServiceMethod(Method method) {
    ServiceMethod<?, ?> result = serviceMethodCache.get(method);
    if (result != null) return result;

    synchronized (serviceMethodCache) {
      result = serviceMethodCache.get(method);
      if (result == null) {
        result = new ServiceMethod.Builder<>(this, method).build();
        serviceMethodCache.put(method, result);
      }
    }
    return result;
  }

#ServiceMethod

Builder(Retrofit retrofit, Method method) {
      this.retrofit = retrofit;
      this.method = method;
      this.methodAnnotations = method.getAnnotations();
      this.parameterTypes = method.getGenericParameterTypes();
      this.parameterAnnotationsArray = method.getParameterAnnotations();
    }

    public ServiceMethod build() {
      callAdapter = createCallAdapter();
      responseType = callAdapter.responseType();
      if (responseType == Response.class || responseType == okhttp3.Response.class) {
        throw methodError("'"
            + Utils.getRawType(responseType).getName()
            + "' is not a valid response body type. Did you mean ResponseBody?");
      }
      responseConverter = createResponseConverter();

      for (Annotation annotation : methodAnnotations) {
        parseMethodAnnotation(annotation);
      }

      if (httpMethod == null) {
        throw methodError("HTTP method annotation is required (e.g., @GET, @POST, etc.).");
      }

      if (!hasBody) {
        if (isMultipart) {
          throw methodError(
              "Multipart can only be specified on HTTP methods with request body (e.g., @POST).");
        }
        if (isFormEncoded) {
          throw methodError("FormUrlEncoded can only be specified on HTTP methods with "
              + "request body (e.g., @POST).");
        }
      }

      int parameterCount = parameterAnnotationsArray.length;
      parameterHandlers = new ParameterHandler<?>[parameterCount];
      for (int p = 0; p < parameterCount; p++) {
        Type parameterType = parameterTypes[p];
        if (Utils.hasUnresolvableType(parameterType)) {
          throw parameterError(p, "Parameter type must not include a type variable or wildcard: %s",
              parameterType);
        }

        Annotation[] parameterAnnotations = parameterAnnotationsArray[p];
        if (parameterAnnotations == null) {
          throw parameterError(p, "No Retrofit annotation found.");
        }

        parameterHandlers[p] = parseParameter(p, parameterType, parameterAnnotations);
      }

      if (relativeUrl == null && !gotUrl) {
        throw methodError("Missing either @%s URL or @Url parameter.", httpMethod);
      }
      if (!isFormEncoded && !isMultipart && !hasBody && gotBody) {
        throw methodError("Non-body HTTP method cannot contain @Body.");
      }
      if (isFormEncoded && !gotField) {
        throw methodError("Form-encoded method must contain at least one @Field.");
      }
      if (isMultipart && !gotPart) {
        throw methodError("Multipart method must contain at least one @Part.");
      }

      return new ServiceMethod<>(this);
    }
```

直接看build方法，首先拿到这个callAdapter最终拿到的是我们在构建retrofit里面时adapterFactories时添加的，即为：`new ExecutorCallbackCall<>(callbackExecutor, call)`，该`ExecutorCallbackCall`唯一做的事情就是将原本call的回调转发至UI线程。

接下来通过`callAdapter.responseType()`返回的是我们方法的实际类型，例如:`Call<User>`,则返回`User`类型，然后对该类型进行判断。

接下来是`createResponseConverter`拿到responseConverter对象，其当然也是根据我们构建retrofit时,`addConverterFactory`添加的ConverterFactory对象来寻找一个合适的返回，寻找的依据主要看该converter能否处理你编写方法的返回值类型，默认实现为`BuiltInConverters`，仅仅支持返回值的实际类型为`ResponseBody`和`Void`，也就说明了默认情况下，是不支持`Call<User>`这类类型的。

接下来就是对注解进行解析了，主要是对方法上的注解进行解析，那么可以拿到httpMethod以及初步的url（包含占位符）。

后面是对方法中参数中的注解进行解析，这一步会拿到很多的`ParameterHandler`对象，该对象在`toRequest()`构造Request的时候调用其apply方法。

总结： ServiceMethod主要用于将我们`接口中的方法`转化为一个`Request对象`，于是根据我们的接口返回值确定responseConverter,解析我们方法上的注解拿到初步的url,解析我们参数上的注解拿到构建RequestBody所需的各种信息，最终调用toRequest的方法完成Request的构建。

* ** 第二步：OkHttpCall的构建，构造函数仅仅是简单的赋值**

```
#Okhttp

  OkHttpCall(ServiceMethod<T, ?> serviceMethod, @Nullable Object[] args) {
    this.serviceMethod = serviceMethod;
    this.args = args;
  }
```

* #### 第三步：`serviceMethod.callAdapter.adapt(okHttpCall)` {#423-执行call}

  callAdapter是`ExecutorCallAdapterFactory.get()`对应代码为：

```
#ExecutorCallAdapterFactory

final class ExecutorCallAdapterFactory extends CallAdapter.Factory {
  final Executor callbackExecutor;

  ExecutorCallAdapterFactory(Executor callbackExecutor) {
    this.callbackExecutor = callbackExecutor;
  }

  @Override
  public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
    if (getRawType(returnType) != Call.class) {
      return null;
    }
    final Type responseType = Utils.getCallResponseType(returnType);
    return new CallAdapter<Object, Call<?>>() {
      @Override public Type responseType() {
        return responseType;
      }

      @Override public Call<Object> adapt(Call<Object> call) {
        return new ExecutorCallbackCall<>(callbackExecutor, call);
      }
    };
  }


#ExecutorCallAdapterFactory

static final class ExecutorCallbackCall<T> implements Call<T> {
    final Executor callbackExecutor;
    final Call<T> delegate;

    ExecutorCallbackCall(Executor callbackExecutor, Call<T> delegate) {
      this.callbackExecutor = callbackExecutor;
      this.delegate = delegate;
    }

    @Override public void enqueue(final Callback<T> callback) {
      checkNotNull(callback, "callback == null");

      delegate.enqueue(new Callback<T>() {
        @Override public void onResponse(Call<T> call, final Response<T> response) {
          callbackExecutor.execute(new Runnable() {
            @Override public void run() {
              if (delegate.isCanceled()) {
                // Emulate OkHttp's behavior of throwing/delivering an IOException on cancellation.
                callback.onFailure(ExecutorCallbackCall.this, new IOException("Canceled"));
              } else {
                callback.onResponse(ExecutorCallbackCall.this, response);
              }
            }
          });
        }

        @Override public void onFailure(Call<T> call, final Throwable t) {
          callbackExecutor.execute(new Runnable() {
            @Override public void run() {
              callback.onFailure(ExecutorCallbackCall.this, t);
            }
          });
        }
      });
    }

    @Override public boolean isExecuted() {
      return delegate.isExecuted();
    }

    @Override public Response<T> execute() throws IOException {
      return delegate.execute();
    }
```

可以看到`adapt`返回的是`ExecutorCallbackCall`对象， ExecutorCallbackCall仅仅是对Call对象进行封装，类似装饰者模式，只不过将其执行时的回调通过callbackExecutor进行回调到UI线程中去了。

#### 执行Call {#423-执行call}

上面已经拿到了经过封装的`ExecutorCallbackCall`类型的call对象，实际上就是我们实际在写代码时拿到的call对象，那么我们一般会执行`enqueue`方法，看看源码是怎么做的。

```
#ExecutorCallAdapterFactory


@Override public void enqueue(final Callback<T> callback) {
      checkNotNull(callback, "callback == null");

      delegate.enqueue(new Callback<T>() {
        @Override public void onResponse(Call<T> call, final Response<T> response) {
          callbackExecutor.execute(new Runnable() {
            @Override public void run() {
              if (delegate.isCanceled()) {
                // Emulate OkHttp's behavior of throwing/delivering an IOException on cancellation.
                callback.onFailure(ExecutorCallbackCall.this, new IOException("Canceled"));
              } else {
                callback.onResponse(ExecutorCallbackCall.this, response);
              }
            }
          });
        }

        @Override public void onFailure(Call<T> call, final Throwable t) {
          callbackExecutor.execute(new Runnable() {
            @Override public void run() {
              callback.onFailure(ExecutorCallbackCall.this, t);
            }
          });
        }
      });
    }
```

首先是`ExecutorCallbackCall.enqueue`方法，可以看到除了将onResponse和onFailure回调到UI线程，主要的操作还是delegate完成的，这个delegate实际上就是OkHttpCall对象，我们看它的enqueue方法。

```
#OkhttpCall


@Override public void enqueue(final Callback<T> callback) {
    checkNotNull(callback, "callback == null");

    okhttp3.Call call;
    Throwable failure;

    synchronized (this) {
      if (executed) throw new IllegalStateException("Already executed.");
      executed = true;

      call = rawCall;
      failure = creationFailure;
      if (call == null && failure == null) {
        try {
          call = rawCall = createRawCall();
        } catch (Throwable t) {
          failure = creationFailure = t;
        }
      }
    }

    if (failure != null) {
      callback.onFailure(this, failure);
      return;
    }

    if (canceled) {
      call.cancel();
    }

    call.enqueue(new okhttp3.Callback() {
      @Override public void onResponse(okhttp3.Call call, okhttp3.Response rawResponse)
          throws IOException {
        Response<T> response;
        try {
          response = parseResponse(rawResponse);
        } catch (Throwable e) {
          callFailure(e);
          return;
        }
        callSuccess(response);
      }

      @Override public void onFailure(okhttp3.Call call, IOException e) {
        try {
          callback.onFailure(OkHttpCall.this, e);
        } catch (Throwable t) {
          t.printStackTrace();
        }
      }

      private void callFailure(Throwable e) {
        try {
          callback.onFailure(OkHttpCall.this, e);
        } catch (Throwable t) {
          t.printStackTrace();
        }
      }

      private void callSuccess(Response<T> response) {
        try {
          callback.onResponse(OkHttpCall.this, response);
        } catch (Throwable t) {
          t.printStackTrace();
        }
      }
    });
  }
```

如上， 内部实际上就是okhttp的Call对象，也是调用`okhttp3.Call.enqueue`方法。

中间`okhttp3.Call`的创建代码为：

```
#OkhttpCall


private okhttp3.Call createRawCall() throws IOException {
    Request request = serviceMethod.toRequest(args);
    okhttp3.Call call = serviceMethod.callFactory.newCall(request);
    if (call == null) {
      throw new NullPointerException("Call.Factory returned null.");
    }
    return call;
  }
```

可以看到，通过`serviceMethod.toRequest`完成对request的构建，通过request去构造call对象，然后返回.。

中间还涉及一个`parseRespons`方法，执行的代码如下：

```
#OkhttpCall

Response<T> parseResponse(okhttp3.Response rawResponse) throws IOException {
    ResponseBody rawBody = rawResponse.body();

    // Remove the body's source (the only stateful object) so we can pass the response along.
    rawResponse = rawResponse.newBuilder()
        .body(new NoContentResponseBody(rawBody.contentType(), rawBody.contentLength()))
        .build();

    int code = rawResponse.code();
    if (code < 200 || code >= 300) {
      try {
        // Buffer the entire body to avoid future I/O.
        ResponseBody bufferedBody = Utils.buffer(rawBody);
        return Response.error(bufferedBody, rawResponse);
      } finally {
        rawBody.close();
      }
    }

    if (code == 204 || code == 205) {
      rawBody.close();
      return Response.success(null, rawResponse);
    }

    ExceptionCatchingRequestBody catchingBody = new ExceptionCatchingRequestBody(rawBody);
    try {
      T body = serviceMethod.toResponse(catchingBody);
      return Response.success(body, rawResponse);
    } catch (RuntimeException e) {
      // If the underlying source threw an exception, propagate that rather than indicating it was
      // a runtime exception.
      catchingBody.throwIfCaught();
      throw e;
    }
  }
```

通过serviceMethod对ResponseBody进行转化，然后返回，转化实际上就是通过`responseConverter`的convert方法。

```
#ServiceMethod

R toResponse(ResponseBody body) throws IOException {
    return responseConverter.convert(body);
  }
```

到这里，我们整个源码的流程分析就差不多了，目的就掌握一个大体的原理和执行流程，了解下几个核心的类。

那么总结一下：

* 首先构造retrofit，几个核心的参数呢，主要就是baseurl,callFactory\(默认okhttpclient\),converterFactories,adapterFactories,excallbackExecutor。

* 然后通过create方法拿到接口的实现类，这里利用Java的`Proxy`类完成动态代理的相关代理

* 在invoke方法内部，拿到我们所声明的注解以及实参等，构造ServiceMethod，ServiceMethod中解析了大量的信息，最痛可以通过`toRequest`构造出`okhttp3.Request`对象。有了`okhttp3.Request`对象就可以很自然的构建出`okhttp3.call`，最后calladapter对Call进行装饰返回。

* 拿到Call就可以执行enqueue或者execute方法了。

# 自定义Converter.Factory（转换器）

关于`Converter.Factory`，肯定是通过`addConverterFactory`设置的。

```
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
```

`addConverterFactory方法：该方法`接受的是一个`Converter.Factory factory`对象。

```
#Retrofit

public Builder addConverterFactory(Converter.Factory factory) {
      converterFactories.add(checkNotNull(factory, "factory == null"));
      return this;
    }
```

该对象呢，是一个抽象类，内部包含3个方法：

```
#Converter

abstract class Factory {
    /**
     * Returns a {@link Converter} for converting an HTTP response body to {@code type}, or null if
     * {@code type} cannot be handled by this factory. This is used to create converters for
     * response types such as {@code SimpleResponse} from a {@code Call<SimpleResponse>}
     * declaration.
     */
    public @Nullable Converter<ResponseBody, ?> responseBodyConverter(Type type,
        Annotation[] annotations, Retrofit retrofit) {
      return null;
    }

    /**
     * Returns a {@link Converter} for converting {@code type} to an HTTP request body, or null if
     * {@code type} cannot be handled by this factory. This is used to create converters for types
     * specified by {@link Body @Body}, {@link Part @Part}, and {@link PartMap @PartMap}
     * values.
     */
    public @Nullable Converter<?, RequestBody> requestBodyConverter(Type type,
        Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
      return null;
    }

    /**
     * Returns a {@link Converter} for converting {@code type} to a {@link String}, or null if
     * {@code type} cannot be handled by this factory. This is used to create converters for types
     * specified by {@link Field @Field}, {@link FieldMap @FieldMap} values,
     * {@link Header @Header}, {@link HeaderMap @HeaderMap}, {@link Path @Path},
     * {@link Query @Query}, and {@link QueryMap @QueryMap} values.
     */
    public @Nullable Converter<?, String> stringConverter(Type type, Annotation[] annotations,
        Retrofit retrofit) {
      return null;
    }
```

可以看到呢，3个方法都是空方法而不是抽象的方法，也就表明了我们可以选择去实现其中的1个或多个方法，一般只需要关注

`requestBodyConverter`和`responseBodyConverter`就可以了。

**1、responseBodyConverter:将responseBody进行转化**

实现`responseBodyConverter`方法，就是将responseBody进行转化。

**2、requestBodyConverter：** 根据参数转化为`RequestBody`

总结：

* responseBodyConverter 主要是对应`@Body`注解，完成ResponseBody到实际的返回类型的转化，这个类型对应`Call<XXX>`里面的泛型XXX，其实`@Part`等注解也会需要responseBodyConverter，只不过我们的参数类型都是`RequestBody`，由默认的converter处理了。
* requestBodyConverter 完成对象到RequestBody的构造。
* 一定要注意，检查type如果不是自己能处理的类型，记得return null （因为可以添加多个，你不能处理return null ,还会去遍历后面的converter）.

# 使用细节总结：

1、不建议在Retrofit中进行文件下载，在Okhttp中进行下载操作。

Retrofit中文件下载：

增加一个方法：

```
@GET("download")
Call<ResponseBody> downloadTest();
```

调用：

```
Call<ResponseBody> call = userBiz.downloadTest();
call.enqueue(new Callback<ResponseBody>()
{
    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
    {
        InputStream is = response.body().byteStream();
        //save file
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t)
    {

    }
});
```

可以看到可以返回`ResponseBody`，那么很多事都能干了~~。but，也看出这种方式下载感觉非常鸡肋，并且onReponse回调虽然在UI线程，但是你还是要处理io操作，也就是说你在这里还要另外开线程操作，或者你可以考虑同步的方式下载。最后还是建议使用okhttp去下载，例如使用[okhttputils](https://github.com/hongyangAndroid/okhttp-utils).

2、配置OkHttpClient

单独写一个OkhttpClient的单例生成类，在这个里面完成你所需的所有的配置，然后将`OkhttpClient`实例通过方法公布出来，设置给retrofit。

设置方式：

```
Retrofit retrofit = new Retrofit.Builder()
    .callFactory(OkHttpUtils.getClient())
    .build();
```

`callFactory`方法接受一个`okhttp3.Call.Factory`对象，`OkHttpClient`即为一个实现类。

使用场景：

使用retrofit需要统一的log管理，给每个请求添加统一的header等，这些都应该通过okhttpclient去操作， 或者需要更多的配置时。

