package com.stericson.RootTools.execution;

import android.util.Log;

import junit.framework.TestCase;

public class SimpleCommandTest extends TestCase
{
    public void testRun_StringBoolean() throws Exception
    {
        String command = "ls system";
        boolean root = false;
        SimpleCommand.run(command, root);
        Log.i("testRun_StringBoolean()", command);
    }

    public void testRun_String() throws Exception
    {

    }
}