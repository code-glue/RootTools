package com.stericson.RootTools;

import android.support.annotation.NonNull;

/**
 * Represents one of seven file types.
 *
 * Based on http://man7.org/linux/man-pages/man2/stat.2.html
 *
 * S_IFMT     0170000   bit mask for the file type bit fields
 *
 * S_IFIFO    0010000   FIFO
 * S_IFCHR    0020000   character device
 * S_IFDIR    0040000   directory
 * S_IFBLK    0060000   block device
 * S_IFREG    0100000   regular file
 * S_IFLNK    0120000   symbolic link
 * S_IFSOCK   0140000   socket
*/
public enum FileType
{
    Unknown         (0x00000000, '?'),
    NamedPipe       (0x00001000, 'p'),
    CharacterDevice (0x00002000, 'c'),
    Directory       (0x00004000, 'd'),
    BlockDevice     (0x00006000, 'b'),
    File            (0x00008000, '-'),
    SymbolicLink    (0x0000a000, 'l'),
    Socket          (0x0000c000, 's');

    private final int _value;
    private final char _symbol;

    FileType(int value, char symbol)
    {
        this._value = value;
        this._symbol = symbol;
    }


    @NonNull
    public static FileType valueOf(int value)
    {
        switch (value)
        {
            case 0x00001000:
                return FileType.NamedPipe;
            case 0x00002000:
                return FileType.CharacterDevice;
            case 0x00004000:
                return FileType.Directory;
            case 0x00006000:
                return FileType.BlockDevice;
            case 0x00008000:
                return FileType.File;
            case 0x0000a000:
                return FileType.SymbolicLink;
            case 0x0000c000:
                return FileType.Socket;
        }

        return FileType.Unknown;
    }

    @NonNull
    public static FileType valueOf(char symbol)
    {
        switch (symbol)
        {
            case '-':
            case 'f':
                return FileType.File;
            case 'b':
                return FileType.BlockDevice;
            case 'c':
                return FileType.CharacterDevice;
            case 'd':
                return FileType.Directory;
            case 'l':
                return FileType.SymbolicLink;
            case 'p':
                return FileType.NamedPipe;
            case 's':
                return FileType.Socket;
        }

        return FileType.Unknown;
    }

    @NonNull
    public static FileType parse(@NonNull String symbol)
    {
        if (symbol.length() == 0) { return FileType.Unknown; }
        if (symbol.length() != 1) { throw new IllegalArgumentException("symbol"); }
        return FileType.valueOf(symbol.charAt(0));
    }

    public int getValue()
    {
        return this._value;
    }

    public char getSymbol()
    {
        return this._symbol;
    }
}