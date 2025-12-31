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
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLLIElement;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.dominokit.domino.ui.IsElement;
import org.dominokit.domino.ui.icons.Icon;
import org.dominokit.domino.ui.layout.NavBar;
import org.dominokit.domino.ui.menu.DropTarget;
import org.dominokit.domino.ui.menu.Menu;
import org.dominokit.domino.ui.menu.MissingItemHandler;
import org.dominokit.domino.ui.menu.OpenMenuCondition;
import org.dominokit.domino.ui.menu.SingleSelectionMode;
import org.dominokit.domino.ui.menu.direction.DropDirection;
import org.dominokit.domino.ui.search.SearchBox;
import org.dominokit.domino.ui.utils.AnyElement;
import org.dominokit.domino.ui.utils.ChildHandler;
import org.dominokit.domino.ui.utils.DominoElement;
import org.dominokit.domino.ui.utils.HasOpenCloseListeners;
import org.dominokit.domino.ui.utils.HasZIndexLayer;
import org.dominokit.domino.ui.utils.KeyboardNavigation;
import org.dominokit.domino.ui.utils.LazyChild;
import org.dominokit.domino.ui.utils.Selectable;
import org.dominokit.domino.ui.utils.SubheaderAddon;

public interface IsMenu<
        V, C extends IsMenu<V, C, I, S>, I extends IsMenuItem<V, I, S>, S extends Selectable<S>>
    extends HasOpenCloseListeners<C>, IsElement<HTMLDivElement> {
  void focusFirstMatch(String token);

  Optional<I> findOptionStarsWith(String token);

  /**
   * Determines if the menu is set to be centered on small screen devices.
   *
   * @return true if the menu should be centered on small screens, false otherwise.
   */
  boolean isCenterOnSmallScreens();

  /**
   * Sets the behavior for the menu to be centered or not on small screen devices.
   *
   * @param centerOnSmallScreens true to center the menu on small screens, false otherwise.
   * @return The current {@link Menu} instance.
   */
  C setCenterOnSmallScreens(boolean centerOnSmallScreens);

  boolean isRootMenu();

  /**
   * Allows adding an icon to the menu header.
   *
   * @param icon The icon to be set.
   * @return The current Menu instance.
   */
  C setIcon(Icon<?> icon);

  /**
   * Sets the title for the menu header.
   *
   * @param title The title to be set.
   * @return The current Menu instance.
   */
  C setTitle(String title);

  /**
   * Appends a subheader addon to the menu.
   *
   * @param addon The subheader addon to be added.
   * @return The current Menu instance.
   */
  C appendChild(SubheaderAddon<?> addon);

  C appendChild(SubheaderAddon<?>... addons);

  /**
   * Clears the current selection of menu items.
   *
   * @param silent if true, does not trigger the deselection listeners; otherwise, does.
   */
  void clearSelection(boolean silent);

  /**
   * Gets the label used when an item is not found during a search.
   *
   * @return the label string.
   */
  String getCreateMissingLabel();

  /**
   * Sets the label to be displayed when an item is not found during a search.
   *
   * @param createMissingLabel the label string.
   * @return The current {@link Menu} instance.
   */
  C setCreateMissingLabel(String createMissingLabel);

  /**
   * Retrieves the element used to display a "no results" message when a search yields no results.
   *
   * @return the "no results" element wrapped in a {@link LazyChild} container.
   */
  LazyChild<AnyElement> getNoResultElement();

  /**
   * Sets the element used to display a "no results" message when a search yields no results.
   *
   * @param noResultElement the HTMLLIElement to be used for displaying "no results".
   * @return The current {@link Menu} instance.
   */
  C setNoResultElement(Element noResultElement);

  /**
   * Sets the element used to display a "no results" message when a search yields no results.
   *
   * @param noResultElement the IsElement wrapping an HTMLLIElement to be used for displaying "no
   *     results".
   * @return The current {@link Menu} instance.
   */
  C setNoResultElement(IsElement<HTMLLIElement> noResultElement);

  /**
   * Checks if the menu's search functionality is case-sensitive.
   *
   * @return true if the search is case-sensitive, false otherwise.
   */
  boolean isCaseSensitive();

  /**
   * Sets the menu's search functionality to be case-sensitive or not.
   *
   * @param caseSensitive a boolean indicating whether to enable or disable case-sensitivity.
   * @return The current {@link Menu} instance.
   */
  C setCaseSensitive(boolean caseSensitive);

  /**
   * Retrieves the current focus element of the menu.
   *
   * <p>The focus element is determined based on the following criteria: - If a custom focus element
   * has been set, it will be returned. - If the menu is searchable, the search box input will be
   * the focus element. - If the menu contains menu items, the first item will be the focus element.
   * - Otherwise, the root element of the menu items list will be the focus element.
   *
   * @return the current focus element of the menu.
   */
  HTMLElement getFocusElement();

  /**
   * Sets the focus element for the menu.
   *
   * @param focusElement the HTMLElement to set as the focus element.
   * @return The current {@link Menu} instance.
   */
  C setFocusElement(HTMLElement focusElement);

  /**
   * Retrieves the search box component used within the menu.
   *
   * @return the {@link SearchBox} instance.
   */
  SearchBox getSearchBox();

  /**
   * Retrieves the keyboard navigation handler for the menu items.
   *
   * @return the keyboard navigation instance.
   */
  KeyboardNavigation<I> getKeyboardNavigation();

  /**
   * Retrieves the header component of the menu.
   *
   * @return the {@link NavBar} instance representing the menu's header.
   */
  NavBar getMenuHeader();

  /**
   * Toggles the visibility of the menu's header.
   *
   * @param visible true to make the header visible, false to hide it.
   * @return The current {@link Menu} instance.
   */
  C setHeaderVisible(boolean visible);

  /**
   * Checks if the menu has a search functionality enabled.
   *
   * @return true if the menu is searchable, false otherwise.
   */
  boolean isSearchable();

  /**
   * Checks if the menu allows for the creation of missing items.
   *
   * @return true if missing items can be created, false otherwise.
   */
  boolean isAllowCreateMissing();

  /**
   * Sets the handler for missing items in the menu. When set, it allows the creation of missing
   * items.
   *
   * @param missingItemHandler the handler to manage missing items.
   * @return The current {@link Menu} instance.
   */
  C setMissingItemHandler(MissingItemHandler<V, C> missingItemHandler);

  /**
   * Selects a given menu item.
   *
   * @param item The menu item to select.
   * @return The current {@link Menu} instance.
   */
  C select(I item);

  /**
   * Selects a given menu item with the option to silence selection events.
   *
   * @param menuItem The menu item to select.
   * @param silent If true, selection listeners will be paused; otherwise, they will be active.
   * @return The current {@link Menu} instance.
   */
  C select(I menuItem, boolean silent);

  /**
   * Selects a menu item at a specified index.
   *
   * @param index The index of the menu item to select.
   * @return The current {@link Menu} instance.
   */
  C selectAt(int index);

  /**
   * Selects a menu item at a specified index with the option to silence selection events.
   *
   * @param index The index of the menu item to select.
   * @param silent If true, selection listeners will be paused; otherwise, they will be active.
   * @return The current {@link Menu} instance.
   */
  C selectAt(int index, boolean silent);

  /**
   * Selects a menu item by its key identifier.
   *
   * @param key The key identifier of the menu item to select.
   * @return The current {@link Menu} instance.
   */
  C selectByKey(String key);

  /**
   * Selects a menu item by its key identifier with the option to silence selection events.
   *
   * @param key The key identifier of the menu item to select.
   * @param silent If true, selection listeners will be paused; otherwise, they will be active.
   * @return The current {@link Menu} instance.
   */
  C selectByKey(String key, boolean silent);

  /**
   * Checks if the menu is set to automatically close upon selection of an item.
   *
   * @return true if the menu will auto-close on selection, false otherwise.
   */
  boolean isAutoCloseOnSelect();

  /**
   * Sets whether the menu should automatically close upon selecting an item.
   *
   * @param autoCloseOnSelect If true, the menu will auto-close on selection.
   * @return The current {@link Menu} instance.
   */
  C setAutoCloseOnSelect(boolean autoCloseOnSelect);

  /**
   * Checks if the menu supports selecting multiple items simultaneously.
   *
   * @return true if the menu supports multi-selection, false otherwise.
   */
  boolean isMultiSelect();

  /**
   * Enables or disables the ability to select multiple items in the menu.
   *
   * @param multiSelect If true, multi-selection will be enabled.
   * @return The current {@link Menu} instance.
   */
  C setMultiSelect(boolean multiSelect);

  /**
   * Checks if the menu is set to automatically open.
   *
   * @return true if the menu will auto-open, false otherwise.
   */
  boolean isAutoOpen();

  /**
   * Sets whether the menu should automatically open.
   *
   * @param autoOpen If true, the menu will auto-open.
   * @return The current {@link Menu} instance.
   */
  C setAutoOpen(boolean autoOpen);

  /**
   * Checks if the menu is set to fit the width of its target.
   *
   * @return true if the menu fits the target width, false otherwise.
   */
  boolean isFitToTargetWidth();

  /**
   * Sets whether the menu should fit the width of its target.
   *
   * @param fitToTargetWidth If true, the menu will fit the target width.
   * @return The current {@link Menu} instance.
   */
  C setFitToTargetWidth(boolean fitToTargetWidth);

  /**
   * Pauses the selection listeners of the menu.
   *
   * @return The current {@link Menu} instance.
   */
  C pauseSelectionListeners();

  /**
   * Resumes the paused selection listeners of the menu.
   *
   * @return The current {@link Menu} instance.
   */
  C resumeSelectionListeners();

  /**
   * Toggles the pause state of the selection listeners.
   *
   * @param toggle If true, pauses the selection listeners; if false, resumes them.
   * @return The current {@link Menu} instance.
   */
  C togglePauseSelectionListeners(boolean toggle);

  /**
   * Checks if the selection listeners of the menu are currently paused.
   *
   * @return true if the selection listeners are paused, false otherwise.
   */
  boolean isSelectionListenersPaused();

  /**
   * Returns the current selection of menu items.
   *
   * @return A list of currently selected menu items.
   */
  List<S> getSelection();

  /**
   * Sets the menu to have a bordered appearance.
   *
   * @param bordered If true, the menu will have a border; if false, it will not.
   * @return The current {@link Menu} instance.
   */
  C setBordered(boolean bordered);

  /**
   * Checks if the menu is currently open.
   *
   * @return true if the menu is open, false otherwise.
   */
  boolean isOpened();

  /**
   * Opens the menu and optionally sets focus on it.
   *
   * @param focus If true, the menu will be focused upon opening.
   */
  void open(boolean focus);

  C triggerOnBeforeOpenListeners();

  /** Sets the focus on the menu. */
  void focus();

  /**
   * Gets the current target element for the menu.
   *
   * @return An optional containing the menu target, or empty if no target is set.
   */
  Optional<DropTarget> getTarget();

  /**
   * Sets the target element for the menu.
   *
   * @param targetElement The element to be set as the menu's target.
   * @return The current {@link Menu} instance.
   */
  C setTargetElement(IsElement<?> targetElement);

  /**
   * Sets the target element for the menu.
   *
   * @param targetElement The element to be set as the menu's target.
   * @return The current {@link Menu} instance.
   */
  C setTargetElement(Element targetElement);

  /**
   * Sets the target element for the menu.
   *
   * @param targetElement The element to be set as the menu's target.
   * @return The current {@link Menu} instance.
   */
  C addTargetElement(Element targetElement);

  /**
   * Sets the menu target.
   *
   * @param menuTarget The {@link DropTarget} instance representing the menu's target.
   * @return The current {@link Menu} instance.
   */
  C setTarget(DropTarget menuTarget);

  C clearTargets();

  /**
   * Adds a new target for the menu.
   *
   * @param menuTarget The new target to add.
   * @return The current {@link Menu} instance.
   */
  C addTarget(DropTarget menuTarget);

  /**
   * Removes a single menu target.
   *
   * @param target the target to be removed
   * @return same menu instance
   */
  C removeTarget(DropTarget target);

  /**
   * Gets the element to which the menu is appended in the DOM.
   *
   * @return The append target element.
   */
  DominoElement<Element> getMenuAppendTarget();

  /**
   * Sets the element to which the menu will be appended in the DOM.
   *
   * @param appendTarget The new append target element.
   * @return The current {@link Menu} instance.
   */
  C setMenuAppendTarget(Element appendTarget);

  /**
   * Opens the menu if it is a dropdown type.
   *
   * @return The current {@link Menu} instance.
   */
  C open();

  /**
   * Closes the menu if it is a dropdown type and if it is currently open.
   *
   * @return The current {@link Menu} instance.
   */
  C close();

  /**
   * Retrieves the direction in which the menu will drop when opened.
   *
   * @return The current drop direction for the menu.
   */
  DropDirection getDropDirection();

  /**
   * Sets the direction in which the menu will drop when opened.
   *
   * @param dropDirection The desired drop direction.
   * @return The current {@link Menu} instance.
   */
  C setDropDirection(DropDirection dropDirection);

  /**
   * Checks if the menu is set as a context menu.
   *
   * @return {@code true} if the menu is a context menu, {@code false} otherwise.
   */
  boolean isContextMenu();

  /**
   * Sets the menu as a context menu or not.
   *
   * @param contextMenu {@code true} to set the menu as a context menu, {@code false} otherwise.
   * @return The current {@link Menu} instance.
   */
  C setContextMenu(boolean contextMenu);

  /**
   * Checks if the menu is configured to use the small screens direction for dropping.
   *
   * @return {@code true} if the menu uses the small screens direction, {@code false} otherwise.
   */
  boolean isUseSmallScreensDirection();

  /**
   * Sets whether the menu should use the small screens drop direction.
   *
   * @param useSmallScreensDropDirection {@code true} to enable small screens drop direction, {@code
   *     false} otherwise.
   * @return The current {@link Menu} instance.
   */
  C setUseSmallScreensDirection(boolean useSmallScreensDropDirection);

  /**
   * Determines if the menu acts as a drop-down or a context menu.
   *
   * @return {@code true} if the menu acts as a drop-down or a context menu, {@code false}
   *     otherwise.
   */
  boolean isDropDown();

  /**
   * Adds a handler that is triggered when a new item is added to the menu.
   *
   * @param onAddItemHandler The handler to add.
   * @return The current {@link Menu} instance.
   */
  C addOnAddItemHandler(BaseMenu.OnAddItemHandler<V, C, I, S> onAddItemHandler);

  /**
   * Configures the menu to include a header.
   *
   * @return The current {@link Menu} instance.
   */
  C withHeader();

  /**
   * Configures the menu to include a customized header.
   *
   * @param handler A handler to customize the header.
   * @return The current {@link Menu} instance.
   */
  C withHeader(ChildHandler<C, NavBar> handler);

  /**
   * Checks if the menu is modal.
   *
   * @return {@code false} since the menu isn't a modal.
   */
  boolean isModal();

  /**
   * Checks if the menu is set to auto-close.
   *
   * @return {@code true} if the menu is set to auto-close, {@code false} otherwise.
   */
  boolean isAutoClose();

  /**
   * Sets the auto-close behavior for the menu.
   *
   * @param autoClose {@code true} to set the menu to auto-close, {@code false} otherwise.
   * @return The current {@link Menu} instance.
   */
  C setAutoClose(boolean autoClose);

  /**
   * Sets the condition for opening the menu.
   *
   * @param openMenuCondition A condition that needs to be met for the menu to open. If null,
   *     defaults to always allow the menu to open.
   * @return The current {@link Menu} instance.
   */
  C setOpenMenuCondition(OpenMenuCondition<V, C, I, S> openMenuCondition);

  /**
   * Checks if the menu is set to close when it loses focus.
   *
   * @return {@code true} if the menu is set to close on blur, {@code false} otherwise.
   */
  boolean isCloseOnBlur();

  /**
   * Sets the close-on-blur behavior for the menu.
   *
   * @param closeOnBlur {@code true} to set the menu to close when it loses focus, {@code false}
   *     otherwise.
   * @return The current {@link Menu} instance.
   */
  C setCloseOnBlur(boolean closeOnBlur);

  C setCloseOnScroll(boolean closeOnScroll);

  /**
   * @return boolean true if the selection style should be preserved after the menu item loses the
   *     selection focus, otherwise false.
   */
  boolean isPreserveSelectionStyles();

  /**
   * if true selecting an Item in the menu will preserve the selection style when the menu loses the
   * focus.
   *
   * @param preserveSelectionStyles boolean, true to preserve the style, false to remove the style.
   * @return same Menu instance.
   */
  C setPreserveSelectionStyles(boolean preserveSelectionStyles);

  HasZIndexLayer.ZIndexLayer getZIndexLayer();

  /**
   * Retrieves the set of {@link HasOpenCloseListeners.OpenListener}s registered for this element.
   *
   * @return A set of {@link HasOpenCloseListeners.OpenListener} instances.
   */
  Set<BaseMenu.OnBeforeOpenListener<? super C>> getOnBeforeOpenListeners();

  /**
   * Adds an open event listener to the element.
   *
   * @param onBeforeOpenListener The open event listener to be added.
   * @return The element with the open event listener added.
   */
  C addOnBeforeOpenListener(BaseMenu.OnBeforeOpenListener<? super C> onBeforeOpenListener);

  /**
   * Removes a close event listener from the element.
   *
   * @param onBeforeOpenListener The close event listener to be removed.
   * @return The element with the close event listener removed.
   */
  C removeOnBeforeOpenListener(BaseMenu.OnBeforeOpenListener<? super C> onBeforeOpenListener);

  C setSelectionMode(SingleSelectionMode selectionMode);

  C setAutoFocus(boolean autoFocus);

  boolean isAutoFocus();

  SingleSelectionMode getEffectiveSelectionMode();
}
