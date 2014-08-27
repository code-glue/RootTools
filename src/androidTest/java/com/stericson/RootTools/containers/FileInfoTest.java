//package com.stericson.RootTools.containers;
//
//import com.stericson.RootTools.lib.Permission;
//import com.stericson.RootTools.lib.FileType;
//
//import junit.framework.TestCase;
//
//import java.text.SimpleDateFormat;
//
//public class FileInfoTest extends TestCase
//{
//    public void testGetFileType() throws Exception
//    {
//
//    }
//
//    public void testGetPermissions() throws Exception
//    {
//
//    }
//
//    public void testParseRootFile() throws Exception
//    {
//        String value = "/data/data/com.king.candycrushsaga/app_storage/settings.dat 20 8 8180 10085 10085 10302 11375 1 0 0 1406872134 1406872135 1406872136 4096";
//        FileInfo fileInfo = FileInfo.parse(value);
//
//        assertEquals("/data/data/com.king.candycrushsaga/app_storage/settings.dat", fileInfo.getFile().getPath());
//        //assertNull(fileInfo.resolvePath());
//        assertEquals("settings.dat", fileInfo.getName());
//        assertEquals(20, fileInfo.getSize());
//        assertEquals(8, fileInfo.getBlocks());
//        assertEquals(FileType.File, fileInfo.getType());
//        assertEquals(Permission.ReadWrite, fileInfo.getPermissions().getUserAccess());
//        assertEquals(Permission.None, fileInfo.getPermissions().getGroupAccess());
//        assertEquals(Permission.None, fileInfo.getPermissions().getOthersAccess());
//        assertEquals(SpecialPermission.None, fileInfo.getPermissions().getSpecialAccess());
//        assertEquals(10085, fileInfo.getUid());
//        assertEquals(10085, fileInfo.getGid());
//        assertEquals(66306, fileInfo.getDevice());
//        assertEquals(11375, fileInfo.getInode());
//        assertEquals(1, fileInfo.getLinks());
//        assertEquals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2014-08-01 00:48:54"), fileInfo.getLastAccessed());
//        assertEquals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2014-08-01 00:48:55"), fileInfo.getLastModified());
//        assertEquals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2014-08-01 00:48:56"), fileInfo.getLastChanged());
//        assertEquals(4096, fileInfo.getIoBlock());
//    }
//
//    // TODO
//    public void testParseDot() throws Exception
//    {
//        String value = ". 20 8 8180 10085 10085 10302 11375 1 0 0 1406872134 1406872135 1406872136 4096";
//        FileInfo fileInfo = FileInfo.parse(value);
//
//        assertEquals("/data/data/com.king.candycrushsaga/app_storage/settings.dat", fileInfo.getFile().getPath());
//        //assertNull(fileInfo.resolvePath());
//        assertEquals("settings.dat", fileInfo.getName());
//        assertEquals(20, fileInfo.getSize());
//        assertEquals(8, fileInfo.getBlocks());
//        assertEquals(FileType.File, fileInfo.getType());
//        assertEquals(Permission.ReadWrite, fileInfo.getPermissions().getUserAccess());
//        assertEquals(Permission.None, fileInfo.getPermissions().getGroupAccess());
//        assertEquals(Permission.None, fileInfo.getPermissions().getOthersAccess());
//        assertEquals(SpecialPermission.None, fileInfo.getPermissions().getSpecialAccess());
//        assertEquals(10085, fileInfo.getUid());
//        assertEquals(10085, fileInfo.getGid());
//        assertEquals(66306, fileInfo.getDevice());
//        assertEquals(11375, fileInfo.getInode());
//        assertEquals(1, fileInfo.getLinks());
//        assertEquals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2014-08-01 00:48:54"), fileInfo.getLastAccessed());
//        assertEquals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2014-08-01 00:48:55"), fileInfo.getLastModified());
//        assertEquals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2014-08-01 00:48:56"), fileInfo.getLastChanged());
//        assertEquals(4096, fileInfo.getIoBlock());
//    }
//
//    public void testParseSymlink() throws Exception
//    {
//        String value = "/system/xbin/groups 7 0 a1ff 0 0 10301 16722 1 0 0 1402876190 1402876190 1402876190 4096";
//        FileInfo fileInfo = FileInfo.parse(value);
//
//        assertEquals("/system/xbin/groups", fileInfo.getFile().getPath());
//        assertEquals(FileType.SymbolicLink, fileInfo.getType());
//        assertEquals("/system/xbin/busybox", fileInfo.resolvePath().getFile().getPath());
//        assertEquals("groups", fileInfo.getName());
//        assertEquals(7, fileInfo.getSize());
//        assertEquals(0, fileInfo.getBlocks());
//        assertEquals(Permission.All, fileInfo.getPermissions().getUserAccess());
//        assertEquals(Permission.All, fileInfo.getPermissions().getGroupAccess());
//        assertEquals(Permission.All, fileInfo.getPermissions().getOthersAccess());
//        assertEquals(SpecialPermission.None, fileInfo.getPermissions().getSpecialAccess());
//        assertEquals(0, fileInfo.getUid());
//        assertEquals(0, fileInfo.getGid());
//        assertEquals(66305, fileInfo.getDevice());
//        assertEquals(16722, fileInfo.getInode());
//        assertEquals(1, fileInfo.getLinks());
//        assertEquals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2014-06-15 18:49:50"), fileInfo.getLastAccessed());
//        assertEquals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2014-06-15 18:49:50"), fileInfo.getLastModified());
//        assertEquals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2014-06-15 18:49:50"), fileInfo.getLastChanged());
//        assertEquals(4096, fileInfo.getIoBlock());
//    }
//
//    public void testToString() throws Exception
//    {
//    }
//}