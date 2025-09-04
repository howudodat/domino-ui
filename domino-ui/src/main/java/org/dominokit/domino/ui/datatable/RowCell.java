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

package org.dominokit.domino.ui.datatable;

import static java.util.Objects.nonNull;
import static org.dominokit.domino.ui.utils.ElementsFactory.elements;

import elemental2.dom.HTMLTableCellElement;
import java.util.Optional;

/**
 * The {@code RowCell} class represents a cell within a data table row. It encapsulates the cell's
 * rendering logic and provides methods to update the content of the cell based on the associated
 * column configuration and cell data.
 *
 * @param <T> The type of data contained in the cell.
 */
public class RowCell<T>
    extends TableCell<T, HTMLTableCellElement, RowCellInfo<T>, RowCellRenderer<T>, RowCell<T>> {

  /** The column configuration associated with this cell. */
  private final ColumnConfig<T> columnConfig;

  /**
   * Constructs a new {@code RowCell} with the given cell information and column configuration.
   *
   * @param cellInfo Information about the cell.
   * @param columnConfig The column configuration associated with this cell.
   */
  public RowCell(RowCellInfo<T> cellInfo, ColumnConfig<T> columnConfig) {
    super(cellInfo);
    this.columnConfig = columnConfig;
  }

  @Override
  public RowCellRenderer<T> getDefaultCellRenderer() {
    return cellInfo -> elements.text();
  }

  /**
   * Gets the column configuration associated with this cell.
   *
   * @return The column configuration of this cell.
   */
  public Optional<ColumnConfig<T>> getColumnConfig() {
    return Optional.ofNullable(columnConfig);
  }

  /**
   * Updates the content of the cell based on the associated column configuration and cell data.
   * This method should be called whenever the cell's content needs to be refreshed.
   */
  public void updateCell() {
    clearElement();

    getColumnConfig()
        .ifPresent(
            columnConfig -> {
              if (nonNull(columnConfig.getTextAlign())) {
                addCss(columnConfig.getTextAlign());
              }

              if (nonNull(columnConfig.getHeaderTextAlign())) {
                columnConfig.getHeadElement().addCss(columnConfig.getHeaderTextAlign());
              }

              if (getCellInfo().getTableRow().isEditable()) {
                if (nonNull(columnConfig.getEditableCellRenderer())) {
                  columnConfig.getEditableCellRenderer().render(this);
                } else {
                  getDefaultCellRenderer().render(this);
                }
              } else {
                if (nonNull(columnConfig.getCellRenderer())) {
                  columnConfig.getCellRenderer().render(this);
                } else {
                  getDefaultCellRenderer().render(this);
                }
              }
            });
  }
}
