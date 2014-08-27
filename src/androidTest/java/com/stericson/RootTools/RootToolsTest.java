package com.stericson.RootTools;

import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;

import com.stericson.RootTools.lib.FileStat;
import com.stericson.RootTools.lib.Permission;
import com.stericson.RootTools.lib.FileType;
import com.stericson.RootTools.containers.SymbolicLink;
import com.stericson.RootTools.internal.RootToolsInternal;

import junit.framework.TestCase;

import java.io.File;
import java.util.List;

public class RootToolsTest extends TestCase
{
    public void setUp() throws Exception
    {
        super.setUp();
    }

    public void testGetBinPaths() throws Exception
    {
        String[] binPaths = RootToolsInternal.getBinPaths();
        assertNotNull(binPaths);
        assertTrue(binPaths.length > 0);
    }

    public void testStatFs() throws Exception
    {
        //for (String line : SimpleCommand.run("ls "))
        StatFs statFs = new StatFs("/storage/usbdisk0");
        Log.i("statFs.toString()", statFs.toString());
    }

    public void testCombinePaths_FileStringArray() throws Exception
    {
        assertEquals(new File(""), RootTools.combinePaths(new File(""), ""));
        assertEquals(new File("/"), RootTools.combinePaths(new File("/"), "/"));
        assertEquals(new File("a"), RootTools.combinePaths(new File("a"), ""));
        assertEquals(new File("a"), RootTools.combinePaths(new File("a"), "/"));
        assertEquals(new File("/a"), RootTools.combinePaths(new File("/a"), ""));
        assertEquals(new File("/a"), RootTools.combinePaths(new File("/a"), "/"));
        assertEquals(new File("a/b"), RootTools.combinePaths(new File("a"), "b"));
        assertEquals(new File("/a/b"), RootTools.combinePaths(new File("/a"), "/b"));
        assertEquals(new File("a/b"), RootTools.combinePaths(new File("a/"), "b/"));
        assertEquals(new File("/a/b"), RootTools.combinePaths(new File("/a/"), "/b/"));
        assertEquals(new File("a/b/c"), RootTools.combinePaths(new File("a"), "", "b/c"));
    }

    public void testCombinePaths_StringStringArray() throws Exception
    {
        assertEquals("", RootTools.combinePaths("", "", "", ""));
        assertEquals("/", RootTools.combinePaths("/", "/", "/", "/"));
        assertEquals("a/b/c/d", RootTools.combinePaths("a", "b", "c", "d"));
        assertEquals("/a/b/c/d", RootTools.combinePaths("/a", "/b", "/c", "/d"));
        assertEquals("a/b/c/d", RootTools.combinePaths("a/", "b/", "c/", "d/"));
        assertEquals("/a/b/c/d", RootTools.combinePaths("/a/", "/b/", "/c/", "/d/"));
        assertEquals("a/b/c/d", RootTools.combinePaths("a", "", "b/c", "", "d"));
    }

    public void testFindBinaryPath() throws Exception
    {
        assertTrue(RootTools.findBinaryPath("find") != null);

    }

    public void testBinaryExists() throws Exception
    {
        assertTrue(RootTools.binaryExists("find"));
    }

    public void testCloseAllShells() throws Exception
    {

    }

    public void testCloseCustomShell() throws Exception
    {

    }

    public void testCloseShell() throws Exception
    {

    }

    public void testCopyFile() throws Exception
    {

    }

    public void testDeleteFileOrDirectory() throws Exception
    {

    }

    public void testExists() throws Exception
    {
        for (int i = 0; i < 100; i++)
        {
            assertTrue(RootTools.exists("/system/xbin/busybox"));
            RootTools.exists("/system/sbin/[");
        }
    }

    public void testFixUtil() throws Exception
    {

    }

    public void testFixUtils() throws Exception
    {

    }

    public void testFindBinaryPaths() throws Exception
    {

    }

    public void testGetBusyBoxVersion() throws Exception
    {

    }

    public void testGetBusyBoxVersion1() throws Exception
    {

    }

    public void testGetBusyBoxApplets() throws Exception
    {

    }

    public void testGetBusyBoxApplets1() throws Exception
    {

    }

    public void testGetCustomShell() throws Exception
    {

    }

    public void testGetCustomShell1() throws Exception
    {

    }

    public void testGetFileInfo_String() throws Exception
    {
        String path = "/data/data/com.king.candycrushsaga/app_storage/settings.dat";
        FileStat fileStat = RootTools.getFileStat(path);

        //assertEquals("/data/data/com.king.candycrushsaga/app_storage/settings.dat", fileStat.getFile().getPath());
        //assertEquals("settings.dat", fileStat.getName());
        assertEquals(20, fileStat.size);
        assertEquals(8, fileStat.blockCount);
        assertEquals(FileType.File, fileStat.type);
        assertEquals(Permission.ReadWrite, fileStat.mode.permissions.user);
        assertEquals(Permission.None, fileStat.mode.permissions.group);
        assertEquals(Permission.None, fileStat.mode.permissions.others);
        //assertEquals(SpecialPermission.None, fileStat.mode.permissions.getSpecialAccess());
        assertEquals(10085, fileStat.userId);
        assertEquals(10085, fileStat.groupId);
        assertEquals(66306, fileStat.deviceId);
        assertEquals(11375, fileStat.inode);
        assertEquals(1, fileStat.hardLinkCount);
        //assertEquals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2014-08-01 00:48:54"), fileStat.getLastAccessed());
        //assertEquals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2014-08-01 00:48:55"), fileStat.getLastModified());
        //assertEquals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2014-08-01 00:48:56"), fileStat.getLastChanged());
        assertEquals(4096, fileStat.blockSize);
    }

    public void testGetFileInfo_File() throws Exception
    {
        File file = new File("/data/data/com.king.candycrushsaga/app_storage/settings.dat");
        FileStat fileStat = RootTools.getFileStat(file);

        //assertEquals("/data/data/com.king.candycrushsaga/app_storage/settings.dat", fileStat.getFile().getPath());
        //assertEquals("settings.dat", fileStat.getName());
        assertEquals(20, fileStat.size);
        assertEquals(8, fileStat.blockCount);
        assertEquals(FileType.File, fileStat.type);
        assertEquals(Permission.ReadWrite, fileStat.mode.permissions.user);
        assertEquals(Permission.None, fileStat.mode.permissions.group);
        assertEquals(Permission.None, fileStat.mode.permissions.others);
        //assertEquals(SpecialPermission.None, fileStat.mode.permissions.getSpecialAccess());
        assertEquals(10085, fileStat.userId);
        assertEquals(10085, fileStat.groupId);
        assertEquals(66306, fileStat.deviceId);
        assertEquals(11375, fileStat.inode);
        assertEquals(1, fileStat.hardLinkCount);
        //assertEquals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2014-08-01 00:48:54"), fileStat.getLastAccessed());
        //assertEquals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2014-08-01 00:48:55"), fileStat.getLastModified());
        //assertEquals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2014-08-01 00:48:56"), fileStat.getLastChanged());
        assertEquals(4096, fileStat.blockSize);
    }

    public void testGetInode() throws Exception
    {

    }

    public void testGetMounts() throws Exception
    {

    }

    public void testGetMountedAs() throws Exception
    {

    }

    public void testGetPath() throws Exception
    {
        List<String> paths = RootTools.getPath();

        assertNotNull(paths);
        assertTrue("Testing getPath", paths.size() > 0);

        Log.d("RootToolsTest.testGetPath()", TextUtils.join(System.getProperty("line.separator"), paths));
    }

    public void testGetShell() throws Exception
    {

    }

    public void testGetShell1() throws Exception
    {

    }

    public void testGetShell2() throws Exception
    {

    }

    public void testGetShell3() throws Exception
    {

    }

    public void testGetShell4() throws Exception
    {

    }

    public void testGetSpace() throws Exception
    {

    }

    public void testGetSymlink() throws Exception
    {

    }

    public void testGetSymlinks_FileBoolean() throws Exception
    {
        File directory = new File("/system");

        List<SymbolicLink> symLinksAll = RootTools.getSymLinks(directory, -1);
        List<SymbolicLink> symLinksDepth0= RootTools.getSymLinks(directory, 0);
        List<SymbolicLink> symLinksDepth1= RootTools.getSymLinks(directory, 1);
        List<SymbolicLink> symLinksDepth2= RootTools.getSymLinks(directory, 2);

        assertTrue(symLinksAll.size() > 0);
        assertTrue(symLinksDepth0.size() == 0);
        assertTrue(symLinksDepth1.size() == 0);
        assertTrue(symLinksDepth2.size() > 0);
        assertTrue(symLinksAll.size() >= symLinksDepth2.size());

        //Log.i("testGetSymlinks_File() symLinksAll", TextUtils.join("\n", symLinksAll));
        //Log.i("testGetSymlinks_File() symLinksDepth0", TextUtils.join("\n", symLinksDepth0));
        //Log.i("testGetSymlinks_File() symLinksDepth1", TextUtils.join("\n", symLinksDepth1));
        //Log.i("testGetSymlinks_File() symLinksDepth2", TextUtils.join("\n", symLinksDepth2));
    }

    public void testGetSymlinks_File() throws Exception
    {
        File directory = new File("/system");
        List<SymbolicLink> symLinks = RootTools.getSymLinks(directory);

        assertTrue(symLinks.size() > 0);
        Log.i("testGetSymlinks_File()", TextUtils.join("\n", symLinks));
    }

    public void testNewSymlink_String() throws Exception
    {
        String directory = "/system";
        RootTools.getSymLinks(directory);
    }

    public void testGetWorkingToolbox() throws Exception
    {

    }

    public void testHasEnoughSpaceOnSdCard() throws Exception
    {

    }

    public void testHasUtil() throws Exception
    {

    }

    public void testInstallBinary() throws Exception
    {

    }

    public void testInstallBinary1() throws Exception
    {

    }

    public void testHasBinary() throws Exception
    {

    }

    public void testIsAppletAvailable() throws Exception
    {

    }

    public void testIsAppletAvailable1() throws Exception
    {

    }

    public void testIsAccessGiven() throws Exception
    {
        RootTools.isAccessGiven();
    }

    public void testIsBusyboxAvailable() throws Exception
    {

    }

    public void testIsNativeToolsReady() throws Exception
    {

    }

    public void testIsProcessRunning() throws Exception
    {

    }

    public void testIsRootAvailable() throws Exception
    {
        RootTools.isRootAvailable();
    }

    public void testKillProcess() throws Exception
    {

    }

    public void testOfferBusyBox() throws Exception
    {

    }

    public void testOfferBusyBox1() throws Exception
    {

    }

    public void testOfferSuperUser() throws Exception
    {

    }

    public void testOfferSuperUser1() throws Exception
    {

    }

    public void testRemount() throws Exception
    {

    }

    public void testRestartAndroid() throws Exception
    {

    }

    public void testRunBinary() throws Exception
    {

    }

    public void testRunShellCommand() throws Exception
    {

    }

    public void testLog() throws Exception
    {

    }

    public void testLog1() throws Exception
    {

    }

    public void testLog2() throws Exception
    {

    }

    public void testIslog() throws Exception
    {

    }

    public void testLog3() throws Exception
    {

    }

    public void testSetPermissions() throws Exception
    {

    }

    public void testSetPermissions1() throws Exception
    {

    }

    public void testSetPermissions2() throws Exception
    {

    }

    public void testSetPermissions3() throws Exception
    {

    }

    public void testSetUserPermissions() throws Exception
    {

    }

    public void testSetGroupPermissions() throws Exception
    {

    }

    public void testSetOthersPermissions() throws Exception
    {

    }
}