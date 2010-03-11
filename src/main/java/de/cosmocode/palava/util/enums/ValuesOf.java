/**
 * palava - a java-php-bridge
 * Copyright (C) 2007-2010  CosmoCode GmbH
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package de.cosmocode.palava.util.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.inject.Singleton;

import de.cosmocode.commons.Enums;
import de.cosmocode.palava.ipc.IpcArguments;
import de.cosmocode.palava.ipc.IpcCall;
import de.cosmocode.palava.ipc.IpcCommand;
import de.cosmocode.palava.ipc.IpcCommandExecutionException;
import de.cosmocode.palava.ipc.IpcCommand.Description;
import de.cosmocode.palava.ipc.IpcCommand.Param;
import de.cosmocode.palava.ipc.IpcCommand.Return;
import de.cosmocode.palava.ipc.IpcCommand.Throw;
import de.cosmocode.palava.ipc.IpcCommand.Throws;
import de.cosmocode.palava.ipc.cache.Cache;

/**
 * See below.
 *
 * @author Willi Schoenborn
 */
@Description("Collect all enum values from the specified enum type.")
@Param(name = ValuesOf.CLASS, description = "The fully qualified enum class name")
@Return(
    name = ValuesOf.VALUES, 
    description = "A list of the found values with their name at the corresponding index. " +
        "The index in the returned is the enum value's ordinal"
)
@Throws({
    @Throw(name = ClassNotFoundException.class, description = "If there is no class with the specified name"),
    @Throw(name = IllegalArgumentException.class, description = "If the specified class is no enum class")
})
@Singleton
@Cache
public final class ValuesOf implements IpcCommand {

    public static final String CLASS = "class";
    public static final String VALUES = "values";
    
    private static final Logger LOG = LoggerFactory.getLogger(ValuesOf.class);

    @Override
    public void execute(IpcCall call, Map<String, Object> result) throws IpcCommandExecutionException {
        final IpcArguments arguments = call.getArguments();
        final String name = arguments.getString(CLASS);
        
        final Class<?> type;
        
        try {
            LOG.trace("Retrieving enum values for {}", name);
            type = Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new IpcCommandExecutionException(e);
        }
        
        Preconditions.checkArgument(type.isEnum(), "%s is no enum type", type);
        
        final Enum<?>[] array = type.asSubclass(Enum.class).getEnumConstants();
        final List<Enum<?>> list = Arrays.asList(array);
        LOG.trace("Found enum values for {}: {}", type, list);
        final List<String> values = Lists.transform(list, Enums.nameFunction());
        
        result.put(VALUES, values);
    }

}
