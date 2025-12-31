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

import elemental2.dom.Node;

/**
 * A functional interface representing the renderer responsible for rendering a cell within the data
 * table.
 *
 * @param <T> the type of data contained within the table row.
 * @deprecated use {@link RowCellRenderer}
 */
@FunctionalInterface
@Deprecated
public interface CellRenderer<T> {

  /**
   * Converts the given cell information into a displayable Node element.
   *
   * @param rowCell information about the cell being rendered.
   * @return the Node representation of the cell content.
   */
  @Deprecated
  Node asElement(RowCell<T> rowCell);
}
