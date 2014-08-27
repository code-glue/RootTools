package com.stericson.RootTools.lib;

import android.support.annotation.NonNull;

public final class UserInfo
{
    public final String name;
    public final int userId;
    public final int groupId;
    public final String directory;
    public final String shell;

    public UserInfo(@NonNull String name, int userId, int groupId, @NonNull String directory, @NonNull String shell)
    {
        this.name = name;
        this.userId = userId;
        this.groupId = groupId;
        this.directory = directory;
        this.shell = shell;
    }

    UserInfo(StructPasswd passwd)
    {
        this(passwd.pw_name, passwd.pw_uid, passwd.pw_gid, passwd.pw_dir, passwd.pw_shell);
    }

    @NonNull
    @Override
    public String toString()
    {
        return String.format("%s:%d:%d:%s:%s", this.name, this.userId, this.groupId, this.directory, this.shell);
    }
}
