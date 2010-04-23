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

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.inject.name.Named;

import de.cosmocode.palava.ipc.IpcCall;
import de.cosmocode.palava.ipc.IpcCommand;

/**
 * Default implementation of the {@link BenchmarkService} which logs all
 * command executions using its {@link Logger}.
 *
 * @author Willi Schoenborn
 */
final class LogBenchmarkService implements BenchmarkService {

    private Logger log = LoggerFactory.getLogger(LogBenchmarkService.class);
    
    public void setLog(@Named(LogBenchmarkServiceConfig.CATEGORY) Logger log) {
        this.log = Preconditions.checkNotNull(log, "Log");
    }
    
    @Override
    public void log(Class<? extends IpcCommand> command, long time, TimeUnit timeUnit, IpcCall call,
        Map<String, Object> result) {

        log.debug("{} took {} {}s to process {}", new Object[] {
            command, time, timeUnit.name().toLowerCase(), call
        });
    }
    
}
