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

package com.stericson.RootTools.execution;

import android.support.annotation.NonNull;

import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public final class SimpleCommand
{
    @NonNull
    public static List<String> run(@NonNull String command, boolean root) throws TimeoutException, RootDeniedException, IOException
    {
        final List<String> output = new ArrayList<String>();
        command += RootTools.RedirectErrorToNull;

        //String[] commands = {
        //    command,
        //    "busybox " + command,
        //    "/system/bin/failsafe/toolbox " + command,
        //    "toolbox " + command
        //};

        CommandCapture commandCapture = new CommandCapture(0, false, command)
        {
            @Override
            protected void output(int id, String line)
            {
                output.add(line);
            }
        };

        RootTools.getShell(root).add(commandCapture);
        RootTools.commandWait(RootTools.getShell(root), commandCapture);

        return output;
    }

    @NonNull
    public static List<String> run(@NonNull String command) throws IOException, RootDeniedException, TimeoutException
    {
        return SimpleCommand.run(command, true);
    }
}