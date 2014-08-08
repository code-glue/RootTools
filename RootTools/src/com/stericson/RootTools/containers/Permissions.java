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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Permissions
{
    private static final Pattern RwxPattern = Pattern.compile("(^|\\s)([^\\s]?)([-r][-w][-xsS])([-r][-w][-xsS])([-r][-w][-xtT])(\\s|$)");

    private final AccessMode _user;
    private final AccessMode _group;
    private final AccessMode _others;
    private final SpecialAccessMode _special;

    public Permissions(AccessMode user, AccessMode group, AccessMode others, SpecialAccessMode special)
    {
        this._user = user;
        this._group = group;
        this._others = others;
        this._special = special;
    }

    public Permissions(AccessMode user, AccessMode group, AccessMode others)
    {
        this(user, group, others, SpecialAccessMode.None);
    }

    public Permissions(AccessMode all)
    {
        this(all, all, all);
    }

    private static boolean isValidUgoAccessMode(int value)
    {
        return (value & 0xfffffff8) == 0;
    }

    private static boolean isValidSpecialAccessMode(int value)
    {
        return (value & 0xffff8fff) == 0;
    }

    private static boolean hasFlag(int value, int flag)
    {
        return (value & flag) != 0;
    }

    public static Permissions valueOf(int value)
    {
        SpecialAccessMode special = SpecialAccessMode.valueOf(value / 1000);
        value %= 1000;
        AccessMode user = AccessMode.valueOf(value / 100);
        value %= 100;
        AccessMode group = AccessMode.valueOf(value / 10);
        AccessMode others = AccessMode.valueOf(value % 10);

        return new Permissions(user, group, others, special);
    }

    public static Permissions parse(String userAccess, String groupAccess, String othersAccess)
    {
        AccessMode user = AccessMode.parse(userAccess);
        AccessMode group = AccessMode.parse(groupAccess);
        AccessMode others = AccessMode.parse(othersAccess);
        SpecialAccessMode special = SpecialAccessMode.valueOf(userAccess, groupAccess, othersAccess);
        return new Permissions(user, group, others, special);
    }

    public static Permissions parse(String value)
    {
        Matcher matcher = Permissions.RwxPattern.matcher(value);

        if (!matcher.find())
        {
            throw new IllegalArgumentException("value");
        }

        return Permissions.parse(matcher.group(3), matcher.group(4), matcher.group(5));
    }

    public AccessMode getUserAccess()
    {
        return this._user;
    }

    public AccessMode getGroupAccess()
    {
        return this._group;
    }

    public AccessMode getOthersAccess()
    {
        return this._others;
    }

    public boolean userHasAccess(AccessMode mode)
    {
        return this._user.hasAccess(mode);
    }

    public boolean groupHasAccess(AccessMode mode)
    {
        return this._group.hasAccess(mode);
    }

    public boolean othersHasAccess(AccessMode mode)
    {
        return this._others.hasAccess(mode);
    }

    public int getValue()
    {
        return this._special.getValue() * 1000 +
               this._user.getValue() * 100 +
               this._group.getValue() * 10 +
               this._others.getValue();
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder(9)
            .append(this._user.toString())
            .append(this._group.toString())
            .append(this._others.toString());

        if (this._special.hasMode(SpecialAccessMode.SetUserId)) { stringBuilder.setCharAt(2, this._user.canExecute() ? 's' : 'S'); }
        if (this._special.hasMode(SpecialAccessMode.SetGroupId)) { stringBuilder.setCharAt(5, this._group.canExecute() ? 's' : 'S'); }
        if (this._special.hasMode(SpecialAccessMode.Sticky)) { stringBuilder.setCharAt(8, this._others.canExecute() ? 't' : 'T'); }

        return stringBuilder.toString();
    }
}
