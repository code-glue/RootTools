package com.stericson.RootTools;

import android.os.StatFs;
import android.util.Log;

import com.stericson.RootTools.internal.RootToolsInternal;

import junit.framework.TestCase;

public class RootToolsNonRootTest extends TestCase
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
        //statFs.getAvailableBlocksLong();
        //statFs.getAvailableBytes();
        //statFs.getBlockSizeLong();
        //statFs.getBlockCountLong();
        Log.i("statFs.toString()", statFs.toString());
    }
}