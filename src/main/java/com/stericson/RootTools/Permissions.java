package com.stericson.RootTools;

import android.support.annotation.NonNull;

public class Permissions implements Comparable<Permissions>
{
    private static final int StickyFlag = 0x00000200;
    private static final int SetGroupIdFlag = 0x00000400;
    private static final int SetUserIdFlag = 0x00000800;
    private static final int UserMask = 0x0000001C0;
    private static final int GroupMask = 0x000000038;
    private static final int OthersMask = 0x000000007;
    
    public final Permission user;
    public final Permission group;
    public final Permission others;
    public final boolean setUserId;
    public final boolean setGroupId;
    public final boolean isSticky;

    //@IntDef(flag=true)
    //public static int flag0 = 0;
    //public static int flag1 = 1;
    //public static int flag2 = 2;
    //public static int flag3 = 3;

    public Permissions(Permission user, Permission group, Permission others, boolean setUserId, boolean setGroudId, boolean isSticky)
    {
        this.user = user == null ? Permission.None : user;
        this.group = group == null ? Permission.None : group;
        this.others = others == null ? Permission.None : others;
        this.setUserId = setUserId;
        this.setGroupId = setGroudId;
        this.isSticky = isSticky;
    }

    public Permissions(Permission user, Permission group, Permission others)
    {
        this(user, group, others, false, false, false);
    }

    public Permissions(Permission all)
    {
        this(all, all, all);
    }

    @NonNull
    public static Permissions valueOf(int value)
    {
        return new Permissions(Permission.valueOf((value & Permissions.UserMask) >> 6),
                               Permission.valueOf((value & Permissions.GroupMask) >> 3),
                               Permission.valueOf(value & Permissions.OthersMask),
                               (value & Permissions.SetUserIdFlag) != 0,
                               (value & Permissions.SetGroupIdFlag) != 0,
                               (value & Permissions.StickyFlag) != 0);
    }

    @NonNull
    public static Permissions parse(@NonNull String value)
    {
        if (value.length() != 9) { throw new IllegalArgumentException("value"); }

        return new Permissions(Permission.parse(value.substring(0, 3)),
                               Permission.parse(value.substring(3, 6)),
                               Permission.parse(value.substring(6)),
                               Character.toLowerCase(value.charAt(2)) == 's',
                               Character.toLowerCase(value.charAt(5)) == 's',
                               Character.toLowerCase(value.charAt(8)) == 't');
    }

    private int getSpecialValue()
    {
        int value = 0;

        if (this.setUserId) { value |= Permissions.SetUserIdFlag; }
        if (this.setGroupId) { value |= Permissions.SetGroupIdFlag; }
        if (this.isSticky) { value |= Permissions.StickyFlag; }

        return value;
    }

    public int getValue()
    {
        return this.getSpecialValue() | (this.user.getValue() << 6) | (this.group.getValue() << 3) | this.others.getValue();
    }

    /** Returns the numeric (octal) representation of the permissions (e.g. 751). */
    @NonNull
    public String toOctalString()
    {
        return String.valueOf(this.getSpecialValue() >> 9) + this.user.toOctalString() + this.group.toOctalString() + this.others.toOctalString();
    }

    /** Returns the symbolic representation of the permissions (e.g. rwx--x--x). */
    @NonNull
    public String getSymbols()
    {
        StringBuilder stringBuilder = new StringBuilder(10)
            .append(this.user.getSymbols())
            .append(this.group.getSymbols())
            .append(this.others.getSymbols());

        if (this.setUserId) { stringBuilder.setCharAt(2, this.user.canExecute() ? 's' : 'S'); }
        if (this.setGroupId) { stringBuilder.setCharAt(5, this.group.canExecute() ? 's' : 'S'); }
        if (this.isSticky) { stringBuilder.setCharAt(8, this.others.canExecute() ? 't' : 'T'); }

        return stringBuilder.toString();
    }

    /** Returns the symbolic representation of the permissions (e.g. rwx--x--x). */
    @Override
    public String toString()
    {
        return this.getSymbols();
    }

    @Override
    public int compareTo(@NonNull Permissions other)
    {
        return Integer.valueOf(this.getValue()).compareTo(other.getValue());
    }

    @Override
    public boolean equals(Object other)
    {
        return other instanceof Permissions && this.compareTo((Permissions)other) == 0;
    }

    @Override
    public int hashCode()
    {
        return Integer.valueOf(this.getValue()).hashCode();
    }
}
