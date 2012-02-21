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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;

import java.util.List;

final class EuropeanWeekdayComparator extends Ordering<Weekday> {

    private final List<Weekday> inOrder = ImmutableList.of(
        Weekday.MONDAY, Weekday.TUESDAY, Weekday.WEDNESDAY, Weekday.THURSDAY,
        Weekday.FRIDAY, Weekday.SATURDAY, Weekday.SUNDAY
    );

    private final Ordering<Weekday> ordering = Ordering.explicit(inOrder);

    @Override
    public int compare(Weekday left, Weekday right) {
        return ordering.compare(left, right);
    }

}
