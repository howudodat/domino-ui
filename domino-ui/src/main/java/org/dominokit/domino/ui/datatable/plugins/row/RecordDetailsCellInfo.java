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

import elemental2.dom.Element;
import elemental2.dom.HTMLTableCellElement;
import org.dominokit.domino.ui.IsElement;
import org.dominokit.domino.ui.datatable.CellInfo;
import org.dominokit.domino.ui.datatable.TableRow;

public class RecordDetailsCellInfo<T>
    extends CellInfo<T, HTMLTableCellElement, RecordDetailsCellInfo<T>> {

  private final Element detailsElement;
  private final TableRow<T> targetRow;

  public RecordDetailsCellInfo(
      TableRow<T> detailsRow,
      TableRow<T> targetRow,
      HTMLTableCellElement element,
      IsElement<? extends Element> detailsElement) {
    super(detailsRow, element);
    this.targetRow = targetRow;
    this.detailsElement = detailsElement.element();
  }

  @Override
  public Element getStyleTarget() {
    return this.element();
  }

  @Override
  public Element getAppendTarget() {
    return detailsElement;
  }
}
