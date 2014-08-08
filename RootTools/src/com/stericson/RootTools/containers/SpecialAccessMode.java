package com.stericson.RootTools.containers;

public enum SpecialAccessMode
{
    None(0),
    Sticky(1),
    SetGroupId(2),
    StickySetGroupId(3),
    SetUserId(4),
    StickySetUserId(5),
    SetUserAndGroupId(6),
    All(7);

    private final int _value;

    private SpecialAccessMode(int value)
    {
        this._value = value;
    }

    private static boolean isValidValue(int value)
    {
        return value >=0 && value < 8;
    }

    private static boolean isValidValue(char[] value)
    {
        return  value != null &&
                value.length == 3 &&
               "-r".indexOf(value[0]) >= 0 &&
               "-w".indexOf(value[1]) >= 0 &&
               "-xsStT".indexOf(value[2]) >= 0;
    }

    public int getValue()
    {
        return this._value;
    }

    public static SpecialAccessMode valueOf(int value)
    {
        if (!SpecialAccessMode.isValidValue(value)) { throw new IllegalArgumentException("value"); }
        return SpecialAccessMode.values()[value];
    }

    public static SpecialAccessMode valueOf(boolean sticky, boolean setGroupId, boolean setUserId)
    {
        int mode = 0;

        if (sticky) { mode |= SpecialAccessMode.Sticky.getValue(); }
        if (setGroupId) { mode |= SpecialAccessMode.SetGroupId.getValue(); }
        if (setUserId) { mode |= SpecialAccessMode.SetUserAndGroupId.getValue(); }

        return SpecialAccessMode.valueOf(mode);
    }

    private static SpecialAccessMode valueOf(char[] userAccess, char[] groupAccess, char[] othersAccess)
    {
        if (!SpecialAccessMode.isValidValue(userAccess)) { throw new IllegalArgumentException("userAccess"); }
        if (!SpecialAccessMode.isValidValue(groupAccess)) { throw new IllegalArgumentException("groupAccess"); }
        if (!SpecialAccessMode.isValidValue(othersAccess)) { throw new IllegalArgumentException("othersAccess"); }

        int mode = 0;

        if (Character.toLowerCase(userAccess[2]) == 's') { mode |= SpecialAccessMode.SetUserId.getValue(); }
        if (Character.toLowerCase(groupAccess[2]) == 's') { mode |= SpecialAccessMode.SetGroupId.getValue(); }
        if (Character.toLowerCase(othersAccess[2]) == 't') { mode |= SpecialAccessMode.Sticky.getValue(); }

        return SpecialAccessMode.valueOf(mode);
    }

    public static SpecialAccessMode valueOf(String userAccess, String groupAccess, String othersAccess)
    {
        if (userAccess == null) { throw new NullPointerException("userAccess"); }
        if (groupAccess == null) { throw new NullPointerException("groupAccess"); }
        if (othersAccess == null) { throw new NullPointerException("othersAccess"); }

        return SpecialAccessMode.valueOf(userAccess.toCharArray(), groupAccess.toCharArray(), othersAccess.toCharArray());
    }

    public boolean hasMode(SpecialAccessMode mode)
    {
        return (this._value & mode._value) != 0;
    }

    public boolean isSticky()
    {
        return this.hasMode(SpecialAccessMode.Sticky);
    }

    public boolean isSetGroupId()
    {
        return this.hasMode(SpecialAccessMode.SetGroupId);
    }

    public boolean isSetUserId()
    {
        return this.hasMode(SpecialAccessMode.SetUserId);
    }
}
