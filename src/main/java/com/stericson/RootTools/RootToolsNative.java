package com.stericson.RootTools;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Bridge between Java and native code.
 */
class RootToolsNative
{
    RootToolsNative() {}

    /** http://man7.org/linux/man-pages/man3/strerror.3.html */
    @NonNull
    static native String strerror(int errorCode);

    /** http://man7.org/linux/man-pages/man2/stat.2.html */
    @Nullable
    static native StructStat stat(@NonNull String path, boolean followLinks, boolean throwOnError) throws ErrnoException;

    /** http://man7.org/linux/man-pages/man3/getpwnam.3.html */
    @Nullable
    static native StructPasswd getpwuid(int userId) throws ErrnoException;

    /** http://man7.org/linux/man-pages/man2/getuid.2.html */
    static native int geteuid();

    /** http://man7.org/linux/man-pages/man3/realpath.3.html */
    @Nullable
    static native String realpath(@NonNull String path, boolean throwOnError) throws ErrnoException;

    /** http://man7.org/linux/man-pages/man2/settimeofday.2.html */
    @Nullable
    static native boolean settimeofday(int seconds, boolean throwOnError) throws ErrnoException;

    static
    {
        System.loadLibrary("roottools");
    }
}
