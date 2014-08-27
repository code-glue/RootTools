/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

#define LOG_TAG "RootToolsLib"

#include "nativehelper/JniConstants.h"
#include "nativehelper/ScopedLocalRef.h"
#include "nativehelper/ScopedUtfChars.h"
#include "nativehelper/UniquePtr.h"
#include "nativehelper/ALog-priv.h"

#include <jni.h>
#include <errno.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <pwd.h>
#include <unistd.h>


#define TO_JAVA_STRING(NAME, EXP) \
        jstring NAME = env->NewStringUTF(EXP); \
        if (NAME == NULL) return NULL;


static void throwException(JNIEnv* env, jclass exceptionClass, jmethodID ctor3, jmethodID ctor2, const char* functionName, int error)
{
    jthrowable cause = NULL;
    if (env->ExceptionCheck())
    {
        cause = env->ExceptionOccurred();
        env->ExceptionClear();
    }

    ScopedLocalRef<jstring> detailMessage(env, env->NewStringUTF(functionName));
    if (detailMessage.get() == NULL)
    {
        // Not really much we can do here. We're probably dead in the water,
        // but let's try to stumble on...
        env->ExceptionClear();
    }

    jobject exception = cause == NULL
        ? env->NewObject(exceptionClass, ctor2, detailMessage.get(), error)
        : env->NewObject(exceptionClass, ctor3, detailMessage.get(), error, cause);

    env->Throw(reinterpret_cast<jthrowable>(exception));
}

static void throwErrnoException(JNIEnv* env, const char* functionName)
{
    int error = errno;
    static jmethodID ctor3 = env->GetMethodID(JniConstants::errnoExceptionClass, "<init>", "(Ljava/lang/String;ILjava/lang/Throwable;)V");
    static jmethodID ctor2 = env->GetMethodID(JniConstants::errnoExceptionClass, "<init>", "(Ljava/lang/String;I)V");
    throwException(env, JniConstants::errnoExceptionClass, ctor3, ctor2, functionName, error);
}

static jobject makeStructStat(JNIEnv* env, const struct stat& structStat)
{
    static jmethodID ctor = env->GetMethodID(JniConstants::structStatClass, "<init>", "(JJIJIIJJJJJJJ)V");

    return env->NewObject(JniConstants::structStatClass, ctor,
           static_cast<jlong>(structStat.st_dev), static_cast<jlong>(structStat.st_ino),
           static_cast<jint>(structStat.st_mode), static_cast<jlong>(structStat.st_nlink),
           static_cast<jint>(structStat.st_uid), static_cast<jint>(structStat.st_gid),
           static_cast<jlong>(structStat.st_rdev), static_cast<jlong>(structStat.st_size),
           static_cast<jlong>(structStat.st_atime), static_cast<jlong>(structStat.st_mtime),
           static_cast<jlong>(structStat.st_ctime), static_cast<jlong>(structStat.st_blksize),
           static_cast<jlong>(structStat.st_blocks));
}

static jobject makeStructPasswd(JNIEnv* env, const struct passwd& structPasswd)
{
    TO_JAVA_STRING(pw_name, structPasswd.pw_name);
    TO_JAVA_STRING(pw_dir, structPasswd.pw_dir);
    TO_JAVA_STRING(pw_shell, structPasswd.pw_shell);

    static jmethodID ctor = env->GetMethodID(JniConstants::structPasswdClass, "<init>", "(Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;)V");
    return env->NewObject(JniConstants::structPasswdClass, ctor, pw_name,
           static_cast<jint>(structPasswd.pw_uid), static_cast<jint>(structPasswd.pw_gid), pw_dir, pw_shell);
}

static jstring roottools_strerror(JNIEnv* env, jint errorCode)
{
    char buffer[256];
    return env->NewStringUTF(jniStrError(errorCode, buffer, 256));
}

static jobject roottools_stat(JNIEnv* env, jstring javaPath, jboolean followLinks, jboolean throwOnError)
{
    ScopedUtfChars path(env, javaPath);
    if (path.c_str() == NULL)
    {
        return NULL;
    }

    struct stat structStat;
    int result = followLinks
        ? TEMP_FAILURE_RETRY(stat(path.c_str(), &structStat))
        : TEMP_FAILURE_RETRY(lstat(path.c_str(), &structStat));

    if (result == -1)
    {
        ALOGE("%s failed: errno %d '%s'", followLinks ? "stat" : "lstat", errno, path.c_str());
        if (throwOnError)
        {
            throwErrnoException(env, followLinks ? "stat" : "lstat");
        }
        return NULL;
    }

    return makeStructStat(env, structStat);
}

jobject roottools_getpwuid(JNIEnv* env, jint userId, jboolean throwOnError)
{
    errno = 0;
    struct passwd* pw = getpwuid(userId);
    if (pw == NULL)
    {
        ALOGE("getpwuid failed: errno %d '%d'", errno, userId);
        if (throwOnError)
        {
            throwErrnoException(env, "getpwuid");
        }
        return NULL;
    }

    return makeStructPasswd(env, *pw);
}

jstring roottools_realpath(JNIEnv* env, jstring javaPath, jboolean throwOnError)
{
    ScopedUtfChars path(env, javaPath);
    if (path.c_str() == NULL)
    {
        return NULL;
    }

    char buffer[PATH_MAX];
    if (realpath(path.c_str(), buffer) == NULL)
    {
        ALOGE("realpath failed: errno %d '%s'", errno, path.c_str());
        if (throwOnError)
        {
            throwErrnoException(env, "realpath");
        }
        return NULL;
    }
    return env->NewStringUTF(buffer);
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_stericson_RootTools_lib_RootToolsLib_strerror(JNIEnv* env, jclass clazz, jint errorCode)
{
    return roottools_strerror(env, errorCode);
}

extern "C" JNIEXPORT jobject JNICALL
Java_com_stericson_RootTools_lib_RootToolsLib_stat(JNIEnv* env, jclass clazz, jstring javaPath, jboolean followLinks, jboolean throwOnError)
{
    return roottools_stat(env, javaPath, followLinks, throwOnError);
}

extern "C" JNIEXPORT jobject JNICALL
Java_com_stericson_RootTools_lib_RootToolsLib_getpwuid(JNIEnv* env, jclass clazz, jint userId, jboolean throwOnError)
{
    return roottools_getpwuid(env, userId, throwOnError);
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_stericson_RootTools_lib_RootToolsLib_realpath(JNIEnv* env, jclass clazz, jstring javaPath, jboolean throwOnError)
{
    return roottools_realpath(env, javaPath, throwOnError);
}

jint JNI_OnLoad(JavaVM* vm, void* reserved)
{
    JNIEnv* env;
    if (vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6) != JNI_OK)
    {
        return -1;
    }

    // Get jclass with env->FindClass.
    // Register methods with env->RegisterNatives.

    JniConstants::init(env);
    return JNI_VERSION_1_6;
}
