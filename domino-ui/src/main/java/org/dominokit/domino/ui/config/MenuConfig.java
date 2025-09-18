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
package org.dominokit.domino.ui.config;

import org.dominokit.domino.ui.menu.SingleSelectionMode;

/**
 * Configuration contract for menu components in Domino UI.
 *
 * <p>This interface centralizes menu-related text and behavior defaults used by menu widgets, such
 * as labels/messages for search results and the default selection mode. Framework users may provide
 * their own implementation to customize messages (for localization or branding) and behavior while
 * leveraging the defaults provided here.
 *
 * <p>Note: Some returned strings include basic HTML markup (e.g., <b> tags) intended for rendering
 * in components that accept HTML content. If you override these methods, ensure the formatting is
 * appropriate for the target component and sanitized if necessary.
 *
 * <p>Extends {@link ZIndexConfig} to inherit z-index related configuration that affects menu popup
 * layering and stacking context.
 */
public interface MenuConfig extends ZIndexConfig {
  /**
   * Returns the message displayed when no search results match the provided token.
   *
   * @param token the user-entered search token that produced no results
   * @return a user-facing message, potentially containing simple HTML emphasizing the token
   */
  default String getNoResultMatchMessage(String token) {
    return "No results matched " + " <b>" + token + "</b>";
  }

  /**
   * Returns the label prefix used when suggesting the creation of a missing item.
   *
   * <p>For example, when the user types a value that doesn't exist, the UI may show a suggestion
   * like "Create <b>token</b>". This method provides the "Create " part, allowing consistent
   * localization/branding.
   *
   * @return the label prefix for the create-missing-item suggestion
   */
  default String getMissingItemCreateLabel() {
    return "Create ";
  }

  /**
   * Returns the full message used when suggesting the creation of a missing item.
   *
   * @param token the user-entered value that is missing
   * @return a suggestion message combining {@link #getMissingItemCreateLabel()} and the token,
   *     often with simple HTML emphasis
   */
  default String getMissingItemCreateMessage(String token) {
    return getMissingItemCreateLabel() + " <b>" + token + "</b>";
  }

  /**
   * Returns the default selection mode for menu components.
   *
   * <p>The default is {@link SingleSelectionMode#RESELECT} which allows selecting an already
   * selected item to trigger its action again.
   *
   * @return the default {@link SingleSelectionMode}
   */
  default SingleSelectionMode getDefaultSelectionMode() {
    return SingleSelectionMode.RESELECT;
  }
}
