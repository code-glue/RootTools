package com.stericson.RootTools.execution;

import android.content.Context;

import com.stericson.RootTools.RootTools;

public class JavaCommandCapture extends Command
{
    private final StringBuilder sb = new StringBuilder();
    private String[] _commands;

    public JavaCommandCapture(int id, Context context, String... command)
    {
        super(id, true, context);//, command);
        this._commands = command;
    }

    public JavaCommandCapture(int id, boolean handlerEnabled, Context context, String... command)
    {
        super(id, handlerEnabled, true, context); //, command);
        this._commands = command;
    }

    public JavaCommandCapture(int id, int timeout, Context context, String... command)
    {
        super(id, timeout, true, context); //, command);
        this._commands = command;
    }

    @Override
    public String[] getCommands()
    {
        return this._commands;
    }

    @Override
    public void commandOutput(int id, String line)
    {
        sb.append(line).append('\n');
        RootTools.log("Command", "ID: " + id + ", " + line);
    }

    @Override
    public void commandTerminated(int id, String reason)
    {
        // pass
    }

    @Override
    public void commandCompleted(int id, int exitCode)
    {
        // pass
    }

    @Override
    public String toString()
    {
        return sb.toString();
    }

}
