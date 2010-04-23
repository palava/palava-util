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
