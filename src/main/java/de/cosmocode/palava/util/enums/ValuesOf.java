/**
 * Copyright 2010 CosmoCode GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.cosmocode.palava.util.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
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
        final List<String> values = ImmutableList.copyOf(Lists.transform(list, Enums.name()));
        
        result.put(VALUES, values);
    }

}
