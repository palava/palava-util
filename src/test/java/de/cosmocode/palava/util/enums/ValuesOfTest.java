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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Functions;
import com.google.common.collect.Lists;
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
        
        @SuppressWarnings("unchecked")
        final List<String> values = List.class.cast(result.get(ValuesOf.VALUES));
        
        Assert.assertNotNull(values);
        Assert.assertFalse(values.isEmpty());
        
        Assert.assertEquals(Lists.transform(Arrays.asList(TimeUnit.values()), Functions.toStringFunction()), values);
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
