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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */

package de.cosmocode.palava.util.benchmark;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.cosmocode.palava.ipc.IpcCall;
import de.cosmocode.palava.ipc.IpcCallFilter;
import de.cosmocode.palava.ipc.IpcCallFilterChain;
import de.cosmocode.palava.ipc.IpcCommand;
import de.cosmocode.palava.ipc.IpcCommandExecutionException;

/**
 * An {@link IpcCallFilter} which measures the time the filter chain
 * needs to filter and execute the given command. This filter
 * delegates the log work to an instance of {@link BenchmarkService}.
 *
 * @author Willi Schoenborn
 */
@Singleton
final class BenchmarkFilter implements IpcCallFilter {

    private final BenchmarkService service;
    
    @Inject
    public BenchmarkFilter(BenchmarkService service) {
        this.service = Preconditions.checkNotNull(service, "Service");
    }

    @Override
    public Map<String, Object> filter(IpcCall call, IpcCommand command, IpcCallFilterChain chain)
        throws IpcCommandExecutionException {

        final long start = System.currentTimeMillis();
        final Map<String, Object> result = chain.filter(call, command);
        final long time = System.currentTimeMillis() - start;
        
        service.log(command.getClass(), time, TimeUnit.MILLISECONDS, call, Collections.unmodifiableMap(result));
        
        return result;
    }

}
