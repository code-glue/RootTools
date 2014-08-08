package com.stericson.RootTools.execution;


import java.io.File;
import java.io.FileNotFoundException;

public class HexDump extends Command
{
    File _file;
    int _radix;
    String _hexDump;

    public File getFile()
    {
        return this._file;
    }

    public int getRadix()
    {
        return this._radix;
    }

    public String getHexDump()
    {
        return this._hexDump;
    }

    public HexDump(int id, String path, int radix) throws FileNotFoundException
    {
        super(id);

        File file = new File(path);
        if (!file.exists())
        {
            throw new FileNotFoundException();
        }
        this._file = file;
        this._radix = radix;
    }

    public HexDump(String path, int radix) throws FileNotFoundException { this(1, path, radix); }

    public HexDump(String path) throws FileNotFoundException { this(path, 10); }

    private static String getCommand(String path, int radix) throws FileNotFoundException
    {
        String format = HexDump.radixToFormat(radix);
        return "hexdump -ve '1/1 \" " + format + "\"' " + path;
    }

    static String radixToFormat(int radix)
    {
        switch (radix)
        {
            case 8:
                return "%o";
            case 10:
                return "%d";
            case 16:
                return "%x";
            default:
                throw new IllegalArgumentException("Unsupported radix '" + radix + "'");
        }
    }

    @Override
    public String[] getCommands()
    {
        String format = HexDump.radixToFormat(this._radix);
        return new String[] { "hexdump -ve '1/1 \" " + format + "\"' " + this._file.getPath() };
    }

    @Override
    public void commandOutput(int id, String line)
    {
        this._hexDump = line;
    }

    @Override
    public String toString()
    {
        return this._hexDump;
    }

    @Override
    public void commandTerminated(int id, String reason) { }

    @Override
    public void commandCompleted(int id, int exitCode) { }
}
