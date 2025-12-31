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
package org.dominokit.domino.ui.menu;

import static org.dominokit.domino.ui.style.GenericCss.dui_minified;

import org.dominokit.domino.ui.forms.CheckBox;

public class CheckMenuItem<V> extends MenuItem<V> {

  private CheckBox checkbox;

  /**
   * Creates a menu item with the specified text.
   *
   * @param text the text for the menu item
   * @return the created menu item
   */
  public static <V> CheckMenuItem<V> create(String text) {
    return new CheckMenuItem<>(text);
  }

  /**
   * Creates a menu item with the specified text and description.
   *
   * @param text the text for the menu item
   * @param description the description for the menu item
   * @return the created menu item
   */
  public static <V> CheckMenuItem<V> create(String text, String description) {
    return new CheckMenuItem<>(text, description);
  }

  public CheckMenuItem(String text) {
    super(text);
    initCheckBox();
  }

  public CheckMenuItem(String text, String description) {
    super(text, description);
    initCheckBox();
  }

  private void initCheckBox() {
    withPrefixElement(
        (menuItem, pre) -> {
          checkbox = CheckBox.create().addCss(dui_minified);
          pre.appendChild(checkbox);
          checkbox.addChangeListener(
              (oldValue, newValue) -> {
                if (newValue) {
                  menuItem.select();
                } else {
                  menuItem.deselect();
                }
              });
        });
  }

  @Override
  public AbstractMenuItem<V> select(boolean silent) {
    checkbox.withValue(true, true);
    return super.select(silent);
  }

  @Override
  public AbstractMenuItem<V> deselect(boolean silent) {
    checkbox.withValue(false, true);
    return super.deselect(silent);
  }
}
