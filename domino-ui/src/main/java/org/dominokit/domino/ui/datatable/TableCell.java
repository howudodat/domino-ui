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

import elemental2.dom.HTMLElement;
import org.dominokit.domino.ui.forms.validations.ValidationResult;
import org.dominokit.domino.ui.utils.BaseDominoElement;

public abstract class TableCell<
        T,
        E extends HTMLElement,
        C extends CellInfo<T, E, C>,
        R extends BaseCellRenderer<T, E, C, O, R>,
        O extends TableCell<T, E, C, R, O>>
    extends BaseDominoElement<E, O> {

  /**
   * Information about the cell, including its parent row and the HTML element representing the
   * cell.
   */
  protected final C cellInfo;

  /**
   * Constructs a new {@code RowCell} with the given cell information and column configuration.
   *
   * @param cellInfo Information about the cell.
   */
  public TableCell(C cellInfo) {
    this.cellInfo = cellInfo;
    init((O) this);
  }

  protected abstract R getDefaultCellRenderer();

  /**
   * Gets the information about the cell, including its parent row and the HTML element representing
   * the cell.
   *
   * @return Information about the cell.
   */
  public C getCellInfo() {
    return cellInfo;
  }

  public E element() {
    return getCellInfo().element();
  }

  public T getRecord() {
    return getCellInfo().getRecord();
  }

  public TableRow<T> getTableRow() {
    return getCellInfo().getTableRow();
  }

  /**
   * Invokes the handler for updating a dirty record.
   *
   * @param dirtyRecord the updated record.
   */
  public void updateDirtyRecord(T dirtyRecord) {
    this.cellInfo.updateDirtyRecord(dirtyRecord);
  }

  /**
   * Validates the cell's content.
   *
   * @return the validation result.
   */
  public ValidationResult validate() {
    return this.cellInfo.validate();
  }

  /**
   * Sets the handler responsible for updating dirty records.
   *
   * @param dirtyRecordHandler the handler to be set.
   */
  public void setDirtyRecordHandler(DirtyRecordHandler<T> dirtyRecordHandler) {
    this.cellInfo.setDirtyRecordHandler(dirtyRecordHandler);
  }

  /**
   * Sets the validator for the cell's content.
   *
   * @param cellValidator the validator to be set.
   */
  public void setCellValidator(CellValidator cellValidator) {
    this.cellInfo.setCellValidator(cellValidator);
  }
}
