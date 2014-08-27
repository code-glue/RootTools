package com.stericson.RootTools;

import android.support.annotation.NonNull;

public enum Permission
{
    None(0),
    Execute(1),
    Write(2),
    WriteExecute(3),
    Read(4),
    ReadExecute(5),
    ReadWrite(6),
    All(7);

    private final int _value;

    private Permission(int value)
    {
        this._value = value;
    }

    private static boolean isValidValue(int value)
    {
        return value >=0 && value < 8;
    }

    private static boolean isValidValue(@NonNull char[] value)
    {
        return value.length == 3 &&
               "-r".indexOf(value[0]) >= 0 &&
               "-w".indexOf(value[1]) >= 0 &&
               "-xSsTt".indexOf(value[2]) >= 0;
    }

    public int getValue()
    {
        return this._value;
    }

    @NonNull
    public static Permission valueOf(int value)
    {
        if (!Permission.isValidValue(value)) { throw new IllegalArgumentException("value"); }
        return Permission.values()[value];
    }

    @NonNull
    static Permission parse(@NonNull char[] value)
    {
        if (!Permission.isValidValue(value)) {
            throw new IllegalArgumentException("value=" + String.valueOf(value)); }

        int modes = 0;

        if (value[0] == 'r') { modes |= Permission.Read.getValue(); }
        if (value[1] == 'w') { modes |= Permission.Write.getValue(); }
        if ("xst".indexOf(value[2]) >= 0) { modes |= Permission.Execute.getValue(); }

        return Permission.valueOf(modes);
    }

    @NonNull
    public static Permission parse(@NonNull String value)
    {
        return Permission.parse(value.toCharArray());
    }

    @NonNull
    public String getSymbols()
    {
        StringBuilder stringBuilder = new StringBuilder("rwx");

        for (int i = 0; i < 3; ++i)
        {
            int mask = 1 << i;
            if ((this._value & mask) == 0)
            {
                stringBuilder.setCharAt(2 - i, '-');
            }
        }
        return stringBuilder.toString();
    }

    public boolean hasAccess(@NonNull Permission mode)
    {
        return (this._value & mode._value) == mode._value;
    }

    public boolean canRead()
    {
        return this.hasAccess(Permission.Read);
    }

    public boolean canWrite()
    {
        return this.hasAccess(Permission.Write);
    }

    public boolean canExecute()
    {
        return this.hasAccess(Permission.Execute);
    }

    @NonNull
    public String toOctalString()
    {
        return String.valueOf(this._value);
    }
}
