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

/**
 * Represents different display modes for an HTML table layout.
 *
 * <p>This enum provides three configurations for rendering table structures. Each mode offers a
 * distinct combination of layout settings, column behavior, and table header interactions. These
 * modes determine how the table is displayed and how it adjusts to content and container size.
 */
public enum TableMode {

  /**
   * Default HTML table layout: columns are sized by content and available table width, height grows
   * with content, column widths are not fixed, and headers are not sticky.
   */
  DEFAULT,
  /** Block table display, with fixed table body height and fixed columns width. */
  FIXED_HEIGHT,
  /** Fixed table layout with fixed and auto columns widths,sticky headers. */
  AUTO;
}
