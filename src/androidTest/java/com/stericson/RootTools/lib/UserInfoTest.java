package com.stericson.RootTools.lib;

import android.support.annotation.NonNull;
import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.TestCase;

public class UserInfoTest extends TestCase
{
    private static void assertEquals(String message, String name, int userId, int groupId, String directory, String shell,
                                     @NonNull UserInfo actual)
    {
        assertEquals(message, name, actual.name);
        assertEquals(message, userId, actual.userId);
        assertEquals(message, groupId, actual.groupId);
        assertEquals(message, directory, actual.directory);
        assertEquals(message, shell, actual.shell);
    }

    @SmallTest
    public void testConstructor() throws Exception
    {
        assertEquals(", 0, 0, /, /",
                     "", 0, 0, "", "", new UserInfo(
                     "", 0, 0, "", ""));

        assertEquals(", 0, 0, /, /",
                     "", 0, 0, "/", "/", new UserInfo(
                     "", 0, 0, "/", "/"));

        assertEquals("name, 1234, 5678, /home, /data",
                     "name", 1234, 5678, "/home", "/data", new UserInfo(
                     "name", 1234, 5678, "/home", "/data"));
    }

    @SmallTest
    public void testToString() throws Exception
    {
        assertEquals(":0:0::", new UserInfo("", 0, 0, "", "").toString());
        assertEquals(":0:0:/:/", new UserInfo("", 0, 0, "/", "/").toString());
        assertEquals("name:1234:5678:/home:/data", new UserInfo("name", 1234, 5678, "/home", "/data").toString());
    }
}