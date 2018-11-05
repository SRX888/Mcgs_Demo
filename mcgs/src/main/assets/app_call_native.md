# APP调用硬件库

# 一、概述

在mcgs-demo app 中的应用栏中有几个应用，分别是“USB主口从口设置”，“蜂鸣设置”，“背光设置”
及“RTC设置”。这个应用的设置在内核驱动的层实现的，由mcgs 的HAL提供的接口来实现，mcgs提供一个硬件库（libmcgs-hal.so）及对应的头文件。很明显，APP层需要调用底层硬件库（libmcgs-hal.so）肯定需要使用jni技术来实现了。具体的实现的思路就是这个方式： APP 层（java）--->jni--->硬件库（libmcgs-hal.so）的方式。接来下就会具体的来说明如何通过APP来设置这几个应用。

## 1.1  NDK介绍

#### 什么是 Android NDK？

  Android NDK（Native Development Kit ）是一套工具集合，允许你用像C/C++语言那样实现应用程序的一部分。同时，Android NDK对于Android SDK只是个组件，它可以帮我们生成的JNI兼容的共享库可以在大于Android1.5平台的ARM CPU上运行，将生成的共享库拷贝到合适的程序工程路径的位置上，以保证它们自动的添加到你的apk包中。
  
#### 何时使用NDK？
 
 Google仅在极少数情况下建议使用NDK，有如下使用场景：<br> 
● 必须提高性能（例如，对大量数据进行排序）。 <br> 
● 使用第三方库。举例说明：许多第三方库由C/C++语言编写，而Android应用程序需要使用现有的第三方库，如Ffmpeg、OpenCV这样的库<br> 

#### Android NDK系统文件的结构如下：

ndk-build：该shell脚本是Android NDK构建系统的起始点。<br>
ndk-gdb:该shell脚本允许用GUN调试器调试原生组件。<br>
ndk-stack：该shell脚本可以帮助分析原生组件崩溃时的堆栈追踪。<br>
build：该目录包含了Android NDK构建系统的所有模块。<br>
platforms：该目录包含了支持不同Android目标版本的头文件和库文件。Android NDK构建系统会根据具体的Android版本自动引用这些文档。<br>
samples：该目录包含了一些示例应用程序，这些程序可以体现AndroidNDK的性能。有利于学习如何使用Android NDK的特性。<br>
sorces：该目录包含了可供开发人员导入到现有的Android NDK项目的一些共享模块。<br>
toolchains：该目录包含目前Android NDK支持的不同目标机体系结构的交叉编译器。Android NDK目前支持ARM、X86和MIPS机体系结构。Android NDK 构建系统根据选定的体系结构使用不同的交叉编译器。<br>

需要将ndk 目录添加到环境变量中。<br>
NDK下载与NDK更新：https://blog.csdn.net/xiayiye5/article/details/80981141<br>
NDK环境变量的配置参考：https://blog.csdn.net/xiayiye5/article/details/79878737


##  1.2  JNI介绍

#### 什么是JNI？
JNI是一种在Java虚拟机控制下执行代码的标准机制。代码被编写成汇编程序或者C/C++程序，并组装为动态库。也就允许了非静态绑定用法。这提供了一个在Java平台上调用C/C++的一种途径，反之亦然。它允许Java类的某些方法原生实现，同时让它们能够像普通Java方法一样被调用和使用。


#### JNI有什么优势？
JNI是Java Native Interface的缩写，它提供了若干的API实现了Java和其他语言的通信（主要是C&C++）。

#### 何时使用JNI？
1、JAVA程序和本地程序使用TCP/IP或者IPC进行交互。<br>
2、当用JAVA程序连接本地数据库时，使用JDBC提供的API。 <br>
3、JAVA程序可以使用分布式对象技术，如JAVA IDL API。 
这些方案的共同点是，JAVA和C处于不同的线程，或者不同的机器上。这样，当本地程序崩溃时，不会影响到JAVA程序。


#### JNI的开发流程主要分为以下几个步骤：
编写带有native声明方法的java类； <br>
使用”javah”java类的名生成扩展名为h的头文件； <br>
复制jni.h和jni_md.h文件到CPP工程中; <br>
实现.h头文件中声明的函数 <br>
ndk-build 编译jni 库 <br>


# 二、mcgs-demo JNI实现

## 2.1  声明一个native方法

**在声明native方法之前，首先要看硬件库（libmcgs-hal.so）的头文件，看看提供了什么接口及对应接口的参数等。**

**hal-api.h内容如下：**

```
#ifndef _INCLUDE_LIBHAL_API_H_
#define _INCLUDE_LIBHAL_API_H_

#ifdef __cplusplus
    extern "C" {
#endif

/* linux header file */

/* C lib header file */
#include <stdbool.h>
#include <time.h>

/* ------------------------------------------------------------------*/
/** @defgroup api    MCGS HAL Application Framework
 *
 *  MCGS HAL functions for managing applicatons and offer api
 */
/* ------------------------------------------------------------------*/

/* ------------------------------------------------------------------*/
/** @addtogroup display     Display
 *  @ingroup api
 *
 *  Display module
 *
 *
 *  @addtogroup lcd         LCD
 *  @ingroup display
 *
 *  LCD 相关状态的获取与设置
 *
 *
 *  @{
 */
/* ------------------------------------------------------------------*/

enum
{
    HAL_DISPLAY_BACKLIGHT_OPEN,         //LCD显示背光打开状态
    HAL_DISPLAY_BACKLIGHT_CLOSE,        //LCD显示背光关闭状态
};
/* ------------------------------------------------------------------*/
/**
 * @brief 获取LCD状态
 *
 * @returns \ref HAL_DISPLAY_BACKLIGHT_OPEN : lcd 背光打开
 * @returns \ref HAL_DISPLAY_BACKLIGHT_CLOSE : lcd 背光关闭
 * @returns others: 函数执行失败
 */
/* ------------------------------------------------------------------*/
extern int hal_get_lcd_status(void);


/* ------------------------------------------------------------------*/
/**
 * @brief 设置LCD状态
 *
 * @param[in] status \ref HAL_DISPLAY_BACKLIGHT_OPEN, \ref HAL_DISPLAY_BACKLIGHT_CLOSE
 * @returns 0 ：函数执行成功
 * @returns others：函数执行失败
 */
/* ------------------------------------------------------------------*/
extern int hal_set_lcd_status(int status);

/* ------------------------------------------------------------------*/
/**
 * @brief 显示模块获取背光亮度
 *
 * @returns 非负数：当前显示模块背光亮度
 * @returns 负数：函数执行失败
 * @returns -ENODEV： 背光设备不存在
 */
/* ------------------------------------------------------------------*/
extern int hal_display_get_backlight_brightness(void);

/* ------------------------------------------------------------------*/
/**
 * @brief 显示模块设置背光亮度
 *
 * @param[in] brightness : 背光亮度百分比，最小值为1, 最大值为100,超过
 *                          该范围认为是参数不可用,
 *                          FIXME: 为什么不从0开始？
 *                              因为从用户体验来说，当背光亮度调节到最低
 *                              时，其显示屏的亮度还有一个最低亮度，此时
 *                              显示屏不会黑屏，只是亮度比较低
 *
 * @returns 0 : 函数执行成功
 * @returns others: 函数执行失败
 * @returns -ENODEV : 背光设备不存在
 */
/* ------------------------------------------------------------------*/
extern int hal_display_set_backlight_brightness(int brightness);


/* ------------------------------------------------------------------*/
/** @addtogroup sound     Sound
 *  @ingroup api
 *
 * Sound module
 *
 *
 *  @addtogroup beep    Beep
 *  @ingroup sound
 *
 * 蜂鸣控制相关的操作方法
 *
 *
 * @{
 */
/* ------------------------------------------------------------------*/

/* ------------------------------------------------------------------*/
/**
 * @brief 设置蜂鸣时间
 *
 * @param[in] time_ms : 需要设置的蜂鸣时间，单位为ms
 *
 * @returns 0       : 设置蜂鸣时间成功
 * @returns others  : 设置蜂鸣时间失败
 */
/* ------------------------------------------------------------------*/
extern int hal_set_bee_time(size_t time_ms);

/** @} */






/* ------------------------------------------------------------------*/
/** @addtogroup clock     Clock
 *  @ingroup api
 *
 * Clock module
 *
 *
 *  @addtogroup rtc     RTC
 *  @ingroup clock
 *
 *  RTC 相关的操作方法
 *
 * @note
 *      The struct tm have accurate to the second level
 *
 * @{
 */
/* ------------------------------------------------------------------*/

/* ------------------------------------------------------------------*/
/**
 * @brief 设置RTC时间
 *
 * @param[in] time : 需设置的时间, struct tm
 *
 * @returns 0       : 设置RTC时间成功
 * @returns others  : 设置RTC时间失败
 */
/* ------------------------------------------------------------------*/
extern int hal_set_rtc_time(const struct tm *time);


/* ------------------------------------------------------------------*/
/**
 * @brief 获取RTC时间
 *
 * @param[in] time : 获取的时间结果, struct tm
 *
 * @returns 0       : 获取RTC时间成功
 * @returns others  : 获取RTC时间失败
 */
/* ------------------------------------------------------------------*/
extern int hal_get_rtc_time(struct tm *time);


/* ------------------------------------------------------------------*/
/**
 * @brief 获取rtc当前状态，正常或者是异常
 *
 * @returns 0       : 当前RTC状态正常
 * @returns others  : 当前RTC状态异常
 */
/* ------------------------------------------------------------------*/
extern int hal_get_rtc_status(void);

/** @} */


/* ------------------------------------------------------------------*/
/** @addtogroup usb     USB
 *  @ingroup api
 *
 * USB模块相关操作方法
 *
 * @{
 */
/* ------------------------------------------------------------------*/

enum
{
    HAL_USB_OTG_MASTER,         /// USB OTG 主机模式
    HAL_USB_OTG_DEVICE,         /// USB OTG 从机模式
    HAL_USB_OTG_UNKNOWN,        /// USB OTG 无效模式
};
/* ------------------------------------------------------------------*/
/**
 * @brief 获取usb当前模式
 *
 * @return  \ref HAL_USB_OTG_MASTER     : host  主口模式
 * @return  \ref HAL_USB_OTG_DEVICE     : device 从口模式
 * @return  \ref HAL_USB_OTG_UNKNOWN    : null 无效模式
 * @return  others                      : 获取USB模式失败
 */
/* ------------------------------------------------------------------*/
extern int hal_get_usb_mode(void);

/* ------------------------------------------------------------------*/
/**
 * @brief 设置usb当前模式
 *
 * @param[in] mode  :   模式: \ref HAL_USB_OTG_MASTER , \ref HAL_USB_OTG_DEVICE,
 *                            \ref HAL_USB_OTG_UNKNOWN
 * @returns 0       :   模式设置成功
 * @returns others  :   函数执行失败
 */
/* ------------------------------------------------------------------*/
extern int hal_set_usb_mode(int mode);

/** @} */

#ifdef __cplusplus
    }
#endif


#endif //end of the file


```

**根据需求及头文件的分析，声明相应的native方法**


***创建一个JniUtil.java jni 操作的工具类JniUtil.java***

```
package com.mcgs.srx.Util;

public class JniUtil {

    private static JniUtil mInstance;

    public static JniUtil getInstance(){
        if(mInstance == null){
            mInstance = new JniUtil();
        }
        return mInstance;
    }

    static {
        System.loadLibrary("mcgs_jni");
    }

    //USB mode set
    public native int getUsbMode();

    public native void setUsbMode(int mode);

    //Bee mode set
    public native int setBeeTime(int time);

    //LCD mode set
    public native int getLcdStatus();

    public native int setLcdStatus(int status);

    public native int getBacklightbrightness();

    public native int setBacklightbrightness(int value);


    //RTC set
    public native int getRtcStatus();

    public native int getRtcTime();

    public native int setRtcTime(int time);

}

```


如上的代码，方法都使用关键字native，这说明这两个方法并不是用Java代码实现的，它们来源于本地库的实现，当Java代码中执行Native的代码的时候，首先是通过一定的方法来找到这些native方法。而注册native函数的具体方法不同，会导致系统在运行时采用不同的方式来寻找这些native方法


## 2.2  通过Native 方法生成jni 头文件

首先,你需要了解Javah命令的用法:<br>
命令的作用是 根据包名类名,生成对应的.h文件<br>

在Terminal中将代码路径切换java 目录<br>
**使用javah -d ../jni  包名+类名**  就可以生成头文件。

如：
```
D:\CodeWorkSpace\SRX_Demo\mcgs\src\main\java>javah  -d  ../jni  com.mcgs.srx.Util.JniUtil
执行命令之后就好在jni 目录下生成头文件com_mcgs_srx_Util_JniUtil.h。
```

***头文件的内容如下：***

```
/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_mcgs_srx_Util_JniUtil */

#ifndef _Included_com_mcgs_srx_Util_JniUtil
#define _Included_com_mcgs_srx_Util_JniUtil
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_mcgs_srx_Util_JniUtil
 * Method:    getUsbMode
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_mcgs_srx_Util_JniUtil_getUsbMode
  (JNIEnv *, jobject);

/*
 * Class:     com_mcgs_srx_Util_JniUtil
 * Method:    setUsbMode
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_mcgs_srx_Util_JniUtil_setUsbMode
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_mcgs_srx_Util_JniUtil
 * Method:    setBeeTime
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_mcgs_srx_Util_JniUtil_setBeeTime
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_mcgs_srx_Util_JniUtil
 * Method:    getLcdStatus
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_mcgs_srx_Util_JniUtil_getLcdStatus
  (JNIEnv *, jobject);

/*
 * Class:     com_mcgs_srx_Util_JniUtil
 * Method:    setLcdStatus
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_mcgs_srx_Util_JniUtil_setLcdStatus
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_mcgs_srx_Util_JniUtil
 * Method:    getBacklightbrightness
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_mcgs_srx_Util_JniUtil_getBacklightbrightness
  (JNIEnv *, jobject);

/*
 * Class:     com_mcgs_srx_Util_JniUtil
 * Method:    setBacklightbrightness
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_mcgs_srx_Util_JniUtil_setBacklightbrightness
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_mcgs_srx_Util_JniUtil
 * Method:    getRtcStatus
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_mcgs_srx_Util_JniUtil_getRtcStatus
  (JNIEnv *, jobject);

/*
 * Class:     com_mcgs_srx_Util_JniUtil
 * Method:    getRtcTime
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_mcgs_srx_Util_JniUtil_getRtcTime
  (JNIEnv *, jobject);

/*
 * Class:     com_mcgs_srx_Util_JniUtil
 * Method:    setRtcTime
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_mcgs_srx_Util_JniUtil_setRtcTime
  (JNIEnv *, jobject, jint);

#ifdef __cplusplus
}
#endif
#endif

```

## 2.3  实现.h头文件中声明的函数
实现头文件中声明的函数，就是比较简单了，不考虑其他的问题，如何实现呢？<br>

如; 以下是头文件的中声明的一个函数

```
/*
 * Class:     com_mcgs_srx_Util_JniUtil
 * Method:    setUsbMode
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_mcgs_srx_Util_JniUtil_setUsbMode
  (JNIEnv *, jobject, jint);
  
```

**在同目录下新建C文件，mcgs_jni.c，对上面列子的实现如下:**
```

/*
 * Class:     com_mcgs_srx_Util_JniUtil
 * Method:    setUsbMode
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_mcgs_srx_Util_JniUtil_setUsbMode
        (JNIEnv * env, jobject jobject1, jint jint1){
           //TODO
        LOGV("Java_cn_com_srx_mcgs_1jni_JniUtil_setUsbMode ");
}


```

***以上就是对头文件中的一个函数的实现。***

#### 2.4 ndk-build 编译jni 库

**Android.mk简介：**

Android.mk在jni目录下，用于描述构建系统的源文件以及 shared libraries <br>

**文件格式如下：**<br>

以LOCAL_PATH变量开始 LOCAL_PATH := $(call my-dir)<br>
紧接着是CLEAR_VARS变量 include $(CLEAR_VARS)<br>
接下来LOCAL_MODULE变量，定义来将要输出的so文件的名，默认情况下，输出的so为 lib+LOCAL_MODULE变量值+.so，如果变量值前面有了lib，就不会加了，或者变量值后面有.xxx，也会去掉。<br>
接下来是LOCAL_SRC_FILES变量，声明我们的原文件路径，如LOCAL_SRC_FILES := hello-jni.c<br>
最后一行是帮助构建系统联系在一起的。include $(BUILD_SHARED_LIBRARY)<br>

如：
```
LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := hello-jni

LOCAL_SRC_FILES := hello.cpp

include $(BUILD_SHARED_LIBRARY)

```

Android.mk文件用来告知NDK Build 系统关于Source的信息。 Android.mk将是GNU Makefile的一部分，且将被Build System解析一次或多次

**我们可以在项目的中建立Android.mk**
```
# Copyright (C) 2009 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := mcgs_jni
LOCAL_SRC_FILES := mcgs_jni.c
LOCAL_LDLIBS += -llog

include $(BUILD_SHARED_LIBRARY)


```

**通过ndk就可以编译libmcgs_jni.so，此时可以将代码切换到jni目录下执行ndk-build**

```
D:\CodeWorkSpace\SRX_Demo\mcgs\src\main\jni>   ndk-build
[armeabi] Compile thumb  : mcgs_jni <= mcgs_jni.c
[armeabi] SharedLibrary  : libmcgs_jni.so

libmcgs_jni.so  就生成了。

```

## 2.5  在Java 加载jni 库

它们都可以用来装载库文件，不论是JNI库文件还是非JNI库文件。在任何本地方法被调用之前必须先用这个两个方法之一把相应的JNI库文件装载。
其实JDK提供给用户了两个方法用于载入文件，一个是System.load(String filename)方法，另外一个是System.loadLibrary(String libname)方法

System.loadLibrary<br>
System.loadLibrary 参数为库文件名，不包含库文件的扩展名。<br>
System.loadLibrary ("TestJNI"); //加载Windows下的TestJNI.dll本地库<br>
System.loadLibrary ("TestJNI"); //加载Linux下的libTestJNI.so本地库<br>

```
static {
    System.loadLibrary("mcgs_jni");
}

```

在任何本地方法被调用之前必须先用这个两个方法之一把相应的JNI库文件装载。JNI是Java
Native Interface的缩写,它实现了Java和其他语言的通信。

**此时在module级的build.gradede 的android标题添加**

```
sourceSets.main {
    jniLibs.srcDirs = ['src/main/libs']
    jni.srcDirs = []
}

```
**编译运行apk 就可以运行即可。**


# 三、 JNI调用底层硬件库（libmcgs-hal.so）

从以上的步骤实现了Java---JNI，但是我们的最终的目的是Java-->JNI-->底层硬件库（libmcgs-hal.so）
所以现在就要实现JNI-->底层硬件库（libmcgs-hal.so），就要实现JNI链接三方库。

**修改Android.mk**

```
# Copyright (C) 2009 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
LOCAL_PATH := $(call my-dir)


LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := libmcgs-hal
LOCAL_SRC_FILES := libmcgs-hal.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)

LOCAL_MODULE    := mcgs_jni
LOCAL_SRC_FILES := mcgs_jni.c
LOCAL_SHARED_LIBRARIES := libmcgs-hal
LOCAL_LDLIBS += -llog

include $(BUILD_SHARED_LIBRARY)

```

**把libmcgs-hal.so放在Android.mk 同级目录中即可**

**通过ndk就可以编译libmcgs_jni.so，此时可以将代码切换到jni目录下执行ndk-build**
```
D:\CodeWorkSpace\SRX_Demo\mcgs\src\main\jni>   ndk-build
[armeabi] Install        : libmcgs-hal.so => libs/armeabi/libmcgs-hal.so
[armeabi] Compile thumb  : mcgs_jni <= mcgs_jni.c
[armeabi] SharedLibrary  : libmcgs_jni.so
[armeabi] Install        : libmcgs_jni.so => libs/armeabi/libmcgs_jni.so

libmcgs-hal.so ，libmcgs_jni.so  就生成了。

在对应修改头文件的实现即可。

```
**mcgs-jni.c**

```
/
// Created by srx on 2018/9/28.
//

#include "com_mcgs_srx_Util_JniUtil.h"
#include "hal-api.h"
#include <string.h>
#include <jni.h>
#include <stdio.h>
#include<stdlib.h>

#include <android/log.h>
#define TAG "Native"
#define LOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, TAG, __VA_ARGS__)

/*
 * Class:     com_mcgs_srx_Util_JniUtil
 * Method:    getUsbMode
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_mcgs_srx_Util_JniUtil_getUsbMode
        (JNIEnv * env, jobject jobject1){
    LOGV("Java_cn_com_srx_mcgs_1jni_JniUtil_getUsbMode ");
    return 0;
}


/*
 * Class:     com_mcgs_srx_Util_JniUtil
 * Method:    setUsbMode
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_mcgs_srx_Util_JniUtil_setUsbMode
        (JNIEnv * env, jobject jobject1, jint jint1){
        int ret = -1;
        if(0 == jint1){
             ret = hal_set_usb_mode(HAL_USB_OTG_MASTER);
            // LOGV( "jint1 HAL_USB_OTG_MASTER = %d\n", jint1 );
        }else if(1 == jint1){
             ret = hal_set_usb_mode(HAL_USB_OTG_DEVICE);
          //   LOGV( "jint1 HAL_USB_OTG_DEVICE = %d\n", jint1 );
        }else if(2 == jint1){
             ret = hal_set_usb_mode(HAL_USB_OTG_UNKNOWN);
          //  LOGV( "jint1 HAL_USB_OTG_UNKNOWN = %d\n", jint1 );
        }
            LOGV( "ret = %d\n", ret );
}

/*
 * Class:     com_mcgs_srx_Util_JniUtil
 * Method:    setBeeTime
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_mcgs_srx_Util_JniUtil_setBeeTime
        (JNIEnv *env, jobject jobject1, jint jint1){

     LOGV( "setBeeTime = %d\n", jint1 );
     int ret = hal_set_bee_time(jint1);
     LOGV( "ret = %d\n", ret );
     return ret;
}


/*
 * Class:     com_mcgs_srx_Util_JniUtil
 * Method:    getLcdStatus
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_mcgs_srx_Util_JniUtil_getLcdStatus
        (JNIEnv * env, jobject jobject1){

      int ret =  hal_get_lcd_status();
      LOGV( "ret = %d\n", ret );
      LOGV( "HAL_DISPLAY_BACKLIGHT_OPEN = %d\n", HAL_DISPLAY_BACKLIGHT_OPEN);
      LOGV( "HAL_DISPLAY_BACKLIGHT_CLOSE = %d\n", HAL_DISPLAY_BACKLIGHT_CLOSE);
      if(HAL_DISPLAY_BACKLIGHT_OPEN == ret){
        return 0;
      }else if(HAL_DISPLAY_BACKLIGHT_CLOSE == ret){
      return 1;
      }else{
       return -1;
      }
}


/*
 * Class:     com_mcgs_srx_Util_JniUtil
 * Method:    setLcdStatus
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_mcgs_srx_Util_JniUtil_setLcdStatus
        (JNIEnv * env, jobject jobject1, jint jint1){
  LOGV( "setLcdStatus = %d\n", jint1 );
  int ret = -1;

  if(0 == jint1){
  // 0 ----HAL_DISPLAY_BACKLIGHT_OPEN
  ret = hal_set_lcd_status(HAL_DISPLAY_BACKLIGHT_OPEN);
  }else if(1 == jint1){
 // 1 ----HAL_DISPLAY_BACKLIGHT_CLOSE
 ret = hal_set_lcd_status(HAL_DISPLAY_BACKLIGHT_CLOSE);
  }
    LOGV( "ret = %d\n", ret );
    return ret;
}

/*
 * Class:     com_mcgs_srx_Util_JniUtil
 * Method:    getBacklightbrightness
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_mcgs_srx_Util_JniUtil_getBacklightbrightness
        (JNIEnv * env, jobject jobject1){

    LOGV("Java_cn_com_srx_mcgs_1jni_JniUtil_getBacklightbrightness ");
    return 0;
}

/*
 * Class:     com_mcgs_srx_Util_JniUtil
 * Method:    setBacklightbrightness
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_mcgs_srx_Util_JniUtil_setBacklightbrightness
        (JNIEnv * env, jobject jobject1, jint jint1){

         LOGV( "setBacklightbrightness = %d\n", jint1 );
       int ret = hal_display_set_backlight_brightness(jint1);
       LOGV( "ret = %d\n", ret );
       return ret;

}

struct tm * datetotm(int t){
  struct tm *ptr;
  time_t lt;
  lt = t;
  ptr=localtime(&lt);
  return ptr;
}

/*
 * Class:     com_mcgs_srx_Util_JniUtil
 * Method:    getRtcStatus
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_mcgs_srx_Util_JniUtil_getRtcStatus
        (JNIEnv * env, jobject jobject1){

    LOGV("Java_cn_com_srx_mcgs_1jni_JniUtil_getRtcStatus ");
    return 0;
}


/*
 * Class:     com_mcgs_srx_Util_JniUtil
 * Method:    getRtcTime
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_mcgs_srx_Util_JniUtil_getRtcTime
        (JNIEnv * env , jobject jobject1){

    LOGV("Java_cn_com_srx_mcgs_1jni_JniUtil_getRtcTime ");
    return 0;
}


/*
 * Class:     com_mcgs_srx_Util_JniUtil
 * Method:    setRtcTime
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_mcgs_srx_Util_JniUtil_setRtcTime
        (JNIEnv * env, jobject jobject1, jint jint1){
      struct tm *time;
      LOGV( "setRtcTime = %d\n", jint1 );
      time = datetotm(jint1);
      int ret = hal_set_rtc_time(time);
      LOGV( "ret = %d\n", ret );
      return ret;
}
```

**编译运行apk 即可验证。**


# 四、总结
**整个实现的过程Java-->JNI-->底层硬件库（libmcgs-hal.so），按照上面的步骤即可调试通。**

**USB模式设置伪代码：**

**APP:**
```
mJniUtil.setUsbMode(mode);

```

**JNI:**
```
/*
 * Class:     com_mcgs_srx_Util_JniUtil
 * Method:    setUsbMode
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_mcgs_srx_Util_JniUtil_setUsbMode
        (JNIEnv * env, jobject jobject1, jint jint1){
        int ret = -1;
        if(0 == jint1){
             ret = hal_set_usb_mode(HAL_USB_OTG_MASTER);
            // LOGV( "jint1 HAL_USB_OTG_MASTER = %d\n", jint1 );
        }else if(1 == jint1){
             ret = hal_set_usb_mode(HAL_USB_OTG_DEVICE);
          //   LOGV( "jint1 HAL_USB_OTG_DEVICE = %d\n", jint1 );
        }else if(2 == jint1){
             ret = hal_set_usb_mode(HAL_USB_OTG_UNKNOWN);
          //  LOGV( "jint1 HAL_USB_OTG_UNKNOWN = %d\n", jint1 );
        }
            LOGV( "ret = %d\n", ret );
}


```
**libmcgs-hal.so**
```
hal_set_usb_mode(int mode)

```

以上就完成了USB模式设置。

**其他的几个模式可以通过参考USB模式设置方式及APP source code。**

