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

import de.cosmocode.palava.ipc.IpcCall;
import de.cosmocode.palava.ipc.IpcCommand;

/**
 * A service which logs command execution time.
 *
 * @author Willi Schoenborn
 */
public interface BenchmarkService {

    /**
     * Logs the given results.
     * 
     * @param command the command being called
     * @param time the time spent executing
     * @param timeUnit the unit of time
     * @param call the incoming call
     * @param result the unmodifiable result
     */
    void log(Class<? extends IpcCommand> command, long time, TimeUnit timeUnit, 
        IpcCall call, Map<String, Object> result);
    
}
