package com.stericson.RootTools.execution;

import java.io.FileNotFoundException;

public class ReadAllBytes extends HexDump
{
    byte[] _bytes;

    public ReadAllBytes(int id, String path, int radix) throws FileNotFoundException
    {
        super(id, path, radix);
    }

    public ReadAllBytes(String path, int radix) throws FileNotFoundException
    {
        this(1, path, radix);
    }

    public ReadAllBytes(String path) throws FileNotFoundException
    {
        this(path, 10);
    }

    public byte[] getBytes()
    {
        return this._bytes;
    }

    @Override
    protected void startExecution()
    {
        super.startExecution();
        this._bytes = new byte[0];
    }

    @Override
    public void commandOutput(int id, String line)
    {
        super.commandOutput(id, line);
        this._bytes = ReadAllBytes.hexDumpToBytes(line, this._radix);
    }

    static byte[] hexDumpToBytes(String hexDump, int radix)
    {
        String[] values = hexDump.trim().split("\\s");
        byte[] bytes = new byte[values.length];
        int index = 0;

        for (String value : values)
        {
            bytes[index++] = (byte) Integer.parseInt(value, radix);
        }

        return bytes;
    }

    static byte[] hexDumpToBytes(String hexDump)
    {
        return ReadAllBytes.hexDumpToBytes(hexDump, 10);
    }
}
