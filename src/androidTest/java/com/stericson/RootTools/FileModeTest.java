package com.stericson.RootTools;

import android.test.suitebuilder.annotation.SmallTest;

import com.stericson.RootTools.FileMode;
import com.stericson.RootTools.FileType;
import com.stericson.RootTools.Permission;
import com.stericson.RootTools.Permissions;

import junit.framework.TestCase;

import java.util.HashSet;
import java.util.Set;

public class FileModeTest extends TestCase
{
    static void assertEquals(String message, FileType expectedType, Permission expectedUser, Permission expectedGroup, Permission expectedOthers, boolean expectedSetUserId, boolean expectedSetGroupId, boolean expectedIsSticky, FileMode actual)
    {
        assertEquals(message, expectedType, actual.type);
        assertEquals(message, expectedUser, actual.permissions.user);
        assertEquals(message, expectedGroup, actual.permissions.group);
        assertEquals(message, expectedOthers, actual.permissions.others);
        assertEquals(message, expectedSetUserId, actual.permissions.setUserId);
        assertEquals(message, expectedSetGroupId, actual.permissions.setGroupId);
        assertEquals(message, expectedIsSticky, actual.permissions.isSticky);
    }

    static void assertEquals(String message, FileMode expected, FileMode actual)
    {
        assertEquals(message, expected.type, expected.permissions.user, expected.permissions.group, expected.permissions.others,
                     expected.permissions.setUserId, expected.permissions.setGroupId, expected.permissions.isSticky, actual);
        assertEquals(message, expected.getValue(), actual.getValue());
        assertEquals(message, expected.toString(), actual.toString());
        assertEquals(message, expected.hashCode(), actual.hashCode());
    }

    @SmallTest
    public void testConstructor_2() throws Exception
    {
        assertEquals("FileType.NamedPipe, Permission.None, Permission.None, Permission.None, false, false, true",
                     FileType.NamedPipe, Permission.None, Permission.None, Permission.None, false, false, true,
                     new FileMode(FileType.NamedPipe, new Permissions(Permission.None, Permission.None, Permission.None, false, false, true)));

        assertEquals("FileType.CharacterDevice, Permission.Execute, Permission.Execute, Permission.Execute, false, true, false",
                     FileType.CharacterDevice, Permission.Execute, Permission.Execute, Permission.Execute, false, true, false,
                     new FileMode(FileType.CharacterDevice, new Permissions(Permission.Execute, Permission.Execute, Permission.Execute, false, true, false)));

        assertEquals("FileType.Directory, Permission.Write, Permission.Write, Permission.Write, false, true, true",
                     FileType.Directory, Permission.Write, Permission.Write, Permission.Write, false, true, true,
                     new FileMode(FileType.Directory, new Permissions(Permission.Write, Permission.Write, Permission.Write, false, true, true)));

        assertEquals("FileType.BlockDevice, Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, true, false, false",
                     FileType.BlockDevice, Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, true, false, false,
                     new FileMode(FileType.BlockDevice, new Permissions(Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, true, false, false)));

        assertEquals("FileType.File, Permission.Read, Permission.Read, Permission.Read, true, false, true",
                     FileType.File, Permission.Read, Permission.Read, Permission.Read, true, false, true,
                     new FileMode(FileType.File, new Permissions(Permission.Read, Permission.Read, Permission.Read, true, false, true)));

        assertEquals("FileType.SymbolicLink, Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, true, false",
                     FileType.SymbolicLink, Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, true, false,
                     new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, true, false)));

        assertEquals("FileType.Socket, Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, true",
                     FileType.Socket, Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, true,
                     new FileMode(FileType.Socket, new Permissions(Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, true)));

        assertEquals("FileType.NamedPipe, Permission.Execute, Permission.Execute, Permission.Execute, false, false, true",
                     FileType.NamedPipe, Permission.Execute, Permission.Execute, Permission.Execute, false, false, true,
                     new FileMode(FileType.NamedPipe, new Permissions(Permission.Execute, Permission.Execute, Permission.Execute, false, false, true)));

        assertEquals("FileType.CharacterDevice, Permission.Write, Permission.Write, Permission.Write, false, true, false",
                     FileType.CharacterDevice, Permission.Write, Permission.Write, Permission.Write, false, true, false,
                     new FileMode(FileType.CharacterDevice, new Permissions(Permission.Write, Permission.Write, Permission.Write, false, true, false)));

        assertEquals("FileType.Directory, Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, true, true",
                     FileType.Directory, Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, true, true,
                     new FileMode(FileType.Directory, new Permissions(Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, true, true)));

        assertEquals("FileType.BlockDevice, Permission.Read, Permission.Read, Permission.Read, true, false, false",
                     FileType.BlockDevice, Permission.Read, Permission.Read, Permission.Read, true, false, false,
                     new FileMode(FileType.BlockDevice, new Permissions(Permission.Read, Permission.Read, Permission.Read, true, false, false)));

        assertEquals("FileType.File, Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, false, true",
                     FileType.File, Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, false, true,
                     new FileMode(FileType.File, new Permissions(Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, false, true)));

        assertEquals("FileType.SymbolicLink, Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, false",
                     FileType.SymbolicLink, Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, false,
                     new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, false)));

        assertEquals("FileType.Socket, Permission.All, Permission.All, Permission.All, true, true, true",
                     FileType.Socket, Permission.All, Permission.All, Permission.All, true, true, true,
                     new FileMode(FileType.Socket, new Permissions(Permission.All, Permission.All, Permission.All, true, true, true)));
    }

    @SmallTest
    public void testConstructor_7() throws Exception
    {
        assertEquals("FileType.NamedPipe, Permission.None, Permission.None, Permission.None, false, false, true",
                     FileType.NamedPipe, Permission.None, Permission.None, Permission.None, false, false, true,
                     new FileMode(FileType.NamedPipe, Permission.None, Permission.None, Permission.None, false, false, true));

        assertEquals("new FileMode(FileType.CharacterDevice, Permission.Execute, Permission.Execute, Permission.Execute, false, true, false",
                     FileType.CharacterDevice, Permission.Execute, Permission.Execute, Permission.Execute, false, true, false,
                     new FileMode(FileType.CharacterDevice, Permission.Execute, Permission.Execute, Permission.Execute, false, true, false));

        assertEquals("FileType.Directory, Permission.Write, Permission.Write, Permission.Write, false, true, true",
                     FileType.Directory, Permission.Write, Permission.Write, Permission.Write, false, true, true,
                     new FileMode(FileType.Directory, Permission.Write, Permission.Write, Permission.Write, false, true, true));

        assertEquals("FileType.BlockDevice, Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, true, false, false",
                     FileType.BlockDevice, Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, true, false, false,
                     new FileMode(FileType.BlockDevice, Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, true, false, false));

        assertEquals("FileType.File, Permission.Read, Permission.Read, Permission.Read, true, false, true",
                     FileType.File, Permission.Read, Permission.Read, Permission.Read, true, false, true,
                     new FileMode(FileType.File, Permission.Read, Permission.Read, Permission.Read, true, false, true));

        assertEquals("FileType.SymbolicLink, Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, true, false",
                     FileType.SymbolicLink, Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, true, false,
                     new FileMode(FileType.SymbolicLink, Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, true, false));

        assertEquals("FileType.Socket, Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, true",
                     FileType.Socket, Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, true,
                     new FileMode(FileType.Socket, Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, true));

        assertEquals("FileType.NamedPipe, Permission.Execute, Permission.Execute, Permission.Execute, false, false, true",
                     FileType.NamedPipe, Permission.Execute, Permission.Execute, Permission.Execute, false, false, true,
                     new FileMode(FileType.NamedPipe, Permission.Execute, Permission.Execute, Permission.Execute, false, false, true));

        assertEquals("FileType.CharacterDevice, Permission.Write, Permission.Write, Permission.Write, false, true, false",
                     FileType.CharacterDevice, Permission.Write, Permission.Write, Permission.Write, false, true, false,
                     new FileMode(FileType.CharacterDevice, Permission.Write, Permission.Write, Permission.Write, false, true, false));

        assertEquals("FileType.Directory, Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, true, true",
                     FileType.Directory, Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, true, true,
                     new FileMode(FileType.Directory, Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, true, true));

        assertEquals("FileType.BlockDevice, Permission.Read, Permission.Read, Permission.Read, true, false, false",
                     FileType.BlockDevice, Permission.Read, Permission.Read, Permission.Read, true, false, false,
                     new FileMode(FileType.BlockDevice, Permission.Read, Permission.Read, Permission.Read, true, false, false));

        assertEquals("FileType.File, Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, false, true",
                     FileType.File, Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, false, true,
                     new FileMode(FileType.File, Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, false, true));

        assertEquals("FileType.SymbolicLink, Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, false",
                     FileType.SymbolicLink, Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, false,
                     new FileMode(FileType.SymbolicLink, Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, false));

        assertEquals("FileType.Socket, Permission.All, Permission.All, Permission.All, true, true, true",
                     FileType.Socket, Permission.All, Permission.All, Permission.All, true, true, true,
                     new FileMode(FileType.Socket, Permission.All, Permission.All, Permission.All, true, true, true));
    }

    @SmallTest
    public void testConstructor_4() throws Exception
    {
        assertEquals("FileType.NamedPipe, Permission.None, Permission.None, Permission.None",
                     FileType.NamedPipe, Permission.None, Permission.None, Permission.None, false, false, false,
                     new FileMode(FileType.NamedPipe, Permission.None, Permission.None, Permission.None));

        assertEquals("new FileMode(FileType.CharacterDevice, Permission.Execute, Permission.Execute, Permission.Execute",
                     FileType.CharacterDevice, Permission.Execute, Permission.Execute, Permission.Execute, false, false, false,
                     new FileMode(FileType.CharacterDevice, Permission.Execute, Permission.Execute, Permission.Execute));

        assertEquals("FileType.Directory, Permission.Write, Permission.Write, Permission.Write",
                     FileType.Directory, Permission.Write, Permission.Write, Permission.Write, false, false, false,
                     new FileMode(FileType.Directory, Permission.Write, Permission.Write, Permission.Write));

        assertEquals("FileType.BlockDevice, Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute",
                     FileType.BlockDevice, Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, false, false,
                     new FileMode(FileType.BlockDevice, Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute));

        assertEquals("FileType.File, Permission.Read, Permission.Read, Permission.Read",
                     FileType.File, Permission.Read, Permission.Read, Permission.Read, false, false, false,
                     new FileMode(FileType.File, Permission.Read, Permission.Read, Permission.Read));

        assertEquals("FileType.SymbolicLink, Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute",
                     FileType.SymbolicLink, Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, false, false, false,
                     new FileMode(FileType.SymbolicLink, Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute));

        assertEquals("FileType.Socket, Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite",
                     FileType.Socket, Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, false, false, false,
                     new FileMode(FileType.Socket, Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite));

        assertEquals("FileType.NamedPipe, Permission.Execute, Permission.Execute, Permission.Execute",
                     FileType.NamedPipe, Permission.Execute, Permission.Execute, Permission.Execute, false, false, false,
                     new FileMode(FileType.NamedPipe, Permission.Execute, Permission.Execute, Permission.Execute));

        assertEquals("FileType.CharacterDevice, Permission.Write, Permission.Write, Permission.Write",
                     FileType.CharacterDevice, Permission.Write, Permission.Write, Permission.Write, false, false, false,
                     new FileMode(FileType.CharacterDevice, Permission.Write, Permission.Write, Permission.Write));

        assertEquals("FileType.Directory, Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute",
                     FileType.Directory, Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, false, false,
                     new FileMode(FileType.Directory, Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute));

        assertEquals("FileType.BlockDevice, Permission.Read, Permission.Read, Permission.Read",
                     FileType.BlockDevice, Permission.Read, Permission.Read, Permission.Read, false, false, false,
                     new FileMode(FileType.BlockDevice, Permission.Read, Permission.Read, Permission.Read));

        assertEquals("FileType.File, Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute",
                     FileType.File, Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, false, false, false,
                     new FileMode(FileType.File, Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute));

        assertEquals("FileType.SymbolicLink, Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite",
                     FileType.SymbolicLink, Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, false, false, false,
                     new FileMode(FileType.SymbolicLink, Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite));

        assertEquals("FileType.Socket, Permission.All, Permission.All, Permission.All",
                     FileType.Socket, Permission.All, Permission.All, Permission.All, false, false, false,
                     new FileMode(FileType.Socket, Permission.All, Permission.All, Permission.All));
    }

    @SmallTest
    public void testValueOf() throws Exception
    {
        assertEquals("FileMode.valueOf(0x0000)", FileType.Unknown, Permission.None, Permission.None, Permission.None, false, false, false, FileMode.valueOf(0x0000));
        assertEquals("FileMode.valueOf(0x0049)", FileType.Unknown, Permission.Execute, Permission.Execute, Permission.Execute, false, false, false, FileMode.valueOf(0x0049));
        assertEquals("FileMode.valueOf(0x0092)", FileType.Unknown, Permission.Write, Permission.Write, Permission.Write, false, false, false, FileMode.valueOf(0x0092));
        assertEquals("FileMode.valueOf(0x00db)", FileType.Unknown, Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, false, false, FileMode.valueOf(0x00db));
        assertEquals("FileMode.valueOf(0x0124)", FileType.Unknown, Permission.Read, Permission.Read, Permission.Read, false, false, false, FileMode.valueOf(0x0124));
        assertEquals("FileMode.valueOf(0x016d)", FileType.Unknown, Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, false, false, false, FileMode.valueOf(0x016d));
        assertEquals("FileMode.valueOf(0x01b6)", FileType.Unknown, Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, false, false, false, FileMode.valueOf(0x01b6));
        assertEquals("FileMode.valueOf(0x01ff)", FileType.Unknown, Permission.All, Permission.All, Permission.All, false, false, false, FileMode.valueOf(0x01ff));

        assertEquals("FileMode.valueOf(0x1049)", FileType.NamedPipe, Permission.Execute, Permission.Execute, Permission.Execute, false, false, false, FileMode.valueOf(0x1049));
        assertEquals("FileMode.valueOf(0x2092)", FileType.CharacterDevice, Permission.Write, Permission.Write, Permission.Write, false, false, false, FileMode.valueOf(0x2092));
        assertEquals("FileMode.valueOf(0x40db)", FileType.Directory, Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, false, false, FileMode.valueOf(0x40db));
        assertEquals("FileMode.valueOf(0x6124)", FileType.BlockDevice, Permission.Read, Permission.Read, Permission.Read, false, false, false, FileMode.valueOf(0x6124));
        assertEquals("FileMode.valueOf(0x816d)", FileType.File, Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, false, false, false, FileMode.valueOf(0x816d));
        assertEquals("FileMode.valueOf(0xa1b6)", FileType.SymbolicLink, Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, false, false, false, FileMode.valueOf(0xa1b6));
        assertEquals("FileMode.valueOf(0xc1ff)", FileType.Socket, Permission.All, Permission.All, Permission.All, false, false, false, FileMode.valueOf(0xc1ff));

        assertEquals("FileMode.valueOf(0x1200)", FileType.NamedPipe, Permission.None, Permission.None, Permission.None, false, false, true, FileMode.valueOf(0x1200));
        assertEquals("FileMode.valueOf(0x2449)", FileType.CharacterDevice, Permission.Execute, Permission.Execute, Permission.Execute, false, true, false, FileMode.valueOf(0x2449));
        assertEquals("FileMode.valueOf(0x4692)", FileType.Directory, Permission.Write, Permission.Write, Permission.Write, false, true, true, FileMode.valueOf(0x4692));
        assertEquals("FileMode.valueOf(0x68db)", FileType.BlockDevice, Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, true, false, false, FileMode.valueOf(0x68db));
        assertEquals("FileMode.valueOf(0x8b24)", FileType.File, Permission.Read, Permission.Read, Permission.Read, true, false, true, FileMode.valueOf(0x8b24));
        assertEquals("FileMode.valueOf(0xad6d)", FileType.SymbolicLink, Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, true, false, FileMode.valueOf(0xad6d));
        assertEquals("FileMode.valueOf(0xcfb6)", FileType.Socket, Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, true, FileMode.valueOf(0xcfb6));

        assertEquals("FileMode.valueOf(0x1249)", FileType.NamedPipe, Permission.Execute, Permission.Execute, Permission.Execute, false, false, true, FileMode.valueOf(0x1249));
        assertEquals("FileMode.valueOf(0x2492)", FileType.CharacterDevice, Permission.Write, Permission.Write, Permission.Write, false, true, false, FileMode.valueOf(0x2492));
        assertEquals("FileMode.valueOf(0x46db)", FileType.Directory, Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, true, true, FileMode.valueOf(0x46db));
        assertEquals("FileMode.valueOf(0x6924)", FileType.BlockDevice, Permission.Read, Permission.Read, Permission.Read, true, false, false, FileMode.valueOf(0x6924));
        assertEquals("FileMode.valueOf(0x8b6d)", FileType.File, Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, false, true, FileMode.valueOf(0x8b6d));
        assertEquals("FileMode.valueOf(0xadb6)", FileType.SymbolicLink, Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, false, FileMode.valueOf(0xadb6));
        assertEquals("FileMode.valueOf(0xcfff)", FileType.Socket, Permission.All, Permission.All, Permission.All, true, true, true, FileMode.valueOf(0xcfff));
    }

    @SmallTest
    public void testParse() throws Exception
    {
        assertEquals("FileMode.parse(\"----------\")", FileType.File, Permission.None, Permission.None, Permission.None, false, false, false, FileMode.parse("----------"));
        assertEquals("FileMode.parse(\"---x--x--x\")", FileType.File, Permission.Execute, Permission.Execute, Permission.Execute, false, false, false, FileMode.parse("---x--x--x"));
        assertEquals("FileMode.parse(\"--w--w--w-\")", FileType.File, Permission.Write, Permission.Write, Permission.Write, false, false, false, FileMode.parse("--w--w--w-"));
        assertEquals("FileMode.parse(\"--wx-wx-wx\")", FileType.File, Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, false, false, FileMode.parse("--wx-wx-wx"));
        assertEquals("FileMode.parse(\"-r--r--r--\")", FileType.File, Permission.Read, Permission.Read, Permission.Read, false, false, false, FileMode.parse("-r--r--r--"));
        assertEquals("FileMode.parse(\"-r-xr-xr-x\")", FileType.File, Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, false, false, false, FileMode.parse("-r-xr-xr-x"));
        assertEquals("FileMode.parse(\"-rw-rw-rw-\")", FileType.File, Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, false, false, false, FileMode.parse("-rw-rw-rw-"));
        assertEquals("FileMode.parse(\"-rwxrwxrwx\")", FileType.File, Permission.All, Permission.All, Permission.All, false, false, false, FileMode.parse("-rwxrwxrwx"));
        assertEquals("FileMode.parse(\"?---------\")", FileType.Unknown, Permission.None, Permission.None, Permission.None, false, false, false, FileMode.parse("?---------"));

        assertEquals("FileMode.parse(\"p--x--x--x\")", FileType.NamedPipe, Permission.Execute, Permission.Execute, Permission.Execute, false, false, false, FileMode.parse("p--x--x--x"));
        assertEquals("FileMode.parse(\"c-w--w--w-\")", FileType.CharacterDevice, Permission.Write, Permission.Write, Permission.Write, false, false, false, FileMode.parse("c-w--w--w-"));
        assertEquals("FileMode.parse(\"d-wx-wx-wx\")", FileType.Directory, Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, false, false, FileMode.parse("d-wx-wx-wx"));
        assertEquals("FileMode.parse(\"br--r--r--\")", FileType.BlockDevice, Permission.Read, Permission.Read, Permission.Read, false, false, false, FileMode.parse("br--r--r--"));
        assertEquals("FileMode.parse(\"-r-xr-xr-x\")", FileType.File, Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, false, false, false, FileMode.parse("-r-xr-xr-x"));
        assertEquals("FileMode.parse(\"lrw-rw-rw-\")", FileType.SymbolicLink, Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, false, false, false, FileMode.parse("lrw-rw-rw-"));
        assertEquals("FileMode.parse(\"srwxrwxrwx\")", FileType.Socket, Permission.All, Permission.All, Permission.All, false, false, false, FileMode.parse("srwxrwxrwx"));

        assertEquals("FileMode.parse(\"p--------T\")", FileType.NamedPipe, Permission.None, Permission.None, Permission.None, false, false, true, FileMode.parse("p--------T"));
        assertEquals("FileMode.parse(\"c--x--s--x\")", FileType.CharacterDevice, Permission.Execute, Permission.Execute, Permission.Execute, false, true, false, FileMode.parse("c--x--s--x"));
        assertEquals("FileMode.parse(\"d-w--wS-wT\")", FileType.Directory, Permission.Write, Permission.Write, Permission.Write, false, true, true, FileMode.parse("d-w--wS-wT"));
        assertEquals("FileMode.parse(\"b-ws-wx-wx\")", FileType.BlockDevice, Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, true, false, false, FileMode.parse("b-ws-wx-wx"));
        assertEquals("FileMode.parse(\"-r-Sr--r-T\")", FileType.File, Permission.Read, Permission.Read, Permission.Read, true, false, true, FileMode.parse("-r-Sr--r-T"));
        assertEquals("FileMode.parse(\"lr-sr-sr-x\")", FileType.SymbolicLink, Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, true, false, FileMode.parse("lr-sr-sr-x"));
        assertEquals("FileMode.parse(\"srwSrwSrwT\")", FileType.Socket, Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, true, FileMode.parse("srwSrwSrwT"));

        assertEquals("FileMode.parse(\"p--x--x--t\")", FileType.NamedPipe, Permission.Execute, Permission.Execute, Permission.Execute, false, false, true, FileMode.parse("p--x--x--t"));
        assertEquals("FileMode.parse(\"c-w--wS-w-\")", FileType.CharacterDevice, Permission.Write, Permission.Write, Permission.Write, false, true, false, FileMode.parse("c-w--wS-w-"));
        assertEquals("FileMode.parse(\"d-wx-ws-wt\")", FileType.Directory, Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, true, true, FileMode.parse("d-wx-ws-wt"));
        assertEquals("FileMode.parse(\"br-Sr--r--\")", FileType.BlockDevice, Permission.Read, Permission.Read, Permission.Read, true, false, false, FileMode.parse("br-Sr--r--"));
        assertEquals("FileMode.parse(\"-r-sr-xr-t\")", FileType.File, Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, false, true, FileMode.parse("-r-sr-xr-t"));
        assertEquals("FileMode.parse(\"lrwSrwSrw-\")", FileType.SymbolicLink, Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, false, FileMode.parse("lrwSrwSrw-"));
        assertEquals("FileMode.parse(\"srwsrwsrwt\")", FileType.Socket, Permission.All, Permission.All, Permission.All, true, true, true, FileMode.parse("srwsrwsrwt"));
    }

    @SmallTest
    public void testGetValue() throws Exception
    {
        assertEquals(0x0000, new FileMode(FileType.Unknown, Permission.None, Permission.None, Permission.None, false, false, false).getValue());
        assertEquals(0x0049, new FileMode(FileType.Unknown, Permission.Execute, Permission.Execute, Permission.Execute, false, false, false).getValue());
        assertEquals(0x0092, new FileMode(FileType.Unknown, Permission.Write, Permission.Write, Permission.Write, false, false, false).getValue());
        assertEquals(0x00db, new FileMode(FileType.Unknown, Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, false, false).getValue());
        assertEquals(0x0124, new FileMode(FileType.Unknown, Permission.Read, Permission.Read, Permission.Read, false, false, false).getValue());
        assertEquals(0x016d, new FileMode(FileType.Unknown, Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, false, false, false).getValue());
        assertEquals(0x01b6, new FileMode(FileType.Unknown, Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, false, false, false).getValue());
        assertEquals(0x01ff, new FileMode(FileType.Unknown, Permission.All, Permission.All, Permission.All, false, false, false).getValue());

        assertEquals(0x1049, new FileMode(FileType.NamedPipe, Permission.Execute, Permission.Execute, Permission.Execute, false, false, false).getValue());
        assertEquals(0x2092, new FileMode(FileType.CharacterDevice, Permission.Write, Permission.Write, Permission.Write, false, false, false).getValue());
        assertEquals(0x40db, new FileMode(FileType.Directory, Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, false, false).getValue());
        assertEquals(0x6124, new FileMode(FileType.BlockDevice, Permission.Read, Permission.Read, Permission.Read, false, false, false).getValue());
        assertEquals(0x816d, new FileMode(FileType.File, Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, false, false, false).getValue());
        assertEquals(0xa1b6, new FileMode(FileType.SymbolicLink, Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, false, false, false).getValue());
        assertEquals(0xc1ff, new FileMode(FileType.Socket, Permission.All, Permission.All, Permission.All, false, false, false).getValue());

        assertEquals(0x1200, new FileMode(FileType.NamedPipe, Permission.None, Permission.None, Permission.None, false, false, true).getValue());
        assertEquals(0x2449, new FileMode(FileType.CharacterDevice, Permission.Execute, Permission.Execute, Permission.Execute, false, true, false).getValue());
        assertEquals(0x4692, new FileMode(FileType.Directory, Permission.Write, Permission.Write, Permission.Write, false, true, true).getValue());
        assertEquals(0x68db, new FileMode(FileType.BlockDevice, Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, true, false, false).getValue());
        assertEquals(0x8b24, new FileMode(FileType.File, Permission.Read, Permission.Read, Permission.Read, true, false, true).getValue());
        assertEquals(0xad6d, new FileMode(FileType.SymbolicLink, Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, true, false).getValue());
        assertEquals(0xcfb6, new FileMode(FileType.Socket, Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, true).getValue());

        assertEquals(0x1249, new FileMode(FileType.NamedPipe, Permission.Execute, Permission.Execute, Permission.Execute, false, false, true).getValue());
        assertEquals(0x2492, new FileMode(FileType.CharacterDevice, Permission.Write, Permission.Write, Permission.Write, false, true, false).getValue());
        assertEquals(0x46db, new FileMode(FileType.Directory, Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, true, true).getValue());
        assertEquals(0x6924, new FileMode(FileType.BlockDevice, Permission.Read, Permission.Read, Permission.Read, true, false, false).getValue());
        assertEquals(0x8b6d, new FileMode(FileType.File, Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, false, true).getValue());
        assertEquals(0xadb6, new FileMode(FileType.SymbolicLink, Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, false).getValue());
        assertEquals(0xcfff, new FileMode(FileType.Socket, Permission.All, Permission.All, Permission.All, true, true, true).getValue());
    }

    @SmallTest
    public void testGetSymbols() throws Exception
    {
        assertEquals("?---------", new FileMode(FileType.Unknown, new Permissions(Permission.None)).getSymbols());
        assertEquals("p--x--x--x", new FileMode(FileType.NamedPipe, new Permissions(Permission.Execute)).getSymbols());
        assertEquals("c-w--w--w-", new FileMode(FileType.CharacterDevice, new Permissions(Permission.Write)).getSymbols());
        assertEquals("d-wx-wx-wx", new FileMode(FileType.Directory, new Permissions(Permission.WriteExecute)).getSymbols());
        assertEquals("br--r--r--", new FileMode(FileType.BlockDevice, new Permissions(Permission.Read)).getSymbols());
        assertEquals("-r-xr-xr-x", new FileMode(FileType.File, new Permissions(Permission.ReadExecute)).getSymbols());
        assertEquals("lrw-rw-rw-", new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadWrite)).getSymbols());
        assertEquals("srwxrwxrwx", new FileMode(FileType.Socket, new Permissions(Permission.All)).getSymbols());

        assertEquals("p--------T", new FileMode(FileType.NamedPipe, new Permissions(Permission.None, Permission.None, Permission.None, false, false, true)).getSymbols());
        assertEquals("c--x--s--x", new FileMode(FileType.CharacterDevice, new Permissions(Permission.Execute, Permission.Execute, Permission.Execute, false, true, false)).getSymbols());
        assertEquals("d-w--wS-wT", new FileMode(FileType.Directory, new Permissions(Permission.Write, Permission.Write, Permission.Write, false, true, true)).getSymbols());
        assertEquals("b-ws-wx-wx", new FileMode(FileType.BlockDevice, new Permissions(Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, true, false, false)).getSymbols());
        assertEquals("-r-Sr--r-T", new FileMode(FileType.File, new Permissions(Permission.Read, Permission.Read, Permission.Read, true, false, true)).getSymbols());
        assertEquals("lr-sr-sr-x", new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, true, false)).getSymbols());
        assertEquals("srwSrwSrwT", new FileMode(FileType.Socket, new Permissions(Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, true)).getSymbols());

        assertEquals("p--x--x--t", new FileMode(FileType.NamedPipe, new Permissions(Permission.Execute, Permission.Execute, Permission.Execute, false, false, true)).getSymbols());
        assertEquals("c-w--wS-w-", new FileMode(FileType.CharacterDevice, new Permissions(Permission.Write, Permission.Write, Permission.Write, false, true, false)).getSymbols());
        assertEquals("d-wx-ws-wt", new FileMode(FileType.Directory, new Permissions(Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, true, true)).getSymbols());
        assertEquals("br-Sr--r--", new FileMode(FileType.BlockDevice, new Permissions(Permission.Read, Permission.Read, Permission.Read, true, false, false)).getSymbols());
        assertEquals("-r-sr-xr-t", new FileMode(FileType.File, new Permissions(Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, false, true)).getSymbols());
        assertEquals("lrwSrwSrw-", new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, false)).getSymbols());
        assertEquals("srwsrwsrwt", new FileMode(FileType.Socket, new Permissions(Permission.All, Permission.All, Permission.All, true, true, true)).getSymbols());
    }

    @SmallTest
    public void testToString() throws Exception
    {
        assertEquals("?---------", new FileMode(FileType.Unknown, new Permissions(Permission.None)).toString());
        assertEquals("p--x--x--x", new FileMode(FileType.NamedPipe, new Permissions(Permission.Execute)).toString());
        assertEquals("c-w--w--w-", new FileMode(FileType.CharacterDevice, new Permissions(Permission.Write)).toString());
        assertEquals("d-wx-wx-wx", new FileMode(FileType.Directory, new Permissions(Permission.WriteExecute)).toString());
        assertEquals("br--r--r--", new FileMode(FileType.BlockDevice, new Permissions(Permission.Read)).toString());
        assertEquals("-r-xr-xr-x", new FileMode(FileType.File, new Permissions(Permission.ReadExecute)).toString());
        assertEquals("lrw-rw-rw-", new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadWrite)).toString());
        assertEquals("srwxrwxrwx", new FileMode(FileType.Socket, new Permissions(Permission.All)).toString());

        assertEquals("p--------T", new FileMode(FileType.NamedPipe, new Permissions(Permission.None, Permission.None, Permission.None, false, false, true)).toString());
        assertEquals("c--x--s--x", new FileMode(FileType.CharacterDevice, new Permissions(Permission.Execute, Permission.Execute, Permission.Execute, false, true, false)).toString());
        assertEquals("d-w--wS-wT", new FileMode(FileType.Directory, new Permissions(Permission.Write, Permission.Write, Permission.Write, false, true, true)).toString());
        assertEquals("b-ws-wx-wx", new FileMode(FileType.BlockDevice, new Permissions(Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, true, false, false)).toString());
        assertEquals("-r-Sr--r-T", new FileMode(FileType.File, new Permissions(Permission.Read, Permission.Read, Permission.Read, true, false, true)).toString());
        assertEquals("lr-sr-sr-x", new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, true, false)).toString());
        assertEquals("srwSrwSrwT", new FileMode(FileType.Socket, new Permissions(Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, true)).toString());

        assertEquals("p--x--x--t", new FileMode(FileType.NamedPipe, new Permissions(Permission.Execute, Permission.Execute, Permission.Execute, false, false, true)).toString());
        assertEquals("c-w--wS-w-", new FileMode(FileType.CharacterDevice, new Permissions(Permission.Write, Permission.Write, Permission.Write, false, true, false)).toString());
        assertEquals("d-wx-ws-wt", new FileMode(FileType.Directory, new Permissions(Permission.WriteExecute, Permission.WriteExecute, Permission.WriteExecute, false, true, true)).toString());
        assertEquals("br-Sr--r--", new FileMode(FileType.BlockDevice, new Permissions(Permission.Read, Permission.Read, Permission.Read, true, false, false)).toString());
        assertEquals("-r-sr-xr-t", new FileMode(FileType.File, new Permissions(Permission.ReadExecute, Permission.ReadExecute, Permission.ReadExecute, true, false, true)).toString());
        assertEquals("lrwSrwSrw-", new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadWrite, Permission.ReadWrite, Permission.ReadWrite, true, true, false)).toString());
        assertEquals("srwsrwsrwt", new FileMode(FileType.Socket, new Permissions(Permission.All, Permission.All, Permission.All, true, true, true)).toString());
    }

    @SmallTest
    public void testCompareTo() throws Exception
    {
        assertEquals(0, new FileMode(FileType.Unknown, new Permissions(Permission.None)).compareTo(new FileMode(FileType.Unknown, new Permissions(Permission.None))));
        assertEquals(-1, new FileMode(FileType.Unknown, new Permissions(Permission.None)).compareTo(new FileMode(FileType.NamedPipe, new Permissions(Permission.None))));
        assertEquals(-1, new FileMode(FileType.Unknown, new Permissions(Permission.None)).compareTo(new FileMode(FileType.CharacterDevice, new Permissions(Permission.None))));
        assertEquals(-1, new FileMode(FileType.Unknown, new Permissions(Permission.None)).compareTo(new FileMode(FileType.Directory, new Permissions(Permission.None))));
        assertEquals(-1, new FileMode(FileType.Unknown, new Permissions(Permission.None)).compareTo(new FileMode(FileType.BlockDevice, new Permissions(Permission.None))));
        assertEquals(-1, new FileMode(FileType.Unknown, new Permissions(Permission.None)).compareTo(new FileMode(FileType.File, new Permissions(Permission.None))));
        assertEquals(-1, new FileMode(FileType.Unknown, new Permissions(Permission.None)).compareTo(new FileMode(FileType.SymbolicLink, new Permissions(Permission.None))));
        assertEquals(-1, new FileMode(FileType.Unknown, new Permissions(Permission.None)).compareTo(new FileMode(FileType.Socket, new Permissions(Permission.None))));

        assertEquals(1, new FileMode(FileType.NamedPipe, new Permissions(Permission.None)).compareTo(new FileMode(FileType.Unknown, new Permissions(Permission.None))));
        assertEquals(0, new FileMode(FileType.NamedPipe, new Permissions(Permission.None)).compareTo(new FileMode(FileType.NamedPipe, new Permissions(Permission.None))));
        assertEquals(-1, new FileMode(FileType.NamedPipe, new Permissions(Permission.None)).compareTo(new FileMode(FileType.CharacterDevice, new Permissions(Permission.None))));
        assertEquals(-1, new FileMode(FileType.NamedPipe, new Permissions(Permission.None)).compareTo(new FileMode(FileType.Directory, new Permissions(Permission.None))));
        assertEquals(-1, new FileMode(FileType.NamedPipe, new Permissions(Permission.None)).compareTo(new FileMode(FileType.BlockDevice, new Permissions(Permission.None))));
        assertEquals(-1, new FileMode(FileType.NamedPipe, new Permissions(Permission.None)).compareTo(new FileMode(FileType.File, new Permissions(Permission.None))));
        assertEquals(-1, new FileMode(FileType.NamedPipe, new Permissions(Permission.None)).compareTo(new FileMode(FileType.SymbolicLink, new Permissions(Permission.None))));
        assertEquals(-1, new FileMode(FileType.NamedPipe, new Permissions(Permission.None)).compareTo(new FileMode(FileType.Socket, new Permissions(Permission.None))));

        assertEquals(1, new FileMode(FileType.CharacterDevice, new Permissions(Permission.None)).compareTo(new FileMode(FileType.Unknown, new Permissions(Permission.None))));
        assertEquals(1, new FileMode(FileType.CharacterDevice, new Permissions(Permission.None)).compareTo(new FileMode(FileType.NamedPipe, new Permissions(Permission.None))));
        assertEquals(0, new FileMode(FileType.CharacterDevice, new Permissions(Permission.None)).compareTo(new FileMode(FileType.CharacterDevice, new Permissions(Permission.None))));
        assertEquals(-1, new FileMode(FileType.CharacterDevice, new Permissions(Permission.None)).compareTo(new FileMode(FileType.Directory, new Permissions(Permission.None))));
        assertEquals(-1, new FileMode(FileType.CharacterDevice, new Permissions(Permission.None)).compareTo(new FileMode(FileType.BlockDevice, new Permissions(Permission.None))));
        assertEquals(-1, new FileMode(FileType.CharacterDevice, new Permissions(Permission.None)).compareTo(new FileMode(FileType.File, new Permissions(Permission.None))));
        assertEquals(-1, new FileMode(FileType.CharacterDevice, new Permissions(Permission.None)).compareTo(new FileMode(FileType.SymbolicLink, new Permissions(Permission.None))));
        assertEquals(-1, new FileMode(FileType.CharacterDevice, new Permissions(Permission.None)).compareTo(new FileMode(FileType.Socket, new Permissions(Permission.None))));

        assertEquals(1, new FileMode(FileType.Directory, new Permissions(Permission.None)).compareTo(new FileMode(FileType.Unknown, new Permissions(Permission.None))));
        assertEquals(1, new FileMode(FileType.Directory, new Permissions(Permission.None)).compareTo(new FileMode(FileType.NamedPipe, new Permissions(Permission.None))));
        assertEquals(1, new FileMode(FileType.Directory, new Permissions(Permission.None)).compareTo(new FileMode(FileType.CharacterDevice, new Permissions(Permission.None))));
        assertEquals(0, new FileMode(FileType.Directory, new Permissions(Permission.None)).compareTo(new FileMode(FileType.Directory, new Permissions(Permission.None))));
        assertEquals(-1, new FileMode(FileType.Directory, new Permissions(Permission.None)).compareTo(new FileMode(FileType.BlockDevice, new Permissions(Permission.None))));
        assertEquals(-1, new FileMode(FileType.Directory, new Permissions(Permission.None)).compareTo(new FileMode(FileType.File, new Permissions(Permission.None))));
        assertEquals(-1, new FileMode(FileType.Directory, new Permissions(Permission.None)).compareTo(new FileMode(FileType.SymbolicLink, new Permissions(Permission.None))));
        assertEquals(-1, new FileMode(FileType.Directory, new Permissions(Permission.None)).compareTo(new FileMode(FileType.Socket, new Permissions(Permission.None))));

        assertEquals(1, new FileMode(FileType.BlockDevice, new Permissions(Permission.None)).compareTo(new FileMode(FileType.Unknown, new Permissions(Permission.None))));
        assertEquals(1, new FileMode(FileType.BlockDevice, new Permissions(Permission.None)).compareTo(new FileMode(FileType.NamedPipe, new Permissions(Permission.None))));
        assertEquals(1, new FileMode(FileType.BlockDevice, new Permissions(Permission.None)).compareTo(new FileMode(FileType.CharacterDevice, new Permissions(Permission.None))));
        assertEquals(1, new FileMode(FileType.BlockDevice, new Permissions(Permission.None)).compareTo(new FileMode(FileType.Directory, new Permissions(Permission.None))));
        assertEquals(0, new FileMode(FileType.BlockDevice, new Permissions(Permission.None)).compareTo(new FileMode(FileType.BlockDevice, new Permissions(Permission.None))));
        assertEquals(-1, new FileMode(FileType.BlockDevice, new Permissions(Permission.None)).compareTo(new FileMode(FileType.File, new Permissions(Permission.None))));
        assertEquals(-1, new FileMode(FileType.BlockDevice, new Permissions(Permission.None)).compareTo(new FileMode(FileType.SymbolicLink, new Permissions(Permission.None))));
        assertEquals(-1, new FileMode(FileType.BlockDevice, new Permissions(Permission.None)).compareTo(new FileMode(FileType.Socket, new Permissions(Permission.None))));

        assertEquals(1, new FileMode(FileType.File, new Permissions(Permission.None)).compareTo(new FileMode(FileType.Unknown, new Permissions(Permission.None))));
        assertEquals(1, new FileMode(FileType.File, new Permissions(Permission.None)).compareTo(new FileMode(FileType.NamedPipe, new Permissions(Permission.None))));
        assertEquals(1, new FileMode(FileType.File, new Permissions(Permission.None)).compareTo(new FileMode(FileType.CharacterDevice, new Permissions(Permission.None))));
        assertEquals(1, new FileMode(FileType.File, new Permissions(Permission.None)).compareTo(new FileMode(FileType.Directory, new Permissions(Permission.None))));
        assertEquals(1, new FileMode(FileType.File, new Permissions(Permission.None)).compareTo(new FileMode(FileType.BlockDevice, new Permissions(Permission.None))));
        assertEquals(0, new FileMode(FileType.File, new Permissions(Permission.None)).compareTo(new FileMode(FileType.File, new Permissions(Permission.None))));
        assertEquals(-1, new FileMode(FileType.File, new Permissions(Permission.None)).compareTo(new FileMode(FileType.SymbolicLink, new Permissions(Permission.None))));
        assertEquals(-1, new FileMode(FileType.File, new Permissions(Permission.None)).compareTo(new FileMode(FileType.Socket, new Permissions(Permission.None))));

        assertEquals(1, new FileMode(FileType.SymbolicLink, new Permissions(Permission.None)).compareTo(new FileMode(FileType.Unknown, new Permissions(Permission.None))));
        assertEquals(1, new FileMode(FileType.SymbolicLink, new Permissions(Permission.None)).compareTo(new FileMode(FileType.NamedPipe, new Permissions(Permission.None))));
        assertEquals(1, new FileMode(FileType.SymbolicLink, new Permissions(Permission.None)).compareTo(new FileMode(FileType.CharacterDevice, new Permissions(Permission.None))));
        assertEquals(1, new FileMode(FileType.SymbolicLink, new Permissions(Permission.None)).compareTo(new FileMode(FileType.Directory, new Permissions(Permission.None))));
        assertEquals(1, new FileMode(FileType.SymbolicLink, new Permissions(Permission.None)).compareTo(new FileMode(FileType.BlockDevice, new Permissions(Permission.None))));
        assertEquals(1, new FileMode(FileType.SymbolicLink, new Permissions(Permission.None)).compareTo(new FileMode(FileType.File, new Permissions(Permission.None))));
        assertEquals(0, new FileMode(FileType.SymbolicLink, new Permissions(Permission.None)).compareTo(new FileMode(FileType.SymbolicLink, new Permissions(Permission.None))));
        assertEquals(-1, new FileMode(FileType.SymbolicLink, new Permissions(Permission.None)).compareTo(new FileMode(FileType.Socket, new Permissions(Permission.None))));

        assertEquals(1, new FileMode(FileType.Socket, new Permissions(Permission.None)).compareTo(new FileMode(FileType.Unknown, new Permissions(Permission.None))));
        assertEquals(1, new FileMode(FileType.Socket, new Permissions(Permission.None)).compareTo(new FileMode(FileType.NamedPipe, new Permissions(Permission.None))));
        assertEquals(1, new FileMode(FileType.Socket, new Permissions(Permission.None)).compareTo(new FileMode(FileType.CharacterDevice, new Permissions(Permission.None))));
        assertEquals(1, new FileMode(FileType.Socket, new Permissions(Permission.None)).compareTo(new FileMode(FileType.Directory, new Permissions(Permission.None))));
        assertEquals(1, new FileMode(FileType.Socket, new Permissions(Permission.None)).compareTo(new FileMode(FileType.BlockDevice, new Permissions(Permission.None))));
        assertEquals(1, new FileMode(FileType.Socket, new Permissions(Permission.None)).compareTo(new FileMode(FileType.File, new Permissions(Permission.None))));
        assertEquals(1, new FileMode(FileType.Socket, new Permissions(Permission.None)).compareTo(new FileMode(FileType.SymbolicLink, new Permissions(Permission.None))));
        assertEquals(0, new FileMode(FileType.Socket, new Permissions(Permission.None)).compareTo(new FileMode(FileType.Socket, new Permissions(Permission.None))));

        assertEquals(-1, new FileMode(FileType.NamedPipe, new Permissions(Permission.None)).compareTo(new FileMode(FileType.NamedPipe, new Permissions(Permission.Execute))));
        assertEquals(-1, new FileMode(FileType.CharacterDevice, new Permissions(Permission.None)).compareTo(new FileMode(FileType.CharacterDevice, new Permissions(Permission.Write))));
        assertEquals(-1, new FileMode(FileType.Directory, new Permissions(Permission.None)).compareTo(new FileMode(FileType.Directory, new Permissions(Permission.WriteExecute))));
        assertEquals(-1, new FileMode(FileType.BlockDevice, new Permissions(Permission.None)).compareTo(new FileMode(FileType.BlockDevice, new Permissions(Permission.Read))));
        assertEquals(-1, new FileMode(FileType.File, new Permissions(Permission.None)).compareTo(new FileMode(FileType.File, new Permissions(Permission.ReadExecute))));
        assertEquals(-1, new FileMode(FileType.SymbolicLink, new Permissions(Permission.None)).compareTo(new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadWrite))));
        assertEquals(-1, new FileMode(FileType.Socket, new Permissions(Permission.None)).compareTo(new FileMode(FileType.Socket, new Permissions(Permission.All))));

        assertEquals(1, new FileMode(FileType.Unknown, new Permissions(Permission.Execute)).compareTo(new FileMode(FileType.Unknown, new Permissions(Permission.None))));
        assertEquals(0, new FileMode(FileType.NamedPipe, new Permissions(Permission.Execute)).compareTo(new FileMode(FileType.NamedPipe, new Permissions(Permission.Execute))));
        assertEquals(-1, new FileMode(FileType.CharacterDevice, new Permissions(Permission.Execute)).compareTo(new FileMode(FileType.CharacterDevice, new Permissions(Permission.Write))));
        assertEquals(-1, new FileMode(FileType.Directory, new Permissions(Permission.Execute)).compareTo(new FileMode(FileType.Directory, new Permissions(Permission.WriteExecute))));
        assertEquals(-1, new FileMode(FileType.BlockDevice, new Permissions(Permission.Execute)).compareTo(new FileMode(FileType.BlockDevice, new Permissions(Permission.Read))));
        assertEquals(-1, new FileMode(FileType.File, new Permissions(Permission.Execute)).compareTo(new FileMode(FileType.File, new Permissions(Permission.ReadExecute))));
        assertEquals(-1, new FileMode(FileType.SymbolicLink, new Permissions(Permission.Execute)).compareTo(new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadWrite))));
        assertEquals(-1, new FileMode(FileType.Socket, new Permissions(Permission.None)).compareTo(new FileMode(FileType.Socket, new Permissions(Permission.All))));

        assertEquals(1, new FileMode(FileType.Unknown, new Permissions(Permission.Write)).compareTo(new FileMode(FileType.Unknown, new Permissions(Permission.None))));
        assertEquals(1, new FileMode(FileType.NamedPipe, new Permissions(Permission.Write)).compareTo(new FileMode(FileType.NamedPipe, new Permissions(Permission.Execute))));
        assertEquals(0, new FileMode(FileType.CharacterDevice, new Permissions(Permission.Write)).compareTo(new FileMode(FileType.CharacterDevice, new Permissions(Permission.Write))));
        assertEquals(-1, new FileMode(FileType.Directory, new Permissions(Permission.Write)).compareTo(new FileMode(FileType.Directory, new Permissions(Permission.WriteExecute))));
        assertEquals(-1, new FileMode(FileType.BlockDevice, new Permissions(Permission.Write)).compareTo(new FileMode(FileType.BlockDevice, new Permissions(Permission.Read))));
        assertEquals(-1, new FileMode(FileType.File, new Permissions(Permission.Write)).compareTo(new FileMode(FileType.File, new Permissions(Permission.ReadExecute))));
        assertEquals(-1, new FileMode(FileType.SymbolicLink, new Permissions(Permission.Write)).compareTo(new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadWrite))));
        assertEquals(-1, new FileMode(FileType.Socket, new Permissions(Permission.None)).compareTo(new FileMode(FileType.Socket, new Permissions(Permission.All))));

        assertEquals(1, new FileMode(FileType.Unknown, new Permissions(Permission.WriteExecute)).compareTo(new FileMode(FileType.Unknown, new Permissions(Permission.None))));
        assertEquals(1, new FileMode(FileType.NamedPipe, new Permissions(Permission.WriteExecute)).compareTo(new FileMode(FileType.NamedPipe, new Permissions(Permission.Execute))));
        assertEquals(1, new FileMode(FileType.CharacterDevice, new Permissions(Permission.WriteExecute)).compareTo(new FileMode(FileType.CharacterDevice, new Permissions(Permission.Write))));
        assertEquals(0, new FileMode(FileType.Directory, new Permissions(Permission.WriteExecute)).compareTo(new FileMode(FileType.Directory, new Permissions(Permission.WriteExecute))));
        assertEquals(-1, new FileMode(FileType.BlockDevice, new Permissions(Permission.WriteExecute)).compareTo(new FileMode(FileType.BlockDevice, new Permissions(Permission.Read))));
        assertEquals(-1, new FileMode(FileType.File, new Permissions(Permission.WriteExecute)).compareTo(new FileMode(FileType.File, new Permissions(Permission.ReadExecute))));
        assertEquals(-1, new FileMode(FileType.SymbolicLink, new Permissions(Permission.WriteExecute)).compareTo(new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadWrite))));
        assertEquals(-1, new FileMode(FileType.Socket, new Permissions(Permission.None)).compareTo(new FileMode(FileType.Socket, new Permissions(Permission.All))));

        assertEquals(1, new FileMode(FileType.Unknown, new Permissions(Permission.Read)).compareTo(new FileMode(FileType.Unknown, new Permissions(Permission.None))));
        assertEquals(1, new FileMode(FileType.NamedPipe, new Permissions(Permission.Read)).compareTo(new FileMode(FileType.NamedPipe, new Permissions(Permission.Execute))));
        assertEquals(1, new FileMode(FileType.CharacterDevice, new Permissions(Permission.Read)).compareTo(new FileMode(FileType.CharacterDevice, new Permissions(Permission.Write))));
        assertEquals(1, new FileMode(FileType.Directory, new Permissions(Permission.Read)).compareTo(new FileMode(FileType.Directory, new Permissions(Permission.WriteExecute))));
        assertEquals(0, new FileMode(FileType.BlockDevice, new Permissions(Permission.Read)).compareTo(new FileMode(FileType.BlockDevice, new Permissions(Permission.Read))));
        assertEquals(-1, new FileMode(FileType.File, new Permissions(Permission.Read)).compareTo(new FileMode(FileType.File, new Permissions(Permission.ReadExecute))));
        assertEquals(-1, new FileMode(FileType.SymbolicLink, new Permissions(Permission.Read)).compareTo(new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadWrite))));
        assertEquals(-1, new FileMode(FileType.Socket, new Permissions(Permission.None)).compareTo(new FileMode(FileType.Socket, new Permissions(Permission.All))));

        assertEquals(1, new FileMode(FileType.Unknown, new Permissions(Permission.ReadExecute)).compareTo(new FileMode(FileType.Unknown, new Permissions(Permission.None))));
        assertEquals(1, new FileMode(FileType.NamedPipe, new Permissions(Permission.ReadExecute)).compareTo(new FileMode(FileType.NamedPipe, new Permissions(Permission.Execute))));
        assertEquals(1, new FileMode(FileType.CharacterDevice, new Permissions(Permission.ReadExecute)).compareTo(new FileMode(FileType.CharacterDevice, new Permissions(Permission.Write))));
        assertEquals(1, new FileMode(FileType.Directory, new Permissions(Permission.ReadExecute)).compareTo(new FileMode(FileType.Directory, new Permissions(Permission.WriteExecute))));
        assertEquals(1, new FileMode(FileType.BlockDevice, new Permissions(Permission.ReadExecute)).compareTo(new FileMode(FileType.BlockDevice, new Permissions(Permission.Read))));
        assertEquals(0, new FileMode(FileType.File, new Permissions(Permission.ReadExecute)).compareTo(new FileMode(FileType.File, new Permissions(Permission.ReadExecute))));
        assertEquals(-1, new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadExecute)).compareTo(new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadWrite))));
        assertEquals(-1, new FileMode(FileType.Socket, new Permissions(Permission.None)).compareTo(new FileMode(FileType.Socket, new Permissions(Permission.All))));

        assertEquals(1, new FileMode(FileType.Unknown, new Permissions(Permission.ReadWrite)).compareTo(new FileMode(FileType.Unknown, new Permissions(Permission.None))));
        assertEquals(1, new FileMode(FileType.NamedPipe, new Permissions(Permission.ReadWrite)).compareTo(new FileMode(FileType.NamedPipe, new Permissions(Permission.Execute))));
        assertEquals(1, new FileMode(FileType.CharacterDevice, new Permissions(Permission.ReadWrite)).compareTo(new FileMode(FileType.CharacterDevice, new Permissions(Permission.Write))));
        assertEquals(1, new FileMode(FileType.Directory, new Permissions(Permission.ReadWrite)).compareTo(new FileMode(FileType.Directory, new Permissions(Permission.WriteExecute))));
        assertEquals(1, new FileMode(FileType.BlockDevice, new Permissions(Permission.ReadWrite)).compareTo(new FileMode(FileType.BlockDevice, new Permissions(Permission.Read))));
        assertEquals(1, new FileMode(FileType.File, new Permissions(Permission.ReadWrite)).compareTo(new FileMode(FileType.File, new Permissions(Permission.ReadExecute))));
        assertEquals(0, new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadWrite)).compareTo(new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadWrite))));
        assertEquals(-1, new FileMode(FileType.Socket, new Permissions(Permission.ReadWrite)).compareTo(new FileMode(FileType.Socket, new Permissions(Permission.All))));

        assertEquals(1, new FileMode(FileType.Unknown, new Permissions(Permission.All)).compareTo(new FileMode(FileType.Unknown, new Permissions(Permission.None))));
        assertEquals(1, new FileMode(FileType.NamedPipe, new Permissions(Permission.All)).compareTo(new FileMode(FileType.NamedPipe, new Permissions(Permission.Execute))));
        assertEquals(1, new FileMode(FileType.CharacterDevice, new Permissions(Permission.All)).compareTo(new FileMode(FileType.CharacterDevice, new Permissions(Permission.Write))));
        assertEquals(1, new FileMode(FileType.Directory, new Permissions(Permission.All)).compareTo(new FileMode(FileType.Directory, new Permissions(Permission.WriteExecute))));
        assertEquals(1, new FileMode(FileType.BlockDevice, new Permissions(Permission.All)).compareTo(new FileMode(FileType.BlockDevice, new Permissions(Permission.Read))));
        assertEquals(1, new FileMode(FileType.File, new Permissions(Permission.All)).compareTo(new FileMode(FileType.File, new Permissions(Permission.ReadExecute))));
        assertEquals(1, new FileMode(FileType.SymbolicLink, new Permissions(Permission.All)).compareTo(new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadWrite))));
        assertEquals(0, new FileMode(FileType.Socket, new Permissions(Permission.All)).compareTo(new FileMode(FileType.Socket, new Permissions(Permission.All))));

        assertEquals(0, new FileMode(FileType.Unknown, new Permissions(Permission.None, Permission.Execute, Permission.Write)).compareTo(new FileMode(FileType.Unknown, new Permissions(Permission.None, Permission.Execute, Permission.Write))));
        assertEquals(0, new FileMode(FileType.NamedPipe, new Permissions(Permission.Execute, Permission.Write, Permission.WriteExecute)).compareTo(new FileMode(FileType.NamedPipe, new Permissions(Permission.Execute, Permission.Write, Permission.WriteExecute))));
        assertEquals(0, new FileMode(FileType.CharacterDevice, new Permissions(Permission.Write, Permission.WriteExecute, Permission.Read)).compareTo(new FileMode(FileType.CharacterDevice, new Permissions(Permission.Write, Permission.WriteExecute, Permission.Read))));
        assertEquals(0, new FileMode(FileType.Directory, new Permissions(Permission.WriteExecute, Permission.Read, Permission.ReadExecute)).compareTo(new FileMode(FileType.Directory, new Permissions(Permission.WriteExecute, Permission.Read, Permission.ReadExecute))));
        assertEquals(0, new FileMode(FileType.BlockDevice, new Permissions(Permission.Read, Permission.ReadExecute, Permission.ReadWrite)).compareTo(new FileMode(FileType.BlockDevice, new Permissions(Permission.Read, Permission.ReadExecute, Permission.ReadWrite))));
        assertEquals(0, new FileMode(FileType.File, new Permissions(Permission.ReadExecute, Permission.ReadWrite, Permission.All)).compareTo(new FileMode(FileType.File, new Permissions(Permission.ReadExecute, Permission.ReadWrite, Permission.All))));
        assertEquals(0, new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadWrite, Permission.All, Permission.None)).compareTo(new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadWrite, Permission.All, Permission.None))));
        assertEquals(0, new FileMode(FileType.Socket, new Permissions(Permission.All, Permission.None, Permission.Execute)).compareTo(new FileMode(FileType.Socket, new Permissions(Permission.All, Permission.None, Permission.Execute))));

        assertEquals(-1, new FileMode(FileType.Unknown, new Permissions(Permission.None, Permission.Execute, Permission.Write)).compareTo(new FileMode(FileType.Unknown, new Permissions(Permission.Execute, Permission.Write, Permission.WriteExecute))));
        assertEquals(-1, new FileMode(FileType.NamedPipe, new Permissions(Permission.Execute, Permission.Write, Permission.WriteExecute)).compareTo(new FileMode(FileType.NamedPipe, new Permissions(Permission.Write, Permission.WriteExecute, Permission.Read))));
        assertEquals(-1, new FileMode(FileType.CharacterDevice, new Permissions(Permission.Write, Permission.WriteExecute, Permission.Read)).compareTo(new FileMode(FileType.CharacterDevice, new Permissions(Permission.WriteExecute, Permission.Read, Permission.ReadExecute))));
        assertEquals(-1, new FileMode(FileType.Directory, new Permissions(Permission.WriteExecute, Permission.Read, Permission.ReadExecute)).compareTo(new FileMode(FileType.Directory, new Permissions(Permission.Read, Permission.ReadExecute, Permission.ReadWrite))));
        assertEquals(-1, new FileMode(FileType.BlockDevice, new Permissions(Permission.Read, Permission.ReadExecute, Permission.ReadWrite)).compareTo(new FileMode(FileType.BlockDevice, new Permissions(Permission.ReadExecute, Permission.ReadWrite, Permission.All))));
        assertEquals(-1, new FileMode(FileType.File, new Permissions(Permission.ReadExecute, Permission.ReadWrite, Permission.All)).compareTo(new FileMode(FileType.File, new Permissions(Permission.ReadWrite, Permission.All, Permission.None))));
        assertEquals(-1, new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadWrite, Permission.All, Permission.None)).compareTo(new FileMode(FileType.SymbolicLink, new Permissions(Permission.All, Permission.None, Permission.Execute))));
        assertEquals(1, new FileMode(FileType.Socket, new Permissions(Permission.All, Permission.None, Permission.Execute)).compareTo(new FileMode(FileType.Socket, new Permissions(Permission.None, Permission.Execute, Permission.Write))));
    }

    @SmallTest
    public void testEquals() throws Exception
    {
        assertTrue(new FileMode(FileType.Unknown, new Permissions(Permission.None)).equals(new FileMode(FileType.Unknown, new Permissions(Permission.None))));
        assertTrue(new FileMode(FileType.Unknown, new Permissions(Permission.None)).equals(new FileMode(FileType.Unknown, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.Unknown, new Permissions(Permission.None)).equals(new FileMode(FileType.NamedPipe, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.Unknown, new Permissions(Permission.None)).equals(new FileMode(FileType.CharacterDevice, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.Unknown, new Permissions(Permission.None)).equals(new FileMode(FileType.Directory, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.Unknown, new Permissions(Permission.None)).equals(new FileMode(FileType.BlockDevice, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.Unknown, new Permissions(Permission.None)).equals(new FileMode(FileType.File, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.Unknown, new Permissions(Permission.None)).equals(new FileMode(FileType.SymbolicLink, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.Unknown, new Permissions(Permission.None)).equals(new FileMode(FileType.Socket, new Permissions(Permission.None))));

        assertFalse(new FileMode(FileType.NamedPipe, new Permissions(Permission.None)).equals(new FileMode(FileType.Unknown, new Permissions(Permission.None))));
        assertTrue(new FileMode(FileType.NamedPipe, new Permissions(Permission.None)).equals(new FileMode(FileType.NamedPipe, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.NamedPipe, new Permissions(Permission.None)).equals(new FileMode(FileType.CharacterDevice, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.NamedPipe, new Permissions(Permission.None)).equals(new FileMode(FileType.Directory, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.NamedPipe, new Permissions(Permission.None)).equals(new FileMode(FileType.BlockDevice, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.NamedPipe, new Permissions(Permission.None)).equals(new FileMode(FileType.File, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.NamedPipe, new Permissions(Permission.None)).equals(new FileMode(FileType.SymbolicLink, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.NamedPipe, new Permissions(Permission.None)).equals(new FileMode(FileType.Socket, new Permissions(Permission.None))));

        assertFalse(new FileMode(FileType.CharacterDevice, new Permissions(Permission.None)).equals(new FileMode(FileType.Unknown, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.CharacterDevice, new Permissions(Permission.None)).equals(new FileMode(FileType.NamedPipe, new Permissions(Permission.None))));
        assertTrue(new FileMode(FileType.CharacterDevice, new Permissions(Permission.None)).equals(new FileMode(FileType.CharacterDevice, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.CharacterDevice, new Permissions(Permission.None)).equals(new FileMode(FileType.Directory, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.CharacterDevice, new Permissions(Permission.None)).equals(new FileMode(FileType.BlockDevice, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.CharacterDevice, new Permissions(Permission.None)).equals(new FileMode(FileType.File, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.CharacterDevice, new Permissions(Permission.None)).equals(new FileMode(FileType.SymbolicLink, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.CharacterDevice, new Permissions(Permission.None)).equals(new FileMode(FileType.Socket, new Permissions(Permission.None))));

        assertFalse(new FileMode(FileType.Directory, new Permissions(Permission.None)).equals(new FileMode(FileType.Unknown, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.Directory, new Permissions(Permission.None)).equals(new FileMode(FileType.NamedPipe, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.Directory, new Permissions(Permission.None)).equals(new FileMode(FileType.CharacterDevice, new Permissions(Permission.None))));
        assertTrue(new FileMode(FileType.Directory, new Permissions(Permission.None)).equals(new FileMode(FileType.Directory, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.Directory, new Permissions(Permission.None)).equals(new FileMode(FileType.BlockDevice, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.Directory, new Permissions(Permission.None)).equals(new FileMode(FileType.File, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.Directory, new Permissions(Permission.None)).equals(new FileMode(FileType.SymbolicLink, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.Directory, new Permissions(Permission.None)).equals(new FileMode(FileType.Socket, new Permissions(Permission.None))));

        assertFalse(new FileMode(FileType.BlockDevice, new Permissions(Permission.None)).equals(new FileMode(FileType.Unknown, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.BlockDevice, new Permissions(Permission.None)).equals(new FileMode(FileType.NamedPipe, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.BlockDevice, new Permissions(Permission.None)).equals(new FileMode(FileType.CharacterDevice, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.BlockDevice, new Permissions(Permission.None)).equals(new FileMode(FileType.Directory, new Permissions(Permission.None))));
        assertTrue(new FileMode(FileType.BlockDevice, new Permissions(Permission.None)).equals(new FileMode(FileType.BlockDevice, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.BlockDevice, new Permissions(Permission.None)).equals(new FileMode(FileType.File, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.BlockDevice, new Permissions(Permission.None)).equals(new FileMode(FileType.SymbolicLink, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.BlockDevice, new Permissions(Permission.None)).equals(new FileMode(FileType.Socket, new Permissions(Permission.None))));

        assertFalse(new FileMode(FileType.File, new Permissions(Permission.None)).equals(new FileMode(FileType.Unknown, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.File, new Permissions(Permission.None)).equals(new FileMode(FileType.NamedPipe, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.File, new Permissions(Permission.None)).equals(new FileMode(FileType.CharacterDevice, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.File, new Permissions(Permission.None)).equals(new FileMode(FileType.Directory, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.File, new Permissions(Permission.None)).equals(new FileMode(FileType.BlockDevice, new Permissions(Permission.None))));
        assertTrue(new FileMode(FileType.File, new Permissions(Permission.None)).equals(new FileMode(FileType.File, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.File, new Permissions(Permission.None)).equals(new FileMode(FileType.SymbolicLink, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.File, new Permissions(Permission.None)).equals(new FileMode(FileType.Socket, new Permissions(Permission.None))));

        assertFalse(new FileMode(FileType.SymbolicLink, new Permissions(Permission.None)).equals(new FileMode(FileType.Unknown, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.SymbolicLink, new Permissions(Permission.None)).equals(new FileMode(FileType.NamedPipe, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.SymbolicLink, new Permissions(Permission.None)).equals(new FileMode(FileType.CharacterDevice, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.SymbolicLink, new Permissions(Permission.None)).equals(new FileMode(FileType.Directory, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.SymbolicLink, new Permissions(Permission.None)).equals(new FileMode(FileType.BlockDevice, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.SymbolicLink, new Permissions(Permission.None)).equals(new FileMode(FileType.File, new Permissions(Permission.None))));
        assertTrue(new FileMode(FileType.SymbolicLink, new Permissions(Permission.None)).equals(new FileMode(FileType.SymbolicLink, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.SymbolicLink, new Permissions(Permission.None)).equals(new FileMode(FileType.Socket, new Permissions(Permission.None))));

        assertFalse(new FileMode(FileType.Socket, new Permissions(Permission.None)).equals(new FileMode(FileType.Unknown, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.Socket, new Permissions(Permission.None)).equals(new FileMode(FileType.NamedPipe, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.Socket, new Permissions(Permission.None)).equals(new FileMode(FileType.CharacterDevice, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.Socket, new Permissions(Permission.None)).equals(new FileMode(FileType.Directory, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.Socket, new Permissions(Permission.None)).equals(new FileMode(FileType.BlockDevice, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.Socket, new Permissions(Permission.None)).equals(new FileMode(FileType.File, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.Socket, new Permissions(Permission.None)).equals(new FileMode(FileType.SymbolicLink, new Permissions(Permission.None))));
        assertTrue(new FileMode(FileType.Socket, new Permissions(Permission.None)).equals(new FileMode(FileType.Socket, new Permissions(Permission.None))));

        assertFalse(new FileMode(FileType.NamedPipe, new Permissions(Permission.None)).equals(new FileMode(FileType.NamedPipe, new Permissions(Permission.Execute))));
        assertFalse(new FileMode(FileType.CharacterDevice, new Permissions(Permission.None)).equals(new FileMode(FileType.CharacterDevice, new Permissions(Permission.Write))));
        assertFalse(new FileMode(FileType.Directory, new Permissions(Permission.None)).equals(new FileMode(FileType.Directory, new Permissions(Permission.WriteExecute))));
        assertFalse(new FileMode(FileType.BlockDevice, new Permissions(Permission.None)).equals(new FileMode(FileType.BlockDevice, new Permissions(Permission.Read))));
        assertFalse(new FileMode(FileType.File, new Permissions(Permission.None)).equals(new FileMode(FileType.File, new Permissions(Permission.ReadExecute))));
        assertFalse(new FileMode(FileType.SymbolicLink, new Permissions(Permission.None)).equals(new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadWrite))));
        assertFalse(new FileMode(FileType.Socket, new Permissions(Permission.None)).equals(new FileMode(FileType.Socket, new Permissions(Permission.All))));

        assertFalse(new FileMode(FileType.Unknown, new Permissions(Permission.Execute)).equals(new FileMode(FileType.Unknown, new Permissions(Permission.None))));
        assertTrue(new FileMode(FileType.NamedPipe, new Permissions(Permission.Execute)).equals(new FileMode(FileType.NamedPipe, new Permissions(Permission.Execute))));
        assertFalse(new FileMode(FileType.CharacterDevice, new Permissions(Permission.Execute)).equals(new FileMode(FileType.CharacterDevice, new Permissions(Permission.Write))));
        assertFalse(new FileMode(FileType.Directory, new Permissions(Permission.Execute)).equals(new FileMode(FileType.Directory, new Permissions(Permission.WriteExecute))));
        assertFalse(new FileMode(FileType.BlockDevice, new Permissions(Permission.Execute)).equals(new FileMode(FileType.BlockDevice, new Permissions(Permission.Read))));
        assertFalse(new FileMode(FileType.File, new Permissions(Permission.Execute)).equals(new FileMode(FileType.File, new Permissions(Permission.ReadExecute))));
        assertFalse(new FileMode(FileType.SymbolicLink, new Permissions(Permission.Execute)).equals(new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadWrite))));
        assertFalse(new FileMode(FileType.Socket, new Permissions(Permission.None)).equals(new FileMode(FileType.Socket, new Permissions(Permission.All))));

        assertFalse(new FileMode(FileType.Unknown, new Permissions(Permission.Write)).equals(new FileMode(FileType.Unknown, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.NamedPipe, new Permissions(Permission.Write)).equals(new FileMode(FileType.NamedPipe, new Permissions(Permission.Execute))));
        assertTrue(new FileMode(FileType.CharacterDevice, new Permissions(Permission.Write)).equals(new FileMode(FileType.CharacterDevice, new Permissions(Permission.Write))));
        assertFalse(new FileMode(FileType.Directory, new Permissions(Permission.Write)).equals(new FileMode(FileType.Directory, new Permissions(Permission.WriteExecute))));
        assertFalse(new FileMode(FileType.BlockDevice, new Permissions(Permission.Write)).equals(new FileMode(FileType.BlockDevice, new Permissions(Permission.Read))));
        assertFalse(new FileMode(FileType.File, new Permissions(Permission.Write)).equals(new FileMode(FileType.File, new Permissions(Permission.ReadExecute))));
        assertFalse(new FileMode(FileType.SymbolicLink, new Permissions(Permission.Write)).equals(new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadWrite))));
        assertFalse(new FileMode(FileType.Socket, new Permissions(Permission.None)).equals(new FileMode(FileType.Socket, new Permissions(Permission.All))));

        assertFalse(new FileMode(FileType.Unknown, new Permissions(Permission.WriteExecute)).equals(new FileMode(FileType.Unknown, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.NamedPipe, new Permissions(Permission.WriteExecute)).equals(new FileMode(FileType.NamedPipe, new Permissions(Permission.Execute))));
        assertFalse(new FileMode(FileType.CharacterDevice, new Permissions(Permission.WriteExecute)).equals(new FileMode(FileType.CharacterDevice, new Permissions(Permission.Write))));
        assertTrue(new FileMode(FileType.Directory, new Permissions(Permission.WriteExecute)).equals(new FileMode(FileType.Directory, new Permissions(Permission.WriteExecute))));
        assertFalse(new FileMode(FileType.BlockDevice, new Permissions(Permission.WriteExecute)).equals(new FileMode(FileType.BlockDevice, new Permissions(Permission.Read))));
        assertFalse(new FileMode(FileType.File, new Permissions(Permission.WriteExecute)).equals(new FileMode(FileType.File, new Permissions(Permission.ReadExecute))));
        assertFalse(new FileMode(FileType.SymbolicLink, new Permissions(Permission.WriteExecute)).equals(new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadWrite))));
        assertFalse(new FileMode(FileType.Socket, new Permissions(Permission.None)).equals(new FileMode(FileType.Socket, new Permissions(Permission.All))));

        assertFalse(new FileMode(FileType.Unknown, new Permissions(Permission.Read)).equals(new FileMode(FileType.Unknown, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.NamedPipe, new Permissions(Permission.Read)).equals(new FileMode(FileType.NamedPipe, new Permissions(Permission.Execute))));
        assertFalse(new FileMode(FileType.CharacterDevice, new Permissions(Permission.Read)).equals(new FileMode(FileType.CharacterDevice, new Permissions(Permission.Write))));
        assertFalse(new FileMode(FileType.Directory, new Permissions(Permission.Read)).equals(new FileMode(FileType.Directory, new Permissions(Permission.WriteExecute))));
        assertTrue(new FileMode(FileType.BlockDevice, new Permissions(Permission.Read)).equals(new FileMode(FileType.BlockDevice, new Permissions(Permission.Read))));
        assertFalse(new FileMode(FileType.File, new Permissions(Permission.Read)).equals(new FileMode(FileType.File, new Permissions(Permission.ReadExecute))));
        assertFalse(new FileMode(FileType.SymbolicLink, new Permissions(Permission.Read)).equals(new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadWrite))));
        assertFalse(new FileMode(FileType.Socket, new Permissions(Permission.None)).equals(new FileMode(FileType.Socket, new Permissions(Permission.All))));

        assertFalse(new FileMode(FileType.Unknown, new Permissions(Permission.ReadExecute)).equals(new FileMode(FileType.Unknown, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.NamedPipe, new Permissions(Permission.ReadExecute)).equals(new FileMode(FileType.NamedPipe, new Permissions(Permission.Execute))));
        assertFalse(new FileMode(FileType.CharacterDevice, new Permissions(Permission.ReadExecute)).equals(new FileMode(FileType.CharacterDevice, new Permissions(Permission.Write))));
        assertFalse(new FileMode(FileType.Directory, new Permissions(Permission.ReadExecute)).equals(new FileMode(FileType.Directory, new Permissions(Permission.WriteExecute))));
        assertFalse(new FileMode(FileType.BlockDevice, new Permissions(Permission.ReadExecute)).equals(new FileMode(FileType.BlockDevice, new Permissions(Permission.Read))));
        assertTrue(new FileMode(FileType.File, new Permissions(Permission.ReadExecute)).equals(new FileMode(FileType.File, new Permissions(Permission.ReadExecute))));
        assertFalse(new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadExecute)).equals(new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadWrite))));
        assertFalse(new FileMode(FileType.Socket, new Permissions(Permission.None)).equals(new FileMode(FileType.Socket, new Permissions(Permission.All))));

        assertFalse(new FileMode(FileType.Unknown, new Permissions(Permission.ReadWrite)).equals(new FileMode(FileType.Unknown, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.NamedPipe, new Permissions(Permission.ReadWrite)).equals(new FileMode(FileType.NamedPipe, new Permissions(Permission.Execute))));
        assertFalse(new FileMode(FileType.CharacterDevice, new Permissions(Permission.ReadWrite)).equals(new FileMode(FileType.CharacterDevice, new Permissions(Permission.Write))));
        assertFalse(new FileMode(FileType.Directory, new Permissions(Permission.ReadWrite)).equals(new FileMode(FileType.Directory, new Permissions(Permission.WriteExecute))));
        assertFalse(new FileMode(FileType.BlockDevice, new Permissions(Permission.ReadWrite)).equals(new FileMode(FileType.BlockDevice, new Permissions(Permission.Read))));
        assertFalse(new FileMode(FileType.File, new Permissions(Permission.ReadWrite)).equals(new FileMode(FileType.File, new Permissions(Permission.ReadExecute))));
        assertTrue(new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadWrite)).equals(new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadWrite))));
        assertFalse(new FileMode(FileType.Socket, new Permissions(Permission.ReadWrite)).equals(new FileMode(FileType.Socket, new Permissions(Permission.All))));

        assertFalse(new FileMode(FileType.Unknown, new Permissions(Permission.All)).equals(new FileMode(FileType.Unknown, new Permissions(Permission.None))));
        assertFalse(new FileMode(FileType.NamedPipe, new Permissions(Permission.All)).equals(new FileMode(FileType.NamedPipe, new Permissions(Permission.Execute))));
        assertFalse(new FileMode(FileType.CharacterDevice, new Permissions(Permission.All)).equals(new FileMode(FileType.CharacterDevice, new Permissions(Permission.Write))));
        assertFalse(new FileMode(FileType.Directory, new Permissions(Permission.All)).equals(new FileMode(FileType.Directory, new Permissions(Permission.WriteExecute))));
        assertFalse(new FileMode(FileType.BlockDevice, new Permissions(Permission.All)).equals(new FileMode(FileType.BlockDevice, new Permissions(Permission.Read))));
        assertFalse(new FileMode(FileType.File, new Permissions(Permission.All)).equals(new FileMode(FileType.File, new Permissions(Permission.ReadExecute))));
        assertFalse(new FileMode(FileType.SymbolicLink, new Permissions(Permission.All)).equals(new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadWrite))));
        assertTrue(new FileMode(FileType.Socket, new Permissions(Permission.All)).equals(new FileMode(FileType.Socket, new Permissions(Permission.All))));

        assertTrue(new FileMode(FileType.Unknown, new Permissions(Permission.None, Permission.Execute, Permission.Write)).equals(new FileMode(FileType.Unknown, new Permissions(Permission.None, Permission.Execute, Permission.Write))));
        assertTrue(new FileMode(FileType.NamedPipe, new Permissions(Permission.Execute, Permission.Write, Permission.WriteExecute)).equals(new FileMode(FileType.NamedPipe, new Permissions(Permission.Execute, Permission.Write, Permission.WriteExecute))));
        assertTrue(new FileMode(FileType.CharacterDevice, new Permissions(Permission.Write, Permission.WriteExecute, Permission.Read)).equals(new FileMode(FileType.CharacterDevice, new Permissions(Permission.Write, Permission.WriteExecute, Permission.Read))));
        assertTrue(new FileMode(FileType.Directory, new Permissions(Permission.WriteExecute, Permission.Read, Permission.ReadExecute)).equals(new FileMode(FileType.Directory, new Permissions(Permission.WriteExecute, Permission.Read, Permission.ReadExecute))));
        assertTrue(new FileMode(FileType.BlockDevice, new Permissions(Permission.Read, Permission.ReadExecute, Permission.ReadWrite)).equals(new FileMode(FileType.BlockDevice, new Permissions(Permission.Read, Permission.ReadExecute, Permission.ReadWrite))));
        assertTrue(new FileMode(FileType.File, new Permissions(Permission.ReadExecute, Permission.ReadWrite, Permission.All)).equals(new FileMode(FileType.File, new Permissions(Permission.ReadExecute, Permission.ReadWrite, Permission.All))));
        assertTrue(new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadWrite, Permission.All, Permission.None)).equals(new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadWrite, Permission.All, Permission.None))));
        assertTrue(new FileMode(FileType.Socket, new Permissions(Permission.All, Permission.None, Permission.Execute)).equals(new FileMode(FileType.Socket, new Permissions(Permission.All, Permission.None, Permission.Execute))));

        assertFalse(new FileMode(FileType.Unknown, new Permissions(Permission.None, Permission.Execute, Permission.Write)).equals(new FileMode(FileType.Unknown, new Permissions(Permission.Execute, Permission.Write, Permission.WriteExecute))));
        assertFalse(new FileMode(FileType.NamedPipe, new Permissions(Permission.Execute, Permission.Write, Permission.WriteExecute)).equals(new FileMode(FileType.NamedPipe, new Permissions(Permission.Write, Permission.WriteExecute, Permission.Read))));
        assertFalse(new FileMode(FileType.CharacterDevice, new Permissions(Permission.Write, Permission.WriteExecute, Permission.Read)).equals(new FileMode(FileType.CharacterDevice, new Permissions(Permission.WriteExecute, Permission.Read, Permission.ReadExecute))));
        assertFalse(new FileMode(FileType.Directory, new Permissions(Permission.WriteExecute, Permission.Read, Permission.ReadExecute)).equals(new FileMode(FileType.Directory, new Permissions(Permission.Read, Permission.ReadExecute, Permission.ReadWrite))));
        assertFalse(new FileMode(FileType.BlockDevice, new Permissions(Permission.Read, Permission.ReadExecute, Permission.ReadWrite)).equals(new FileMode(FileType.BlockDevice, new Permissions(Permission.ReadExecute, Permission.ReadWrite, Permission.All))));
        assertFalse(new FileMode(FileType.File, new Permissions(Permission.ReadExecute, Permission.ReadWrite, Permission.All)).equals(new FileMode(FileType.File, new Permissions(Permission.ReadWrite, Permission.All, Permission.None))));
        assertFalse(new FileMode(FileType.SymbolicLink, new Permissions(Permission.ReadWrite, Permission.All, Permission.None)).equals(new FileMode(FileType.SymbolicLink, new Permissions(Permission.All, Permission.None, Permission.Execute))));
        assertFalse(new FileMode(FileType.Socket, new Permissions(Permission.All, Permission.None, Permission.Execute)).equals(new FileMode(FileType.Socket, new Permissions(Permission.None, Permission.Execute, Permission.Write))));
    }

    @SmallTest
    public void testHashCode() throws Exception
    {
        boolean[] bools = {false, true};
        FileType[] types = {
            FileType.Unknown,
            FileType.NamedPipe,
            FileType.CharacterDevice,
            FileType.Directory,
            FileType.BlockDevice,
            FileType.File,
            FileType.SymbolicLink,
            FileType.Socket,
        };
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

        Set<FileMode> set = new HashSet<FileMode>();

        for (FileType type : types)
        {
            for (Permission user : permissions)
            {
                for (Permission group : permissions)
                {
                    for (Permission others : permissions)
                    {
                        for (boolean setUserId : bools)
                        {
                            for (boolean setGroupId : bools)
                            {
                                for (boolean isSticky : bools)
                                {
                                    FileMode fileMode1 = new FileMode(type, new Permissions(user, group, others, setUserId, setGroupId, isSticky));
                                    FileMode fileMode2 = new FileMode(type, new Permissions(user, group, others, setUserId, setGroupId, isSticky));

                                    assertEquals(fileMode1.hashCode(), fileMode2.hashCode());
                                    assertTrue(set.add(fileMode1));
                                    assertFalse(set.add(fileMode2));
                                }
                            }
                        }
                    }
                }
            }
        }

        assertEquals(32768, set.size());
    }
}