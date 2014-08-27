package com.stericson.RootTools.lib;

import android.support.annotation.NonNull;
import android.test.suitebuilder.annotation.SmallTest;
import android.text.TextUtils;
import android.util.Log;

import junit.framework.TestCase;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FileStatTest extends TestCase
{
    static void assertEquals(String message, long deviceId, long inode, FileMode mode, long hardLinkCount, int userId,
                             int groupId, long deviceIdSpecial, long size, Date lastAccessed, Date lastModified,
                             Date lastChanged, long blockSize, long blockCount, @NonNull FileStat actual)
    {
        //assertEquals("settings.dat", fileInfo.getName());
        assertEquals(deviceId, actual.deviceId);
        assertEquals(inode, actual.inode);
        FileModeTest.assertEquals(message, mode, actual.mode);
        assertEquals(hardLinkCount, actual.hardLinkCount);
        assertEquals(userId, actual.userId);
        assertEquals(groupId, actual.groupId);
        assertEquals(deviceIdSpecial, actual.deviceIdSpecial);
        assertEquals(size, actual.size);
        assertEquals(lastAccessed, actual.lastAccessed);
        assertEquals(lastModified, actual.lastModified);
        assertEquals(lastChanged, actual.lastChanged);
        assertEquals(blockSize, actual.blockSize);
        assertEquals(blockCount, actual.blockCount);
        assertEquals(mode.type, actual.type);
    }

    static void assertEquals(String statOutput, @NonNull FileStat actual) throws IOException
    {
        if (statOutput == null)
        {
            assertNull(actual);
            return;
        }

        String[] stats = statOutput.split("\\s");

        //String path = stats[0];
        int size = Integer.parseInt(stats[1]);
        int blockCount = Integer.parseInt(stats[2]);
        FileMode mode = FileMode.valueOf(Integer.parseInt(stats[3], 16));
        int uid = Integer.parseInt(stats[4]);
        int gid = Integer.parseInt(stats[5]);
        int deviceId = Integer.parseInt(stats[6], 16);
        int inode = Integer.parseInt(stats[7]);
        int hardLinkCount = Integer.parseInt(stats[8]);
        int deviceIdSpecial = (Integer.parseInt(stats[9], 16) << 8) | Integer.parseInt(stats[10], 16);
        Date lastAccessed = new Date((long)Integer.parseInt(stats[11]) * 1000);
        Date lastModified = new Date((long)Integer.parseInt(stats[12]) * 1000);
        Date lastChanged = new Date((long)Integer.parseInt(stats[13]) * 1000);
        int blockSize = Integer.parseInt(stats[14]);

        assertEquals(statOutput, deviceId, inode, mode, hardLinkCount, uid, gid, deviceIdSpecial, size,
                     lastAccessed, lastModified, lastChanged, blockSize, blockCount, actual);
    }

    //@SmallTest
    //public void testConstructor_13() throws Exception
    //{
    //    assertEquals("new FileStat(66306, 11375, 384, 1, 10085, 10085, 0, 20, 1406872134, 1406872135, 1406872136, 4096, 8)",
    //                         66306, 11375,
    //                         new FileMode(FileType.File, Permission.Read, Permission.Write, Permission.Execute, false, false, false),
    //                         1, 10085, 10085, 0, 20, new Date(1406872134000L), new Date(1406872135000L), new Date(1406872136000L), 4096, 8, new FileStat(
    //                         66306, 11375, 384, 1, 10085, 10085, 0, 20, 1406872134, 1406872135, 1406872136, 4096, 8));
    //}

    @SmallTest
    public void testConstructor_1() throws Exception
    {
        FileMode mode = new FileMode(FileType.File, Permission.Read, Permission.Write, Permission.Execute, false, false, false);
        assertEquals("new FileStat(fileName, new StructStat(66306, 11375, 384, 1, 10085, 10085, 0, 20, 1406872134, 1406872135, 1406872136, 4096, 8))",
                     66306, 11375, mode, 1, 10085, 10085, 0, 20, new Date(1406872134000L), new Date(1406872135000L), new Date(1406872136000L), 4096, 8, new FileStat("fileName", new StructStat(
                     66306, 11375, mode.getValue(), 1, 10085, 10085, 0, 20, 1406872134L, 1406872135L, 1406872136L, 4096, 8)));
    }

    @SmallTest
    public void testIsNamedPipe() throws Exception
    {
        assertFalse(new FileStat("name", 0, 0, 0x0000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isNamedPipe());
        assertTrue(new FileStat("name", 0, 0, 0x1000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isNamedPipe());
        assertFalse(new FileStat("name", 0, 0, 0x2000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isNamedPipe());
        assertFalse(new FileStat("name", 0, 0, 0x4000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isNamedPipe());
        assertFalse(new FileStat("name", 0, 0, 0x6000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isNamedPipe());
        assertFalse(new FileStat("name", 0, 0, 0xa000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isNamedPipe());
        assertFalse(new FileStat("name", 0, 0, 0xc000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isNamedPipe());
        assertFalse(new FileStat("name", 0, 0, 0xe000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isNamedPipe());
    }

    @SmallTest
    public void testIsCharDevice() throws Exception
    {
        assertFalse(new FileStat("name", 0, 0, 0x0000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isCharDevice());
        assertFalse(new FileStat("name", 0, 0, 0x1000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isCharDevice());
        assertTrue(new FileStat("name", 0, 0, 0x2000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isCharDevice());
        assertFalse(new FileStat("name", 0, 0, 0x4000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isCharDevice());
        assertFalse(new FileStat("name", 0, 0, 0x6000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isCharDevice());
        assertFalse(new FileStat("name", 0, 0, 0xa000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isCharDevice());
        assertFalse(new FileStat("name", 0, 0, 0xc000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isCharDevice());
        assertFalse(new FileStat("name", 0, 0, 0xe000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isCharDevice());
    }

    @SmallTest
    public void testIsDirectory() throws Exception
    {
        assertFalse(new FileStat("name", 0, 0, 0x0000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isDirectory());
        assertFalse(new FileStat("name", 0, 0, 0x1000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isDirectory());
        assertFalse(new FileStat("name", 0, 0, 0x2000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isDirectory());
        assertTrue(new FileStat("name", 0, 0, 0x4000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isDirectory());
        assertFalse(new FileStat("name", 0, 0, 0x6000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isDirectory());
        assertFalse(new FileStat("name", 0, 0, 0xa000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isDirectory());
        assertFalse(new FileStat("name", 0, 0, 0xc000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isDirectory());
        assertFalse(new FileStat("name", 0, 0, 0xe000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isDirectory());
    }

    @SmallTest
    public void testIsBlockDevice() throws Exception
    {
        assertFalse(new FileStat("name", 0, 0, 0x0000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isBlockDevice());
        assertFalse(new FileStat("name", 0, 0, 0x1000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isBlockDevice());
        assertFalse(new FileStat("name", 0, 0, 0x2000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isBlockDevice());
        assertFalse(new FileStat("name", 0, 0, 0x4000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isBlockDevice());
        assertTrue(new FileStat("name", 0, 0, 0x6000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isBlockDevice());
        assertFalse(new FileStat("name", 0, 0, 0xa000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isBlockDevice());
        assertFalse(new FileStat("name", 0, 0, 0xc000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isBlockDevice());
        assertFalse(new FileStat("name", 0, 0, 0xe000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isBlockDevice());
    }

    @SmallTest
    public void testIsFile() throws Exception
    {
        assertFalse(new FileStat("name", 0, 0, 0x0000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isFile());
        assertFalse(new FileStat("name", 0, 0, 0x1000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isFile());
        assertFalse(new FileStat("name", 0, 0, 0x2000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isFile());
        assertFalse(new FileStat("name", 0, 0, 0x4000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isFile());
        assertFalse(new FileStat("name", 0, 0, 0x6000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isFile());
        assertTrue(new FileStat("name", 0, 0, 0x8000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isFile());
        assertFalse(new FileStat("name", 0, 0, 0xa000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isFile());
        assertFalse(new FileStat("name", 0, 0, 0xc000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isFile());
    }

    @SmallTest
    public void testIsSymLink() throws Exception
    {
        assertFalse(new FileStat("name", 0, 0, 0x0000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isSymLink());
        assertFalse(new FileStat("name", 0, 0, 0x1000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isSymLink());
        assertFalse(new FileStat("name", 0, 0, 0x2000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isSymLink());
        assertFalse(new FileStat("name", 0, 0, 0x4000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isSymLink());
        assertFalse(new FileStat("name", 0, 0, 0x6000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isSymLink());
        assertFalse(new FileStat("name", 0, 0, 0x8000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isSymLink());
        assertTrue(new FileStat("name", 0, 0, 0xa000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isSymLink());
        assertFalse(new FileStat("name", 0, 0, 0xc000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isSymLink());
    }

    @SmallTest
    public void testIsSocket() throws Exception
    {
        assertFalse(new FileStat("name", 0, 0, 0x0000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isSocket());
        assertFalse(new FileStat("name", 0, 0, 0x1000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isSocket());
        assertFalse(new FileStat("name", 0, 0, 0x2000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isSocket());
        assertFalse(new FileStat("name", 0, 0, 0x4000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isSocket());
        assertFalse(new FileStat("name", 0, 0, 0x6000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isSocket());
        assertFalse(new FileStat("name", 0, 0, 0x8000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isSocket());
        assertFalse(new FileStat("name", 0, 0, 0xa000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isSocket());
        assertTrue(new FileStat("name", 0, 0, 0xc000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isSocket());
    }

    @SmallTest
    public void testIsUnknownType() throws Exception
    {
        assertTrue(new FileStat("name", 0, 0, 0x0000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isUnknownType());
        assertFalse(new FileStat("name", 0, 0, 0x1000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isUnknownType());
        assertFalse(new FileStat("name", 0, 0, 0x2000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isUnknownType());
        assertFalse(new FileStat("name", 0, 0, 0x4000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isUnknownType());
        assertFalse(new FileStat("name", 0, 0, 0x6000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isUnknownType());
        assertFalse(new FileStat("name", 0, 0, 0x8000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isUnknownType());
        assertFalse(new FileStat("name", 0, 0, 0xa000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isUnknownType());
        assertFalse(new FileStat("name", 0, 0, 0xc000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0).isUnknownType());
    }

    @SmallTest
    public void testToTerseString() throws Exception
    {
        assertEquals("/dev/fscklogs 40 0 41f8 0 1000 c 1247 2 0 0 1408979492 1408899042 1408899042 4096", new FileStat("/dev/fscklogs", 12, 1247, 0x41f8, 2, 0, 1000, 0, 40, 1408979492000L, 1408899042000L, 1408899042000L, 4096, 0).toTerseString());
        assertEquals("/dev/socket/property_service 0 0 c1b6 0 0 c 1271 1 0 0 1408898995 1408898995 1408898995 4096", new FileStat("/dev/socket/property_service", 12, 1271, 0xc1b6, 1, 0, 0, 0, 0, 1408898995000L, 1408898995000L, 1408898995000L, 4096, 0).toTerseString());
        assertEquals("/dev/socket/adbd 0 0 c1b0 1000 1000 c 676 1 0 0 1408898995 1408898995 1408898995 4096", new FileStat("/dev/socket/adbd", 12, 676, 0xc1b0, 1, 1000, 1000, 0, 0, 1408898995000L, 1408898995000L, 1408898995000L, 4096, 0).toTerseString());
        assertEquals("/system/bin/cat 7 0 a1ff 0 0 10301 24834 1 0 0 1402876190 1402876190 1402876190 4096", new FileStat("/system/bin/cat", 66305, 24834, 0xa1ff, 1, 0, 0, 0, 7, 1402876190000L, 1402876190000L, 1402876190000L, 4096, 0).toTerseString());
        assertEquals("/dev/accelerometer 0 0 21b0 1000 1000 c 466 1 a 2b 1408898993 1408898993 1408898993 4096", new FileStat("/dev/accelerometer", 12, 466, 0x21b0, 1, 1000, 1000, 0xa2b, 0, 1408898993000L, 1408898993000L, 1408898993000L, 4096, 0).toTerseString());
        assertEquals("/dev/HPD 0 0 21b6 1000 1000 c 455 1 a f3 1408898993 1408898993 1408898993 4096", new FileStat("/dev/HPD", 12, 455, 0x21b6, 1, 1000, 1000, 0xaf3, 0, 1408898993000L, 1408898993000L, 1408898993000L, 4096, 0).toTerseString());
        assertEquals("/dev/block/mmcblk0p8 0 0 61b0 1000 1001 c 395 1 103 0 1408898996 1408898993 1408898995 4096", new FileStat("/dev/block/mmcblk0p8", 12, 395, 0x61b0, 1, 1000, 1001, 0x10300, 0, 1408898996000L, 1408898993000L, 1408898995000L, 4096, 0).toTerseString());
        assertEquals("/dev/alarm 0 0 21b4 1000 1001 c 453 1 a 32 1408898993 1408898993 1408898993 4096", new FileStat("/dev/alarm", 12, 453, 0x21b4, 1, 1000, 1001, 0xa32, 0, 1408898993000L, 1408898993000L, 1408898993000L, 4096, 0).toTerseString());
        assertEquals("/dev/block 960 0 41ed 0 0 c 360 4 0 0 1408902374 1408899018 1408899018 4096", new FileStat("/dev/block", 12, 360, 0x41ed, 4, 0, 0, 0, 960, 1408902374000L, 1408899018000L, 1408899018000L, 4096, 0).toTerseString());
        assertEquals("/system/bin/netcfg 5536 16 85e8 0 3003 10301 24634 1 0 0 1217592000 1217592000 1402876190 4096", new FileStat("/system/bin/netcfg", 66305, 24634, 0x85e8, 1, 0, 3003, 0, 5536, 1217592000000L, 1217592000000L, 1402876190000L, 4096, 16).toTerseString());
        assertEquals("/dev/socket/installd 0 0 c180 1000 1000 c 1331 1 0 0 1408899001 1408898995 1408898995 4096", new FileStat("/dev/socket/installd", 12, 1331, 0xc180, 1, 1000, 1000, 0, 0, 1408899001000L, 1408898995000L, 1408898995000L, 4096, 0).toTerseString());
        assertEquals("/system/bin/adb 109008 216 81ed 0 2000 10301 24578 1 0 0 1217592000 1217592000 1402876190 4096", new FileStat("/system/bin/adb", 66305, 24578, 0x81ed, 1, 0, 2000, 0, 109008, 1217592000000L, 1217592000000L, 1402876190000L, 4096, 216).toTerseString());
        assertEquals("/dev/bus_dma_throughput 0 0 2180 0 0 c 483 1 a 1c 1408898993 1408898993 1408898993 4096", new FileStat("/dev/bus_dma_throughput", 12, 483, 0x2180, 1, 0, 0, 0xa1c, 0, 1408898993000L, 1408898993000L, 1408898993000L, 4096, 0).toTerseString());
        assertEquals("/dev/block/vold 300 0 41c0 0 0 c 1345 2 0 0 1408979492 1408898995 1408898995 4096", new FileStat("/dev/block/vold", 12, 1345, 0x41c0, 2, 0, 0, 0, 300, 1408979492000L, 1408898995000L, 1408898995000L, 4096, 0).toTerseString());
        assertEquals("/dev/__properties__ 131072 272 8124 0 0 c 1122 1 0 0 1408985394 1408898990 1408898990 4096", new FileStat("/dev/__properties__", 12, 1122, 0x8124, 1, 0, 0, 0, 131072, 1408985394000L, 1408898990000L, 1408898990000L, 4096, 272).toTerseString());
        assertEquals("/system/bin/run-as 9504 32 81e8 0 2000 10301 24647 1 0 0 1217592000 1217592000 1402876190 4096", new FileStat("/system/bin/run-as", 66305, 24647, 0x81e8, 1, 0, 2000, 0, 9504, 1217592000000L, 1217592000000L, 1402876190000L, 4096, 32).toTerseString());
        assertEquals("/dev/block/dm-0 0 0 6180 0 0 c 2186 1 fe 0 1408899012 1408899012 1408899012 4096", new FileStat("/dev/block/dm-0", 12, 2186, 0x6180, 1, 0, 0, 0xfe00, 0, 1408899012000L, 1408899012000L, 1408899012000L, 4096, 0).toTerseString());
        assertEquals("/dev/xt_qtaguid 0 0 21a4 0 0 c 470 1 a 29 1408898993 1408898993 1408898993 4096", new FileStat("/dev/xt_qtaguid", 12, 470, 0x21a4, 1, 0, 0, 0xa29, 0, 1408898993000L, 1408898993000L, 1408898993000L, 4096, 0).toTerseString());
    }

    @SmallTest
    public void testToLsString() throws Exception
    {
        assertEquals("drwxrwx--- root     system            2014-08-24 11:50 fscklogs", new FileStat("/dev/fscklogs", 12, 1247, 0x41f8, 2, 0, 1000, 0x00, 40, 1408979492000L, 1408899042000L, 1408899042000L, 4096, 0).toLsString());
        assertEquals("srw-rw-rw- root     root              2014-08-24 11:49 property_service", new FileStat("/dev/socket/property_service", 12, 1271, 0xc1b6, 1, 0, 0, 0, 0, 1408898995000L, 1408898995000L, 1408898995000L, 4096, 0).toLsString());
        assertEquals("srw-rw---- system   system            2014-08-24 11:49 adbd", new FileStat("/dev/socket/adbd", 12, 676, 0xc1b0, 1, 1000, 1000, 0, 0, 1408898995000L, 1408898995000L, 1408898995000L, 4096, 0).toLsString());
        assertEquals("lrwxrwxrwx root     root              2014-06-15 18:49 cat -> toolbox", new FileStat("/system/bin/cat", 66305, 24834, 0xa1ff, 1, 0, 0, 0, 7, 1402876190000L, 1402876190000L, 1402876190000L, 4096, 0).toLsString());
        assertEquals("crw-rw---- system   system    10,  43 2014-08-24 11:49 accelerometer", new FileStat("/dev/accelerometer", 12, 466, 0x21b0, 1, 1000, 1000, 0x0a2b, 0, 1408898993000L, 1408898993000L, 1408898993000L, 4096, 0).toLsString());
        assertEquals("crw-rw-rw- system   system    10, 243 2014-08-24 11:49 HPD", new FileStat("/dev/HPD", 12, 455, 0x21b6, 1, 1000, 1000, 0x0af3, 0, 1408898993000L, 1408898993000L, 1408898993000L, 4096, 0).toLsString());
        assertEquals("brw-rw---- system   radio    259,   0 2014-08-24 11:49 mmcblk0p8", new FileStat("/dev/block/mmcblk0p8", 12, 395, 0x61b0, 1, 1000, 1001, 0x10300, 0, 1408898996000L, 1408898993000L, 1408898995000L, 4096, 0).toLsString());
        assertEquals("crw-rw-r-- system   radio     10,  50 2014-08-24 11:49 alarm", new FileStat("/dev/alarm", 12, 453, 0x21b4, 1, 1000, 1001, 0x0a32, 0, 1408898993000L, 1408898993000L, 1408898993000L, 4096, 0).toLsString());
        assertEquals("drwxr-xr-x root     root              2014-08-24 11:50 block", new FileStat("/dev/block", 12, 360, 0x41ed, 4, 0, 0, 0, 960, 1408902374000L, 1408899018000L, 1408899018000L, 4096, 0).toLsString());
        assertEquals("-rwxr-s--- root     inet         5536 2008-08-01 07:00 netcfg", new FileStat("/system/bin/netcfg", 66305, 24634, 0x85e8, 1, 0, 3003, 0, 5536, 1217592000000L, 1217592000000L, 1402876190000L, 4096, 16).toLsString());
        assertEquals("srw------- system   system            2014-08-24 11:49 installd", new FileStat("/dev/socket/installd", 12, 1331, 0xc180, 1, 1000, 1000, 0, 0, 1408899001000L, 1408898995000L, 1408898995000L, 4096, 0).toLsString());
        assertEquals("-rwxr-xr-x root     shell      109008 2008-08-01 07:00 adb", new FileStat("/system/bin/adb", 66305, 24578, 0x81ed, 1, 0, 2000, 0, 109008, 1217592000000L, 1217592000000L, 1402876190000L, 4096, 216).toLsString());
        assertEquals("crw------- root     root      10,  28 2014-08-24 11:49 bus_dma_throughput", new FileStat("/dev/bus_dma_throughput", 12, 483, 0x2180, 1, 0, 0, 0x0a1c, 0, 1408898993000L, 1408898993000L, 1408898993000L, 4096, 0).toLsString());
        assertEquals("drwx------ root     root              2014-08-24 11:49 vold", new FileStat("/dev/block/vold", 12, 1345, 0x41c0, 2, 0, 0, 0, 300, 1408979492000L, 1408898995000L, 1408898995000L, 4096, 0).toLsString());
        assertEquals("-r--r--r-- root     root       131072 2014-08-24 11:49 __properties__", new FileStat("/dev/__properties__", 12, 1122, 0x8124, 1, 0, 0, 0, 131072, 1408985394000L, 1408898990000L, 1408898990000L, 4096, 272).toLsString());
        assertEquals("-rwxr-x--- root     shell        9504 2008-08-01 07:00 run-as", new FileStat("/system/bin/run-as", 66305, 24647, 0x81e8, 1, 0, 2000, 0, 9504, 1217592000000L, 1217592000000L, 1402876190000L, 4096, 32).toLsString());
        assertEquals("brw------- root     root     254,   0 2014-08-24 11:50 dm-0", new FileStat("/dev/block/dm-0", 12, 2186, 0x6180, 1, 0, 0, 0xfe00, 0, 1408899012000L, 1408899012000L, 1408899012000L, 4096, 0).toLsString());
        assertEquals("crw-r--r-- root     root      10,  41 2014-08-24 11:49 xt_qtaguid", new FileStat("/dev/xt_qtaguid", 12, 470, 0x21a4, 1, 0, 0, 0x0a29, 0, 1408898993000L, 1408898993000L, 1408898993000L, 4096, 0).toLsString());
    }

    @SmallTest
    public void testToString() throws Exception
    {
        assertEquals("drwxrwx--- root     system            2014-08-24 11:50 fscklogs", new FileStat("/dev/fscklogs", 12, 1247, 0x41f8, 2, 0, 1000, 0x00, 40, 1408979492000L, 1408899042000L, 1408899042000L, 4096, 0).toLsString());
        assertEquals("srw-rw-rw- root     root              2014-08-24 11:49 property_service", new FileStat("/dev/socket/property_service", 12, 1271, 0xc1b6, 1, 0, 0, 0, 0, 1408898995000L, 1408898995000L, 1408898995000L, 4096, 0).toLsString());
        assertEquals("srw-rw---- system   system            2014-08-24 11:49 adbd", new FileStat("/dev/socket/adbd", 12, 676, 0xc1b0, 1, 1000, 1000, 0, 0, 1408898995000L, 1408898995000L, 1408898995000L, 4096, 0).toLsString());
        assertEquals("lrwxrwxrwx root     root              2014-06-15 18:49 cat -> toolbox", new FileStat("/system/bin/cat", 66305, 24834, 0xa1ff, 1, 0, 0, 0, 7, 1402876190000L, 1402876190000L, 1402876190000L, 4096, 0).toLsString());
        assertEquals("crw-rw---- system   system    10,  43 2014-08-24 11:49 accelerometer", new FileStat("/dev/accelerometer", 12, 466, 0x21b0, 1, 1000, 1000, 0x0a2b, 0, 1408898993000L, 1408898993000L, 1408898993000L, 4096, 0).toLsString());
        assertEquals("crw-rw-rw- system   system    10, 243 2014-08-24 11:49 HPD", new FileStat("/dev/HPD", 12, 455, 0x21b6, 1, 1000, 1000, 0x0af3, 0, 1408898993000L, 1408898993000L, 1408898993000L, 4096, 0).toLsString());
        assertEquals("brw-rw---- system   radio    259,   0 2014-08-24 11:49 mmcblk0p8", new FileStat("/dev/block/mmcblk0p8", 12, 395, 0x61b0, 1, 1000, 1001, 0x10300, 0, 1408898996000L, 1408898993000L, 1408898995000L, 4096, 0).toLsString());
        assertEquals("crw-rw-r-- system   radio     10,  50 2014-08-24 11:49 alarm", new FileStat("/dev/alarm", 12, 453, 0x21b4, 1, 1000, 1001, 0x0a32, 0, 1408898993000L, 1408898993000L, 1408898993000L, 4096, 0).toLsString());
        assertEquals("drwxr-xr-x root     root              2014-08-24 11:50 block", new FileStat("/dev/block", 12, 360, 0x41ed, 4, 0, 0, 0, 960, 1408902374000L, 1408899018000L, 1408899018000L, 4096, 0).toLsString());
        assertEquals("-rwxr-s--- root     inet         5536 2008-08-01 07:00 netcfg", new FileStat("/system/bin/netcfg", 66305, 24634, 0x85e8, 1, 0, 3003, 0, 5536, 1217592000000L, 1217592000000L, 1402876190000L, 4096, 16).toLsString());
        assertEquals("srw------- system   system            2014-08-24 11:49 installd", new FileStat("/dev/socket/installd", 12, 1331, 0xc180, 1, 1000, 1000, 0, 0, 1408899001000L, 1408898995000L, 1408898995000L, 4096, 0).toLsString());
        assertEquals("-rwxr-xr-x root     shell      109008 2008-08-01 07:00 adb", new FileStat("/system/bin/adb", 66305, 24578, 0x81ed, 1, 0, 2000, 0, 109008, 1217592000000L, 1217592000000L, 1402876190000L, 4096, 216).toLsString());
        assertEquals("crw------- root     root      10,  28 2014-08-24 11:49 bus_dma_throughput", new FileStat("/dev/bus_dma_throughput", 12, 483, 0x2180, 1, 0, 0, 0x0a1c, 0, 1408898993000L, 1408898993000L, 1408898993000L, 4096, 0).toLsString());
        assertEquals("drwx------ root     root              2014-08-24 11:49 vold", new FileStat("/dev/block/vold", 12, 1345, 0x41c0, 2, 0, 0, 0, 300, 1408979492000L, 1408898995000L, 1408898995000L, 4096, 0).toLsString());
        assertEquals("-r--r--r-- root     root       131072 2014-08-24 11:49 __properties__", new FileStat("/dev/__properties__", 12, 1122, 0x8124, 1, 0, 0, 0, 131072, 1408985394000L, 1408898990000L, 1408898990000L, 4096, 272).toLsString());
        assertEquals("-rwxr-x--- root     shell        9504 2008-08-01 07:00 run-as", new FileStat("/system/bin/run-as", 66305, 24647, 0x81e8, 1, 0, 2000, 0, 9504, 1217592000000L, 1217592000000L, 1402876190000L, 4096, 32).toLsString());
        assertEquals("brw------- root     root     254,   0 2014-08-24 11:50 dm-0", new FileStat("/dev/block/dm-0", 12, 2186, 0x6180, 1, 0, 0, 0xfe00, 0, 1408899012000L, 1408899012000L, 1408899012000L, 4096, 0).toLsString());
        assertEquals("crw-r--r-- root     root      10,  41 2014-08-24 11:49 xt_qtaguid", new FileStat("/dev/xt_qtaguid", 12, 470, 0x21a4, 1, 0, 0, 0x0a29, 0, 1408898993000L, 1408898993000L, 1408898993000L, 4096, 0).toLsString());
    }
}