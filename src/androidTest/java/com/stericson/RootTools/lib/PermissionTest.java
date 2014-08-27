package com.stericson.RootTools.lib;

import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

public class PermissionTest extends TestCase
{
    @SmallTest
    public void testGetValue() throws Exception
    {
        assertEquals(0, Permission.None.getValue());
        assertEquals(1, Permission.Execute.getValue());
        assertEquals(2, Permission.Write.getValue());
        assertEquals(3, Permission.WriteExecute.getValue());
        assertEquals(4, Permission.Read.getValue());
        assertEquals(5, Permission.ReadExecute.getValue());
        assertEquals(6, Permission.ReadWrite.getValue());
        assertEquals(7, Permission.All.getValue());
    }

    @SmallTest
    public void testValueOf() throws Exception
    {
        assertEquals(Permission.None, Permission.valueOf(0));
        assertEquals(Permission.Execute, Permission.valueOf(1));
        assertEquals(Permission.Write, Permission.valueOf(2));
        assertEquals(Permission.WriteExecute, Permission.valueOf(3));
        assertEquals(Permission.Read, Permission.valueOf(4));
        assertEquals(Permission.ReadExecute, Permission.valueOf(5));
        assertEquals(Permission.ReadWrite, Permission.valueOf(6));
        assertEquals(Permission.All, Permission.valueOf(7));

        try
        {
            assertEquals(Permission.All, Permission.valueOf(-1));
            throw new AssertionFailedError();
        }
        catch (IllegalArgumentException ignore) {}

        try
        {
            assertEquals(Permission.All, Permission.valueOf(8));
            throw new AssertionFailedError();
        }
        catch (IllegalArgumentException ignore) {}
    }

    @SmallTest
    public void testParse() throws Exception
    {
        assertEquals(Permission.None, Permission.parse("---"));
        assertEquals(Permission.Execute, Permission.parse("--x"));
        assertEquals(Permission.Execute, Permission.parse("--s"));
        assertEquals(Permission.None, Permission.parse("--S"));
        assertEquals(Permission.Execute, Permission.parse("--t"));
        assertEquals(Permission.None, Permission.parse("--T"));
        assertEquals(Permission.Write, Permission.parse("-w-"));
        assertEquals(Permission.WriteExecute, Permission.parse("-wx"));
        assertEquals(Permission.WriteExecute, Permission.parse("-ws"));
        assertEquals(Permission.Write, Permission.parse("-wS"));
        assertEquals(Permission.WriteExecute, Permission.parse("-wt"));
        assertEquals(Permission.Write, Permission.parse("-wT"));
        assertEquals(Permission.Read, Permission.parse("r--"));
        assertEquals(Permission.ReadExecute, Permission.parse("r-x"));
        assertEquals(Permission.ReadExecute, Permission.parse("r-s"));
        assertEquals(Permission.Read, Permission.parse("r-S"));
        assertEquals(Permission.ReadExecute, Permission.parse("r-t"));
        assertEquals(Permission.Read, Permission.parse("r-T"));
        assertEquals(Permission.ReadWrite, Permission.parse("rw-"));
        assertEquals(Permission.All, Permission.parse("rwx"));
        assertEquals(Permission.All, Permission.parse("rws"));
        assertEquals(Permission.ReadWrite, Permission.parse("rwS"));
        assertEquals(Permission.All, Permission.parse("rwt"));
        assertEquals(Permission.ReadWrite, Permission.parse("rwT"));
    }

    @SmallTest
    public void testGetSymbols() throws Exception
    {
        assertEquals("---", Permission.None.getSymbols());
        assertEquals("--x", Permission.Execute.getSymbols());
        assertEquals("-w-", Permission.Write.getSymbols());
        assertEquals("-wx", Permission.WriteExecute.getSymbols());
        assertEquals("r--", Permission.Read.getSymbols());
        assertEquals("r-x", Permission.ReadExecute.getSymbols());
        assertEquals("rw-", Permission.ReadWrite.getSymbols());
        assertEquals("rwx", Permission.All.getSymbols());
    }

    @SmallTest
    public void testHasAccess() throws Exception
    {
        assertEquals(true, Permission.None.hasAccess(Permission.None));
        assertEquals(false, Permission.None.hasAccess(Permission.Execute));
        assertEquals(false, Permission.None.hasAccess(Permission.Write));
        assertEquals(false, Permission.None.hasAccess(Permission.WriteExecute));
        assertEquals(false, Permission.None.hasAccess(Permission.Read));
        assertEquals(false, Permission.None.hasAccess(Permission.ReadExecute));
        assertEquals(false, Permission.None.hasAccess(Permission.ReadWrite));
        assertEquals(false, Permission.None.hasAccess(Permission.All));

        assertEquals(true, Permission.Execute.hasAccess(Permission.None));
        assertEquals(true, Permission.Execute.hasAccess(Permission.Execute));
        assertEquals(false, Permission.Execute.hasAccess(Permission.Write));
        assertEquals(false, Permission.Execute.hasAccess(Permission.WriteExecute));
        assertEquals(false, Permission.Execute.hasAccess(Permission.Read));
        assertEquals(false, Permission.Execute.hasAccess(Permission.ReadExecute));
        assertEquals(false, Permission.Execute.hasAccess(Permission.ReadWrite));
        assertEquals(false, Permission.Execute.hasAccess(Permission.All));

        assertEquals(true, Permission.Write.hasAccess(Permission.None));
        assertEquals(false, Permission.Write.hasAccess(Permission.Execute));
        assertEquals(true, Permission.Write.hasAccess(Permission.Write));
        assertEquals(false, Permission.Write.hasAccess(Permission.WriteExecute));
        assertEquals(false, Permission.Write.hasAccess(Permission.Read));
        assertEquals(false, Permission.Write.hasAccess(Permission.ReadExecute));
        assertEquals(false, Permission.Write.hasAccess(Permission.ReadWrite));
        assertEquals(false, Permission.Write.hasAccess(Permission.All));

        assertEquals(true, Permission.WriteExecute.hasAccess(Permission.None));
        assertEquals(true, Permission.WriteExecute.hasAccess(Permission.Execute));
        assertEquals(true, Permission.WriteExecute.hasAccess(Permission.Write));
        assertEquals(true, Permission.WriteExecute.hasAccess(Permission.WriteExecute));
        assertEquals(false, Permission.WriteExecute.hasAccess(Permission.Read));
        assertEquals(false, Permission.WriteExecute.hasAccess(Permission.ReadExecute));
        assertEquals(false, Permission.WriteExecute.hasAccess(Permission.ReadWrite));
        assertEquals(false, Permission.WriteExecute.hasAccess(Permission.All));

        assertEquals(true, Permission.Read.hasAccess(Permission.None));
        assertEquals(false, Permission.Read.hasAccess(Permission.Execute));
        assertEquals(false, Permission.Read.hasAccess(Permission.Write));
        assertEquals(false, Permission.Read.hasAccess(Permission.WriteExecute));
        assertEquals(true, Permission.Read.hasAccess(Permission.Read));
        assertEquals(false, Permission.Read.hasAccess(Permission.ReadExecute));
        assertEquals(false, Permission.Read.hasAccess(Permission.ReadWrite));
        assertEquals(false, Permission.Read.hasAccess(Permission.All));

        assertEquals(true, Permission.ReadExecute.hasAccess(Permission.None));
        assertEquals(true, Permission.ReadExecute.hasAccess(Permission.Execute));
        assertEquals(false, Permission.ReadExecute.hasAccess(Permission.Write));
        assertEquals(false, Permission.ReadExecute.hasAccess(Permission.WriteExecute));
        assertEquals(true, Permission.ReadExecute.hasAccess(Permission.Read));
        assertEquals(true, Permission.ReadExecute.hasAccess(Permission.ReadExecute));
        assertEquals(false, Permission.ReadExecute.hasAccess(Permission.ReadWrite));
        assertEquals(false, Permission.ReadExecute.hasAccess(Permission.All));

        assertEquals(true, Permission.ReadWrite.hasAccess(Permission.None));
        assertEquals(false, Permission.ReadWrite.hasAccess(Permission.Execute));
        assertEquals(true, Permission.ReadWrite.hasAccess(Permission.Write));
        assertEquals(false, Permission.ReadWrite.hasAccess(Permission.WriteExecute));
        assertEquals(true, Permission.ReadWrite.hasAccess(Permission.Read));
        assertEquals(false, Permission.ReadWrite.hasAccess(Permission.ReadExecute));
        assertEquals(true, Permission.ReadWrite.hasAccess(Permission.ReadWrite));
        assertEquals(false, Permission.ReadWrite.hasAccess(Permission.All));

        assertEquals(true, Permission.All.hasAccess(Permission.None));
        assertEquals(true, Permission.All.hasAccess(Permission.Execute));
        assertEquals(true, Permission.All.hasAccess(Permission.Write));
        assertEquals(true, Permission.All.hasAccess(Permission.WriteExecute));
        assertEquals(true, Permission.All.hasAccess(Permission.Read));
        assertEquals(true, Permission.All.hasAccess(Permission.ReadExecute));
        assertEquals(true, Permission.All.hasAccess(Permission.ReadWrite));
        assertEquals(true, Permission.All.hasAccess(Permission.All));
    }

    @SmallTest
    public void testCanRead() throws Exception
    {
        assertEquals(false, Permission.None.canRead());
        assertEquals(false, Permission.Execute.canRead());
        assertEquals(false, Permission.Write.canRead());
        assertEquals(false, Permission.WriteExecute.canRead());
        assertEquals(true, Permission.Read.canRead());
        assertEquals(true, Permission.ReadExecute.canRead());
        assertEquals(true, Permission.ReadWrite.canRead());
        assertEquals(true, Permission.All.canRead());
    }

    @SmallTest
    public void testCanWrite() throws Exception
    {
        assertEquals(false, Permission.None.canWrite());
        assertEquals(false, Permission.Execute.canWrite());
        assertEquals(true, Permission.Write.canWrite());
        assertEquals(true, Permission.WriteExecute.canWrite());
        assertEquals(false, Permission.Read.canWrite());
        assertEquals(false, Permission.ReadExecute.canWrite());
        assertEquals(true, Permission.ReadWrite.canWrite());
        assertEquals(true, Permission.All.canWrite());
    }

    @SmallTest
    public void testCanExecute() throws Exception
    {
        assertEquals(false, Permission.None.canExecute());
        assertEquals(true, Permission.Execute.canExecute());
        assertEquals(false, Permission.Write.canExecute());
        assertEquals(true, Permission.WriteExecute.canExecute());
        assertEquals(false, Permission.Read.canExecute());
        assertEquals(true, Permission.ReadExecute.canExecute());
        assertEquals(false, Permission.ReadWrite.canExecute());
        assertEquals(true, Permission.All.canExecute());
    }

    @SmallTest
    public void testToHexString() throws Exception
    {
        assertEquals("0", Permission.None.toOctalString());
        assertEquals("1", Permission.Execute.toOctalString());
        assertEquals("2", Permission.Write.toOctalString());
        assertEquals("3", Permission.WriteExecute.toOctalString());
        assertEquals("4", Permission.Read.toOctalString());
        assertEquals("5", Permission.ReadExecute.toOctalString());
        assertEquals("6", Permission.ReadWrite.toOctalString());
        assertEquals("7", Permission.All.toOctalString());
    }
}
