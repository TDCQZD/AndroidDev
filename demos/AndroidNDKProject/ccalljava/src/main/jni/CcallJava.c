//
// Created by Administrator on 2017/6/21.
//
#include "com_example_ccalljava_JNI.h"
#include <stdlib.h>
#include <stdio.h>

#include <android/log.h>
#define LOG_TAG "System.out"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)



/**
 * 让C代码调用 java 中JNI类的 public int add(int x, int y)
 */
JNIEXPORT void Java_com_example_ccalljava_JNI_callbackAdd
        (JNIEnv * env, jobject job) {
//1.得到字节码
//jclass      (*FindClass)(JNIEnv*, const char*);
jclass jclazz = (*env)->FindClass(env,"com/example/ccalljava/JNI");
//2.得到方法
//jmethodID   (*GetMethodID)(JNIEnv*, jclass, const char*, const char*);
//最后一个参数是方法签名
jmethodID jmethodIDs= (*env)->GetMethodID(env,jclazz,"add","(II)I");
//3.实例化该类
// jobject     (*AllocObject)(JNIEnv*, jclass);
jobject  jobject =(*env)->AllocObject(env,jclazz);
//4.调用方法
//jint        (*CallIntMethod)(JNIEnv*, jobject, jmethodID, ...);
jint value =  (*env)->CallIntMethod(env,jobject,jmethodIDs,99,1);
//成功调用了public int add(int x, int y)
printf("value===%d\n",value);
LOGE("value===%d\n",value);

};

/**
 * 让C代码调用
 * public void helloFromJava()
 */
JNIEXPORT void Java_com_example_ccalljava_JNI_callbackHelloFromJava
        (JNIEnv * env, jobject job) {
//1.得到字节码
//jclass      (*FindClass)(JNIEnv*, const char*);
jclass jclazz = (*env)->FindClass(env,"com/example/ccalljava/JNI");
//2.得到方法
//jmethodID   (*GetMethodID)(JNIEnv*, jclass, const char*, const char*);
//最后一个参数是方法签名
jmethodID jmethodIDs= (*env)->GetMethodID(env,jclazz,"helloFromJava","()V");
//3.实例化该类
// jobject     (*AllocObject)(JNIEnv*, jclass);
jobject  jobject =(*env)->AllocObject(env,jclazz);
//4.调用方法
//void        (*CallVoidMethod)(JNIEnv*, jobject, jmethodID, ...);
(*env)->CallVoidMethod(env,jobject,jmethodIDs);
//成功调用了public void helloFromJava()

};

/**
 * 让C代码调用void printString(String s)
 */
JNIEXPORT void Java_com_example_ccalljava_JNI_callbackPrintString
        (JNIEnv * env, jobject job) {
//1.得到字节码
//jclass      (*FindClass)(JNIEnv*, const char*);
jclass jclazz = (*env)->FindClass(env,"com/example/ccalljava/JNI");
//2.得到方法
//jmethodID   (*GetMethodID)(JNIEnv*, jclass, const char*, const char*);
//最后一个参数是方法签名
jmethodID jmethodIDs= (*env)->GetMethodID(env,jclazz,"printString","(Ljava/lang/String;)V");
//3.实例化该类
// jobject     (*AllocObject)(JNIEnv*, jclass);
jobject  jobject =(*env)->AllocObject(env,jclazz);
//4.调用方法
//void        (*CallVoidMethod)(JNIEnv*, jobject, jmethodID, ...);
//jstring     (*NewStringUTF)(JNIEnv*, const char*);
jstring jst = (*env)->NewStringUTF(env,"I am afu!!!(*env)->");
(*env)->CallVoidMethod(env,jobject,jmethodIDs,jst);
//成功调用了public void helloFromJava()

};

/**
 * 让C代码静态方法 static void sayHello(String s)
 */
JNIEXPORT void Java_com_example_ccalljava_JNI_callbackSayHello
        (JNIEnv * env, jobject job) {
//1.得到字节码
//jclass      (*FindClass)(JNIEnv*, const char*);
jclass jclazz = (*env)->FindClass(env,"com/example/ccalljava/JNI");
//2.得到方法
//jmethodID   (*GetStaticMethodID)(JNIEnv*, jclass, const char*, const char*);
//最后一个参数是方法签名
jmethodID jmethodIDs= (*env)->GetStaticMethodID(env,jclazz,"sayHello","(Ljava/lang/String;)V");
//3.实例化该类
//void        (*CallStaticVoidMethod)(JNIEnv*, jclass, jmethodID, ...);
jstring jst = (*env)->NewStringUTF(env,"I am android1223");
(*env)->CallStaticVoidMethod(env,jclazz,jmethodIDs,jst);
//成功调用了static void sayHello(String s)

};
/**
 * instance:谁调用了当前Java_com_atguigu_ccalljava_JNI_callBackShowToast对应的Java的接口
 * 就是谁的实例：当前是JNI.this-->MainActivity.this
 */


void Java_com_example_ccalljava_MainActivity_callBackShowToast(JNIEnv *env, jobject instance) {

//1.得到字节码
//jclass      (*FindClass)(JNIEnv*, const char*);
jclass jclazz = (*env)->FindClass(env,"com/example/ccalljava/MainActivity");
//2.得到方法
//最后一个参数是方法签名
//jmethodID   (*GetMethodID)(JNIEnv*, jclass, const char*, const char*);
jmethodID jmethodIDs= (*env)->GetMethodID(env,jclazz,"showToast","()V");
//3.实例化该类
//   jobject     (*AllocObject)(JNIEnv*, jclass);
//jobject  jobject1 = (*env)->AllocObject(env,jclazz);
//4.调用方法
//void        (*CallVoidMethod)(JNIEnv*, jobject, jmethodID, ...);
(*env)->CallVoidMethod(env,instance,jmethodIDs);
//成功调用了static void sayHello(String s)

};