/* 
 * This file is part of the RootTools Project: http://code.google.com/p/roottools/
 *  
 * Copyright (c) 2012 Stephen Erickson, Chris Ravenscroft, Dominik Schuermann, Adam Shanks
 *  
 * This code is dual-licensed under the terms of the Apache License Version 2.0 and
 * the terms of the General Public License (GPL) Version 2.
 * You may use this code according to either of these licenses as is most appropriate
 * for your project on a case-by-case basis.
 * 
 * The terms of each license can be found in the root directory of this project's repository as well as at:
 * 
 * * http://www.apache.org/licenses/LICENSE-2.0
 * * http://www.gnu.org/licenses/gpl-2.0.txt
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under these Licenses is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See each License for the specific language governing permissions and
 * limitations under that License.
 */

package com.stericson.RootTools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.stericson.RootTools.containers.Mount;
import com.stericson.RootTools.containers.SymbolicLink;
import com.stericson.RootTools.execution.Command;
import com.stericson.RootTools.execution.CommandCapture;
import com.stericson.RootTools.execution.Shell;
import com.stericson.RootTools.execution.SimpleCommand;
import com.stericson.RootTools.internal.Remounter;
import com.stericson.RootTools.internal.Runner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RootTools
{
    public static final String RedirectErrorToNull = " 2>/dev/null";
    /**
     * Setting this will change the default command timeout.
     * <p/>
     * The default is 20000ms
     */
    public static final int default_Command_Timeout = 20000;
    // regex to get pid out of ps line, example:
    // root 2611 0.0 0.0 19408 2104 pts/2 S 13:41 0:00 bash
    protected static final Pattern processRegexPattern = Pattern.compile("^\\S+\\s+([0-9]+).*$");
    private static final String[] binPaths = {
        "/sbin/",
        "/system/bin/",
        "/system/xbin/",
        "/data/local/xbin/",
        "/data/local/bin/",
        "/system/sd/xbin/",
        "/system/bin/failsafe/",
        "/data/local/"
    };
    //    public static final List<String> lastFoundBinaryPaths = new ArrayList<>();
    public static boolean debugMode = false;

    // --------------------
    // # Public Variables #
    // --------------------
    /**
     * Setting this to false will disable the handler that is used
     * by default for the 3 callback methods for Command.
     * <p/>
     * By disabling this all callbacks will be called from a thread other than
     * the main UI thread.
     */
    public static boolean handlerEnabled = false;
//    public static String utilPath;

    /**
     * This class is the gateway to every functionality within the RootTools library.The developer
     * should only have access to this class and this class only.This means that this class should
     * be the only one to be public.The rest of the classes within this library must not have the
     * public modifier.
     * <p/>
     * All methods and Variables that the developer may need to have access to should be here.
     * <p/>
     * If a method, or a specific functionality, requires a fair amount of code, or work to be done,
     * then that functionality should probably be moved to its own class and the call to it done
     * here.For examples of this being done, look at the remount functionality.
     */

    @NonNull
    public static String getErrorMessage(final int errorCode)
    {
        return RootToolsNative.strerror(errorCode);
    }

    @Nullable
    public static FileStat getFileStat(@NonNull final String path, final boolean followLinks)
    {
        try
        {
            StructStat structStat = RootToolsNative.stat(path, followLinks, false);
            return structStat == null ? null : new FileStat(path, structStat);
        }
        catch (ErrnoException ignore) { return null; }
    }


    /**
     * @param path String that represent the file, including the full path to the file and its name.
     * @return An instance of the class permissions from which you can get the permissions of the
     * file or if the file could not be found or permissions couldn't be determined then
     * permissions will be null.
     */
    @Nullable
    public static FileStat getFileStat(@NonNull final String path)
    {
        return RootTools.getFileStat(path, true);
    }

    @Nullable
    public static FileStat getFileStat(@NonNull final File file, final boolean followLinks)
    {
        return RootTools.getFileStat(file.getPath(), followLinks);
    }

    @Nullable
    public static FileStat getFileStat(@NonNull final File file)
    {
        return RootTools.getFileStat(file.getPath());
    }

    @NonNull
    public static FileStat getFileStatAssert(@NonNull final String path, final boolean followLinks) throws ErrnoException
    {
        return new FileStat(path, RootToolsNative.stat(path, followLinks, true));
    }

    @NonNull
    public static FileStat getFileStatAssert(@NonNull final String path) throws ErrnoException
    {
        return RootTools.getFileStatAssert(path, true);
    }

    @NonNull
    public static FileStat getFileStatAssert(@NonNull final File file, final boolean followLinks) throws ErrnoException
    {
        return RootTools.getFileStatAssert(file.getPath(), followLinks);
    }

    @NonNull
    public static FileStat getFileStatAssert(@NonNull final File file) throws ErrnoException
    {
        return RootTools.getFileStatAssert(file.getPath());
    }

    @Nullable
    public static UserInfo getUserInfo(final int userId)
    {
        try
        {
            StructPasswd passwd = RootToolsNative.getpwuid(userId);
            return passwd == null ? null : new UserInfo(passwd);
        }
        catch (ErrnoException ignore) { return null; }
    }

    @NonNull
    public static UserInfo getUserInfoAssert(final int userId) throws ErrnoException
    {
        return new UserInfo(RootToolsNative.getpwuid(userId));
    }

    @Nullable
    public static String getUserName(final int userId)
    {
        UserInfo userInfo = RootTools.getUserInfo(userId);
        return userInfo == null ? null : userInfo.name;
    }

    @NonNull
    public static String getUserNameAssert(final int userId) throws ErrnoException
    {
        return RootTools.getUserInfoAssert(userId).name;
    }

    @Nullable
    public static String getRealPath(@NonNull final String path)
    {
        try
        {
            String realPath = RootToolsNative.realpath(path, false);
            return realPath == null ? null : realPath;
        }
        catch (ErrnoException ignore) { return null; }
    }

    @NonNull
    public static String getRealPathAssert(@NonNull final String path) throws ErrnoException
    {
        return RootToolsNative.realpath(path, true);
    }

    @NonNull
    public static List<String> getBinPaths()
    {
        return Arrays.asList(RootTools.binPaths);
    }

    @NonNull
    public static String cleanPath(@NonNull final String path)
    {
        return new File(path).getPath();
    }

    @NonNull
    public static File combinePaths(@NonNull final File parent, @NonNull final String... child)
    {
        File combined = parent;

        for (int i = 0; i < child.length; ++i)
        {
            combined = new File(parent, child[i]);
        }

        return combined;
    }

    @NonNull
    public static String combinePaths(@NonNull final String parent, @NonNull final String... child)
    {
        return RootTools.combinePaths(new File(parent), child).getPath();
    }

    public static boolean setCurrentTime(long milliseconds)
    {
        final String alarmPath = "/dev/alarm";
        final String rtcPath = "/dev/rtc0";

        FileStat alarmStat = RootTools.getFileStat(alarmPath);
        FileStat rtcStat = RootTools.getFileStat(rtcPath);

        boolean setAlarmPermissions = alarmStat != null && !alarmStat.permissions.others.hasAccess(Permission.ReadWrite);
        boolean setRtcPermissions = rtcStat != null && !rtcStat.permissions.others.hasAccess(Permission.ReadWrite);

        try
        {
            if (setAlarmPermissions)
            {
                try
                {
                    RootTools.setOthersPermissions(alarmPath, Permission.ReadWrite);
                }
                catch (Exception ignore) {}
            }

            if (setRtcPermissions)
            {
                try
                {
                    RootTools.setOthersPermissions(rtcPath, Permission.ReadWrite);
                }
                catch (Exception ignore) {}
            }

            boolean result = SystemClock.setCurrentTimeMillis(milliseconds);
            if (!result)
            {
                try
                {
                    int seconds = (int)((milliseconds + 500) / 1000);
                    result = RootToolsNative.settimeofday(seconds, false);

                    if (!result)
                    {
                        // I've got /dev/alarm set up with 666 and am calling setCurrentTimeMillis, but it always returns false
                        // Iit can happen on the latest Samsung firmwares with KNOX (SELinux) enabled. The only way to overcome it is to use the date command line call
                        // http://stackoverflow.com/questions/8739074/setting-system-time-of-rooted-phone/8752130#8752130
                        String dateFormat = new SimpleDateFormat("yyyyMMdd.HHmmss").format(new Date(milliseconds));
                        String command = String.format("date -s %s", dateFormat);
                        result = SimpleCommand.run(command).size() > 0;
                    }
                }
                catch (Exception ignore) {}
            }

            return result;
        }
        finally
        {
            if (setAlarmPermissions)
            {
                try
                {
                    RootTools.setOthersPermissions(alarmPath, alarmStat.permissions.others);
                }
                catch (Exception ignore) {}
            }

            if (setRtcPermissions)
            {
                try
                {
                    RootTools.setOthersPermissions(rtcPath, rtcStat.permissions.others);
                }
                catch (Exception ignore) {}
            }
        }
    }

    //public static boolean setCurrentTime(Date date)
    //{
    //
    //}

    public static boolean setCurrentTime(int seconds)
    {
        return RootTools.setCurrentTime((long)seconds * 1000);
    }

    public static boolean setCurrentTimeAssert(int seconds) throws ErrnoException
    {
        return RootToolsNative.settimeofday(seconds, false);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Use this to check whether or not a path exists on the filesystem.
     *
     * @param path     String representing the filesystem path.
     * @param fileType The type of file represented by "path". If null, any file type, if found, will return true.
     * @return a boolean that will indicate whether or not the path exists.
     */
    public static boolean exists(@NonNull final String path, final boolean followLinks, @Nullable final FileType fileType)
    {
        //if (fileType == null)
        //{
        //    try
        //    {
        //        return SimpleCommand.run("stat -t " + path).size() > 0;
        //    }
        //    catch (Exception ignore)
        //    {
        //        return false;
        //    }
        //}

        FileStat fileStat = RootTools.getFileStat(path, followLinks);
        return fileStat != null && (fileType == null || fileStat.type == fileType);
    }

    public static boolean exists(@NonNull final String path, final boolean followLinks)
    {
        return RootTools.exists(path, followLinks, null);
    }

    public static boolean exists(@NonNull final String path, @Nullable final FileType fileType)
    {
        return RootTools.exists(path, true, fileType);
    }

    public static boolean exists(@NonNull final String path)
    {
        return RootTools.exists(path, true, null);
    }

    public static boolean fileExists(@NonNull final String path, final boolean followLinks)
    {
        return RootTools.exists(path, followLinks, FileType.File);
    }

    public static boolean fileExists(@NonNull final String path)
    {
        return RootTools.fileExists(path, true);
    }

    public static boolean directoryExists(@NonNull final String directory, final boolean followLinks)
    {
        return RootTools.exists(directory, followLinks, FileType.Directory);
    }

    public static boolean directoryExists(@NonNull final String directory)
    {
        return RootTools.directoryExists(directory, true);
    }

    public static boolean symLinkExists(@NonNull final String symLink)
    {
        return RootTools.exists(symLink, false, FileType.SymbolicLink);
    }

    public static boolean blockDeviceExists(@NonNull final String blockDevice, final boolean followLinks)
    {
        return RootTools.exists(blockDevice, followLinks, FileType.BlockDevice);
    }

    public static boolean blockDeviceExists(@NonNull final String blockDevice)
    {
        return RootTools.blockDeviceExists(blockDevice, true);
    }

    public static boolean charDeviceExists(@NonNull final String charDevice, final boolean followLinks)
    {
        return RootTools.exists(charDevice, followLinks, FileType.CharacterDevice);
    }

    public static boolean charDeviceExists(@NonNull final String charDevice)
    {
        return RootTools.charDeviceExists(charDevice, true);
    }

    public static boolean namedPipedExists(@NonNull final String namedPipe, final boolean followLinks)
    {
        return RootTools.exists(namedPipe, followLinks, FileType.NamedPipe);
    }

    public static boolean namedPipedExists(@NonNull final String namedPipe)
    {
        return RootTools.namedPipedExists(namedPipe, true);
    }

    public static void assertExists(@NonNull final String path, final boolean followLinks, @Nullable final FileType fileType) throws FileNotFoundException
    {
        if (!RootTools.exists(path, followLinks, fileType))
        {
            throw new FileNotFoundException(path);
        }
    }

    public static void assertExists(@NonNull final String path, final boolean followLinks) throws FileNotFoundException
    {
        assertExists(path, followLinks, null);
    }

    public static void assertExists(@NonNull final String path, @Nullable final FileType fileType) throws FileNotFoundException
    {
        assertExists(path, true, fileType);
    }

    public static void assertExists(@NonNull final String path) throws FileNotFoundException
    {
        assertExists(path, true, null);
    }

    public static void assertIsType(@NonNull final FileStat fileStat, @NonNull final FileType fileType) throws FileNotFoundException
    {
        if (fileStat.type != fileType)
        {
            throw new FileNotFoundException(fileStat.file.getPath());
        }
    }

    /**
     * @param binaryName String that represent the binary to find.
     * @return <code>true</code> if the specified binary was found. Also, the path the binary was
     * found at can be retrieved via the variable lastFoundBinaryPath, if the binary was
     * found in more than one location this will contain all of these locations.
     */
    @NonNull
    public static List<String> findBinaryPaths(@NonNull final String binaryName)
    {
//        boolean found = false;
//        RootTools.lastFoundBinaryPaths.clear();

        final List<String> pathList = new ArrayList<String>();


        RootTools.log("Checking for " + binaryName);

//        //Try to use stat first
//        try
//        {
//            for (final String path : binPaths)
//            {
//                CommandCapture cc = new CommandCapture(0, false, "stat " + path + binaryName)
//                {
//                    @Override
//                    public void commandOutput(int id, String line)
//                    {
//                        if (line.contains("File: ") && line.contains(binaryName))
//                        {
//                            pathList.add(path);
//                            RootTools.log(binaryName + " was found here: " + path);
//                        }
//
//                        RootTools.log(line);
//                    }
//                };
//
//                RootTools.getShell(false).add(cc);
//                commandWait(RootTools.getShell(false), cc);
//            }
//
////            found = !pathList.isEmpty();
//        }
//        catch (Exception e)
//        {
//            RootTools.log(binaryName + " was not found, more information MAY be available with Debugging on.");
//        }

        if (pathList.isEmpty())
        {
            RootTools.log("Trying second method");

            for (String binPath : binPaths)
            {
                FileStat fileStat = RootTools.getFileStat(new File(binPath, binaryName).getPath());

                if (fileStat != null && fileStat.isFile())
                {
                    RootTools.log(binaryName + " was found here: " + binPath);
                    pathList.add(binPath);
//                    found = true;
                }
                else
                {
                    RootTools.log(binaryName + " was NOT found here: " + binPath);
                }
            }
        }

        //if (pathList.isEmpty())
        //{
        //    RootTools.log("Trying third method");
        //
        //    try
        //    {
        //        List<String> paths = RootTools.getPath();
        //
        //        if (paths != null)
        //        {
        //            for (String path : paths)
        //            {
        //                new File("", "");
        //                if (RootTools.exists(path + "/" + binaryName))
        //                {
        //                    RootTools.log(binaryName + " was found here: " + path);
        //                    pathList.add(path);
        //                }
        //                else
        //                {
        //                    RootTools.log(binaryName + " was NOT found here: " + path);
        //                }
        //            }
        //        }
        //    }
        //    catch (Exception e)
        //    {
        //        RootTools.log(binaryName + " was not found, more information MAY be available with Debugging on.");
        //    }
        //}

        Collections.reverse(pathList);

//        RootTools.lastFoundBinaryPaths.addAll(pathList);

        return pathList;

//        return found;
    }

    /**
     * This will check a given binary, determine if it exists and determine that
     * it has either the permissions 755, 775, or 777.  <----- Why? Executable? What about 1777?
     *
     * @param binaryName Name of the utility to check.
     * @return boolean to indicate whether the binary is installed and has
     * appropriate permissions.
     */
    @Nullable
    public static String findBinaryPath(@NonNull final String binaryName)
    {
        List<String> binaryPaths = RootTools.findBinaryPaths(binaryName);

        if (binaryPaths.isEmpty())
        {
            return null;
        }

//        binaryPaths.addAll(RootTools.lastFoundBinaryPaths);

        for (String path : binaryPaths)
        {
            String fullPath = RootTools.combinePaths(path, binaryName);
            FileStat fileStat = RootTools.getFileStat(fullPath);

            if (fileStat != null)
            {
                switch (fileStat.mode.permissions.getValue())
                {
                    case 755:
                    case 775:
                    case 777:
                        return fullPath;
                }
            }
        }
        return null;
    }

    public static boolean binaryExists(@NonNull final String binaryName)
    {
        return RootTools.findBinaryPath(binaryName) != null;
    }

    /**
     * This will close all open shells.
     *
     * @throws IOException
     */
    public static void closeAllShells() throws IOException
    {
        Shell.closeAll();
    }

    /**
     * This will close the custom shell that you opened.
     *
     * @throws IOException
     */
    public static void closeCustomShell() throws IOException
    {
        Shell.closeCustomShell();
    }

    /**
     * This will close either the root shell or the standard shell depending on what you specify.
     *
     * @param root a <code>boolean</code> to specify whether to close the root shell or the standard shell.
     * @throws IOException
     */
    public static void closeShell(final boolean root) throws IOException
    {
        if (root)
        {
            Shell.closeRootShell();
        }
        else
        {
            Shell.closeShell();
        }
    }

    /**
     * Copies a file to a destination. Because cp is not available on all android devices, we have a
     * fallback on the cat command
     *
     * @param source                 example: /data/data/org.adaway/files/hosts
     * @param destination            example: /system/etc/hosts
     * @param remountAsRw            remounts the destination as read/write before writing to it
     * @param preserveFileAttributes tries to copy file attributes from source to destination, if only cat is available
     *                               only permissions are preserved
     * @return true if it was successfully copied
     */
    public static boolean copyFile(@NonNull final String source, @NonNull final String destination, final boolean remountAsRw, final boolean preserveFileAttributes)
    {
        CommandCapture command = null;
        boolean result = true;

        try
        {
            // mount destination as rw before writing to it
            if (remountAsRw)
            {
                RootTools.remount(destination, "RW");
            }

            // if cp is available and has appropriate permissions
            String cpPath = findBinaryPath("cp");
            if (cpPath != null)
            {
                RootTools.log("cp command is available!");

                if (preserveFileAttributes)
                {
                    command = new CommandCapture(0, false, "cp -fp " + source + " " + destination);
                    Shell.startRootShell().add(command);
                    commandWait(Shell.startRootShell(), command);

                    //ensure that the file was copied, an exitcode of zero means success
                    result = command.getExitCode() == 0;

                }
                else
                {
                    command = new CommandCapture(0, false, "cp -f " + source + " " + destination);
                    Shell.startRootShell().add(command);
                    commandWait(Shell.startRootShell(), command);

                    //ensure that the file was copied, an exitcode of zero means success
                    result = command.getExitCode() == 0;

                }
            }
            else
            {
                String busyboxPath = findBinaryPath("busybox");
                if (busyboxPath != null && hasUtil("cp", "busybox"))
                {
                    RootTools.log("busybox cp command is available!");

                    if (preserveFileAttributes)
                    {
                        command = new CommandCapture(0, false, "busybox cp -fp " + source + " " + destination);
                        Shell.startRootShell().add(command);
                        commandWait(Shell.startRootShell(), command);

                    }
                    else
                    {
                        command = new CommandCapture(0, false, "busybox cp -f " + source + " " + destination);
                        Shell.startRootShell().add(command);
                        commandWait(Shell.startRootShell(), command);

                    }
                }
                else
                { // if cp is not available use cat
                    // if cat is available and has appropriate permissions
                    String catPath = findBinaryPath("cat");
                    if (catPath != null)
                    {
                        RootTools.log("cp is not available, use cat!");

                        int filePermission = -1;
                        if (preserveFileAttributes)
                        {
                            // get permissions of source before overwriting
                            FileStat fileStat = getFileStat(source);

                            if (fileStat != null)
                            {
                                filePermission = fileStat.mode.permissions.getValue();
                            }
//                            Permissions permissions = getFilePermissionsSymlinks(source);
//                            filePermission = permissions.getValue();
                        }

                        // copy with cat
                        command = new CommandCapture(0, false, "cat " + source + " > " + destination);
                        Shell.startRootShell().add(command);
                        commandWait(Shell.startRootShell(), command);

                        if (preserveFileAttributes)
                        {
                            // set premissions of source to destination
                            command = new CommandCapture(0, false, "chmod " + filePermission + " " + destination);
                            Shell.startRootShell().add(command);
                            commandWait(Shell.startRootShell(), command);
                        }
                    }
                    else
                    {
                        result = false;
                    }
                }
            }

            // mount destination back to ro
            if (remountAsRw)
            {
                RootTools.remount(destination, "RO");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result = false;
        }

        if (command != null)
        {
            //ensure that the file was copied, an exitcode of zero means success
            result = command.getExitCode() == 0;
        }

        return result;
    }

    /**
     * Deletes a file or directory
     *
     * @param target      example: /data/data/org.adaway/files/hosts
     * @param remountAsRw remounts the destination as read/write before writing to it
     * @return true if it was successfully deleted
     */
    public static boolean deleteFileOrDirectory(@NonNull final String target, final boolean remountAsRw)
    {
        boolean result = true;

        try
        {
            // mount destination as rw before writing to it
            if (remountAsRw)
            {
                RootTools.remount(target, "RW");
            }

            if (hasUtil("rm", "toolbox"))
            {
                RootTools.log("rm command is available!");

                CommandCapture command = new CommandCapture(0, false, "rm -r " + target);
                Shell.startRootShell().add(command);
                commandWait(Shell.startRootShell(), command);

                if (command.getExitCode() != 0)
                {
                    RootTools.log("target not exist or unable to delete file");
                    result = false;
                }
            }
            else
            {
                String busyBoxPath = findBinaryPath("busybox");
                if (busyBoxPath != null && hasUtil("rm", "busybox"))
                {
                    RootTools.log("busybox rm command is available!");

                    CommandCapture command = new CommandCapture(0, false, "busybox rm -rf " + target);
                    Shell.startRootShell().add(command);
                    commandWait(Shell.startRootShell(), command);

                    if (command.getExitCode() != 0)
                    {
                        RootTools.log("target not exist or unable to delete file");
                        result = false;
                    }
                }
            }

            // mount destination back to ro
            if (remountAsRw)
            {
                RootTools.remount(target, "RO");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result = false;
        }

        return result;
    }

    /**
     * This will try and fix a given binary. (This is for Busybox applets or Toolbox applets) By
     * "fix", I mean it will try and symlink the binary from either toolbox or Busybox and fix the
     * permissions if the permissions are not correct.
     *
     * @param util     Name of the utility to fix.
     * @param utilPath path to the toolbox that provides ln, rm, and chmod. This can be a blank string, a
     *                 path to a binary that will provide these, or you can use
     *                 RootTools.getWorkingToolbox()
     */
    public static void fixUtil(@NonNull final String util, @NonNull final String utilPath)
    {
        try
        {
            RootTools.remount("/system", "rw");

            List<String> utilPaths = RootTools.findBinaryPaths(util);

            if (!utilPaths.isEmpty())
            {
                for (String path : utilPaths)
                {
                    String fullPath = RootTools.combinePaths(path, util);
                    CommandCapture command = new CommandCapture(0, false, utilPath + " rm " + fullPath);
                    Shell.startRootShell().add(command);
                    commandWait(Shell.startRootShell(), command);
                }

                newSymLink(utilPath, RootTools.combinePaths("/system/bin", util));
            }

            RootTools.remount("/system", "ro");
        }
        catch (Exception ignored)
        {
        }
    }

    /**
     * This will check an array of binaries, determine if they exist and determine that it has
     * either the permissions 755, 775, or 777. If an applet is not setup correctly it will try and
     * fix it. (This is for Busybox applets or Toolbox applets)
     *
     * @param utils Name of the utility to check.
     * @return boolean to indicate whether the operation completed. Note that this is not indicative
     * of whether the problem was fixed, just that the method did not encounter any
     * exceptions.
     * @throws Exception if the operation cannot be completed.
     */
    public static boolean fixUtils(@NonNull final String[] utils)
    {
        for (String util : utils)
        {
            String utilPath = findBinaryPath(util);
            if (utilPath == null)
            {
                String busyboxPath = findBinaryPath("busybox");
                if (busyboxPath != null)
                {
                    if (hasUtil(util, "busybox"))
                    {
                        fixUtil(util, busyboxPath);
                    }
                }
                else
                {
                    String toolboxPath = findBinaryPath("toolbox");
                    if (toolboxPath != null)
                    {
                        if (hasUtil(util, "toolbox"))
                        {
                            fixUtil(util, toolboxPath);
                        }
                    }
                    else
                    {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * @return BusyBox version if found, "" if not found.
     */
    @NonNull
    public static String getBusyBoxVersion(@NonNull String path)
    {
        if (!path.equals("") && !path.endsWith("/"))
        {
            path += "/";
        }

        final String[] busyboxVersion = {""};

        try
        {
            CommandCapture command = new CommandCapture(Constants.BusyBoxVersion, false, path + "busybox")
            {
                @Override
                public void output(int id, String line)
                {
                    if (id == Constants.BusyBoxVersion)
                    {
                        if (line.startsWith("BusyBox") && busyboxVersion[0].equals(""))
                        {
                            String[] temp = line.split(" ");
                            busyboxVersion[0] = temp[1];
                        }
                    }
                }
            };

            //try without root first
            RootTools.log("Getting BusyBox Version without root");
            Shell.startShell().add(command);
            commandWait(Shell.startShell(), command);

            if (busyboxVersion[0].length() <= 0)
            {
                //try without root first
                RootTools.log("Getting BusyBox Version with root");
                Shell.startRootShell().add(command);
                commandWait(Shell.startRootShell(), command);
            }

        }
        catch (Exception e)
        {
            RootTools.log("BusyBox was not found, more information MAY be available with Debugging on.");
            return "";
        }

        return busyboxVersion[0];
    }

    /**
     * @return BusyBox version is found, "" if not found.
     */
    @NonNull
    public static String getBusyBoxVersion()
    {
        return RootTools.getBusyBoxVersion("");
    }

    /**
     * This will return an List of Strings. Each string represents an applet available from BusyBox.
     * <p/>
     *
     * @param path Path to the busybox binary that you want the list of applets from.
     * @return <code>null</code> If we cannot return the list of applets.
     */
    @NonNull
    public static List<String> getBusyBoxApplets(@NonNull String path) throws Exception
    {
        if (!path.endsWith("/") && !path.equals(""))
        {
            path += "/";
        }

        final List<String> results = new ArrayList<String>();

        CommandCapture command = new CommandCapture(Constants.BusyBoxApplets, false, path + "busybox --list")
        {

            @Override
            public void output(int id, String line)
            {
                if (id == Constants.BusyBoxApplets)
                {
                    if (!line.trim().equals("") && !line.trim().contains("not found"))
                    {
                        results.add(line);
                    }
                }
            }
        };

        //try without root first...
        Shell.startShell().add(command);
        commandWait(Shell.startShell(), command);

        if (results.size() <= 0)
        {
            //try with root...
            Shell.startRootShell().add(command);
            commandWait(Shell.startRootShell(), command);
        }

        return results;
    }

    /**
     * This will return an List of Strings. Each string represents an applet available from BusyBox.
     * <p/>
     *
     * @return <code>null</code> If we cannot return the list of applets.
     */
    @NonNull
    public static List<String> getBusyBoxApplets() throws Exception
    {
        return RootTools.getBusyBoxApplets("");
    }

    /**
     * This will open or return, if one is already open, a custom shell, you are responsible for managing the shell, reading the output
     * and for closing the shell when you are done using it.
     *
     * @param shellPath a <code>String</code> to Indicate the path to the shell that you want to open.
     * @param timeout   an <code>int</code> to Indicate the length of time before giving up on opening a shell.
     * @throws TimeoutException
     * @throws RootDeniedException
     * @throws IOException
     */
    @NonNull
    public static Shell getCustomShell(@NonNull final String shellPath, final int timeout) throws IOException, TimeoutException, RootDeniedException
    {
        return Shell.startCustomShell(shellPath, timeout);
    }

    /**
     * This will open or return, if one is already open, a custom shell, you are responsible for managing the shell, reading the output
     * and for closing the shell when you are done using it.
     *
     * @param shellPath a <code>String</code> to Indicate the path to the shell that you want to open.
     * @throws TimeoutException
     * @throws RootDeniedException
     * @throws IOException
     */
    @NonNull
    public static Shell getCustomShell(@NonNull final String shellPath) throws IOException, TimeoutException, RootDeniedException
    {
        return RootTools.getCustomShell(shellPath, 10000);
    }

    /**
     * This method will return the inode number of a file. This method is dependent on having a version of
     * ls that supports the -i parameter.
     *
     * @param file path to the file that you wish to return the inode number
     * @return String The inode number for this file or "" if the inode number could not be found.
     */
    //public static String getInode(String file)
    //{
    //    return RootToolsInternal.getInode(file);
    //}

    /**
     * This will return an ArrayList of the class Mount. The class mount contains the following
     * property's: device mountPoint type flags
     * <p/>
     * These will provide you with any information you need to work with the mount points.
     *
     * @return <code>ArrayList<Mount></code> an ArrayList of the class Mount.
     * @throws Exception if we cannot return the mount points.
     */
    @NonNull
    public static ArrayList<Mount> getMounts() throws Exception
    {

        Shell shell = RootTools.getShell(true);

        CommandCapture cmd = new CommandCapture(0,
                                                false,
                                                "cat /proc/mounts > /data/local/RootToolsMounts",
                                                "chmod 0777 /data/local/RootToolsMounts");
        shell.add(cmd);
        RootTools.commandWait(shell, cmd);

        LineNumberReader lnr = null;
        FileReader fr = null;

        try
        {
            fr = new FileReader("/data/local/RootToolsMounts");
            lnr = new LineNumberReader(fr);
            String line;
            ArrayList<Mount> mounts = new ArrayList<Mount>();
            while ((line = lnr.readLine()) != null)
            {

                RootTools.log(line);

                String[] fields = line.split(" ");
                mounts.add(new Mount(new File(fields[0]), // device
                                     new File(fields[1]), // mountPoint
                                     fields[2], // fstype
                                     fields[3] // flags
                ));
            }

            if (mounts == null)
            {
                throw new Exception();
            }

            return mounts;

        }
        finally
        {
            try
            {
                fr.close();
            }
            catch (Exception ignored) {}

            try
            {
                lnr.close();
            }
            catch (Exception ignored) {}
        }
    }

    /**
     * This will tell you how the specified mount is mounted. rw, ro, etc...
     * <p/>
     *
     * @param path mount you want to check
     * @return <code>String</code> What the mount is mounted as.
     * @throws Exception if we cannot determine how the mount is mounted.
     */
    @NonNull
    public static String getMountedAs(@NonNull final String path) throws Exception
    {
        for (Mount mount : RootTools.getMounts())
        {
            String mountPoint = mount.getMountPoint().getAbsolutePath();

            if (mountPoint.equals("/"))
            {
                if (path.equals("/"))
                {
                    return (String) mount.getFlags().toArray()[0];
                }
                continue;
            }

            if (path.equals(mountPoint) || path.startsWith(mountPoint + "/"))
            {
                RootTools.log((String) mount.getFlags().toArray()[0]);
                return (String) mount.getFlags().toArray()[0];
            }
        }

        throw new Exception();
    }

    /**
     * This will return the environment variable PATH
     *
     * @return <code>List<String></code> A List of Strings representing the environment variable $PATH
     */
    @NonNull
    public static List<String> getPath()
    {
        return Arrays.asList(System.getenv("PATH").split(":"));
    }

    /**
     * This will open or return, if one is already open, a shell, you are responsible for managing the shell, reading the output
     * and for closing the shell when you are done using it.
     *
     * @param root         a <code>boolean</code> to Indicate whether or not you want to open a root shell or a standard shell
     * @param timeout      an <code>int</code> to Indicate the length of time to wait before giving up on opening a shell.
     * @param shellContext the context to execute the shell with
     * @param retry        a <code>int</code> to indicate how many times the ROOT shell should try to open with root priviliges...
     * @throws TimeoutException
     * @throws RootDeniedException
     * @throws IOException
     */
    @NonNull
    public static Shell getShell(final boolean root, final int timeout, @NonNull final Shell.ShellContext shellContext, final int retry) throws IOException, TimeoutException, RootDeniedException
    {
        if (root)
        { return Shell.startRootShell(timeout, shellContext, retry); }
        else
        { return Shell.startShell(timeout); }
    }

    /**
     * This will open or return, if one is already open, a shell, you are responsible for managing the shell, reading the output
     * and for closing the shell when you are done using it.
     *
     * @param root         a <code>boolean</code> to Indicate whether or not you want to open a root shell or a standard shell
     * @param timeout      an <code>int</code> to Indicate the length of time to wait before giving up on opening a shell.
     * @param shellContext the context to execute the shell with
     * @throws TimeoutException
     * @throws RootDeniedException
     * @throws IOException
     */
    @NonNull
    public static Shell getShell(final boolean root, final int timeout, @NonNull final Shell.ShellContext shellContext) throws IOException, TimeoutException, RootDeniedException
    {
        return getShell(root, timeout, shellContext, 3);
    }

    /**
     * This will open or return, if one is already open, a shell, you are responsible for managing the shell, reading the output
     * and for closing the shell when you are done using it.
     *
     * @param root         a <code>boolean</code> to Indicate whether or not you want to open a root shell or a standard shell
     * @param shellContext the context to execute the shell with
     * @throws TimeoutException
     * @throws RootDeniedException
     * @throws IOException
     */
    @NonNull
    public static Shell getShell(final boolean root, @NonNull final Shell.ShellContext shellContext) throws IOException, TimeoutException, RootDeniedException
    {
        return getShell(root, 0, Shell.defaultContext, 3);
    }

    /**
     * This will open or return, if one is already open, a shell, you are responsible for managing the shell, reading the output
     * and for closing the shell when you are done using it.
     *
     * @param root    a <code>boolean</code> to Indicate whether or not you want to open a root shell or a standard shell
     * @param timeout an <code>int</code> to Indicate the length of time to wait before giving up on opening a shell.
     * @throws TimeoutException
     * @throws RootDeniedException
     * @throws IOException
     */
    @NonNull
    public static Shell getShell(final boolean root, final int timeout) throws IOException, TimeoutException, RootDeniedException
    {
        return getShell(root, timeout, Shell.defaultContext, 3);
    }

    /**
     * This will open or return, if one is already open, a shell, you are responsible for managing the shell, reading the output
     * and for closing the shell when you are done using it.
     *
     * @param root a <code>boolean</code> to Indicate whether or not you want to open a root shell or a standard shell
     * @throws TimeoutException
     * @throws RootDeniedException
     * @throws IOException
     */
    @NonNull
    public static Shell getShell(final boolean root) throws IOException, TimeoutException, RootDeniedException
    {
        return RootTools.getShell(root, 0);
    }

    /**
     * @return long Size, converted to kilobytes (from xxx or xxxm or xxxk etc.)
     */
    public static long getConvertedSpace(@NonNull final String spaceStr)
    {
        try
        {
            double multiplier = 1.0;
            char c;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < spaceStr.length(); i++)
            {
                c = spaceStr.charAt(i);
                if (!Character.isDigit(c) && c != '.')
                {
                    if (c == 'm' || c == 'M')
                    {
                        multiplier = 1024.0;
                    }
                    else if (c == 'g' || c == 'G')
                    {
                        multiplier = 1024.0 * 1024.0;
                    }
                    break;
                }
                sb.append(spaceStr.charAt(i));
            }
            return (long) Math.ceil(Double.valueOf(sb.toString()) * multiplier);
        }
        catch (Exception e)
        {
            return -1;
        }
    }


    /**
     * Get the space for a desired partition.
     *
     * @param path The partition to find the space for.
     * @return the amount if space found within the desired partition. If the space was not found
     * then the value is -1
     * @throws TimeoutException
     */
    public static long getSpace(@NonNull final String path)
    {
        boolean found = false;
        RootTools.log("Looking for Space");
        final String[][] space = {null};
        try
        {
            final CommandCapture command = new CommandCapture(Constants.GetSpace, false, "df " + path)
            {

                @Override
                public void output(int id, String line)
                {
                    if (id == Constants.GetSpace)
                    {
                        if (line.contains(path.trim()))
                        {
                            space[0] = line.split(" ");
                        }
                    }
                }
            };
            Shell.startRootShell().add(command);
            commandWait(Shell.startRootShell(), command);

        }
        catch (Exception ignored) {}

        if (space[0] != null)
        {
            RootTools.log("First Method");

            for (String spaceSearch : space[0])
            {

                RootTools.log(spaceSearch);

                if (found)
                {
                    return getConvertedSpace(spaceSearch);
                }
                else if (spaceSearch.equals("used,"))
                {
                    found = true;
                }
            }

            // Try this way
            int count = 0, targetCount = 3;

            RootTools.log("Second Method");

            if (space[0][0].length() <= 5)
            {
                targetCount = 2;
            }

            for (String spaceSearch : space[0])
            {

                RootTools.log(spaceSearch);
                if (spaceSearch.length() > 0)
                {
                    RootTools.log(spaceSearch + ("Valid"));
                    if (count == targetCount)
                    {
                        return getConvertedSpace(spaceSearch);
                    }
                    count++;
                }
            }
        }
        RootTools.log("Returning -1, space could not be determined.");
        return -1;
    }

    /**
     * This will return a String that represent the symlink for a specified file.
     * <p/>
     *
     * @param file file to get the Symlink for. (must have absolute path)
     * @return <code>String</code> a String that represent the symlink for a specified file or an
     * empty string if no symlink exists.
     */
    @NonNull
    public static String getSymlink(@NonNull final String file)
    {
        RootTools.log("Looking for Symlink for " + file);

        try
        {
            final List<String> results = new ArrayList<String>();

            CommandCapture command = new CommandCapture(Constants.GetSymLink, false, "ls -l " + file)
            {
                @Override
                public void output(int id, String line)
                {
                    if (id == Constants.GetSymLink)
                    {
                        if (!line.trim().equals(""))
                        {
                            results.add(line);
                        }
                    }
                }
            };
            Shell.startRootShell().add(command);
            commandWait(Shell.startRootShell(), command);

            String[] symlink = results.get(0).split(" ");
            if (symlink.length > 2 && symlink[symlink.length - 2].equals("->"))
            {
                RootTools.log("Symlink found.");

                String final_symlink;

                if (!symlink[symlink.length - 1].equals("") && !symlink[symlink.length - 1].contains("/"))
                {
                    //We assume that we need to get the path for this symlink as it is probably not absolute.
                    List<String> symlinkPaths = findBinaryPaths(symlink[symlink.length - 1]);
                    if (!symlinkPaths.isEmpty())
                    {
                        //We return the first found location.
                        final_symlink = RootTools.combinePaths(symlinkPaths.get(0), symlink[symlink.length - 1]);
                    }
                    else
                    {
                        //we couldnt find a path, return the symlink by itself.
                        final_symlink = symlink[symlink.length - 1];
                    }
                }
                else
                {
                    final_symlink = symlink[symlink.length - 1];
                }

                return final_symlink;
            }
        }
        catch (Exception e)
        {
            if (RootTools.debugMode)
            { e.printStackTrace(); }
        }

        RootTools.log("Symlink not found");
        return "";
    }

    /**
     * This will return a List Symlink. The class Symlink contains the following
     * property's: path SymplinkPath
     * <p/>
     * These will provide you with any Symlinks in the given path.
     *
     * @param directory path to search for Symlinks.
     * @return <code>List<Symlink></code> an ArrayList of the class Symlink.
     * @throws Exception if we cannot return the Symlinks.
     */
    @NonNull
    public static List<SymbolicLink> getSymLinks(@NonNull final String directory, final int maxDepth) throws TimeoutException, RootDeniedException, IOException
    {
        RootTools.assertExists(directory, FileType.Directory);

        String maxDepthArg = maxDepth < 0 ? "" : " -maxdepth " + maxDepth;
        String command = "find " + directory + maxDepthArg + " -type l -exec ls -l {} \\;";
        final List<SymbolicLink> symLinks = new ArrayList<SymbolicLink>();

        for (String line : SimpleCommand.run(command))
        {
            symLinks.add(SymbolicLink.parse(line));
        }

        return symLinks;
    }

    @NonNull
    public static List<SymbolicLink> getSymLinks(@NonNull final String directory) throws IOException, TimeoutException, RootDeniedException
    {
        return RootTools.getSymLinks(directory, -1);
    }

    public static boolean newSymLink(@NonNull final String target, @NonNull final String name)
    {
        try
        {
            String commandLine1 = String.format("%s ln -s %s %s", target, target, name);
            String commandLine2 = String.format("%s chmod 0755 %s", target, name);

            CommandCapture command = new CommandCapture(0, false, commandLine1, commandLine2);
            Shell.startRootShell().add(command);
            commandWait(Shell.startRootShell(), command);
            return true;
        }
        catch (Exception ignored)
        {
        }

        return false;
    }

    /**
     * This will return to you a string to be used in your shell commands which will represent the
     * valid working toolbox with correct permissions. For instance, if Busybox is available it will
     * return "busybox", if busybox is not available but toolbox is then it will return "toolbox"
     *
     * @return String that indicates the available toolbox to use for accessing applets.
     */
    @NonNull
    public static String getWorkingToolbox()
    {
        if (RootTools.findBinaryPath("busybox") != null)
        {
            return "busybox";
        }
        else if (RootTools.findBinaryPath("toolbox") != null)
        {
            return "toolbox";
        }
        else
        {
            return "";
        }
    }

    /**
     * Checks if there is enough Space on SDCard
     *
     * @param updateSize size to Check (long)
     * @return <code>true</code> if the Update will fit on SDCard, <code>false</code> if not enough
     * space on SDCard. Will also return <code>false</code>, if the SDCard is not mounted as
     * read/write
     */
    public static boolean hasEnoughSpaceOnSdCard(final long updateSize)
    {
        RootTools.log("Checking SDcard size and that it is mounted as RW");
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED))
        {
            return false;
        }
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return (updateSize < availableBlocks * blockSize);
    }

    /**
     * Checks whether the toolbox or busybox binary contains a specific util
     *
     * @param util
     * @param box  Should contain "toolbox" or "busybox"
     * @return true if it contains this util
     */
    public static boolean hasUtil(@NonNull final String util, final String box)
    {
        final boolean[] found = {false};

        // only for busybox and toolbox
        if (!(box.endsWith("toolbox") || box.endsWith("busybox")))
        {
            return false;
        }

        try
        {
            CommandCapture command = new CommandCapture(0, false, box.endsWith("toolbox") ? box + " " + util : box + " --list")
            {
                @Override
                public void output(int id, String line)
                {
                    if (box.endsWith("toolbox"))
                    {
                        if (!line.contains("no such tool"))
                        {
                            found[0] = true;
                        }
                    }
                    else if (box.endsWith("busybox"))
                    {
                        // go through all lines of busybox --list
                        if (line.contains(util))
                        {
                            RootTools.log("Found util!");
                            found[0] = true;
                        }
                    }
                }
            };
            RootTools.getShell(true).add(command);
            commandWait(RootTools.getShell(true), command);

            String contains = found[0] ? "contains " : "does not contain ";
            RootTools.log("Box " + contains + util + " util!");

            return found[0];
        }
        catch (Exception e)
        {
            RootTools.log(e.getMessage());
            return false;
        }
    }

    /**
     * This method can be used to unpack a binary from the raw resources folder and store it in
     * /data/data/app.package/files/ This is typically useful if you provide your own C- or
     * C++-based binary. This binary can then be executed using sendShell() and its full path.
     *
     * @param context  the current activity's <code>Context</code>
     * @param sourceId resource id; typically <code>R.raw.id</code>
     * @param destName destination file name; appended to /data/data/app.package/files/
     * @param mode     chmod value for this file
     * @return a <code>boolean</code> which indicates whether or not we were able to create the new
     * file.
     */
    public static boolean installBinary(@NonNull final Context context, final int sourceId, @NonNull final String destName, @NonNull final String mode)
    {
        Installer installer;

        try
        {
            installer = new Installer(context);
        }
        catch (IOException ex)
        {
            if (RootTools.debugMode)
            {
                ex.printStackTrace();
            }
            return false;
        }

        return (installer.installBinary(sourceId, destName, mode));
    }

    /**
     * This method can be used to unpack a binary from the raw resources folder and store it in
     * /data/data/app.package/files/ This is typically useful if you provide your own C- or
     * C++-based binary. This binary can then be executed using sendShell() and its full path.
     *
     * @param context    the current activity's <code>Context</code>
     * @param sourceId   resource id; typically <code>R.raw.id</code>
     * @param binaryName destination file name; appended to /data/data/app.package/files/
     * @return a <code>boolean</code> which indicates whether or not we were able to create the new
     * file.
     */
    public static boolean installBinary(@NonNull final Context context, final int sourceId, @NonNull final String binaryName)
    {
        return installBinary(context, sourceId, binaryName, "700");
    }

    /**
     * This method checks whether a binary is installed.
     *
     * @param context    the current activity's <code>Context</code>
     * @param binaryName binary file name; appended to /data/data/app.package/files/
     * @return a <code>boolean</code> which indicates whether or not
     * the binary already exists.
     */
    public static boolean isBinaryAvailable(@NonNull final Context context, @NonNull final String binaryName)
    {
        Installer installer;

        try
        {
            installer = new Installer(context);
        }
        catch (IOException ex)
        {
            if (RootTools.debugMode)
            {
                ex.printStackTrace();
            }
            return false;
        }

        return (installer.isBinaryInstalled(binaryName));
    }

    /**
     * This will let you know if an applet is available from BusyBox
     * <p/>
     *
     * @param applet The applet to check for.
     * @return <code>true</code> if applet is available, false otherwise.
     */
    public static boolean isAppletAvailable(@NonNull final String applet, @NonNull final String binaryPath)
    {
        try
        {
            for (String aplet : getBusyBoxApplets(binaryPath))
            {
                if (aplet.equals(applet))
                {
                    return true;
                }
            }
            return false;
        }
        catch (Exception e)
        {
            RootTools.log(e.toString());
            return false;
        }
    }

    /**
     * This will let you know if an applet is available from BusyBox
     * <p/>
     *
     * @param applet The applet to check for.
     * @return <code>true</code> if applet is available, false otherwise.
     */
    public static boolean isAppletAvailable(@NonNull final String applet)
    {
        return RootTools.isAppletAvailable(applet, "");
    }

    /**
     * @return <code>true</code> if your app has been given root access.
     * @throws TimeoutException if this operation times out. (cannot determine if access is given)
     */
    public static boolean isAccessGiven()
    {
        try
        {
            RootTools.log("Checking for Root access");
            final boolean[] accessGiven = {false};

            CommandCapture command = new CommandCapture(Constants.IsAccessGiven, false, "id")
            {
                @Override
                public void output(int id, String line)
                {
                    if (id == Constants.IsAccessGiven)
                    {
                        Set<String> ID = new HashSet<String>(Arrays.asList(line.split(" ")));
                        for (String userid : ID)
                        {
                            RootTools.log(userid);

                            if (userid.toLowerCase().contains("uid=0"))
                            {
                                accessGiven[0] = true;
                                RootTools.log("Access Given");
                                break;
                            }
                        }
                        if (!accessGiven[0])
                        {
                            RootTools.log("Access Denied?");
                        }
                    }
                }
            };
            Shell.startRootShell().add(command);
            commandWait(Shell.startRootShell(), command);

            return accessGiven[0];

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @return <code>true</code> if BusyBox was found.
     */
    public static boolean isBusyboxAvailable()
    {
        return !findBinaryPaths("busybox").isEmpty();
    }

    public static boolean isNativeToolsReady(final int nativeToolsId, @NonNull final Context context)
    {
        RootTools.log("Preparing Native Tools");

        Installer installer;
        try
        {
            installer = new Installer(context);
        }
        catch (IOException ex)
        {
            if (RootTools.debugMode)
            {
                ex.printStackTrace();
            }
            return false;
        }

        return installer.isBinaryInstalled("nativetools") || installer.installBinary(nativeToolsId, "nativetools", "700");
    }

    /**
     * This method can be used to to check if a process is running
     *
     * @param processName name of process to check
     * @return <code>true</code> if process was found
     * @throws TimeoutException (Could not determine if the process is running)
     */
    public static boolean isProcessRunning(@NonNull final String processName)
    {
        //TODO convert to new shell

        RootTools.log("Checks if process is running: " + processName);

        final boolean[] processRunning = {false};

        try
        {
            CommandCapture command = new CommandCapture(0, false, "ps")
            {
                @Override
                public void output(int id, String line)
                {
                    if (line.contains(processName))
                    {
                        processRunning[0] = true;
                    }
                }
            };
            RootTools.getShell(true).add(command);
            commandWait(RootTools.getShell(true), command);

        }
        catch (Exception e)
        {
            RootTools.log(e.getMessage());
        }

        return processRunning[0];
    }

    public static File[] getAllPaths(@NonNull final String path)
    {
        List<File> paths = new ArrayList<File>();

        File file = new File(path);
        paths.add(file);

        while (null != (file = file.getParentFile()))
        {
            paths.add(file);
        }

        Collections.reverse(paths);
        return paths.toArray(new File[paths.size()]);
    }

    /**
     * @return <code>true</code> if su was found.
     */
    public static boolean isRootAvailable()
    {
        return !RootTools.findBinaryPaths("su").isEmpty();
    }

    /**
     * This method can be used to kill a running process
     *
     * @param processName name of process to kill
     * @return <code>true</code> if process was found and killed successfully
     */
    public static boolean killProcess(@NonNull final String processName)
    {
        //TODO convert to new shell
        RootTools.log("Killing process " + processName);

        final String[] pid_list = {""};

        //Assume that the process is running
        final boolean[] processRunning = {true};

        try
        {

            CommandCapture command = new CommandCapture(0, false, "ps")
            {
                @Override
                public void output(int id, String line)
                {
                    if (line.contains(processName))
                    {
                        Matcher psMatcher = RootTools.processRegexPattern.matcher(line);

                        try
                        {
                            if (psMatcher.find())
                            {
                                String pid = psMatcher.group(1);

                                pid_list[0] += " " + pid;
                                pid_list[0] = pid_list[0].trim();

                                RootTools.log("Found pid: " + pid);
                            }
                            else
                            {
                                RootTools.log("Matching in ps command failed!");
                            }
                        }
                        catch (Exception e)
                        {
                            RootTools.log("Error with regex!");
                            e.printStackTrace();
                        }
                    }
                }
            };
            RootTools.getShell(true).add(command);
            commandWait(RootTools.getShell(true), command);

            // get all pids in one string, created in process method
            String pids = pid_list[0];

            // kill processes
            if (!pids.equals(""))
            {
                try
                {
                    // example: kill -9 1234 1222 5343
                    command = new CommandCapture(0, false, "kill -9 " + pids);
                    RootTools.getShell(true).add(command);
                    commandWait(RootTools.getShell(true), command);

                    return true;
                }
                catch (Exception e)
                {
                    RootTools.log(e.getMessage());
                }
            }
            else
            {
                //no pids match, must be dead
                return true;
            }
        }
        catch (Exception e)
        {
            RootTools.log(e.getMessage());
        }

        return false;
    }

    /**
     * This will launch the Android market looking for BusyBox, but will return the intent fired and
     * starts the activity with startActivityForResult
     *
     * @param activity    pass in your Activity
     * @param requestCode pass in the request code
     * @return intent fired
     */
    @NonNull
    public static Intent offerBusyBox(@NonNull final Activity activity, final int requestCode)
    {
        RootTools.log("Launching Market for BusyBox");
        Intent i = new Intent(Intent.ACTION_VIEW,
                              Uri.parse("market://details?id=stericson.busybox"));
        activity.startActivityForResult(i, requestCode);
        return i;
    }

    /**
     * This will launch the Android market looking for BusyBox
     *
     * @param activity pass in your Activity
     */
    public static void offerBusyBox(@NonNull final Activity activity)
    {
        RootTools.log("Launching Market for BusyBox");
        Intent i = new Intent(Intent.ACTION_VIEW,
                              Uri.parse("market://details?id=stericson.busybox"));
        activity.startActivity(i);
    }

    /**
     * This will launch the Android market looking for SuperUser, but will return the intent fired
     * and starts the activity with startActivityForResult
     *
     * @param activity    pass in your Activity
     * @param requestCode pass in the request code
     * @return intent fired
     */
    @NonNull
    public static Intent offerSuperUser(@NonNull final Activity activity, final int requestCode)
    {
        RootTools.log("Launching Market for SuperUser");
        Intent i = new Intent(Intent.ACTION_VIEW,
                              Uri.parse("market://details?id=com.noshufou.android.su"));
        activity.startActivityForResult(i, requestCode);
        return i;
    }

    /**
     * This will launch the Android market looking for SuperUser
     *
     * @param activity pass in your Activity
     */
    public static void offerSuperUser(@NonNull final Activity activity)
    {
        RootTools.log("Launching Market for SuperUser");
        Intent i = new Intent(Intent.ACTION_VIEW,
                              Uri.parse("market://details?id=com.noshufou.android.su"));
        activity.startActivity(i);
    }

    /**
     * This will take a path, which can contain the file name as well, and attempt to remount the
     * underlying partition.
     * <p/>
     * For example, passing in the following string:
     * "/system/bin/some/directory/that/really/would/never/exist" will result in /system ultimately
     * being remounted. However, keep in mind that the longer the path you supply, the more work
     * this has to do, and the slower it will run.
     *
     * @param file      file path
     * @param mountType mount type: pass in RO (Read only) or RW (Read Write)
     * @return a <code>boolean</code> which indicates whether or not the partition has been
     * remounted as specified.
     */
    public static boolean remount(@NonNull final String file, @NonNull final String mountType)
    {
        // Recieved a request, get an instance of Remounter
        Remounter remounter = new Remounter();
        // send the request.
        return (remounter.remount(file, mountType));
    }

    /**
     * This restarts only Android OS without rebooting the whole device. This does NOT work on all
     * devices. This is done by killing the main init process named zygote. Zygote is restarted
     * automatically by Android after killing it.
     *
     * @throws TimeoutException
     */
    public static void restartAndroid()
    {
        RootTools.log("Restart Android");
        killProcess("zygote");
    }

    /**
     * Executes binary in a separated process. Before using this method, the binary has to be
     * installed in /data/data/app.package/files/ using the installBinary method.
     *
     * @param context    the current activity's <code>Context</code>
     * @param binaryName name of installed binary
     * @param parameter  parameter to append to binary like "-vxf"
     */
    public static void runBinary(@NonNull final Context context, @NonNull final String binaryName, @NonNull final String parameter)
    {
        Runner runner = new Runner(context, binaryName, parameter);
        runner.start();
    }

    /**
     * Executes a given command with root access or without depending on the value of the boolean passed.
     * This will also start a root shell or a standard shell without you having to open it specifically.
     * <p/>
     * You will still need to close the shell after you are done using the shell.
     *
     * @param shell   The shell to execute the command on, this can be a root shell or a standard shell.
     * @param command The command to execute in the shell
     * @throws IOException
     */
    public static void runShellCommand(@NonNull final Shell shell, @NonNull final Command command)
    {
        shell.add(command);
    }

    /**
     * This method allows you to output debug messages only when debugging is on. This will allow
     * you to add a debug option to your app, which by default can be left off for performance.
     * However, when you need debugging information, a simple switch can enable it and provide you
     * with detailed logging.
     * <p/>
     * This method handles whether or not to log the information you pass it depending whether or
     * not RootTools.debugMode is on. So you can use this and not have to worry about handling it
     * yourself.
     *
     * @param msg The message to output.
     */
    public static void log(@NonNull final String msg)
    {
        log(null, msg, 3, null);
    }

    /**
     * This method allows you to output debug messages only when debugging is on. This will allow
     * you to add a debug option to your app, which by default can be left off for performance.
     * However, when you need debugging information, a simple switch can enable it and provide you
     * with detailed logging.
     * <p/>
     * This method handles whether or not to log the information you pass it depending whether or
     * not RootTools.debugMode is on. So you can use this and not have to worry about handling it
     * yourself.
     *
     * @param TAG Optional parameter to define the tag that the Log will use.
     * @param msg The message to output.
     */
    public static void log(@NonNull final String TAG, @NonNull final String msg)
    {
        log(TAG, msg, 3, null);
    }

    /**
     * This method allows you to output debug messages only when debugging is on. This will allow
     * you to add a debug option to your app, which by default can be left off for performance.
     * However, when you need debugging information, a simple switch can enable it and provide you
     * with detailed logging.
     * <p/>
     * This method handles whether or not to log the information you pass it depending whether or
     * not RootTools.debugMode is on. So you can use this and not have to worry about handling it
     * yourself.
     *
     * @param msg  The message to output.
     * @param type The type of log, 1 for verbose, 2 for error, 3 for debug
     * @param e    The exception that was thrown (Needed for errors)
     */
    public static void log(@NonNull final String msg, final int type, @NonNull final Exception e)
    {
        log(null, msg, type, e);
    }

    /**
     * This method allows you to check whether logging is enabled.
     * Yes, it has a goofy name, but that's to keep it as short as possible.
     * After all writing logging calls should be painless.
     * This method exists to save Android going through the various Java layers
     * that are traversed any time a string is created (i.e. what you are logging)
     * <p/>
     * Example usage:
     * if(islog) {
     * StrinbBuilder sb = new StringBuilder();
     * // ...
     * // build string
     * // ...
     * log(sb.toString());
     * }
     *
     * @return true if logging is enabled
     */
    public static boolean islog()
    {
        return debugMode;
    }

    /**
     * This method allows you to output debug messages only when debugging is on. This will allow
     * you to add a debug option to your app, which by default can be left off for performance.
     * However, when you need debugging information, a simple switch can enable it and provide you
     * with detailed logging.
     * <p/>
     * This method handles whether or not to log the information you pass it depending whether or
     * not RootTools.debugMode is on. So you can use this and not have to worry about handling it
     * yourself.
     *
     * @param TAG  Optional parameter to define the tag that the Log will use.
     * @param msg  The message to output.
     * @param type The type of log, 1 for verbose, 2 for error, 3 for debug
     * @param e    The exception that was thrown (Needed for errors)
     */
    public static void log(@NonNull String TAG, @NonNull final String msg, final int type, @NonNull final Exception e)
    {
        if (msg != null && !msg.equals(""))
        {
            if (debugMode)
            {
                if (TAG == null)
                {
                    TAG = Constants.TAG;
                }

                switch (type)
                {
                    case 1:
                        Log.v(TAG, msg);
                        break;
                    case 2:
                        Log.e(TAG, msg, e);
                        break;
                    case 3:
                        Log.d(TAG, msg);
                        break;
                }
            }
        }
    }

    // TODO: Add native function for chmod
    public static void setPermissions(@NonNull final String path, @Nullable final Permission user, @Nullable final Permission group, @Nullable final Permission others, final boolean followLinks) throws IOException, RootDeniedException, TimeoutException, ErrnoException
    {
        FileStat fileStat = RootTools.getFileStatAssert(path, followLinks);
        Permissions newPermissions = new Permissions(user == null ? fileStat.permissions.user : user,
                                                     group == null ? fileStat.permissions.group : group,
                                                     others == null ? fileStat.permissions.others : others);

        if (!newPermissions.equals(fileStat.permissions))
        {
            String commandLine = "chmod " + newPermissions.toOctalString() + " " + path;
            CommandCapture command = new CommandCapture(0, false, commandLine);
            Shell.startRootShell().add(command);
            commandWait(Shell.startRootShell(), command);
        }
    }

    public static void setPermissions(@NonNull final String path, @Nullable final Permission user, @Nullable final Permission group, @Nullable final Permission others) throws IOException, RootDeniedException, TimeoutException, ErrnoException
    {
        RootTools.setPermissions(path, user, group, others, true);
    }

    public static void setUserPermissions(@NonNull final String path, @NonNull final Permission user, final boolean followLinks) throws IOException, RootDeniedException, TimeoutException, ErrnoException
    {
        RootTools.setPermissions(path, user, null, null, followLinks);
    }

    public static void setUserPermissions(@NonNull final String path, @NonNull final Permission user) throws IOException, RootDeniedException, TimeoutException, ErrnoException
    {
        RootTools.setUserPermissions(path, user, true);
    }

    public static void setGroupPermissions(@NonNull final String path, @NonNull final Permission group, final boolean followLinks) throws IOException, RootDeniedException, TimeoutException, ErrnoException
    {
        RootTools.setPermissions(path, null, group, null, followLinks);
    }

    public static void setGroupPermissions(@NonNull final String path, @NonNull final Permission group) throws IOException, RootDeniedException, TimeoutException, ErrnoException
    {
        RootTools.setGroupPermissions(path, group, true);
    }

    public static void setOthersPermissions(@NonNull final String path, @NonNull final Permission others, final boolean followLinks) throws IOException, RootDeniedException, TimeoutException, ErrnoException
    {
        RootTools.setPermissions(path, null, null, others, followLinks);
    }

    public static void setOthersPermissions(@NonNull final String path, @NonNull final Permission others) throws IOException, RootDeniedException, TimeoutException, ErrnoException
    {
        RootTools.setOthersPermissions(path, others, true);
    }

    @NonNull
    public static byte[] readAllBytes(@NonNull final String path) throws IOException, RootDeniedException, TimeoutException, InterruptedException, ErrnoException
    {
        FileStat fileStat = RootTools.getFileStatAssert(path);
        boolean setPermissions = !fileStat.mode.permissions.others.hasAccess(Permission.Read);

        if (setPermissions)
        {
            RootTools.setOthersPermissions(path, Permission.ReadWrite);
        }

        try
        {
            FileInputStream stream = null;

            try
            {
                byte[] bytes = new byte[(int) fileStat.size];
                stream = new FileInputStream(path);
                stream.read(bytes);
                return bytes;
            }
            finally
            {
                if (stream != null)
                {
                    stream.close();
                }
            }
        }
        finally
        {
            if (setPermissions)
            {
                RootTools.setOthersPermissions(path, fileStat.mode.permissions.others);
            }
        }
    }

    public static void writeAllBytes(@NonNull final String path, @NonNull final byte[] bytes) throws IOException, RootDeniedException, TimeoutException, ErrnoException
    {
        // TODO: Fix writeAllBytes() so it works when file/parent doesn't exist and directory is not writable.

        FileStat fileStat = RootTools.getFileStat(path);
        boolean setPermissions = false;

        if (fileStat != null)
        {
            setPermissions = !fileStat.mode.permissions.others.hasAccess(Permission.ReadWrite);
        }

        try
        {
            if (setPermissions)
            {
                RootTools.setOthersPermissions(path, Permission.ReadWrite);
            }

            FileOutputStream stream = null;

            try
            {
                stream = new FileOutputStream(path);
                stream.write(bytes);
                stream.flush();
            }
            finally
            {
                if (stream != null)
                {
                    stream.close();
                }
            }
        }
        finally
        {
            if (setPermissions)
            {
                RootTools.setOthersPermissions(path, fileStat.mode.permissions.others);
            }
        }
    }

    // TODO: Make package local
    public static void commandWait(Shell shell, Command cmd)
    {
        while (!cmd.isFinished())
        {
            RootTools.log(Constants.TAG, shell.getCommandQueuePositionString(cmd));

            synchronized (cmd)
            {
                try
                {
                    if (!cmd.isFinished())
                    {
                        cmd.wait(2000);
                    }
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            if (!cmd.isExecuting() && !cmd.isFinished())
            {
                if (!shell.isExecuting && !shell.isReading)
                {
                    Log.e(Constants.TAG, "Waiting for a command to be executed in a shell that is not executing and not reading! \n\n Command: " + cmd.getCommand());
                    Exception e = new Exception();
                    e.setStackTrace(Thread.currentThread().getStackTrace());
                    e.printStackTrace();
                }
                else if (shell.isExecuting && !shell.isReading)
                {
                    Log.e(Constants.TAG, "Waiting for a command to be executed in a shell that is executing but not reading! \n\n Command: " + cmd.getCommand());
                    Exception e = new Exception();
                    e.setStackTrace(Thread.currentThread().getStackTrace());
                    e.printStackTrace();
                }
                else
                {
                    Log.e(Constants.TAG, "Waiting for a command to be executed in a shell that is not reading! \n\n Command: " + cmd.getCommand());
                    Exception e = new Exception();
                    e.setStackTrace(Thread.currentThread().getStackTrace());
                    e.printStackTrace();
                }
            }

        }
    }
}
