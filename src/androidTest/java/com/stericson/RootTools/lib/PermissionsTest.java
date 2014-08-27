package com.stericson.RootTools.lib;

import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.TestCase;

import java.util.HashSet;
import java.util.Set;

public class PermissionsTest extends TestCase
{
    private static void assertEquals(Permission expectedUser, Permission expectedGroup, Permission expectedOthers, boolean expectedSetUserId, boolean expectedSetGroupId, boolean expectedIsSticky, Permissions actual)
    {
        assertEquals(expectedUser, actual.user);
        assertEquals(expectedGroup, actual.group);
        assertEquals(expectedOthers, actual.others);
        assertEquals(expectedSetUserId, actual.setUserId);
        assertEquals(expectedSetGroupId, actual.setGroupId);
        assertEquals(expectedIsSticky, actual.isSticky);
    }

    @SmallTest
    public void testConstructor_6() throws Exception
    {
        assertEquals(Permission.None, Permission.None, Permission.None, false, false, true, new Permissions(
            Permission.None, Permission.None, Permission.None, false, false, true));

        assertEquals(Permission.Execute, Permission.Execute, Permission.Execute, false, true, false, new Permissions(
            Permission.Execute, Permission.Execute, Permission.Execute, false, true, false));

        assertEquals(Permission.Write, Permission.Write, Permission.Write, false, true, true, new Permissions(
            Permission.Write, Permission.Write, Permission.Write, false, true, true));

        assertEquals(Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, true, false, false, new Permissions(
            Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, true, false, false));

        assertEquals(Permission.Read, Permission.Read, Permission.Read, true, false, true, new Permissions(
            Permission.Read, Permission.Read, Permission.Read, true, false, true));

        assertEquals(Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, true, false, new Permissions(
            Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, true, false));

        assertEquals(Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, true, new Permissions(
            Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, true));

        assertEquals(Permission.Execute, Permission.Execute, Permission.Execute, false, false, true, new Permissions(
            Permission.Execute, Permission.Execute, Permission.Execute, false, false, true));

        assertEquals(Permission.Write, Permission.Write, Permission.Write, false, true, false, new Permissions(
            Permission.Write, Permission.Write, Permission.Write, false, true, false));

        assertEquals(Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, true, true, new Permissions(
            Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, true, true));

        assertEquals(Permission.Read, Permission.Read, Permission.Read, true, false, false, new Permissions(
            Permission.Read, Permission.Read, Permission.Read, true, false, false));

        assertEquals(Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, false, true, new Permissions(
            Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, false, true));

        assertEquals(Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, false, new Permissions(
            Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, false));

        assertEquals(Permission.All, Permission.All, Permission.All, true, true, true, new Permissions(
            Permission.All, Permission.All, Permission.All, true, true, true));
    }

    @SmallTest
    public void testConstructor_3() throws Exception
    {
        assertEquals(Permission.None, Permission.None, Permission.None, false, false, false, new Permissions(
            Permission.None, Permission.None, Permission.None));

        assertEquals(Permission.Execute, Permission.Execute, Permission.Execute, false, false, false, new Permissions(
            Permission.Execute, Permission.Execute, Permission.Execute));

        assertEquals(Permission.Write, Permission.Write, Permission.Write, false, false, false, new Permissions(
            Permission.Write, Permission.Write, Permission.Write));

        assertEquals(Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, false, false, new Permissions(
            Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute));

        assertEquals(Permission.Read, Permission.Read, Permission.Read, false, false, false, new Permissions(
            Permission.Read, Permission.Read, Permission.Read));

        assertEquals(Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, false, false, false, new Permissions(
            Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute));

        assertEquals(Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, false, false, false, new Permissions(
            Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite));

        assertEquals(Permission.Execute, Permission.Execute, Permission.Execute, false, false, false, new Permissions(
            Permission.Execute, Permission.Execute, Permission.Execute));

        assertEquals(Permission.Write, Permission.Write, Permission.Write, false, false, false, new Permissions(
            Permission.Write, Permission.Write, Permission.Write));

        assertEquals(Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, false, false, new Permissions(
            Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute));

        assertEquals(Permission.Read, Permission.Read, Permission.Read, false, false, false, new Permissions(
            Permission.Read, Permission.Read, Permission.Read));

        assertEquals(Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, false, false, false, new Permissions(
            Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute));

        assertEquals(Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, false, false, false, new Permissions(
            Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite));

        assertEquals(Permission.All, Permission.All, Permission.All, false, false, false, new Permissions(
            Permission.All, Permission.All, Permission.All));
    }

    @SmallTest
    public void testConstructor_1() throws Exception
    {
        assertEquals(Permission.None, Permission.None, Permission.None, false, false, false, new Permissions(Permission.None));
        assertEquals(Permission.Execute, Permission.Execute, Permission.Execute, false, false, false, new Permissions(Permission.Execute));
        assertEquals(Permission.Write, Permission.Write, Permission.Write, false, false, false, new Permissions(Permission.Write));
        assertEquals(Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, false, false, new Permissions(Permission.WriteExecute));
        assertEquals(Permission.Read, Permission.Read, Permission.Read, false, false, false, new Permissions(Permission.Read));
        assertEquals(Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, false, false, false, new Permissions(Permission.ReadExecute));
        assertEquals(Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, false, false, false, new Permissions(Permission.ReadWrite));
        assertEquals(Permission.All, Permission.All, Permission.All, false, false, false, new Permissions(Permission.All));
    }

    @SmallTest
    public void testValueOf() throws Exception
    {
        assertEquals(Permission.None, Permission.None, Permission.None, false, false, false, Permissions.valueOf(0x0000));
        assertEquals(Permission.None, Permission.None, Permission.Execute, false, false, false, Permissions.valueOf(0x0001));
        assertEquals(Permission.None, Permission.None, Permission.Write, false, false, false, Permissions.valueOf(0x0002));
        assertEquals(Permission.None, Permission.None, Permission.WriteExecute, false, false, false, Permissions.valueOf(0x0003));
        assertEquals(Permission.None, Permission.None, Permission.Read, false, false, false, Permissions.valueOf(0x0004));
        assertEquals(Permission.None, Permission.None, Permission.ReadExecute, false, false, false, Permissions.valueOf(0x0005));
        assertEquals(Permission.None, Permission.None, Permission.ReadWrite, false, false, false, Permissions.valueOf(0x0006));
        assertEquals(Permission.None, Permission.None, Permission.All, false, false, false, Permissions.valueOf(0x0007));
        assertEquals(Permission.None, Permission.Execute, Permission.None, false, false, false, Permissions.valueOf(0x0008));
        assertEquals(Permission.None, Permission.Write, Permission.None, false, false, false, Permissions.valueOf(0x0010));
        assertEquals(Permission.None, Permission.WriteExecute, Permission.None, false, false, false, Permissions.valueOf(0x0018));
        assertEquals(Permission.None, Permission.Read, Permission.None, false, false, false, Permissions.valueOf(0x0020));
        assertEquals(Permission.None, Permission.ReadExecute, Permission.None, false, false, false, Permissions.valueOf(0x0028));
        assertEquals(Permission.None, Permission.ReadWrite, Permission.None, false, false, false, Permissions.valueOf(0x0030));
        assertEquals(Permission.None, Permission.All, Permission.None, false, false, false, Permissions.valueOf(0x0038));
        assertEquals(Permission.Execute, Permission.None, Permission.None, false, false, false, Permissions.valueOf(0x0040));
        assertEquals(Permission.Write, Permission.None, Permission.None, false, false, false, Permissions.valueOf(0x0080));
        assertEquals(Permission.WriteExecute, Permission.None, Permission.None, false, false, false, Permissions.valueOf(0x00C0));
        assertEquals(Permission.Read, Permission.None, Permission.None, false, false, false, Permissions.valueOf(0x0100));
        assertEquals(Permission.ReadExecute, Permission.None, Permission.None, false, false, false, Permissions.valueOf(0x0140));
        assertEquals(Permission.ReadWrite, Permission.None, Permission.None, false, false, false, Permissions.valueOf(0x0180));
        assertEquals(Permission.All, Permission.None, Permission.None, false, false, false, Permissions.valueOf(0x01C0));
        assertEquals(Permission.None, Permission.None, Permission.None, false, false, true, Permissions.valueOf(0x0200));
        assertEquals(Permission.None, Permission.None, Permission.None, false, true, false, Permissions.valueOf(0x0400));
        assertEquals(Permission.None, Permission.None, Permission.None, false, true, true, Permissions.valueOf(0x0600));
        assertEquals(Permission.None, Permission.None, Permission.None, true, false, false, Permissions.valueOf(0x0800));
        assertEquals(Permission.None, Permission.None, Permission.None, true, false, true, Permissions.valueOf(0x0A00));
        assertEquals(Permission.None, Permission.None, Permission.None, true, true, false, Permissions.valueOf(0x0C00));
        assertEquals(Permission.None, Permission.None, Permission.None, true, true, true, Permissions.valueOf(0x0E00));
        assertEquals(Permission.Execute, Permission.Execute, Permission.Execute, false, false, false, Permissions.valueOf(0x0049));
        assertEquals(Permission.Write, Permission.Write, Permission.Write, false, false, false, Permissions.valueOf(0x0092));
        assertEquals(Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, false, false, Permissions.valueOf(0x00DB));
        assertEquals(Permission.Read, Permission.Read, Permission.Read, false, false, false, Permissions.valueOf(0x0124));
        assertEquals(Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, false, false, false, Permissions.valueOf(0x016D));
        assertEquals(Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, false, false, false, Permissions.valueOf(0x01B6));
        assertEquals(Permission.All, Permission.All, Permission.All, false, false, false, Permissions.valueOf(0x01FF));
        assertEquals(Permission.None, Permission.None, Permission.None, false, false, true, Permissions.valueOf(0x0200));
        assertEquals(Permission.Execute, Permission.Execute, Permission.Execute, false, true, false, Permissions.valueOf(0x0449));
        assertEquals(Permission.Write, Permission.Write, Permission.Write, false, true, true, Permissions.valueOf(0x0692));
        assertEquals(Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, true, false, false, Permissions.valueOf(0x08DB));
        assertEquals(Permission.Read, Permission.Read, Permission.Read, true, false, true, Permissions.valueOf(0x0B24));
        assertEquals(Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, true, false, Permissions.valueOf(0x0D6D));
        assertEquals(Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, true, Permissions.valueOf(0x0FB6));
        assertEquals(Permission.Execute, Permission.Execute, Permission.Execute, false, false, true, Permissions.valueOf(0x0249));
        assertEquals(Permission.Write, Permission.Write, Permission.Write, false, true, false, Permissions.valueOf(0x0492));
        assertEquals(Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, true, true, Permissions.valueOf(0x06DB));
        assertEquals(Permission.Read, Permission.Read, Permission.Read, true, false, false, Permissions.valueOf(0x0924));
        assertEquals(Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, false, true, Permissions.valueOf(0x0B6D));
        assertEquals(Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, false, Permissions.valueOf(0x0DB6));
        assertEquals(Permission.All, Permission.All, Permission.All, true, true, true, Permissions.valueOf(0x0FFF));
    }

    @SmallTest
    public void testParse() throws Exception
    {
        assertEquals(Permission.None, Permission.None, Permission.None, false, false, false, Permissions.parse("---------"));
        assertEquals(Permission.Execute, Permission.Execute, Permission.Execute, false, false, false, Permissions.parse("--x--x--x"));
        assertEquals(Permission.Write, Permission.Write, Permission.Write, false, false, false, Permissions.parse("-w--w--w-"));
        assertEquals(Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, false, false, Permissions.parse("-wx-wx-wx"));
        assertEquals(Permission.Read, Permission.Read, Permission.Read, false, false, false, Permissions.parse("r--r--r--"));
        assertEquals(Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, false, false, false, Permissions.parse("r-xr-xr-x"));
        assertEquals(Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, false, false, false, Permissions.parse("rw-rw-rw-"));
        assertEquals(Permission.All, Permission.All, Permission.All, false, false, false, Permissions.parse("rwxrwxrwx"));

        assertEquals(Permission.None, Permission.None, Permission.None, false, false, false, Permissions.parse("---------"));
        assertEquals(Permission.Execute, Permission.Execute, Permission.Execute, false, false, false, Permissions.parse("--x--x--x"));
        assertEquals(Permission.Write, Permission.Write, Permission.Write, false, false, false, Permissions.parse("-w--w--w-"));
        assertEquals(Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, false, false, Permissions.parse("-wx-wx-wx"));
        assertEquals(Permission.Read, Permission.Read, Permission.Read, false, false, false, Permissions.parse("r--r--r--"));
        assertEquals(Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, false, false, false, Permissions.parse("r-xr-xr-x"));
        assertEquals(Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, false, false, false, Permissions.parse("rw-rw-rw-"));
        assertEquals(Permission.All, Permission.All, Permission.All, false, false, false, Permissions.parse("rwxrwxrwx"));

        assertEquals(Permission.None, Permission.None, Permission.None, false, false, true, Permissions.parse("--------T"));
        assertEquals(Permission.Execute, Permission.Execute, Permission.Execute, false, true, false, Permissions.parse("--x--s--x"));
        assertEquals(Permission.Write, Permission.Write, Permission.Write, false, true, true, Permissions.parse("-w--wS-wT"));
        assertEquals(Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, true, false, false, Permissions.parse("-ws-wx-wx"));
        assertEquals(Permission.Read, Permission.Read, Permission.Read, true, false, true, Permissions.parse("r-Sr--r-T"));
        assertEquals(Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, true, false, Permissions.parse("r-sr-sr-x"));
        assertEquals(Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, true, Permissions.parse("rwSrwSrwT"));

        assertEquals(Permission.Execute, Permission.Execute, Permission.Execute, false, false, true, Permissions.parse("--x--x--t"));
        assertEquals(Permission.Write, Permission.Write, Permission.Write, false, true, false, Permissions.parse("-w--wS-w-"));
        assertEquals(Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, true, true, Permissions.parse("-wx-ws-wt"));
        assertEquals(Permission.Read, Permission.Read, Permission.Read, true, false, false, Permissions.parse("r-Sr--r--"));
        assertEquals(Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, false, true, Permissions.parse("r-sr-xr-t"));
        assertEquals(Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, false, Permissions.parse("rwSrwSrw-"));
        assertEquals(Permission.All, Permission.All, Permission.All, true, true, true, Permissions.parse("rwsrwsrwt"));
    }

    @SmallTest
    public void testGetValue() throws Exception
    {
        assertEquals(0x0000, new Permissions(Permission.None, Permission.None, Permission.None, false, false, false).getValue());
        assertEquals(0x0001, new Permissions(Permission.None, Permission.None, Permission.Execute, false, false, false).getValue());
        assertEquals(0x0002, new Permissions(Permission.None, Permission.None, Permission.Write, false, false, false).getValue());
        assertEquals(0x0003, new Permissions(Permission.None, Permission.None, Permission.WriteExecute, false, false, false).getValue());
        assertEquals(0x0004, new Permissions(Permission.None, Permission.None, Permission.Read, false, false, false).getValue());
        assertEquals(0x0005, new Permissions(Permission.None, Permission.None, Permission.ReadExecute, false, false, false).getValue());
        assertEquals(0x0006, new Permissions(Permission.None, Permission.None, Permission.ReadWrite, false, false, false).getValue());
        assertEquals(0x0007, new Permissions(Permission.None, Permission.None, Permission.All, false, false, false).getValue());
        assertEquals(0x0008, new Permissions(Permission.None, Permission.Execute, Permission.None, false, false, false).getValue());
        assertEquals(0x0010, new Permissions(Permission.None, Permission.Write, Permission.None, false, false, false).getValue());
        assertEquals(0x0018, new Permissions(Permission.None, Permission.WriteExecute, Permission.None, false, false, false).getValue());
        assertEquals(0x0020, new Permissions(Permission.None, Permission.Read, Permission.None, false, false, false).getValue());
        assertEquals(0x0028, new Permissions(Permission.None, Permission.ReadExecute, Permission.None, false, false, false).getValue());
        assertEquals(0x0030, new Permissions(Permission.None, Permission.ReadWrite, Permission.None, false, false, false).getValue());
        assertEquals(0x0038, new Permissions(Permission.None, Permission.All, Permission.None, false, false, false).getValue());
        assertEquals(0x0040, new Permissions(Permission.Execute, Permission.None, Permission.None, false, false, false).getValue());
        assertEquals(0x0080, new Permissions(Permission.Write, Permission.None, Permission.None, false, false, false).getValue());
        assertEquals(0x00C0, new Permissions(Permission.WriteExecute, Permission.None, Permission.None, false, false, false).getValue());
        assertEquals(0x0100, new Permissions(Permission.Read, Permission.None, Permission.None, false, false, false).getValue());
        assertEquals(0x0140, new Permissions(Permission.ReadExecute, Permission.None, Permission.None, false, false, false).getValue());
        assertEquals(0x0180, new Permissions(Permission.ReadWrite, Permission.None, Permission.None, false, false, false).getValue());
        assertEquals(0x01C0, new Permissions(Permission.All, Permission.None, Permission.None, false, false, false).getValue());
        assertEquals(0x0200, new Permissions(Permission.None, Permission.None, Permission.None, false, false, true).getValue());
        assertEquals(0x0400, new Permissions(Permission.None, Permission.None, Permission.None, false, true, false).getValue());
        assertEquals(0x0600, new Permissions(Permission.None, Permission.None, Permission.None, false, true, true).getValue());
        assertEquals(0x0800, new Permissions(Permission.None, Permission.None, Permission.None, true, false, false).getValue());
        assertEquals(0x0A00, new Permissions(Permission.None, Permission.None, Permission.None, true, false, true).getValue());
        assertEquals(0x0C00, new Permissions(Permission.None, Permission.None, Permission.None, true, true, false).getValue());
        assertEquals(0x0E00, new Permissions(Permission.None, Permission.None, Permission.None, true, true, true).getValue());
        assertEquals(0x0049, new Permissions(Permission.Execute, Permission.Execute, Permission.Execute, false, false, false).getValue());
        assertEquals(0x0092, new Permissions(Permission.Write, Permission.Write, Permission.Write, false, false, false).getValue());
        assertEquals(0x00DB, new Permissions(Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, false, false).getValue());
        assertEquals(0x0124, new Permissions(Permission.Read, Permission.Read, Permission.Read, false, false, false).getValue());
        assertEquals(0x016D, new Permissions(Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, false, false, false).getValue());
        assertEquals(0x01B6, new Permissions(Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, false, false, false).getValue());
        assertEquals(0x01FF, new Permissions(Permission.All, Permission.All, Permission.All, false, false, false).getValue());
        assertEquals(0x0200, new Permissions(Permission.None, Permission.None, Permission.None, false, false, true).getValue());
        assertEquals(0x0449, new Permissions(Permission.Execute, Permission.Execute, Permission.Execute, false, true, false).getValue());
        assertEquals(0x0692, new Permissions(Permission.Write, Permission.Write, Permission.Write, false, true, true).getValue());
        assertEquals(0x08DB, new Permissions(Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, true, false, false).getValue());
        assertEquals(0x0B24, new Permissions(Permission.Read, Permission.Read, Permission.Read, true, false, true).getValue());
        assertEquals(0x0D6D, new Permissions(Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, true, false).getValue());
        assertEquals(0x0FB6, new Permissions(Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, true).getValue());
        assertEquals(0x0249, new Permissions(Permission.Execute, Permission.Execute, Permission.Execute, false, false, true).getValue());
        assertEquals(0x0492, new Permissions(Permission.Write, Permission.Write, Permission.Write, false, true, false).getValue());
        assertEquals(0x06DB, new Permissions(Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, true, true).getValue());
        assertEquals(0x0924, new Permissions(Permission.Read, Permission.Read, Permission.Read, true, false, false).getValue());
        assertEquals(0x0B6D, new Permissions(Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, false, true).getValue());
        assertEquals(0x0DB6, new Permissions(Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, false).getValue());
        assertEquals(0x0FFF, new Permissions(Permission.All, Permission.All, Permission.All, true, true, true).getValue());
    }

    @SmallTest
    public void testToOctalString() throws Exception
    {
        assertEquals("0000", new Permissions(Permission.None).toOctalString());
        assertEquals("0111", new Permissions(Permission.Execute).toOctalString());
        assertEquals("0222", new Permissions(Permission.Write).toOctalString());
        assertEquals("0333", new Permissions(Permission.WriteExecute).toOctalString());
        assertEquals("0444", new Permissions(Permission.Read).toOctalString());
        assertEquals("0555", new Permissions(Permission.ReadExecute).toOctalString());
        assertEquals("0666", new Permissions(Permission.ReadWrite).toOctalString());
        assertEquals("0777", new Permissions(Permission.All).toOctalString());

        assertEquals("1000", new Permissions(Permission.None, Permission.None, Permission.None, false, false, true).toOctalString());
        assertEquals("2111", new Permissions(Permission.Execute, Permission.Execute, Permission.Execute, false, true, false).toOctalString());
        assertEquals("3222", new Permissions(Permission.Write, Permission.Write, Permission.Write, false, true, true).toOctalString());
        assertEquals("4333", new Permissions(Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, true, false, false).toOctalString());
        assertEquals("5444", new Permissions(Permission.Read, Permission.Read, Permission.Read, true, false, true).toOctalString());
        assertEquals("6555", new Permissions(Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, true, false).toOctalString());
        assertEquals("7666", new Permissions(Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, true).toOctalString());

        assertEquals("1111", new Permissions(Permission.Execute, Permission.Execute, Permission.Execute, false, false, true).toOctalString());
        assertEquals("2222", new Permissions(Permission.Write, Permission.Write, Permission.Write, false, true, false).toOctalString());
        assertEquals("3333", new Permissions(Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, true, true).toOctalString());
        assertEquals("4444", new Permissions(Permission.Read, Permission.Read, Permission.Read, true, false, false).toOctalString());
        assertEquals("5555", new Permissions(Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, false, true).toOctalString());
        assertEquals("6666", new Permissions(Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, false).toOctalString());
        assertEquals("7777", new Permissions(Permission.All, Permission.All, Permission.All, true, true, true).toOctalString());
    }

    @SmallTest
    public void testGetSymbols() throws Exception
    {
        assertEquals("---------", new Permissions(Permission.None).getSymbols());
        assertEquals("--x--x--x", new Permissions(Permission.Execute).getSymbols());
        assertEquals("-w--w--w-", new Permissions(Permission.Write).getSymbols());
        assertEquals("-wx-wx-wx", new Permissions(Permission.WriteExecute).getSymbols());
        assertEquals("r--r--r--", new Permissions(Permission.Read).getSymbols());
        assertEquals("r-xr-xr-x", new Permissions(Permission.ReadExecute).getSymbols());
        assertEquals("rw-rw-rw-", new Permissions(Permission.ReadWrite).getSymbols());
        assertEquals("rwxrwxrwx", new Permissions(Permission.All).getSymbols());

        assertEquals("--------T", new Permissions(Permission.None, Permission.None, Permission.None, false, false, true).getSymbols());
        assertEquals("--x--s--x", new Permissions(Permission.Execute, Permission.Execute, Permission.Execute, false, true, false).getSymbols());
        assertEquals("-w--wS-wT", new Permissions(Permission.Write, Permission.Write, Permission.Write, false, true, true).getSymbols());
        assertEquals("-ws-wx-wx", new Permissions(Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, true, false, false).getSymbols());
        assertEquals("r-Sr--r-T", new Permissions(Permission.Read, Permission.Read, Permission.Read, true, false, true).getSymbols());
        assertEquals("r-sr-sr-x", new Permissions(Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, true, false).getSymbols());
        assertEquals("rwSrwSrwT", new Permissions(Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, true).getSymbols());

        assertEquals("--x--x--t", new Permissions(Permission.Execute, Permission.Execute, Permission.Execute, false, false, true).getSymbols());
        assertEquals("-w--wS-w-", new Permissions(Permission.Write, Permission.Write, Permission.Write, false, true, false).getSymbols());
        assertEquals("-wx-ws-wt", new Permissions(Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, true, true).getSymbols());
        assertEquals("r-Sr--r--", new Permissions(Permission.Read, Permission.Read, Permission.Read, true, false, false).getSymbols());
        assertEquals("r-sr-xr-t", new Permissions(Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, false, true).getSymbols());
        assertEquals("rwSrwSrw-", new Permissions(Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, false).getSymbols());
        assertEquals("rwsrwsrwt", new Permissions(Permission.All, Permission.All, Permission.All, true, true, true).getSymbols());
    }

    @SmallTest
    public void testToString() throws Exception
    {
        assertEquals("---------", new Permissions(Permission.None).toString());
        assertEquals("--x--x--x", new Permissions(Permission.Execute).toString());
        assertEquals("-w--w--w-", new Permissions(Permission.Write).toString());
        assertEquals("-wx-wx-wx", new Permissions(Permission.WriteExecute).toString());
        assertEquals("r--r--r--", new Permissions(Permission.Read).toString());
        assertEquals("r-xr-xr-x", new Permissions(Permission.ReadExecute).toString());
        assertEquals("rw-rw-rw-", new Permissions(Permission.ReadWrite).toString());
        assertEquals("rwxrwxrwx", new Permissions(Permission.All).toString());

        assertEquals("--------T", new Permissions(Permission.None, Permission.None, Permission.None, false, false, true).toString());
        assertEquals("--x--s--x", new Permissions(Permission.Execute, Permission.Execute, Permission.Execute, false, true, false).toString());
        assertEquals("-w--wS-wT", new Permissions(Permission.Write, Permission.Write, Permission.Write, false, true, true).toString());
        assertEquals("-ws-wx-wx", new Permissions(Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, true, false, false).toString());
        assertEquals("r-Sr--r-T", new Permissions(Permission.Read, Permission.Read, Permission.Read, true, false, true).toString());
        assertEquals("r-sr-sr-x", new Permissions(Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, true, false).toString());
        assertEquals("rwSrwSrwT", new Permissions(Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, true).toString());

        assertEquals("--x--x--t", new Permissions(Permission.Execute, Permission.Execute, Permission.Execute, false, false, true).toString());
        assertEquals("-w--wS-w-", new Permissions(Permission.Write, Permission.Write, Permission.Write, false, true, false).toString());
        assertEquals("-wx-ws-wt", new Permissions(Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, true, true).toString());
        assertEquals("r-Sr--r--", new Permissions(Permission.Read, Permission.Read, Permission.Read, true, false, false).toString());
        assertEquals("r-sr-xr-t", new Permissions(Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, false, true).toString());
        assertEquals("rwSrwSrw-", new Permissions(Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, false).toString());
        assertEquals("rwsrwsrwt", new Permissions(Permission.All, Permission.All, Permission.All, true, true, true).toString());
    }

    @SmallTest
    public void testCompareTo() throws Exception
    {
        assertEquals(0, new Permissions(Permission.None).compareTo(new Permissions(Permission.None)));
        assertEquals(-1, new Permissions(Permission.None).compareTo(new Permissions(Permission.Execute)));
        assertEquals(-1, new Permissions(Permission.None).compareTo(new Permissions(Permission.Write)));
        assertEquals(-1, new Permissions(Permission.None).compareTo(new Permissions(Permission.WriteExecute)));
        assertEquals(-1, new Permissions(Permission.None).compareTo(new Permissions(Permission.Read)));
        assertEquals(-1, new Permissions(Permission.None).compareTo(new Permissions(Permission.ReadExecute)));
        assertEquals(-1, new Permissions(Permission.None).compareTo(new Permissions(Permission.ReadWrite)));
        assertEquals(-1, new Permissions(Permission.None).compareTo(new Permissions(Permission.All)));

        assertEquals(1, new Permissions(Permission.Execute).compareTo(new Permissions(Permission.None)));
        assertEquals(0, new Permissions(Permission.Execute).compareTo(new Permissions(Permission.Execute)));
        assertEquals(-1, new Permissions(Permission.Execute).compareTo(new Permissions(Permission.Write)));
        assertEquals(-1, new Permissions(Permission.Execute).compareTo(new Permissions(Permission.WriteExecute)));
        assertEquals(-1, new Permissions(Permission.Execute).compareTo(new Permissions(Permission.Read)));
        assertEquals(-1, new Permissions(Permission.Execute).compareTo(new Permissions(Permission.ReadExecute)));
        assertEquals(-1, new Permissions(Permission.Execute).compareTo(new Permissions(Permission.ReadWrite)));
        assertEquals(-1, new Permissions(Permission.None).compareTo(new Permissions(Permission.All)));

        assertEquals(1, new Permissions(Permission.Write).compareTo(new Permissions(Permission.None)));
        assertEquals(1, new Permissions(Permission.Write).compareTo(new Permissions(Permission.Execute)));
        assertEquals(0, new Permissions(Permission.Write).compareTo(new Permissions(Permission.Write)));
        assertEquals(-1, new Permissions(Permission.Write).compareTo(new Permissions(Permission.WriteExecute)));
        assertEquals(-1, new Permissions(Permission.Write).compareTo(new Permissions(Permission.Read)));
        assertEquals(-1, new Permissions(Permission.Write).compareTo(new Permissions(Permission.ReadExecute)));
        assertEquals(-1, new Permissions(Permission.Write).compareTo(new Permissions(Permission.ReadWrite)));
        assertEquals(-1, new Permissions(Permission.None).compareTo(new Permissions(Permission.All)));

        assertEquals(1, new Permissions(Permission.WriteExecute).compareTo(new Permissions(Permission.None)));
        assertEquals(1, new Permissions(Permission.WriteExecute).compareTo(new Permissions(Permission.Execute)));
        assertEquals(1, new Permissions(Permission.WriteExecute).compareTo(new Permissions(Permission.Write)));
        assertEquals(0, new Permissions(Permission.WriteExecute).compareTo(new Permissions(Permission.WriteExecute)));
        assertEquals(-1, new Permissions(Permission.WriteExecute).compareTo(new Permissions(Permission.Read)));
        assertEquals(-1, new Permissions(Permission.WriteExecute).compareTo(new Permissions(Permission.ReadExecute)));
        assertEquals(-1, new Permissions(Permission.WriteExecute).compareTo(new Permissions(Permission.ReadWrite)));
        assertEquals(-1, new Permissions(Permission.None).compareTo(new Permissions(Permission.All)));

        assertEquals(1, new Permissions(Permission.Read).compareTo(new Permissions(Permission.None)));
        assertEquals(1, new Permissions(Permission.Read).compareTo(new Permissions(Permission.Execute)));
        assertEquals(1, new Permissions(Permission.Read).compareTo(new Permissions(Permission.Write)));
        assertEquals(1, new Permissions(Permission.Read).compareTo(new Permissions(Permission.WriteExecute)));
        assertEquals(0, new Permissions(Permission.Read).compareTo(new Permissions(Permission.Read)));
        assertEquals(-1, new Permissions(Permission.Read).compareTo(new Permissions(Permission.ReadExecute)));
        assertEquals(-1, new Permissions(Permission.Read).compareTo(new Permissions(Permission.ReadWrite)));
        assertEquals(-1, new Permissions(Permission.None).compareTo(new Permissions(Permission.All)));

        assertEquals(1, new Permissions(Permission.ReadExecute).compareTo(new Permissions(Permission.None)));
        assertEquals(1, new Permissions(Permission.ReadExecute).compareTo(new Permissions(Permission.Execute)));
        assertEquals(1, new Permissions(Permission.ReadExecute).compareTo(new Permissions(Permission.Write)));
        assertEquals(1, new Permissions(Permission.ReadExecute).compareTo(new Permissions(Permission.WriteExecute)));
        assertEquals(1, new Permissions(Permission.ReadExecute).compareTo(new Permissions(Permission.Read)));
        assertEquals(0, new Permissions(Permission.ReadExecute).compareTo(new Permissions(Permission.ReadExecute)));
        assertEquals(-1, new Permissions(Permission.ReadExecute).compareTo(new Permissions(Permission.ReadWrite)));
        assertEquals(-1, new Permissions(Permission.None).compareTo(new Permissions(Permission.All)));

        assertEquals(1, new Permissions(Permission.ReadWrite).compareTo(new Permissions(Permission.None)));
        assertEquals(1, new Permissions(Permission.ReadWrite).compareTo(new Permissions(Permission.Execute)));
        assertEquals(1, new Permissions(Permission.ReadWrite).compareTo(new Permissions(Permission.Write)));
        assertEquals(1, new Permissions(Permission.ReadWrite).compareTo(new Permissions(Permission.WriteExecute)));
        assertEquals(1, new Permissions(Permission.ReadWrite).compareTo(new Permissions(Permission.Read)));
        assertEquals(1, new Permissions(Permission.ReadWrite).compareTo(new Permissions(Permission.ReadExecute)));
        assertEquals(0, new Permissions(Permission.ReadWrite).compareTo(new Permissions(Permission.ReadWrite)));
        assertEquals(-1, new Permissions(Permission.ReadWrite).compareTo(new Permissions(Permission.All)));

        assertEquals(1, new Permissions(Permission.All).compareTo(new Permissions(Permission.None)));
        assertEquals(1, new Permissions(Permission.All).compareTo(new Permissions(Permission.Execute)));
        assertEquals(1, new Permissions(Permission.All).compareTo(new Permissions(Permission.Write)));
        assertEquals(1, new Permissions(Permission.All).compareTo(new Permissions(Permission.WriteExecute)));
        assertEquals(1, new Permissions(Permission.All).compareTo(new Permissions(Permission.Read)));
        assertEquals(1, new Permissions(Permission.All).compareTo(new Permissions(Permission.ReadExecute)));
        assertEquals(1, new Permissions(Permission.All).compareTo(new Permissions(Permission.ReadWrite)));
        assertEquals(0, new Permissions(Permission.All).compareTo(new Permissions(Permission.All)));

        assertEquals(0, new Permissions(Permission.None, Permission.Execute, Permission.Write).compareTo(new Permissions(Permission.None, Permission.Execute, Permission.Write)));
        assertEquals(0, new Permissions(Permission.Execute, Permission.Write, Permission.WriteExecute).compareTo(new Permissions(Permission.Execute, Permission.Write, Permission.WriteExecute)));
        assertEquals(0, new Permissions(Permission.Write, Permission.WriteExecute, Permission.Read).compareTo(new Permissions(Permission.Write, Permission.WriteExecute, Permission.Read)));
        assertEquals(0, new Permissions(Permission.WriteExecute, Permission.Read, Permission.ReadExecute).compareTo(new Permissions(Permission.WriteExecute, Permission.Read, Permission.ReadExecute)));
        assertEquals(0, new Permissions(Permission.Read, Permission.ReadExecute, Permission.ReadWrite).compareTo(new Permissions(Permission.Read, Permission.ReadExecute, Permission.ReadWrite)));
        assertEquals(0, new Permissions(Permission.ReadExecute, Permission.ReadWrite, Permission.All).compareTo(new Permissions(Permission.ReadExecute, Permission.ReadWrite, Permission.All)));
        assertEquals(0, new Permissions(Permission.ReadWrite, Permission.All, Permission.None).compareTo(new Permissions(Permission.ReadWrite, Permission.All, Permission.None)));
        assertEquals(0, new Permissions(Permission.All, Permission.None, Permission.Execute).compareTo(new Permissions(Permission.All, Permission.None, Permission.Execute)));

        assertEquals(-1, new Permissions(Permission.None, Permission.Execute, Permission.Write).compareTo(new Permissions(Permission.Execute, Permission.Write, Permission.WriteExecute)));
        assertEquals(-1, new Permissions(Permission.Execute, Permission.Write, Permission.WriteExecute).compareTo(new Permissions(Permission.Write, Permission.WriteExecute, Permission.Read)));
        assertEquals(-1, new Permissions(Permission.Write, Permission.WriteExecute, Permission.Read).compareTo(new Permissions(Permission.WriteExecute, Permission.Read, Permission.ReadExecute)));
        assertEquals(-1, new Permissions(Permission.WriteExecute, Permission.Read, Permission.ReadExecute).compareTo(new Permissions(Permission.Read, Permission.ReadExecute, Permission.ReadWrite)));
        assertEquals(-1, new Permissions(Permission.Read, Permission.ReadExecute, Permission.ReadWrite).compareTo(new Permissions(Permission.ReadExecute, Permission.ReadWrite, Permission.All)));
        assertEquals(-1, new Permissions(Permission.ReadExecute, Permission.ReadWrite, Permission.All).compareTo(new Permissions(Permission.ReadWrite, Permission.All, Permission.None)));
        assertEquals(-1, new Permissions(Permission.ReadWrite, Permission.All, Permission.None).compareTo(new Permissions(Permission.All, Permission.None, Permission.Execute)));
        assertEquals(1, new Permissions(Permission.All, Permission.None, Permission.Execute).compareTo(new Permissions(Permission.None, Permission.Execute, Permission.Write)));
    }

    @SmallTest
    public void testEquals() throws Exception
    {
        assertTrue(new Permissions(Permission.None).equals(new Permissions(Permission.None)));
        assertFalse(new Permissions(Permission.None).equals(new Permissions(Permission.Execute)));
        assertFalse(new Permissions(Permission.None).equals(new Permissions(Permission.Write)));
        assertFalse(new Permissions(Permission.None).equals(new Permissions(Permission.WriteExecute)));
        assertFalse(new Permissions(Permission.None).equals(new Permissions(Permission.Read)));
        assertFalse(new Permissions(Permission.None).equals(new Permissions(Permission.ReadExecute)));
        assertFalse(new Permissions(Permission.None).equals(new Permissions(Permission.ReadWrite)));
        assertFalse(new Permissions(Permission.None).equals(new Permissions(Permission.All)));

        assertFalse(new Permissions(Permission.Execute).equals(new Permissions(Permission.None)));
        assertTrue(new Permissions(Permission.Execute).equals(new Permissions(Permission.Execute)));
        assertFalse(new Permissions(Permission.Execute).equals(new Permissions(Permission.Write)));
        assertFalse(new Permissions(Permission.Execute).equals(new Permissions(Permission.WriteExecute)));
        assertFalse(new Permissions(Permission.Execute).equals(new Permissions(Permission.Read)));
        assertFalse(new Permissions(Permission.Execute).equals(new Permissions(Permission.ReadExecute)));
        assertFalse(new Permissions(Permission.Execute).equals(new Permissions(Permission.ReadWrite)));
        assertFalse(new Permissions(Permission.None).equals(new Permissions(Permission.All)));

        assertFalse(new Permissions(Permission.Write).equals(new Permissions(Permission.None)));
        assertFalse(new Permissions(Permission.Write).equals(new Permissions(Permission.Execute)));
        assertTrue(new Permissions(Permission.Write).equals(new Permissions(Permission.Write)));
        assertFalse(new Permissions(Permission.Write).equals(new Permissions(Permission.WriteExecute)));
        assertFalse(new Permissions(Permission.Write).equals(new Permissions(Permission.Read)));
        assertFalse(new Permissions(Permission.Write).equals(new Permissions(Permission.ReadExecute)));
        assertFalse(new Permissions(Permission.Write).equals(new Permissions(Permission.ReadWrite)));
        assertFalse(new Permissions(Permission.None).equals(new Permissions(Permission.All)));

        assertFalse(new Permissions(Permission.WriteExecute).equals(new Permissions(Permission.None)));
        assertFalse(new Permissions(Permission.WriteExecute).equals(new Permissions(Permission.Execute)));
        assertFalse(new Permissions(Permission.WriteExecute).equals(new Permissions(Permission.Write)));
        assertTrue(new Permissions(Permission.WriteExecute).equals(new Permissions(Permission.WriteExecute)));
        assertFalse(new Permissions(Permission.WriteExecute).equals(new Permissions(Permission.Read)));
        assertFalse(new Permissions(Permission.WriteExecute).equals(new Permissions(Permission.ReadExecute)));
        assertFalse(new Permissions(Permission.WriteExecute).equals(new Permissions(Permission.ReadWrite)));
        assertFalse(new Permissions(Permission.None).equals(new Permissions(Permission.All)));

        assertFalse(new Permissions(Permission.Read).equals(new Permissions(Permission.None)));
        assertFalse(new Permissions(Permission.Read).equals(new Permissions(Permission.Execute)));
        assertFalse(new Permissions(Permission.Read).equals(new Permissions(Permission.Write)));
        assertFalse(new Permissions(Permission.Read).equals(new Permissions(Permission.WriteExecute)));
        assertTrue(new Permissions(Permission.Read).equals(new Permissions(Permission.Read)));
        assertFalse(new Permissions(Permission.Read).equals(new Permissions(Permission.ReadExecute)));
        assertFalse(new Permissions(Permission.Read).equals(new Permissions(Permission.ReadWrite)));
        assertFalse(new Permissions(Permission.None).equals(new Permissions(Permission.All)));

        assertFalse(new Permissions(Permission.ReadExecute).equals(new Permissions(Permission.None)));
        assertFalse(new Permissions(Permission.ReadExecute).equals(new Permissions(Permission.Execute)));
        assertFalse(new Permissions(Permission.ReadExecute).equals(new Permissions(Permission.Write)));
        assertFalse(new Permissions(Permission.ReadExecute).equals(new Permissions(Permission.WriteExecute)));
        assertFalse(new Permissions(Permission.ReadExecute).equals(new Permissions(Permission.Read)));
        assertTrue(new Permissions(Permission.ReadExecute).equals(new Permissions(Permission.ReadExecute)));
        assertFalse(new Permissions(Permission.ReadExecute).equals(new Permissions(Permission.ReadWrite)));
        assertFalse(new Permissions(Permission.None).equals(new Permissions(Permission.All)));

        assertFalse(new Permissions(Permission.ReadWrite).equals(new Permissions(Permission.None)));
        assertFalse(new Permissions(Permission.ReadWrite).equals(new Permissions(Permission.Execute)));
        assertFalse(new Permissions(Permission.ReadWrite).equals(new Permissions(Permission.Write)));
        assertFalse(new Permissions(Permission.ReadWrite).equals(new Permissions(Permission.WriteExecute)));
        assertFalse(new Permissions(Permission.ReadWrite).equals(new Permissions(Permission.Read)));
        assertFalse(new Permissions(Permission.ReadWrite).equals(new Permissions(Permission.ReadExecute)));
        assertTrue(new Permissions(Permission.ReadWrite).equals(new Permissions(Permission.ReadWrite)));
        assertFalse(new Permissions(Permission.ReadWrite).equals(new Permissions(Permission.All)));

        assertFalse(new Permissions(Permission.All).equals(new Permissions(Permission.None)));
        assertFalse(new Permissions(Permission.All).equals(new Permissions(Permission.Execute)));
        assertFalse(new Permissions(Permission.All).equals(new Permissions(Permission.Write)));
        assertFalse(new Permissions(Permission.All).equals(new Permissions(Permission.WriteExecute)));
        assertFalse(new Permissions(Permission.All).equals(new Permissions(Permission.Read)));
        assertFalse(new Permissions(Permission.All).equals(new Permissions(Permission.ReadExecute)));
        assertFalse(new Permissions(Permission.All).equals(new Permissions(Permission.ReadWrite)));
        assertTrue(new Permissions(Permission.All).equals(new Permissions(Permission.All)));

        assertTrue(new Permissions(Permission.None, Permission.Execute, Permission.Write).equals(new Permissions(Permission.None, Permission.Execute, Permission.Write)));
        assertTrue(new Permissions(Permission.Execute, Permission.Write, Permission.WriteExecute).equals(new Permissions(Permission.Execute, Permission.Write, Permission.WriteExecute)));
        assertTrue(new Permissions(Permission.Write, Permission.WriteExecute, Permission.Read).equals(new Permissions(Permission.Write, Permission.WriteExecute, Permission.Read)));
        assertTrue(new Permissions(Permission.WriteExecute, Permission.Read, Permission.ReadExecute).equals(new Permissions(Permission.WriteExecute, Permission.Read, Permission.ReadExecute)));
        assertTrue(new Permissions(Permission.Read, Permission.ReadExecute, Permission.ReadWrite).equals(new Permissions(Permission.Read, Permission.ReadExecute, Permission.ReadWrite)));
        assertTrue(new Permissions(Permission.ReadExecute, Permission.ReadWrite, Permission.All).equals(new Permissions(Permission.ReadExecute, Permission.ReadWrite, Permission.All)));
        assertTrue(new Permissions(Permission.ReadWrite, Permission.All, Permission.None).equals(new Permissions(Permission.ReadWrite, Permission.All, Permission.None)));
        assertTrue(new Permissions(Permission.All, Permission.None, Permission.Execute).equals(new Permissions(Permission.All, Permission.None, Permission.Execute)));

        assertFalse(new Permissions(Permission.None, Permission.Execute, Permission.Write).equals(new Permissions(Permission.Execute, Permission.Write, Permission.WriteExecute)));
        assertFalse(new Permissions(Permission.Execute, Permission.Write, Permission.WriteExecute).equals(new Permissions(Permission.Write, Permission.WriteExecute, Permission.Read)));
        assertFalse(new Permissions(Permission.Write, Permission.WriteExecute, Permission.Read).equals(new Permissions(Permission.WriteExecute, Permission.Read, Permission.ReadExecute)));
        assertFalse(new Permissions(Permission.WriteExecute, Permission.Read, Permission.ReadExecute).equals(new Permissions(Permission.Read, Permission.ReadExecute, Permission.ReadWrite)));
        assertFalse(new Permissions(Permission.Read, Permission.ReadExecute, Permission.ReadWrite).equals(new Permissions(Permission.ReadExecute, Permission.ReadWrite, Permission.All)));
        assertFalse(new Permissions(Permission.ReadExecute, Permission.ReadWrite, Permission.All).equals(new Permissions(Permission.ReadWrite, Permission.All, Permission.None)));
        assertFalse(new Permissions(Permission.ReadWrite, Permission.All, Permission.None).equals(new Permissions(Permission.All, Permission.None, Permission.Execute)));
        assertFalse(new Permissions(Permission.All, Permission.None, Permission.Execute).equals(new Permissions(Permission.None, Permission.Execute, Permission.Write)));
    }

    @SmallTest
    public void testHashCode() throws Exception
    {
        boolean[] bools = { false, true };
        Permission[] permissions = {
            Permission.None,
            Permission.Execute,
            Permission.Write,
            Permission.WriteExecute,
            Permission.Read,
            Permission.ReadExecute,
            Permission.ReadWrite,
            Permission.All
        };

        Set<Permissions> set = new HashSet<Permissions>();

        for (Permission user : permissions)
        for (Permission group : permissions)
        for (Permission others : permissions)
        for (boolean setUserId : bools)
        for (boolean setGroupId : bools)
        for (boolean isSticky : bools)
        {
            Permissions permissions1 = new Permissions(user, group, others, setUserId, setGroupId, isSticky);
            Permissions permissions2 = new Permissions(user, group, others, setUserId, setGroupId, isSticky);

            assertEquals(permissions1.hashCode(), permissions2.hashCode());
            assertTrue(set.add(permissions1));
            assertFalse(set.add(permissions2));
        }

        assertEquals(4096, set.size());
    }
}