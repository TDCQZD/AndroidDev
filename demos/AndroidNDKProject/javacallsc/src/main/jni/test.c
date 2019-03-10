//
// Created by Administrator on 2017/6/20.
//
#include "com_example_javacallsc_JNI.h"
#include <string.h>


/**
* 把一个jstring转换成一个c语言的char* 类型.
*/
char *_JString2CStr(JNIEnv *env, jstring jstr) {
    char *rtn = NULL;
    jclass clsstring = (*env)->FindClass(env, "java/lang/String");
    jstring strencode = (*env)->NewStringUTF(env, "GB2312");
    jmethodID mid = (*env)->GetMethodID(env, clsstring, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray barr = (jbyteArray)(*env)->CallObjectMethod(env, jstr, mid,
                                                           strencode); // String .getByte("GB2312");
    jsize alen = (*env)->GetArrayLength(env, barr);
    jbyte *ba = (*env)->GetByteArrayElements(env, barr, JNI_FALSE);
    if (alen > 0) {
        rtn = (char *) malloc(alen + 1); //"\0"
        memcpy(rtn, ba, alen);
        rtn[alen] = 0;
    }
    (*env)->ReleaseByteArrayElements(env, barr, ba, 0);
    return rtn;
}

/**
* jint：返回值
* Java_全类名_方法名
* JNIEnv *env：
*/
jint Java_com_example_javacallsc_JNI_add
        (JNIEnv *env, jobject jobject, jint jin, jint ji) {
    int result = jin + ji;
//    LOGD("result===%d\n", result);
    return result;
};

/**
 *  从java传入字符串，C代码进程拼接
     *
     * @param java : I am from java
     *        c    : add I am from C
     * @return  I am form java add I am from C
 */
jstring Java_com_example_javacallsc_JNI_sayHello
        (JNIEnv *env, jobject jobject, jstring jst) {
    char *fromJava = _JString2CStr(env, jst);//I am form java add I am from C
    //c:
    char *fromC = "add I am from C";
    //拼接函数strcat
    strcat(fromJava, fromC);//把拼接的结果放在第一参数里面
    //jstring     (*NewStringUTF)(JNIEnv*, const char*);
//    LOGD("fromJava===%s\n",fromJava);
    return (*env)->NewStringUTF(env, fromJava);
};

/**
 * 给每个元素加上10
 */
jintArray Java_com_example_javacallsc_JNI_increaseArrayEles
        (JNIEnv *env, jobject jobject, jintArray array) {
    //1.得到数组的长度
    //jsize       (*GetArrayLength)(JNIEnv*, jarray);
    jsize size = (*env)->GetArrayLength(env, array);
    //2.得到数组元素
    //jint*       (*GetIntArrayElements)(JNIEnv*, jintArray, jboolean*);
    jint * intArray = (*env)->GetIntArrayElements(env, array, JNI_FALSE);
    //3.遍历数组，给每个元素加上10
    int i;
    for (i = 0; i < size; i++) {
//        *(intArray+i) = *(intArray+i) + 10;
        *(intArray + i) += 10;
    }
    //4.返回结果
    return array;
};

jint Java_com_example_javacallsc_JNI_checkPwd
        (JNIEnv *env, jobject jobject, jstring jst) {
    //服务器的密码是123456
    char *origin = "123456";
    char *fromUser = _JString2CStr(env, jst);

    //函数比较字符串是否相同
    int code = strcmp(origin, fromUser);
//    LOGD("code===%d\n", code);
    if (code == 0) {
        return 200;
    } else {
        return 400;
    }

};
