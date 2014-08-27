package com.stericson.RootTools;

import android.support.annotation.NonNull;
import android.test.suitebuilder.annotation.SmallTest;

import com.stericson.RootTools.StructStat;

import junit.framework.TestCase;

public class StructStatTest extends TestCase
{
    private static void assertEquals(String message, long st_dev, long st_ino, int st_mode, long st_nlink, int st_uid,
                                     int st_gid, long st_rdev, long st_size, long st_atime, long st_mtime, long st_ctime,
                                     long st_blksize, long st_blocks, @NonNull StructStat actual)
    {
        assertEquals(message, st_dev, actual.st_dev);
        assertEquals(message, st_ino, actual.st_ino);
        assertEquals(message, st_mode, actual.st_mode);
        assertEquals(message, st_nlink, actual.st_nlink);
        assertEquals(message, st_uid, actual.st_uid);
        assertEquals(message, st_gid, actual.st_gid);
        assertEquals(message, st_rdev, actual.st_rdev);
        assertEquals(message, st_size, actual.st_size);
        assertEquals(message, st_atime, actual.st_atime);
        assertEquals(message, st_mtime, actual.st_mtime);
        assertEquals(message, st_ctime, actual.st_ctime);
        assertEquals(message, st_blksize, actual.st_blksize);
        assertEquals(message, st_blocks, actual.st_blocks);
    }

    static void assertEquals(String statOutput, @NonNull StructStat actual)
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
        int mode = Integer.parseInt(stats[3], 16);
        int uid = Integer.parseInt(stats[4]);
        int gid = Integer.parseInt(stats[5]);
        int deviceId = Integer.parseInt(stats[6], 16);
        int inode = Integer.parseInt(stats[7]);
        int hardLinkCount = Integer.parseInt(stats[8]);
        int deviceIdSpecial = (Integer.parseInt(stats[9], 16) << 8) | Integer.parseInt(stats[10], 16);
        int lastAccessed = Integer.parseInt(stats[11]);
        int lastModified = Integer.parseInt(stats[12]);
        int lastChanged = Integer.parseInt(stats[13]);
        int blockSize = Integer.parseInt(stats[14]);

        assertEquals(statOutput, deviceId, inode, mode, hardLinkCount, uid, gid, deviceIdSpecial, size,
                     lastAccessed, lastModified, lastChanged, blockSize, blockCount, actual);
    }

    @SmallTest
    public void testConstructor_StructStat() throws Exception
    {
        assertEquals("new StructStat(66306, 11375, 384, 1, 10085, 10085, 0, 20, 1406872134, 1406872135, 1406872136, 4096, 8)",
                     66306, 11375, 384, 1, 10085, 10085, 0, 20, 1406872134, 1406872135, 1406872136, 4096, 8, new StructStat(
                66306, 11375, 384, 1, 10085, 10085, 0, 20, 1406872134, 1406872135, 1406872136, 4096, 8));
    }
}
