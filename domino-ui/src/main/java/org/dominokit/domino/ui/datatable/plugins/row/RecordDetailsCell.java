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

package org.dominokit.domino.ui.datatable.plugins.row;

import static org.dominokit.domino.ui.utils.ElementsFactory.elements;

import elemental2.dom.HTMLTableCellElement;
import org.dominokit.domino.ui.datatable.TableCell;

/**
 * The {@code RowCell} class represents a cell within a data table row. It encapsulates the cell's
 * rendering logic and provides methods to update the content of the cell based on the associated
 * column configuration and cell data.
 *
 * @param <T> The type of data contained in the cell.
 */
public class RecordDetailsCell<T>
    extends TableCell<
        T,
        HTMLTableCellElement,
        RecordDetailsCellInfo<T>,
        RecordDetailsRenderer<T>,
        RecordDetailsCell<T>> {

  /**
   * Constructs a new {@code RowCell} with the given cell information and column configuration.
   *
   * @param cellInfo Information about the cell.
   */
  public RecordDetailsCell(RecordDetailsCellInfo<T> cellInfo) {
    super(cellInfo);
  }

  @Override
  public RecordDetailsRenderer<T> getDefaultCellRenderer() {
    return cellInfo -> elements.text();
  }
}
