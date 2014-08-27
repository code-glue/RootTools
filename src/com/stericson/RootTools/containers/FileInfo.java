package com.stericson.RootTools.containers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FileInfo
{
    private static final Pattern Regex = Pattern.compile("(^|\\s)([-bcdlps])([-r][-w][-xsS])([-r][-w][-xsS])([-r][-w][-xtT])(\\s|$)");

    private final FileType _fileType;
    private final Permissions _permissions;

    public FileType getFileType()
    {
        return _fileType;
    }

    public Permissions getPermissions()
    {
        return _permissions;
    }

    public FileInfo(FileType fileType, Permissions permissions)
    {
        this._fileType = fileType;
        this._permissions = permissions;
    }

    public FileInfo(FileType fileType, AccessMode user, AccessMode group, AccessMode others, SpecialAccessMode special)
    {
        this(fileType, new Permissions(user, group, others, special));
    }

    public static FileInfo parse(String fileType, String userAccess, String groupAccess, String othersAccess)
    {
        FileType type = FileType.parse(fileType);
        Permissions permissions = Permissions.parse(userAccess, groupAccess, othersAccess);
        return new FileInfo(type, permissions);
    }

    public static FileInfo parse(String userAccess, String groupAccess, String othersAccess)
    {
        return FileInfo.parse("-", userAccess, groupAccess, othersAccess);
    }

    public static FileInfo parse(String value)
    {
        Matcher matcher = FileInfo.Regex.matcher(value);

        if (!matcher.find())
        {
            throw new IllegalArgumentException("value");
        }

        return FileInfo.parse(matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5));
    }

    @Override
    public String toString()
    {
        return this._fileType.getSymbol() + this._permissions.toString();
    }
}
