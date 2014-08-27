package com.stericson.RootTools.lib;

import android.test.suitebuilder.annotation.SmallTest;

import com.stericson.RootTools.lib.FileType;

import junit.framework.TestCase;

public class FileTypeTest extends TestCase
{
    @SmallTest
    public void testValueOf_Int() throws Exception
    {
        assertEquals(FileType.Unknown, FileType.valueOf(0x00000000));
        assertEquals(FileType.NamedPipe, FileType.valueOf(0x00001000));
        assertEquals(FileType.CharacterDevice, FileType.valueOf(0x00002000));
        assertEquals(FileType.Unknown, FileType.valueOf(0x00003000));
        assertEquals(FileType.Directory, FileType.valueOf(0x00004000));
        assertEquals(FileType.Unknown, FileType.valueOf(0x00005000));
        assertEquals(FileType.BlockDevice, FileType.valueOf(0x00006000));
        assertEquals(FileType.Unknown, FileType.valueOf(0x00007000));
        assertEquals(FileType.File, FileType.valueOf(0x00008000));
        assertEquals(FileType.Unknown, FileType.valueOf(0x00009000));
        assertEquals(FileType.SymbolicLink, FileType.valueOf(0x0000a000));
        assertEquals(FileType.Unknown, FileType.valueOf(0x0000b000));
        assertEquals(FileType.Socket, FileType.valueOf(0x0000c000));
        assertEquals(FileType.Unknown, FileType.valueOf(0x0000d000));
        assertEquals(FileType.Unknown, FileType.valueOf(0x0000e000));
        assertEquals(FileType.Unknown, FileType.valueOf(0x0000f000));
        assertEquals(FileType.Unknown, FileType.valueOf(0x00010000));
        assertEquals(FileType.Unknown, FileType.valueOf(0x00011000));
        assertEquals(FileType.Unknown, FileType.valueOf(0x00012000));
        assertEquals(FileType.Unknown, FileType.valueOf(0x00013000));
        assertEquals(FileType.Unknown, FileType.valueOf(0x00014000));
        assertEquals(FileType.Unknown, FileType.valueOf(0x00015000));
        assertEquals(FileType.Unknown, FileType.valueOf(0x00016000));
        assertEquals(FileType.Unknown, FileType.valueOf(0x00017000));
        assertEquals(FileType.Unknown, FileType.valueOf(0x00018000));
        assertEquals(FileType.Unknown, FileType.valueOf(0x00019000));
        assertEquals(FileType.Unknown, FileType.valueOf(0x0001a000));
        assertEquals(FileType.Unknown, FileType.valueOf(0x0001b000));
        assertEquals(FileType.Unknown, FileType.valueOf(0x0001c000));
        assertEquals(FileType.Unknown, FileType.valueOf(0x0001d000));
        assertEquals(FileType.Unknown, FileType.valueOf(0x0001e000));
        assertEquals(FileType.Unknown, FileType.valueOf(0x0001f000));
    }

    @SmallTest
    public void testValueOf_Char() throws Exception
    {
        assertEquals(FileType.Unknown, FileType.valueOf('?'));
        assertEquals(FileType.File, FileType.valueOf('-'));
        assertEquals(FileType.BlockDevice, FileType.valueOf('b'));
        assertEquals(FileType.CharacterDevice, FileType.valueOf('c'));
        assertEquals(FileType.Directory, FileType.valueOf('d'));
        assertEquals(FileType.File, FileType.valueOf('f'));
        assertEquals(FileType.SymbolicLink, FileType.valueOf('l'));
        assertEquals(FileType.NamedPipe, FileType.valueOf('p'));
        assertEquals(FileType.Socket, FileType.valueOf('s'));
        assertEquals(FileType.Unknown, FileType.valueOf('a'));
        assertEquals(FileType.Unknown, FileType.valueOf('z'));
    }

    @SmallTest
    public void testParse() throws Exception
    {
        assertEquals(FileType.Unknown, FileType.parse(""));
        assertEquals(FileType.Unknown, FileType.parse("?"));
        assertEquals(FileType.BlockDevice, FileType.parse("b"));
        assertEquals(FileType.CharacterDevice, FileType.parse("c"));
        assertEquals(FileType.Directory, FileType.parse("d"));
        assertEquals(FileType.SymbolicLink, FileType.parse("l"));
        assertEquals(FileType.NamedPipe, FileType.parse("p"));
        assertEquals(FileType.Socket, FileType.parse("s"));
        assertEquals(FileType.Unknown, FileType.parse("a"));
        assertEquals(FileType.Unknown, FileType.parse("z"));
    }

    @SmallTest
    public void testGetValue() throws Exception
    {
        assertEquals(0x00000000, FileType.Unknown.getValue());
        assertEquals(0x00001000, FileType.NamedPipe.getValue());
        assertEquals(0x00002000, FileType.CharacterDevice.getValue());
        assertEquals(0x00004000, FileType.Directory.getValue());
        assertEquals(0x00006000, FileType.BlockDevice.getValue());
        assertEquals(0x00008000, FileType.File.getValue());
        assertEquals(0x0000a000, FileType.SymbolicLink.getValue());
        assertEquals(0x0000c000, FileType.Socket.getValue());
    }

    @SmallTest
    public void testGetSymbol() throws Exception
    {
        assertEquals('?', FileType.Unknown.getSymbol());
        assertEquals('p', FileType.NamedPipe.getSymbol());
        assertEquals('c', FileType.CharacterDevice.getSymbol());
        assertEquals('d', FileType.Directory.getSymbol());
        assertEquals('b', FileType.BlockDevice.getSymbol());
        assertEquals('-', FileType.File.getSymbol());
        assertEquals('l', FileType.SymbolicLink.getSymbol());
        assertEquals('s', FileType.Socket.getSymbol());
    }
}