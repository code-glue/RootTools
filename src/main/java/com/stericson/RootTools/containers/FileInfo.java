//package com.stericson.RootTools.containers;
//
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//
//import com.stericson.RootTools.internal.RootToolsInternal;
//import com.stericson.RootTools.FileType;
//
//import java.io.File;
//import java.util.Date;
//
//
//public class FileInfo
//{
//    //private static final Pattern Regex = Pattern.compile("(^|\\s)([-bcdlps])([-r][-w][-xsS])([-r][-w][-xsS])([-r][-w][-xtT])(\\s|$)");
//
//    private final File _file;
//    private final int _size;
//    private final int _blocks;
//    private final FileType _type;
//    private final Permissions _permissions;
//    private final int _uid;
//    private final int _gid;
//    private final int _device;
//    private final int _inode;
//    private final int _links;
//    private final Date _lastAccessed;
//    private final Date _lastModified;
//    private final Date _lastChanged;
//    private final int _ioBlock;
//
//    private FileInfo(File file, int size, int blocks, FileType fileType, Permissions permissions, int uid, int gid, int device, int inode, int links, Date lastAccessed, Date lastModified, Date lastChanged, int ioBlock)
//    {
//        this._file = file;
//        this._size = size;
//        this._blocks = blocks;
//        this._type = fileType;
//        this._permissions = permissions;
//        this._uid = uid;
//        this._gid = gid;
//        this._device = device;
//        this._inode = inode;
//        this._links = links;
//        this._lastAccessed = lastAccessed;
//        this._lastModified = lastModified;
//        this._lastChanged = lastChanged;
//        this._ioBlock = ioBlock;
//    }
//
//    public static FileInfo parse(@NonNull String value) throws NumberFormatException
//    {
//        String parts[] = value.split("\\s");
//
//        File file = new File(parts[0]);
//        int size = Integer.parseInt(parts[1]);
//        int blocks = Integer.parseInt(parts[2]);
//        int access = Integer.parseInt(parts[3], 16);
//        int uid = Integer.parseInt(parts[4]);
//        int gid = Integer.parseInt(parts[5]);
//        int device = Integer.parseInt(parts[6], 16);
//        int inode = Integer.parseInt(parts[7]);
//        int links = Integer.parseInt(parts[8]);
//
//        int unknown1 = Integer.parseInt(parts[9]);
//        int unknown2 = Integer.parseInt(parts[10]);
//
//        Date lastAccessed = new Date((long)Integer.parseInt(parts[11]) * 1000);
//        Date lastModified = new Date((long)Integer.parseInt(parts[12]) * 1000);
//        Date lastChanged = new Date((long)Integer.parseInt(parts[13]) * 1000);
//        int ioBlock = Integer.parseInt(parts[14]);
//
//        FileType fileType = FileType.valueOf((access & 0xf000) >> 12);
//        Permissions permissions = Permissions.valueOf(access & 0x0fff);
//
//        return new FileInfo(file, size, blocks, fileType, permissions, uid, gid, device, inode, links, lastAccessed, lastModified, lastChanged, ioBlock);
//
//        //Matcher matcher = FileInfo.Regex.matcher(value);
//        //
//        //if (!matcher.find())
//        //{
//        //    throw new IllegalArgumentException("value");
//        //}
//
//        //return FileInfo.parse(matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5));
//    }
//
//    public File getFile()
//    {
//        return _file;
//    }
//
//    public String getName()
//    {
//        return _file.getName();
//    }
//
//    public int getSize()
//    {
//        return _size;
//    }
//
//    public int getBlocks()
//    {
//        return _blocks;
//    }
//
//    public FileType getType()
//    {
//        return _type;
//    }
//
//    public Permissions getPermissions()
//    {
//        return _permissions;
//    }
//
//    public int getUid()
//    {
//        return _uid;
//    }
//
//    public int getGid()
//    {
//        return _gid;
//    }
//
//    public int getDevice()
//    {
//        return _device;
//    }
//
//    public int getInode()
//    {
//        return _inode;
//    }
//
//    public int getLinks()
//    {
//        return _links;
//    }
//
//    public Date getLastAccessed()
//    {
//        return _lastAccessed;
//    }
//
//    public Date getLastModified()
//    {
//        return _lastModified;
//    }
//
//    public Date getLastChanged()
//    {
//        return _lastChanged;
//    }
//
//    public int getIoBlock()
//    {
//        return _ioBlock;
//    }
//
//    public boolean isNamedPipe()
//    {
//        return this._type == FileType.NamedPipe;
//    }
//
//    public boolean isCharDevice()
//    {
//        return this._type == FileType.CharacterDevice;
//    }
//
//    public boolean isDirectory()
//    {
//        return this._type == FileType.Directory;
//    }
//
//    public boolean isBlockDevice()
//    {
//        return this._type == FileType.BlockDevice;
//    }
//
//    public boolean isFile()
//    {
//        return this._type == FileType.File;
//    }
//
//    public boolean isSymLink()
//    {
//        return this._type == FileType.SymbolicLink;
//    }
//
//    public boolean isSocket()
//    {
//        return this._type == FileType.Socket;
//    }
//
//    @Nullable
//    public FileInfo resolvePath()
//    {
//        FileInfo fileInfo = this;
//
//        while (fileInfo._type == FileType.SymbolicLink)
//        {
//            try
//            {
//                fileInfo = RootToolsInternal.getFileStat(RootToolsInternal.resolvePath(fileInfo._file.getPath()));
//            }
//            catch (Exception ignore)
//            {
//                return null;
//            }
//        }
//
//        return fileInfo;
//    }
//
//    public boolean isFileOrFileLink()
//    {
//        if (this.isSymLink())
//        {
//            FileInfo target = this.resolvePath();
//            return target != null && target.isFile();
//        }
//
//        return this.isFile();
//    }
//
//    //public FileInfo(FileType fileType, Permissions permissions)
//    //{
//    //    this._type = fileType;
//    //    this._permissions = permissions;
//    //}
//    //
//    //public FileInfo(FileType fileType, AccessMode user, AccessMode group, AccessMode others, SpecialAccessMode special)
//    //{
//    //    this(fileType, new Permissions(user, group, others, special));
//    //}
//
//
//    //public static FileInfo parse(String fileType, String userAccess, String groupAccess, String othersAccess)
//    //{
//    //    FileType type = FileType.parse(fileType);
//    //    Permissions permissions = Permissions.parse(userAccess, groupAccess, othersAccess);
//    //    return new FileInfo(type, permissions);
//    //}
//
//    //public static FileInfo parse(String userAccess, String groupAccess, String othersAccess)
//    //{
//    //    return FileInfo.parse("-", userAccess, groupAccess, othersAccess);
//    //}
//
//    //private static FileInfo parse(@NonNull String[] value)
//    //{
//    //
//    //}
//
//    @Override
//    public String toString()
//    {
//        return this._type.getSymbol() + this._permissions.toString();
//    }
//}
