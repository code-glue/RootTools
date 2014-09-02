/*
 * Copyright (C) 2010 The Android Open Source Project
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
 */

#define LOG_TAG "JniConstants"

#include "ALog-priv.h"
#include "JniConstants.h"
#include "ScopedLocalRef.h"

#include <stdlib.h>

jclass JniConstants::errnoExceptionClass;
jclass JniConstants::fileDescriptorClass;
jclass JniConstants::referenceClass;
jclass JniConstants::structStatClass;
jclass JniConstants::structPasswdClass;

static jclass findClass(JNIEnv* env, const char* name) {
    jclass result = NULL;

    ALOGD("JniConstants::findClass %s", name);
    ScopedLocalRef<jclass> localClass(env, env->FindClass(name));
    if (localClass.get() != NULL)
    {
        result = reinterpret_cast<jclass>(env->NewGlobalRef(localClass.get()));
    }

    //try
    //{
    //    ScopedLocalRef<jclass> localClass(env, env->FindClass(name));
    //    jclass result = reinterpret_cast<jclass>(env->NewGlobalRef(localClass.get()));
    //}
    //catch(std::exception ignore)
    //{
    //}
    if (result == NULL) {
        ALOGE("failed to find class '%s'", name);
        //abort();
        return NULL;
    }
    return result;
}

void JniConstants::init(JNIEnv* env) {
    errnoExceptionClass = findClass(env, "com/stericson/RootTools/ErrnoException");
    fileDescriptorClass = findClass(env, "java/io/FileDescriptor");
    referenceClass = findClass(env, "java/lang/ref/Reference");
    structStatClass = findClass(env, "com/stericson/RootTools/StructStat");
    structPasswdClass = findClass(env, "com/stericson/RootTools/StructPasswd");
}
