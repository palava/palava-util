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

package de.cosmocode.palava.util.qa;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.internal.Preconditions;
import com.google.inject.name.Named;

import de.cosmocode.palava.ipc.IpcCall;
import de.cosmocode.palava.ipc.IpcCallFilter;
import de.cosmocode.palava.ipc.IpcCallFilterChain;
import de.cosmocode.palava.ipc.IpcCommand;
import de.cosmocode.palava.ipc.IpcCommandExecutionException;
import de.cosmocode.palava.ipc.IpcCommand.Description;

/**
 * A filter which logs a warning or throws an {@link IllegalStateException}
 * if the accessed command is not annotated with {@link Description}.
 *
 * @author Willi Schoenborn
 */
@Singleton
final class DescriptionFilter implements IpcCallFilter {

    private static final Logger LOG = LoggerFactory.getLogger(DescriptionFilter.class);

    private QualityMode mode = QualityMode.WARN;
    
    @Inject(optional = true)
    void setMode(@Named(QualityConfig.MODE) QualityMode mode) {
        this.mode = Preconditions.checkNotNull(mode, "Mode");
    }
    
    @Override
    public Map<String, Object> filter(IpcCall call, IpcCommand command, IpcCallFilterChain chain)
        throws IpcCommandExecutionException {
        
        switch (mode) {
            case WARN: {
                LOG.warn("Command {} has no description", command);
                return chain.filter(call, command);
            }
            case FAIL: {
                throw new IllegalStateException(String.format("Missing description for %s", command));
            }
            default: {
                throw new AssertionError(mode);
            }
        }
    }

}
