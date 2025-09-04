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

public interface BaseCellRenderer<
    T,
    E extends HTMLElement,
    C extends CellInfo<T, E, C>,
    O extends TableCell<T, E, C, R, O>,
    R extends BaseCellRenderer<T, E, C, O, R>> {

  /**
   * Converts the given cell information into a displayable Node element.
   *
   * @param cell {@link TableCell} information about the cell being rendered.
   */
  void render(O cell);
}
