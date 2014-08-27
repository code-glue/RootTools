package com.stericson.RootTools.lib;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Bridge between Java and native code.
 */
public class RootToolsLib
{
    /** http://man7.org/linux/man-pages/man3/strerror.3.html */
    @NonNull
    static native String strerror(int errorCode);

    /** http://man7.org/linux/man-pages/man2/stat.2.html */
    @Nullable
    static native StructStat stat(String path, boolean followLinks, boolean throwOnError) throws ErrnoException;

    /** http://man7.org/linux/man-pages/man3/getpwnam.3.html */
    @Nullable
    static native StructPasswd getpwuid(int userId) throws ErrnoException;

    /** http://man7.org/linux/man-pages/man3/realpath.3.html */
    @Nullable
    static native String realpath(String path, boolean throwOnError) throws ErrnoException;

    @NonNull
    public static String getErrorMessage(int errorCode)
    {
        return RootToolsLib.strerror(errorCode);
    }

    @Nullable
    public static FileStat getFileStat(@NonNull String path, boolean followLinks)
    {
        try
        {
            StructStat structStat = RootToolsLib.stat(path, followLinks, false);
            return structStat == null ? null : new FileStat(path, structStat);
        }
        catch (ErrnoException ignore) { return null; }
    }

    @Nullable
    public static FileStat getFileStat(@NonNull String path)
    {
        return RootToolsLib.getFileStat(path, true);
    }

    @NonNull
    public static FileStat getFileStatAssert(@NonNull String path, boolean followLinks) throws ErrnoException
    {
        return new FileStat(path, RootToolsLib.stat(path, followLinks, true));
    }

    @NonNull
    public static FileStat getFileStatAssert(@NonNull String path) throws ErrnoException
    {
        return RootToolsLib.getFileStatAssert(path, true);
    }

    @Nullable
    public static UserInfo getUserInfo(int userId)
    {
        try
        {
            StructPasswd passwd = RootToolsLib.getpwuid(userId);
            return passwd == null ? null : new UserInfo(passwd);
        }
        catch (ErrnoException ignore) { return null; }
    }

    @NonNull
    public static UserInfo getUserInfoAssert(int userId) throws ErrnoException
    {
        return new UserInfo(RootToolsLib.getpwuid(userId));
    }

    @Nullable
    public static String getUserName(int userId)
    {
        UserInfo userInfo = RootToolsLib.getUserInfo(userId);
        return userInfo == null ? null : userInfo.name;
    }

    @NonNull
    public static String getUserNameAssert(int userId) throws ErrnoException
    {
        return RootToolsLib.getUserInfo(userId).name;
    }

    @Nullable
    public static String getRealPath(String path)
    {
        try
        {
            String realPath = RootToolsLib.realpath(path, false);
            return realPath == null ? null : realPath;
        }
        catch (ErrnoException ignore) { return null; }
    }

    @NonNull
    public static String getRealPathAssert(String path) throws ErrnoException
    {
        return RootToolsLib.realpath(path, true);
    }

    static
    {
        System.loadLibrary("roottools");
    }
}
