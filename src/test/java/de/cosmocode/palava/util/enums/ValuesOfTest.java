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

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import com.google.inject.internal.Maps;

import de.cosmocode.junit.UnitProvider;
import de.cosmocode.palava.ipc.IpcArguments;
import de.cosmocode.palava.ipc.IpcCall;
import de.cosmocode.palava.ipc.IpcCommandExecutionException;

/**
 * Tests {@link ValuesOf}.
 *
 * @author Willi Schoenborn
 */
public final class ValuesOfTest implements UnitProvider<ValuesOf> {

    @Override
    public ValuesOf unit() {
        return new ValuesOf();
    }
    
    /**
     * Tests {@link ValuesOf} with a valid input.
     * 
     * @throws IpcCommandExecutionException if execution failed, should not happen 
     */
    @Test
    public void valid() throws IpcCommandExecutionException {
        final IpcCall call = EasyMock.createMock("call", IpcCall.class);
        final IpcArguments arguments = EasyMock.createMock("arguments", IpcArguments.class);
        EasyMock.expect(arguments.getString(ValuesOf.CLASS)).andReturn(TimeUnit.class.getName());
        EasyMock.expect(call.getArguments()).andReturn(arguments);
        EasyMock.replay(call, arguments);
        
        final Map<String, Object> result = Maps.newLinkedHashMap();
        unit().execute(call, result);
        
        final Enum<?>[] values = Enum[].class.cast(result.get(ValuesOf.VALUES));
        
        Assert.assertNotNull(values);
        Assert.assertFalse(values.length == 0);
        
        Assert.assertArrayEquals(TimeUnit.values(), values);
        EasyMock.verify(call, arguments);
    }
    
    /**
     * Tests {@link ValuesOf} with a wrong class name.
     * 
     * @throws ClassNotFoundException expected
     */
    @Test(expected = ClassNotFoundException.class)
    public void classNotFound() throws ClassNotFoundException {
        final IpcCall call = EasyMock.createMock("call", IpcCall.class);
        final IpcArguments arguments = EasyMock.createMock("arguments", IpcArguments.class);
        EasyMock.expect(arguments.getString(ValuesOf.CLASS)).andReturn("java.uti.TimeUnit");
        EasyMock.expect(call.getArguments()).andReturn(arguments);
        EasyMock.replay(call, arguments);
        
        final Map<String, Object> result = Maps.newLinkedHashMap();
        
        try {
            unit().execute(call, result);
        } catch (IpcCommandExecutionException e) {
            EasyMock.verify(call, arguments);
            if (e.getCause() instanceof ClassNotFoundException) {
                throw ClassNotFoundException.class.cast(e.getCause());
            } else {
                throw new IllegalStateException(e);
            }
        }
    }
    
    /**
     * Tests {@link ValuesOf} with a class name which addresses a valid non-enum class.
     */
    @Test(expected = IllegalArgumentException.class)
    public void noEnum() {
        final IpcCall call = EasyMock.createMock("call", IpcCall.class);
        final IpcArguments arguments = EasyMock.createMock("arguments", IpcArguments.class);
        EasyMock.expect(arguments.getString(ValuesOf.CLASS)).andReturn(ArrayList.class.getName());
        EasyMock.expect(call.getArguments()).andReturn(arguments);
        EasyMock.replay(call, arguments);
        
        final Map<String, Object> result = Maps.newLinkedHashMap();
        
        try {
            unit().execute(call, result);
        } catch (IpcCommandExecutionException e) {
            EasyMock.verify(call, arguments);
            if (e.getCause() instanceof IllegalArgumentException) {
                throw IllegalArgumentException.class.cast(e.getCause());
            } else {
                throw new IllegalStateException(e);
            }
        }
    }
    
}
