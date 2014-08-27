package com.stericson.RootTools;

import android.support.annotation.NonNull;

public class FileMode implements Comparable<FileMode>
{
    private static final int FileTypeMask    = 0x0000F000;
    private static final int PermissionsMask = 0x00000FFF;

    public final FileType type;
    public final Permissions permissions;

    public FileMode(@NonNull FileType type, @NonNull Permissions permissions)
    {
        this.type = type;
        this.permissions = permissions;
    }

    public FileMode(@NonNull FileType type, Permission user, Permission group, Permission others, boolean setUserId, boolean setGroupId, boolean isSticky)
    {
        this(type, new Permissions(user, group, others, setUserId, setGroupId, isSticky));
    }

    public FileMode(@NonNull FileType type, Permission user, Permission group, Permission others)
    {
        this(type, user, group, others, false, false, false);
    }

    @NonNull
    public static FileMode valueOf(int value)
    {
        return new FileMode(FileType.valueOf(value & FileMode.FileTypeMask), Permissions.valueOf(value & FileMode.PermissionsMask));
    }

    @NonNull
    public static FileMode parse(@NonNull String value)
    {
        if (value.length() != 10) { throw new IllegalArgumentException("value"); }

        return new FileMode(FileType.valueOf(value.charAt(0)), Permissions.parse(value.substring(1)));
    }

    public int getValue()
    {
        return this.type.getValue() | this.permissions.getValue();
    }

    /** Returns the symbolic representation of the mode (e.g. drwx--x--x). */
    @NonNull
    public String getSymbols()
    {
        return this.type.getSymbol() + this.permissions.getSymbols();
    }

    @Override
    public String toString()
    {
        return this.getSymbols();
    }

    @Override
    public int compareTo(@NonNull FileMode other)
    {
        return Integer.valueOf(this.getValue()).compareTo(other.getValue());
    }

    @Override
    public boolean equals(Object other)
    {
        return other instanceof FileMode && this.compareTo((FileMode)other) == 0;
    }

    @Override
    public int hashCode()
    {
        return Integer.valueOf(this.getValue()).hashCode();
    }
}
