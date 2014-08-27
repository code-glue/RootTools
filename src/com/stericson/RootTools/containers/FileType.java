package com.stericson.RootTools.containers;


public enum FileType
{
    File(0, '-'),
    Directory(1, 'd'),
    CharacterDevice(2, 'c'),
    BlockDevice(3, 'b'),
    Socket(4, 's'),
    NamedPipe(5, 'p'),
    SymbolicLink(6, 'l');

    private final int _index;
    private final char _symbol;

    FileType(int index, char symbol)
    {
        this._index = index;
        this._symbol = symbol;
    }

    private static boolean isValidValue(int value)
    {
        return value >= 0 && value < 7;
    }

    public static FileType valueOf(int value)
    {
        if (!FileType.isValidValue(value)) { throw new IllegalArgumentException("value"); }
        return FileType.values()[value];
    }

    public static FileType valueOf(char symbol)
    {
        switch (symbol)
        {
            case '-': return FileType.File;
            case 'b': return FileType.BlockDevice;
            case 'c': return FileType.CharacterDevice;
            case 'd': return FileType.Directory;
            case 'l': return FileType.SymbolicLink;
            case 'p': return FileType.NamedPipe;
            case 's': return FileType.Socket;
        }

        throw new IllegalArgumentException("symbol");
    }

    public static FileType parse(String symbol)
    {
        if (symbol == null || symbol.length() != 1) { throw new IllegalArgumentException("symbol"); }
        return FileType.valueOf(symbol.charAt(0));
    }

    public int getValue()
    {
        return this._index;
    }

    public char getSymbol()
    {
        return this._symbol;
    }
}