# RxJava线程调度：

**1、线程分类**

Schedulers.io\(\) 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作

Schedulers.computation\(\) 代表CPU计算密集型的操作, 例如需要大量计算的操作

Schedulers.newThread\(\) 代表一个常规的新线程

AndroidSchedulers.mainThread\(\) 代表Android的主线程

**2、线程调度**

```
 observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
```

obscrvcOn\(\)指定Observer线程

subscribcOn\(\)指定Observable线程

注：1、每调用一次obscrvcOn\(\)线程便会切换一次。

2、调用多次subscribcOn\(\)线程，只有第一次指定的有效。

**3、doOnSubscribe\(\)**

# RxJava变换：

**1、概念**

所谓变换，就是将事件序列中的对象或整个序列进行加工处理，转换成不同的事件或事件序列。

**2、变换原理**

# RxJava操作符：

1.x

**一、创建操作符**

1、**just\( \) — 将一个或多个对象转换成发射这个或这些对象的一个Observable  
2、from\( \)— 将一个Iterable, 一个Future, 或者一个数组转换成一个Observable  
3、repeat\( \)— 创建一个重复发射指定数据或数据序列的Observable  
4、repeatWhen\( \)— 创建一个重复发射指定数据或数据序列的Observable，它依赖于另一个Observable发射的数据  
5、create\( \)— 使用一个函数从头创建一个Observable  
6、defer\( \)— 只有当订阅者订阅才创建Observable；为每个订阅创建一个新的Observable  
7、range\( \)— 创建一个发射指定范围的整数序列的Observable  
8、interval\( \)— 创建一个按照给定的时间间隔发射整数序列的Observable  
9、timer\( \)— 创建一个在给定的延时之后发射单个数据的Observable  
10、empty\( \)— 创建一个什么都不做直接通知完成的Observable  
11、error\( \)— 创建一个什么都不做直接通知错误的Observable  
12、never\( \)— 创建一个不发射任何数据的Observable**

**二、变换操作符**

**map\( \)— 对序列的每一项都应用一个函数来变换Observable发射的数据序列  
flatMap\( \), concatMap\( \), and flatMapIterable\( \)— 将Observable发射的数据集合变换为Observables集合，然后将这些Observable发射的数据平坦化的放进一个单独的Observable  
switchMap\( \)— 将Observable发射的数据集合变换为Observables集合，然后只发射这些Observables最近发射的数据  
scan\( \)— 对Observable发射的每一项数据应用一个函数，然后按顺序依次发射每一个值  
groupBy\( \)— 将Observable分拆为Observable集合，将原始Observable发射的数据按Key分组，每一个Observable发射一组不同的数据  
buffer\( \)— 它定期从Observable收集数据到一个集合，然后把这些数据集合打包发射，而不是一次发射一个  
window\( \)— 定期将来自Observable的数据分拆成一些Observable窗口，然后发射这些窗口，而不是每次发射一项  
cast\( \)— 在发射之前强制将Observable发射的所有数据转换为指定类型**

**三、过滤操作符**

**filter\( \)— 过滤数据  
takeLast\( \)— 只发射最后的N项数据  
last\( \)— 只发射最后的一项数据  
lastOrDefault\( \)— 只发射最后的一项数据，如果Observable为空就发射默认值  
takeLastBuffer\( \)— 将最后的N项数据当做单个数据发射  
skip\( \)— 跳过开始的N项数据  
skipLast\( \)— 跳过最后的N项数据  
take\( \)— 只发射开始的N项数据  
first\( \) and takeFirst\( \)— 只发射第一项数据，或者满足某种条件的第一项数据  
firstOrDefault\( \)— 只发射第一项数据，如果Observable为空就发射默认值  
elementAt\( \)— 发射第N项数据  
elementAtOrDefault\( \)— 发射第N项数据，如果Observable数据少于N项就发射默认值  
sample\( \) or throttleLast\( \)— 定期发射Observable最近的数据  
throttleFirst\( \)— 定期发射Observable发射的第一项数据  
throttleWithTimeout\( \) or debounce\( \)— 只有当Observable在指定的时间后还没有发射数据时，才发射一个数据  
timeout\( \)— 如果在一个指定的时间段后还没发射数据，就发射一个异常  
distinct\( \)— 过滤掉重复数据  
distinctUntilChanged\( \)— 过滤掉连续重复的数据  
ofType\( \)— 只发射指定类型的数据  
ignoreElements\( \)— 丢弃所有的正常数据，只发射错误或完成通知**

**1、Map\(\)：**事件对象的直接变换

```
map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                return integer+"、学习是第一步";
            }
        })
```

Integer:转换前（Observable）数据类型

String:转换后（observer）数据类型

**2、flatMap\(\)：**

