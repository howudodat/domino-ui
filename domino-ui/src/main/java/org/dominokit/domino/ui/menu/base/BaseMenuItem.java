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
package org.dominokit.domino.ui.menu.base;

import elemental2.dom.Element;
import java.util.List;
import java.util.Set;
import org.dominokit.domino.ui.elements.DivElement;
import org.dominokit.domino.ui.menu.AbstractMenuItem;
import org.dominokit.domino.ui.menu.HasSelectionMode;
import org.dominokit.domino.ui.menu.MenuSearchFilter;
import org.dominokit.domino.ui.menu.MenuStyles;
import org.dominokit.domino.ui.menu.SingleSelectionMode;
import org.dominokit.domino.ui.utils.BaseDominoElement;
import org.dominokit.domino.ui.utils.HasDeselectionHandler;
import org.dominokit.domino.ui.utils.HasSelectionHandler;
import org.dominokit.domino.ui.utils.HasSelectionListeners;
import org.gwtproject.editor.client.TakesValue;

/**
 * Represents a general purpose menu item that can be used in different types of menus.
 *
 * <p>Usage example:
 *
 * <pre>
 * AbstractMenuItem<String> item = new AbstractMenuItem<>();
 * item.setKey("item1").withValue("Value1");
 * </pre>
 *
 * @param <V> the type parameter defining the value of the menu item
 * @see BaseDominoElement
 */
public abstract class BaseMenuItem<V, C extends BaseMenuItem<V, C, E>, E extends Element>
    extends BaseDominoElement<E, C>
    implements HasSelectionHandler<C, C>,
        HasDeselectionHandler<C>,
        HasSelectionListeners<C, C, C>,
        HasSelectionMode<C>,
        TakesValue<V>,
        MenuStyles {

  protected List<HasSelectionHandler.SelectionHandler<AbstractMenuItem<V>>> selectionHandlers;
  protected List<HasDeselectionHandler.DeselectionHandler> deselectionHandlers;
  protected String key;
  protected V value;

  protected DivElement prefixElement;
  protected DivElement bodyElement;
  protected DivElement postfixElement;
  protected boolean searchable = true;
  protected boolean selectable = true;

  protected MenuSearchFilter searchFilter = (token, caseSensitive) -> false;
  protected SingleSelectionMode selectionMode = SingleSelectionMode.INHERIT;

  protected boolean selectionListenersPaused = false;
  protected Set<SelectionListener<? super AbstractMenuItem<V>, ? super AbstractMenuItem<V>>>
      selectionListeners;
  protected Set<SelectionListener<? super AbstractMenuItem<V>, ? super AbstractMenuItem<V>>>
      deselectionListeners;
}
