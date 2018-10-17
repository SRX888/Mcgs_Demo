//
// Created by srx on 2018/9/28.
//

#include "com_mcgs_srx_Util_JniUtil.h"
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

           int  ret = hal_set_usb_mode(jint1);
            LOGV( "jint1 ret = %d\n", ret );
            LOGV( "jint1 setUsbMode = %d\n", jint1 );
    LOGV("Java_cn_com_srx_mcgs_1jni_JniUtil_setUsbMode ");
}

/*
 * Class:     com_mcgs_srx_Util_JniUtil
 * Method:    setBeeTime
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_mcgs_srx_Util_JniUtil_setBeeTime
        (JNIEnv *env, jobject jobject1, jint jint1){

    LOGV("Java_cn_com_srx_mcgs_1jni_JniUtil_setBeeTime ");
    return 0;
}


/*
 * Class:     com_mcgs_srx_Util_JniUtil
 * Method:    getLcdStatus
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_mcgs_srx_Util_JniUtil_getLcdStatus
        (JNIEnv * env, jobject jobject1){

    LOGV("Java_cn_com_srx_mcgs_1jni_JniUtil_getLcdStatus ");
    return 0;
}


/*
 * Class:     com_mcgs_srx_Util_JniUtil
 * Method:    setLcdStatus
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_mcgs_srx_Util_JniUtil_setLcdStatus
        (JNIEnv * env, jobject jobject1, jint jint1){

    LOGV("Java_cn_com_srx_mcgs_1jni_JniUtil_setLcdStatus ");
    return 0;
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

    LOGV("Java_cn_com_srx_mcgs_1jni_JniUtil_setBacklightbrightness ");
    return 0;

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

    LOGV("Java_cn_com_srx_mcgs_1jni_JniUtil_setRtcTime ");
    return 0;
}