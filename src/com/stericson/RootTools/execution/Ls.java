package com.stericson.RootTools.execution;


import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Ls extends Command
{
    private final File _path;
    private final File _directory;
    private List<File> _files;

    public File getPath()
    {
        return this._path;
    }

    public File getDirectory()
    {
        return this._directory;
    }

    public List<File> getFiles()
    {
        return this._files;
    }

    public Ls(int id, String path)
    {
        super(id);

        this._path = new File(path);
        this._directory = new File(this._path.isDirectory() ? this._path.getPath() : this._path.getParent());
    }

    public Ls(String path) throws FileNotFoundException { this(1, path); }

    public Ls(File path) throws FileNotFoundException { this(path.getPath()); }

    public Ls(File directory, String filter) throws FileNotFoundException { this(Ls.combinePaths(directory, filter)); }

    public Ls(String directory, String filter) throws FileNotFoundException { this(new File(directory), filter); }

    private static File combinePaths(File directory, String filter)
    {
        if (!directory.isDirectory()) { throw new IllegalArgumentException("'" + directory + "' is not a directory."); }
        return new File(directory, filter);
    }

    @Override
    public String[] getCommands()
    {
        return new String[] { "ls " + this._path };
    }

    @Override
    protected void startExecution()
    {
//        Log.d("Ls", "startExecution()");
        super.startExecution();
        this._files = new ArrayList<>();
    }

    @Override
    public void commandOutput(int id, String line)
    {
//        Log.d("Ls", "commandOutput(" + id + ", '" + line + "')");
        this._files.add(new File(this._directory, new File(line).getName()));
    }

    @Override
    public String toString()
    {
        if (this._files == null) { return ""; }
        List<String> filePaths = new ArrayList<>();
        for (File file : this._files) { filePaths.add(file.getPath()); }
        return TextUtils.join(System.getProperty("line.separator"), filePaths);
    }

    @Override
    public void commandTerminated(int id, String reason)
    {
        synchronized (Ls.this)
        {
            Ls.this.notify();
        }
    }

    @Override
    public void commandCompleted(int id, int exitCode)
    {
        synchronized (Ls.this)
        {
            Ls.this.notify();
        }
    }
}
