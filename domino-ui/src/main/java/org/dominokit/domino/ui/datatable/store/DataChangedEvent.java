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

import java.util.List;
import org.dominokit.domino.ui.datatable.plugins.pagination.SortDirection;

/**
 * The {@code DataChangedEvent} class represents an event that is triggered when data in a data
 * store changes, such as when records are loaded or sorted.
 *
 * @param <T> The type of data representing the records in the data table.
 * @deprecated use {@link org.dominokit.domino.ui.data.DataChangedEvent} instead
 */
@Deprecated
public class DataChangedEvent<T> extends org.dominokit.domino.ui.data.DataChangedEvent<T> {

  /**
   * Constructs a new {@code DataChangedEvent} with the provided data and total count.
   *
   * @param newData The list of new data records.
   * @param totalCount The total count of records.
   */
  public DataChangedEvent(List<T> newData, int totalCount) {
    super(newData, totalCount);
  }

  /**
   * Constructs a new {@code DataChangedEvent} with the provided data, total count, sort direction,
   * and sort column.
   *
   * @param newData The list of new data records.
   * @param totalCount The total count of records.
   * @param sortDirection The sorting direction.
   * @param sortColumn The column used for sorting.
   */
  public DataChangedEvent(
      List<T> newData, int totalCount, SortDirection sortDirection, String sortColumn) {
    super(newData, totalCount, sortDirection, sortColumn);
  }

  /**
   * Constructs a new {@code DataChangedEvent} with the provided data, append flag, and total count.
   *
   * @param newData The list of new data records.
   * @param append {@code true} if the data is being appended to the existing data; {@code false}
   *     otherwise.
   * @param totalCount The total count of records.
   */
  public DataChangedEvent(List<T> newData, boolean append, int totalCount) {
    super(newData, append, totalCount);
  }

  /**
   * Constructs a new {@code DataChangedEvent} with the provided data, append flag, total count,
   * sort direction, and sort column.
   *
   * @param newData The list of new data records.
   * @param append {@code true} if the data is being appended to the existing data; {@code false}
   *     otherwise.
   * @param totalCount The total count of records.
   * @param sortDirection The sorting direction.
   * @param sortColumn The column used for sorting.
   */
  public DataChangedEvent(
      List<T> newData,
      boolean append,
      int totalCount,
      SortDirection sortDirection,
      String sortColumn) {
    super(newData, append, totalCount, sortDirection, sortColumn);
  }
}
