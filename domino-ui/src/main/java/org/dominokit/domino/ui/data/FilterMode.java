/*
 * Copyright © 2019 Dominokit
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
package org.dominokit.domino.ui.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface FilterMode<T> {

  List<T> apply(Collection<T> data, Collection<? extends DataFilter<? super T>> filters);

  static <T> FilterMode<T> denial() {
    return (data, filters) -> {
      if (data == null || data.isEmpty()) return new ArrayList<>();
      if (filters == null || filters.isEmpty()) return new ArrayList<>(data);
      List<T> out = new ArrayList<>(data.size());
      outer:
      for (T item : data) {
        for (DataFilter<? super T> f : filters) {
          if (!f.filter(item)) continue outer;
        }
        out.add(item);
      }
      return out;
    };
  }

  static <T> FilterMode<T> acceptance() {
    return (data, filters) -> {
      if (data == null || data.isEmpty()) return new ArrayList<>();
      if (filters == null || filters.isEmpty()) return new ArrayList<>(data);
      List<T> out = new ArrayList<>();
      outer:
      for (T item : data) {
        for (DataFilter<? super T> f : filters) {
          if (f.filter(item)) {
            out.add(item);
            continue outer;
          }
        }
      }
      return out;
    };
  }

  /** Ignore filters and accept all items */
  static <T> FilterMode<T> acceptAll() {
    return (data, filters) -> new ArrayList<>(data);
  }

  /** Ignore filters and deny all items */
  static <T> FilterMode<T> denyAll() {
    return (data, filters) -> new ArrayList<>();
  }
}
