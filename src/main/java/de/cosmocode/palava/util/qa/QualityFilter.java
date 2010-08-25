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

import com.google.inject.Inject;
import com.google.inject.internal.Preconditions;
import com.google.inject.name.Named;

import de.cosmocode.palava.ipc.IpcCallFilter;

/**
 * Abstract class for quality based filters.
 *
 * @since 1.2
 * @author Willi Schoenborn
 */
abstract class QualityFilter implements IpcCallFilter {

    private QualityMode mode = QualityMode.WARNING;
    
    @Inject(optional = true)
    final void setMode(@Named(QualityConfig.MODE) QualityMode mode) {
        this.mode = Preconditions.checkNotNull(mode, "Mode");
    }
    
    final QualityMode getMode() {
        return mode;
    }

}
