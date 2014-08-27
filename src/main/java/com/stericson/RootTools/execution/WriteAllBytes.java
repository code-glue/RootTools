package com.stericson.RootTools.execution;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.sql.CommonDataSource;

public class WriteAllBytes extends Command
{
    private Context _context;
    private byte[] _bytes;
    private File _file;
    private File _tempFile;

    public Context getContext()
    {
        return this._context;
    }

    public byte[] getBytes()
    {
        return this._bytes;
    }

    public File getFile()
    {
        return this._file;
    }

    public WriteAllBytes(int id, Context context, File file, byte[] bytes) throws IOException
    {
        super(id);

        this._context = context;
        this._file = file;
        this._bytes = bytes;

        String tempFileName = file.getName() + "." + UUID.randomUUID().toString();
        FileOutputStream fileOutputStream = context.openFileOutput(tempFileName, Context.MODE_PRIVATE);
        fileOutputStream.write(bytes);
        fileOutputStream.close();
        this._tempFile = this._context.getFileStreamPath(tempFileName);
    }

    public WriteAllBytes(Context context, File file, byte[] bytes) throws IOException
    {
        this(1, context, file, bytes);
    }

    public WriteAllBytes(Context context, String path, byte[] bytes) throws IOException
    {
        this(1, context, new File(path), bytes);
    }

    @Override
    public String[] getCommands()
    {
        Log.d("WriteAllBytes", "cat " + this._tempFile.getPath() + " > " + this._file.getPath());
        return new String[] { "cat " + this._tempFile.getPath() + " > " + this._file.getPath() };
    }

    @Override
    public void commandTerminated(int id, String reason)
    {
    }

    @Override
    public void commandOutput(int id, String line)
    {
    }

    @Override
    public void commandCompleted(int id, int exitCode)
    {
        this._context.deleteFile(this._tempFile.getName());
    }
}
