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

import elemental2.dom.Element;

/** @deprecated use {@link DropTarget} */
@Deprecated
public class MenuTarget extends DropTarget {

  /**
   * Factory method to create an instance of {@link DropTarget} using the given DOM {@link Element}.
   *
   * @param element the target DOM element
   * @return a new instance of {@link DropTarget}
   * @deprecated use {@link DropTarget#of(Element)}
   */
  @Deprecated
  public static DropTarget of(Element element) {
    return new DropTarget(element);
  }

  /**
   * Constructs a new {@link DropTarget} for the provided target DOM {@link Element}.
   *
   * @param targetElement the target DOM element
   * @deprecated use {@link DropTarget(Element)}
   */
  @Deprecated
  public MenuTarget(Element targetElement) {
    super(targetElement);
  }
}
