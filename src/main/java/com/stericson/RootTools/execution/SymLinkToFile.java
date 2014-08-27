package com.stericson.RootTools.execution;

import java.io.File;
import java.io.FileNotFoundException;

public class SymLinkToFile extends Command
{
    private final File _target;
    private final File _name;

    public File getTarget()
    {
        return this._target;
    }

    public File getName()
    {
        return this._name;
    }

    public SymLinkToFile(int id, String target, String name) throws FileNotFoundException
    {
        super(id);

        File targetFile = new File(target);
        if (!targetFile.exists()) { throw new FileNotFoundException(); }

        this._target = targetFile;
        this._name = new File(name);
    }

    public SymLinkToFile(String target, String name) throws FileNotFoundException { this(1, target, name); }

    private static String getCommand(String target, String name) throws FileNotFoundException
    {
        File targetFile = new File(target);
        if (!targetFile.exists()) { throw new FileNotFoundException(); }

        return String.format("ln -s %s %s", targetFile.getPath(), name);
    }

    @Override
    public String[] getCommands()
    {
        return new String[] { String.format("ln -s %s %s", this._target.getPath(), this._name) };
    }

    @Override
    public void commandOutput(int id, String line)
    {
//        Log.d("SymLinkToFile", "commandOutput(" + id + ", '" + line + "')");
    }

    @Override
    public String toString()
    {
        return this._name.getPath();
    }

    @Override
    public void commandTerminated(int id, String reason)
    {
        synchronized (SymLinkToFile.this)
        {
            SymLinkToFile.this.notify();
        }
    }

    @Override
    public void commandCompleted(int id, int exitCode)
    {
        synchronized (SymLinkToFile.this)
        {
            SymLinkToFile.this.notify();
        }
    }
}
