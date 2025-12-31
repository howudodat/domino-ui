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

import elemental2.dom.HTMLElement;
import org.dominokit.domino.ui.IsElement;
import org.dominokit.domino.ui.elements.DivElement;
import org.dominokit.domino.ui.menu.SingleSelectionMode;
import org.dominokit.domino.ui.utils.ChildHandler;
import org.dominokit.domino.ui.utils.HasSelectionListeners;
import org.dominokit.domino.ui.utils.PostfixElement;
import org.dominokit.domino.ui.utils.PrefixElement;
import org.dominokit.domino.ui.utils.Selectable;

public interface IsMenuItem<V, I extends IsMenuItem<V, I, S>, S extends Selectable<S>>
    extends IsElement<HTMLElement>, HasSelectionListeners<S, S, S> {
  I select(boolean silent);

  I deselect(boolean silent);

  boolean isRootItem();

  /**
   * Determines whether the menu item is selectable.
   *
   * @return true if the item is selectable, false otherwise
   */
  boolean isSelectable();

  /**
   * Sets the selectable property of the menu item.
   *
   * @param selectable true to make the item selectable, false otherwise
   * @return the current instance of the menu item
   */
  I setSelectable(boolean selectable);

  /**
   * Sets the selection state of this item.
   *
   * @param selected true to select, false to deselect
   * @return this item (for chaining)
   */
  I setSelected(boolean selected);

  /**
   * Sets the selection state of this item, optionally silencing events.
   *
   * <p>When selected is true, selection logic obeys the current {@link
   * #getEffectiveSelectionMode()}.
   *
   * @param selected true to select, false to deselect
   * @param silent if true, selection/deselection handlers and listeners are not notified
   * @return this item (for chaining)
   */
  I setSelected(boolean selected, boolean silent);

  /**
   * Determines whether the menu item is searchable.
   *
   * @return true if the item is searchable, false otherwise
   */
  boolean isSearchable();

  /**
   * Sets the searchable property of the menu item.
   *
   * @param searchable true to make the item searchable, false otherwise
   * @return the current instance of the menu item
   */
  I setSearchable(boolean searchable);

  /**
   * Performs a search on the menu item based on the given token.
   *
   * <p>This method typically determines the visibility of the menu item based on the search token.
   *
   * @param token the search token or keyword
   * @param caseSensitive determines if the search should consider case sensitivity
   * @return always returns {@code false}; the reason for this should be provided based on the
   *     method's context
   */
  boolean onSearch(String token, boolean caseSensitive);

  /**
   * Selects the menu item without notifying the selection handlers.
   *
   * @return the current instance of the menu item
   */
  I select();

  /**
   * Deselects the menu item without notifying the deselection handlers.
   *
   * @return the current instance of the menu item
   */
  I deselect();

  /**
   * Checks if the menu item is currently selected.
   *
   * @return {@code true} if the menu item is selected, {@code false} otherwise
   */
  boolean isSelected();

  /**
   * Sets focus on the clickable element of the menu item.
   *
   * @return the current instance of the menu item
   */
  I focus();

  /**
   * Retrieves the key associated with this menu item.
   *
   * @return the key of the menu item
   */
  String getKey();

  /**
   * Retrieves the value associated with this menu item.
   *
   * @return the value of the menu item
   */
  V getValue();

  /**
   * Sets the value for this menu item.
   *
   * <p>This can represent any associated data or context for the item.
   *
   * @param value the value to set
   */
  void setValue(V value);

  /**
   * Assigns a value to the menu item and returns the item instance.
   *
   * <p>This is a fluid API method to allow chained calls.
   *
   * @param value the value to set
   * @return the current instance of the menu item with the specified value set
   */
  <T extends I> T withValue(V value);

  /**
   * Retrieves the parent menu of this menu item.
   *
   * @return the parent menu of this item
   */
  <C extends IsMenu<V, C, I, S>> IsMenu<V, C, I, S> getParent();

  /**
   * Determines if this menu item has an associated sub-menu.
   *
   * @return true if there is a sub-menu, false otherwise
   */
  boolean hasMenu();

  /**
   * Retrieves the clickable element associated with this menu item.
   *
   * @return the HTML element that can be clicked to trigger this menu item
   */
  HTMLElement getClickableElement();

  PrefixElement getPrefixElement();

  PostfixElement getPostfixElement();

  /**
   * Allows customizing the body element that contains the content of the item.
   *
   * @param handler a handler that receives this menu item and the body element
   * @return this item (for chaining)
   */
  I withBodyElement(ChildHandler<I, DivElement> handler);

  /**
   * Check if the menu item text starts with a specific string
   *
   * @param character the text to check against.
   * @return boolean, <b>true</b> if the menu item starts with the text, <b>false</b> otherwise.
   */
  boolean startsWith(String character);

  /**
   * Sets the selection mode behavior for single selection scenarios.
   *
   * @param selectionMode the selection mode to apply; use {@link SingleSelectionMode#INHERIT} to
   *     inherit from the parent menu
   * @return this item (for chaining)
   */
  I setSelectionMode(SingleSelectionMode selectionMode);

  /**
   * Resolves the effective {@link SingleSelectionMode} for this item.
   *
   * <p>If this item is configured with {@link SingleSelectionMode#INHERIT}, the value is resolved
   * from the parent menu (falling back to {@link SingleSelectionMode#RESELECT} if no parent is
   * present). Otherwise, returns the explicitly configured value.
   *
   * @return the effective selection mode to be used
   */
  SingleSelectionMode getEffectiveSelectionMode();

  <C extends IsMenu<V, C, I, S>> C getMenu();
}
