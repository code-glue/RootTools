package com.stericson.RootTools;

import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RootToolsNativeTest extends TestCase
{
    static String getStats(String path, boolean followLinks) throws IOException
    {
        String command = String.format("stat -t%s %s", followLinks ? "L" : "", path);
        Process process = Runtime.getRuntime().exec(command);
        return new BufferedReader(new InputStreamReader(process.getInputStream())).readLine();
    }

    static String getLsOutput(String path) throws IOException
    {
        String command = String.format("ls -l %s", path);
        Process process = Runtime.getRuntime().exec(command);
        return new BufferedReader(new InputStreamReader(process.getInputStream())).readLine();
    }

    static String[] getFiles(String absPathDirectory) throws IOException
    {
        //String command = String.format("su -c 'ls %s'", absPathDirectory);
        String command = String.format("ls %s", absPathDirectory);
        Process process = Runtime.getRuntime().exec(command);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        List<String> lines = new ArrayList<String>();
        String line;
        while (null != (line = reader.readLine()))
        {
            lines.add(new File(absPathDirectory, line).getPath());
        }
        return lines.toArray(new String[lines.size()]);
    }

    public void testStat() throws Exception
    {
        // These directories should cover all the file types except maybe named pipes (fifo), including:
        // char device, block device, sockets, symlinks, directories, and regular files
        String[] directories = {
            "/dev",
            "/dev/block",
            "/dev/socket",
            "/system/bin"
        };

        for (String directory : directories)
        {
            String[] filePaths = getFiles(directory);
            assertNotNull(filePaths);
            assertTrue(filePaths.length > 0);

            for (String path : filePaths)
            {
                String stats1 = RootToolsNativeTest.getStats(path, false);
                StructStatTest.assertEquals(stats1, RootToolsNative.stat(path, false, false));

                String stats2 = RootToolsNativeTest.getStats(path, true);
                StructStatTest.assertEquals(stats2, RootToolsNative.stat(path, true, false));
            }
        }

        String stats3 = RootToolsNativeTest.getStats("", false);
        StructStatTest.assertEquals(stats3, RootToolsNative.stat("", false, false));

        String stats4 = RootToolsNativeTest.getStats("", true);
        StructStatTest.assertEquals(stats4, RootToolsNative.stat("", true, false));

        try
        {
            RootToolsNative.stat("", false, true);
            throw new AssertionError();
        }
        catch (ErrnoException e)
        {
            assertEquals("stat failed: errno 2 (No such file or directory)", e.getMessage());
        }

        try
        {
            RootToolsNative.stat("", true, true);
            throw new AssertionError();
        }
        catch (ErrnoException e)
        {
            assertEquals("lstat failed: errno 2 (No such file or directory)", e.getMessage());
        }
    }

    public void testGetFileStat() throws Exception
    {
        // These directories should cover all the file types except maybe named pipes (fifo), including:
        // char device, block device, sockets, symlinks, directories, and regular files
        String[] directories = {
            "/dev",
            "/dev/block",
            "/dev/socket",
            "/system/bin"
        };

        for (String directory : directories)
        {
            String[] filePaths = getFiles(directory);
            assertNotNull(filePaths);
            assertTrue(filePaths.length > 0);

            for (String path : filePaths)
            {
                String stats1 = RootToolsNativeTest.getStats(path, false);
                FileStatTest.assertEquals(stats1, RootTools.getFileStat(path, false));

                String stats2 = RootToolsNativeTest.getStats(path, true);
                FileStatTest.assertEquals(stats2, RootTools.getFileStat(path, true));
            }
        }

        String stats3 = RootToolsNativeTest.getStats("", false);
        FileStatTest.assertEquals(stats3, RootTools.getFileStat("", false));

        String stats4 = RootToolsNativeTest.getStats("", true);
        FileStatTest.assertEquals(stats4, RootTools.getFileStat("", true));
    }

    public void testGetFileStatAssert() throws Exception
    {
        // These directories should cover all the file types except maybe named pipes (fifo), including:
        // char device, block device, sockets, symlinks, directories, and regular files
        String[] directories = {
            "/dev",
            "/dev/block",
            "/dev/socket",
            "/system/bin"
        };

        for (String directory : directories)
        {
            String[] filePaths = getFiles(directory);
            assertNotNull(filePaths);
            assertTrue(filePaths.length > 0);

            for (String path : filePaths)
            {
                String stats1 = RootToolsNativeTest.getStats(path, false);
                FileStatTest.assertEquals(stats1, RootTools.getFileStatAssert(path, false));

                String stats2 = RootToolsNativeTest.getStats(path, true);
                if (stats2 == null)
                {
                    try
                    {
                        RootTools.getFileStatAssert(path, true);
                        throw new AssertionError();
                    }
                    catch (ErrnoException e)
                    {
                        assertEquals("lstat failed: errno 2 (No such file or directory)", e.getMessage());
                    }
                }
                else
                {
                    FileStatTest.assertEquals(stats2, RootTools.getFileStatAssert(path, true));
                }
            }
        }

        try
        {
            RootTools.getFileStatAssert("", false);
            throw new AssertionError();
        }
        catch (ErrnoException e)
        {
            assertEquals("stat failed: errno 2 (No such file or directory)", e.getMessage());
        }

        try
        {
            RootTools.getFileStatAssert("", true);
            throw new AssertionError();
        }
        catch (ErrnoException e)
        {
            assertEquals("lstat failed: errno 2 (No such file or directory)", e.getMessage());
        }
    }

    public void testGetErrorMessage() throws Exception
    {
        assertEquals("Success", RootTools.getErrorMessage(0));
        assertEquals("Operation not permitted", RootTools.getErrorMessage(1));
        assertEquals("Unknown error -1", RootTools.getErrorMessage(-1));
        assertEquals("Address family not supported by protocol", RootTools.getErrorMessage(97));
    }

    public void testGetUserInfo() throws Exception
    {
        // Values from http://android-dls.com/wiki/index.php?title=Android_UIDs_and_GIDs
        assertEquals("root:0:0:/:/system/bin/sh", RootTools.getUserInfo(0).toString());
        assertEquals("system:1000:1000:/:/system/bin/sh", RootTools.getUserInfo(1000).toString());
        assertEquals("radio:1001:1001:/:/system/bin/sh", RootTools.getUserInfo(1001).toString());
        assertEquals("bluetooth:1002:1002:/:/system/bin/sh", RootTools.getUserInfo(1002).toString());
        assertEquals("graphics:1003:1003:/:/system/bin/sh", RootTools.getUserInfo(1003).toString());
        assertEquals("input:1004:1004:/:/system/bin/sh", RootTools.getUserInfo(1004).toString());
        assertEquals("audio:1005:1005:/:/system/bin/sh", RootTools.getUserInfo(1005).toString());
        assertEquals("camera:1006:1006:/:/system/bin/sh", RootTools.getUserInfo(1006).toString());
        assertEquals("log:1007:1007:/:/system/bin/sh", RootTools.getUserInfo(1007).toString());
        assertEquals("compass:1008:1008:/:/system/bin/sh", RootTools.getUserInfo(1008).toString());
        assertEquals("mount:1009:1009:/:/system/bin/sh", RootTools.getUserInfo(1009).toString());
        assertEquals("wifi:1010:1010:/:/system/bin/sh", RootTools.getUserInfo(1010).toString());
        assertEquals("adb:1011:1011:/:/system/bin/sh", RootTools.getUserInfo(1011).toString());
        assertEquals("install:1012:1012:/:/system/bin/sh", RootTools.getUserInfo(1012).toString());
        assertEquals("media:1013:1013:/:/system/bin/sh", RootTools.getUserInfo(1013).toString());
        assertEquals("dhcp:1014:1014:/:/system/bin/sh", RootTools.getUserInfo(1014).toString());
        assertEquals("shell:2000:2000:/:/system/bin/sh", RootTools.getUserInfo(2000).toString());
        assertEquals("cache:2001:2001:/:/system/bin/sh", RootTools.getUserInfo(2001).toString());
        assertEquals("diag:2002:2002:/:/system/bin/sh", RootTools.getUserInfo(2002).toString());
        assertEquals("net_bt_admin:3001:3001:/:/system/bin/sh", RootTools.getUserInfo(3001).toString());
        assertEquals("net_bt:3002:3002:/:/system/bin/sh", RootTools.getUserInfo(3002).toString());
        assertEquals("inet:3003:3003:/:/system/bin/sh", RootTools.getUserInfo(3003).toString());
        assertEquals("net_raw:3004:3004:/:/system/bin/sh", RootTools.getUserInfo(3004).toString());
        assertEquals("misc:9998:9998:/:/system/bin/sh", RootTools.getUserInfo(9998).toString());
        assertEquals("nobody:9999:9999:/:/system/bin/sh", RootTools.getUserInfo(9999).toString());
        assertEquals("u0_a0:10000:10000:/data:/system/bin/sh", RootTools.getUserInfo(10000).toString());
    }

    public void testGetUserName() throws Exception
    {
        // Values from http://android-dls.com/wiki/index.php?title=Android_UIDs_and_GIDs
        assertEquals("root", RootTools.getUserName(0));
        assertEquals("system", RootTools.getUserName(1000));
        assertEquals("radio", RootTools.getUserName(1001));
        assertEquals("bluetooth", RootTools.getUserName(1002));
        assertEquals("graphics", RootTools.getUserName(1003));
        assertEquals("input", RootTools.getUserName(1004));
        assertEquals("audio", RootTools.getUserName(1005));
        assertEquals("camera", RootTools.getUserName(1006));
        assertEquals("log", RootTools.getUserName(1007));
        assertEquals("compass", RootTools.getUserName(1008));
        assertEquals("mount", RootTools.getUserName(1009));
        assertEquals("wifi", RootTools.getUserName(1010));
        assertEquals("adb", RootTools.getUserName(1011));
        assertEquals("install", RootTools.getUserName(1012));
        assertEquals("media", RootTools.getUserName(1013));
        assertEquals("dhcp", RootTools.getUserName(1014));
        assertEquals("shell", RootTools.getUserName(2000));
        assertEquals("cache", RootTools.getUserName(2001));
        assertEquals("diag", RootTools.getUserName(2002));
        assertEquals("net_bt_admin", RootTools.getUserName(3001));
        assertEquals("net_bt", RootTools.getUserName(3002));
        assertEquals("inet", RootTools.getUserName(3003));
        assertEquals("net_raw", RootTools.getUserName(3004));
        assertEquals("misc", RootTools.getUserName(9998));
        assertEquals("nobody", RootTools.getUserName(9999));
        assertEquals("u0_a0", RootTools.getUserName(10000));
    }
}