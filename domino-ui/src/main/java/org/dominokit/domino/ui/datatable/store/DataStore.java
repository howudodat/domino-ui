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

package org.dominokit.domino.ui.datatable.store;

import java.util.Objects;
import org.dominokit.domino.ui.data.DataChangedEvent;

/**
 * The {@code DataStore} interface defines a contract for managing and retrieving data for a data
 * table.
 *
 * @param <T> The type of data representing the records in the data table.
 * @deprecated use {@link org.dominokit.domino.ui.data.DataStore} instead
 */
@Deprecated
public interface DataStore<T> extends org.dominokit.domino.ui.data.DataStore<T> {

  void onDataChanged(
      org.dominokit.domino.ui.datatable.store.StoreDataChangeListener<T> dataChangeListener);

  void removeDataChangeListener(
      org.dominokit.domino.ui.datatable.store.StoreDataChangeListener<T> dataChangeListener);

  default void onDataChanged(
      org.dominokit.domino.ui.data.StoreDataChangeListener<T> dataChangeListener) {
    onDataChanged(new ListenerWrapper<>(dataChangeListener));
  }

  default void removeDataChangeListener(
      org.dominokit.domino.ui.data.StoreDataChangeListener<T> dataChangeListener) {
    removeDataChangeListener(new ListenerWrapper<>(dataChangeListener));
  }

  final class ListenerWrapper<T>
      implements org.dominokit.domino.ui.datatable.store.StoreDataChangeListener<T> {

    private final org.dominokit.domino.ui.data.StoreDataChangeListener<T> wrapped;

    public ListenerWrapper(org.dominokit.domino.ui.data.StoreDataChangeListener<T> wrapped) {
      this.wrapped = wrapped;
    }

    @Override
    public void onDataChanged(DataChangedEvent<T> dataChangedEvent) {
      wrapped.onDataChanged(dataChangedEvent);
    }

    @Override
    public boolean equals(Object o) {
      if (o == null || getClass() != o.getClass()) return false;
      ListenerWrapper<?> that = (ListenerWrapper<?>) o;
      return Objects.equals(wrapped, that.wrapped);
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(wrapped);
    }
  }
}
