package com.stericson.RootTools;


import android.text.TextUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileStat
{
    /** Device ID of device containing file. */
    public final long deviceId;

    /** File serial number (inode). */
    public final long inode;

    /** Mode (type, permissions, etc) of file. */
    public final FileMode mode;

    /** Number of hard links to the file.*/
    public final long hardLinkCount;

    /** User ID of file. */
    public final int userId;

    /** Group ID of file. */
    public final int groupId;

    /** Device ID (if file is character or block special). */
    public final long deviceIdSpecial;

    /**
     * For regular files, the file size in bytes.
     * For symbolic links, the length in bytes of the pathname contained in the symbolic link.
     * For a shared memory object, the length in bytes.
     * For a typed memory object, the length in bytes.
     * For other file types, the use of this field is unspecified.
     */
    public final long size;

    /** Time of last access. */
    public final Date lastAccessed;

    /** Time of last data modification. */
    public final Date lastModified;

    /** Time of last status change. */
    public final Date lastChanged;

    /**
     * A file system-specific preferred I/O block size for this object.
     * For some file system types, this may vary from file to file.
     */
    public final long blockSize;

    /** Number of blocks allocated for this object. */
    public final long blockCount;

    public final File file;

    /** Type of file. */
    public final FileType type;

    /** User, group, and others permissions. */
    public final Permissions permissions;

    FileStat(String path, long deviceId, long inode, int mode, long hardLinkCount, int userId, int groupId, long deviceIdSpecial, long size,
                    long lastAccessedMillis, long lastModifiedMillis, long lastChangedMillis, long blockSize, long blockCount)
    {
        this.file = new File(path);
        this.deviceId = deviceId;
        this.inode = inode;
        this.mode = FileMode.valueOf(mode);
        this.hardLinkCount = hardLinkCount;
        this.userId = userId;
        this.groupId = groupId;
        this.deviceIdSpecial = deviceIdSpecial;
        this.size = size;
        this.lastAccessed = new Date(lastAccessedMillis);
        this.lastModified = new Date(lastModifiedMillis);
        this.lastChanged = new Date(lastChangedMillis);
        this.blockSize = blockSize;
        this.blockCount = blockCount;
        this.type = this.mode.type;
        this.permissions = this.mode.permissions;
    }

    FileStat(String path, StructStat stat)
    {
        this(path, stat.st_dev, stat.st_ino, stat.st_mode, stat.st_nlink, stat.st_uid, stat.st_gid, stat.st_rdev,
             stat.st_size, stat.st_atime * 1000, stat.st_mtime * 1000, stat.st_ctime * 1000, stat.st_blksize, stat.st_blocks);
    }

    public boolean isNamedPipe()
    {
        return this.type == FileType.NamedPipe;
    }

    public boolean isCharDevice()
    {
        return this.type == FileType.CharacterDevice;
    }

    public boolean isDirectory()
    {
        return this.type == FileType.Directory;
    }

    public boolean isBlockDevice()
    {
        return this.type == FileType.BlockDevice;
    }

    public boolean isFile()
    {
        return this.type == FileType.File;
    }

    public boolean isSymLink()
    {
        return this.type == FileType.SymbolicLink;
    }

    public boolean isSocket()
    {
        return this.type == FileType.Socket;
    }

    public boolean isUnknownType()
    {
        return this.type == FileType.Unknown;
    }

    /** Returns file information similar to the output of "stat -t". */
    public String toTerseString()
    {
        return TextUtils.join(" ", new String[] {
            this.file.getPath(),
            String.valueOf(this.size),
            String.valueOf(this.blockCount),
            Integer.toHexString(this.mode.getValue()),
            String.valueOf(this.userId),
            String.valueOf(this.groupId),
            Long.toHexString(this.deviceId),
            String.valueOf(this.inode),
            String.valueOf(this.hardLinkCount),
            Long.toHexString(this.deviceIdSpecial >> 8),
            Long.toHexString(this.deviceIdSpecial & 0xff),
            String.valueOf(this.lastAccessed.getTime() / 1000),
            String.valueOf(this.lastModified.getTime() / 1000),
            String.valueOf(this.lastChanged.getTime() / 1000),
            String.valueOf(this.blockSize)
        });
    }

    /** Returns file information similar to the output of "ls -l". */
    public String toLsString()
    {
        String sizeOrDeviceId = "";
        String name = this.file.getName();
        String realPath = this.isSymLink() ? RootTools.getRealPath(this.file.getPath()) : null;

        switch (this.type)
        {
            case File:
                sizeOrDeviceId = String.valueOf(this.size);
                break;
            case CharacterDevice:
            case BlockDevice:
                sizeOrDeviceId = String.format("%3d,%4d", this.deviceIdSpecial >> 8, this.deviceIdSpecial & 0xff);
                break;
            case SymbolicLink:
                name = name + " -> " + (realPath == null ? "(null)" : new File(realPath).getName());
                break;
        }

        return String.format("%s %-8s %-8s %8s %s %s",
                             this.mode.getSymbols(),
                             //this.hardLinkCount,
                             RootTools.getUserName(this.userId),
                             RootTools.getUserName(this.groupId),
                             sizeOrDeviceId,
                             new SimpleDateFormat("yyyy-MM-dd HH:mm").format(this.lastModified),
                             name);
    }

    /** Returns file information similar to the output of "ls -l". */
    @Override
    public String toString()
    {
        return this.toLsString();
    }
}
