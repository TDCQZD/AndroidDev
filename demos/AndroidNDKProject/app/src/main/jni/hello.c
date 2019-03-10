//
// Created by Administrator on 2017/6/20.
//
#include <jni.h>
#include <stdio.h>
#include <stdlib.h>


/**
 * jstring ：返回值
 * Java_全类名_方法名
 * JNIEnv* env:里面有很多方法
 * jobject jobj：谁调用了这个方法就是谁的实例
 * 当前就是JNI.thi
 */
jstring Java_com_example_administrator_androidndkproject_JNIS_helloJNI(JNIEnv *env, jobject jobj) {
    char *text = "Hello from JNI !";
    return (*env)->NewStringUTF(env, text);
}