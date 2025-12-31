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
import org.dominokit.domino.ui.datatable.DataTable;
import org.dominokit.domino.ui.datatable.TableRow;
import org.dominokit.domino.ui.datatable.events.TableDataUpdatedEvent;
import org.dominokit.domino.ui.datatable.plugins.DataTablePlugin;
import org.dominokit.domino.ui.style.CssClass;
import org.dominokit.domino.ui.utils.DominoEvent;

/**
 * A DataTable plugin that highlights a table row when clicked and clears highlights when the table
 * data is updated.
 *
 * @param <T> the type of data in the DataTable rows
 * @see DataTablePlugin
 */
public class RowHighlightPlugin<T> implements DataTablePlugin<T> {
  private RowHighlightCssClass highlightCssClass = RowHighlightCssClass.of();

  /**
   * add the highlight css root class to the table
   *
   * @param dataTable The DataTable instance to which this plugin is applied.
   */
  @Override
  public void onAfterAddTable(DataTable<T> dataTable) {
    dataTable.addCss("dui-datatable-row-highlight");
  }

  /**
   * highlight the row when clicked
   *
   * @param dataTable The DataTable to which this plugin is applied.
   * @param tableRow The row to be added.
   */
  @Override
  public void onRowAdded(DataTable<T> dataTable, TableRow<T> tableRow) {
    tableRow.addClickListener(evt -> highlightCssClass.apply(tableRow.element()));
  }

  /**
   * clear the row highlight when data changed.
   *
   * @param event The DataTable event.
   */
  @Override
  public void handleEvent(DominoEvent event) {
    if (TableDataUpdatedEvent.DATA_UPDATED.equals(event.getType())) {
      highlightCssClass.remove();
    }
  }

  private static class RowHighlightCssClass implements CssClass {

    private Runnable onApply = () -> {};
    private final CssClass css = () -> "dui-datatable-row-highlighted";

    public static RowHighlightCssClass of() {
      return new RowHighlightCssClass();
    }

    @Override
    public String getCssClass() {
      return css.getCssClass();
    }

    @Override
    public void apply(Element element) {
      onApply.run();
      css.apply(element);
      onApply = () -> css.remove(element);
    }

    public void remove() {
      onApply.run();
    }
  }
}
