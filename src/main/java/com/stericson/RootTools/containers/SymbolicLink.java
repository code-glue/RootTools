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

package com.stericson.RootTools.containers;

import android.support.annotation.NonNull;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SymbolicLink
{
    private static final Pattern _SymLinkPatternNoQuotes = Pattern.compile("([^\\s]+)\\s+->\\s+([^\\s]+)");
    private static final Pattern _SymLinkPatternQuotes = Pattern.compile("'([^\\s]+)'\\s+->\\s+'([^\\s]+)'");
    private final File _target;
    private final File _link;

    public SymbolicLink(@NonNull File target, @NonNull File linkName)
    {
        this._target = target;
        this._link = linkName;
    }

    public SymbolicLink(@NonNull String target, @NonNull String linkName)
    {
        this(new File(target), new File(linkName));
    }

    @NonNull
    public static SymbolicLink parse(@NonNull String value)
    {
        Matcher matcher = SymbolicLink._SymLinkPatternNoQuotes.matcher(value);

        if (!matcher.find())
        {
            throw new IllegalArgumentException("value");
        }

        return new SymbolicLink(matcher.group(2), matcher.group(1));
    }

    @NonNull
    public static SymbolicLink parseQuoted(@NonNull String value)
    {
        Matcher matcher = SymbolicLink._SymLinkPatternQuotes.matcher(value);

        if (!matcher.find())
        {
            throw new IllegalArgumentException("value");
        }

        return new SymbolicLink(matcher.group(2), matcher.group(1));
    }

    @NonNull
    public File getTarget()
    {
        return this._target;
    }

    @NonNull
    public File getLink()
    {
        return this._link;
    }

    @Override
    public String toString()
    {
        return String.format("%s -> %s", this._link, this._target);
    }
}
