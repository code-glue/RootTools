/*
 * Copyright (C) 2011 The Android Open Source Project
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

package com.stericson.RootTools.lib;

/*
 * A checked exception thrown when native methods fail. This exception contains the native
 * errno value should sophisticated callers need to adjust their behavior based on the exact failure.
 * NOTE: It does NOT retrieve the names of constants from android.system.OsConstants as that class
 * is difficult (and not worth it) to reproduce here.
 *
 * Modified from https://android.googlesource.com/platform/libcore/+/master/luni/src/main/java/android/system/ErrnoException.java
 *
 * @hide
 */
public final class ErrnoException extends Exception
{
    private static final long serialVersionUID = 3039278086810425112L;
    /*
     * The errno value as defined in {@code &lt;errno.h&gt;}
     */
    public final int errno;
    private final String functionName;

    /**
     * Constructs an instance with the given function name and errno value.
     */
    public ErrnoException(String functionName, int errno)
    {
        this.functionName = functionName;
        this.errno = errno;
    }

    /**
     * Constructs an instance with the given function name, errno value, and cause.
     */
    public ErrnoException(String functionName, int errno, Throwable cause)
    {
        super(cause);
        this.functionName = functionName;
        this.errno = errno;
    }

    /**
     * Converts the stashed function name and errno value to a human-readable string.
     * We do this here rather than in the constructor so that callers only pay for
     * this if they need it.
     */
    @Override
    public String getMessage()
    {
        return String.format("%s failed: errno %d (%s)", functionName, errno, RootToolsLib.strerror(errno));
    }
}
