

# NDK\_1课程介绍

## NDK学习目录

1、C

2、JNI

3、C++

4、音视频处理

5、linux系统编程

6、NDK相关

7、视频直播与通话

# NDK\_2\_C

# 一、C语言语法

1、宏定义

```
#define _CRT_SECURE_NO_WARNINGS //宏定义
```

2、头文件

```
//引入头文件
//只有函数的声明，编译时会去找到函数的实现
#include <stdio.h>
#include <stdlib.h>
#include <Windows.h>
```

3、数据基本类型

| 数据类型 | 说明 | 所占字节数 |
| :---: | :---: | :---: |
| int |  | 4 |
| short |  |  |
| long |  |  |
| float |  |  |
| double |  |  |
| char |  |  |
|  |  |  |
|  |  |  |
|  |  |  |

**注：不必去记字节数，每一种语言都不同。使用sizeof\(\)去查**

```
    printf("int占%d字节\n",sizeof(int));
```

4、输入输出符

scan 输入符

ptintf  输出符

| 输出标识 | 输出数据类型 |
| :---: | :---: |
| %d | int short |
| %ld | long |
| %f | float |
| %lf | double |
| %c | char |
| %x | 十六进制 |
| %o | 八进制 |
| %s | 字符串 |
| %\#x | 指针 |

eg:

```
void main(){
    int i;
    printf("请输入一个整数：");
    //赋值
    scanf("%d",&i);  //控制台输入，&取地址符
    //打印
    printf("i的值为：%d\n",i);

    system("pause");
}
```

5、进制转换

6、指针

指针存储的是变量的内存地址

内存地址，系统给数据分配的编号

```
void main(){
    int i = 90;
    //指针变量，创建一个int类型的指针
    int* p = &i; //p的值就是i这个变量的内存地址
    printf("%#x\n",p);

    float f = 89.5f;
    //创建一个float类型的指针
    float *fp = &f;
    printf("%#x\n", fp);

    system("pause");
}
```

7、变量名

变量名，对内存空间上的一段数据的抽象

```
void main(){
    int i = 90;
    //i = 89;
    //创建一个int类型的指针
    int *p = &i;
    //输出地址
    printf("p的地址：%#x\n",&p);
    printf("i的地址：%#x\n",&i);

    printf("i的值为：%d\n", i);
    //间接赋值 i = 200;

    //对p存的地址指向的变量进行操作
    //*p = 200;
    //change(p);
    change(&i);  // int *p = &i;
    printf("i的值为：%d\n",i);

    system("pause");
}
```





