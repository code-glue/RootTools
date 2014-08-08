package com.stericson.RootTools.containers;

public enum AccessMode
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

    private AccessMode(int value)
    {
        this._value = value;
    }

    private static boolean isValidValue(int value)
    {
        return value >=0 && value < 8;
    }

    private static boolean isValidValue(char[] value)
    {
        return value != null && value.length == 3 &&
               "-r".indexOf(value[0]) >= 0 &&
               "-w".indexOf(value[1]) >= 0 &&
               "-xsStT".indexOf(value[2]) >= 0;
    }

    public int getValue()
    {
        return this._value;
    }

    public static AccessMode valueOf(int value)
    {
        if (!AccessMode.isValidValue(value)) { throw new IllegalArgumentException("value"); }
        return AccessMode.values()[value];
    }

    private static AccessMode parse(char[] value)
    {
        if (!AccessMode.isValidValue(value)) { throw new IllegalArgumentException("value"); }

        int modes = 0;

        if (value[0] == 'r') { modes |= AccessMode.Read.getValue(); }
        if (value[1] == 'w') { modes |= AccessMode.Write.getValue(); }
        if ("xst".indexOf(value[2]) >= 0) { modes |= AccessMode.Execute.getValue(); }

        return AccessMode.valueOf(modes);
    }

    public static AccessMode parse(String value)
    {
        if (value == null) { throw new NullPointerException("value"); }
        return AccessMode.parse(value.toCharArray());
    }

    @Override
    public String toString()
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

    public boolean hasAccess(AccessMode mode)
    {
        return (this._value & mode._value) != 0;
    }

    public boolean canRead()
    {
        return this.hasAccess(AccessMode.Read);
    }

    public boolean canWrite()
    {
        return this.hasAccess(AccessMode.Write);
    }

    public boolean canExecute()
    {
        return this.hasAccess(AccessMode.Execute);
    }
}
