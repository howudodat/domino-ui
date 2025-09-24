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
package org.dominokit.domino.ui.button.group;

import static org.dominokit.domino.ui.button.ButtonStyles.*;
import static org.dominokit.domino.ui.utils.Domino.*;

import org.dominokit.domino.ui.button.IsButton;
import org.dominokit.domino.ui.utils.BaseDominoElement;

/**
 * a component to group a set of buttons and align them horizontally or vertically, by default
 * buttons will be aligned horizontally.
 *
 * @see BaseDominoElement
 */
public class ButtonsGroup extends BaseButtonsGroup<ButtonsGroup, IsButton<?>> {

  /** Creates an empty ButtonsGroup */
  public ButtonsGroup() {}

  /**
   * Creates a ButtonsGroup that holds the provided buttons
   *
   * @param buttons The set of {@link org.dominokit.domino.ui.button.IsButton} components to be
   *     appended to the ButtonsGroup
   */
  public ButtonsGroup(IsButton<?>... buttons) {
    super(buttons);
  }

  /**
   * Factory method to create an empty ButtonsGroup
   *
   * @return An empty {@link org.dominokit.domino.ui.button.group.ButtonsGroup}.
   */
  public static ButtonsGroup create() {
    return new ButtonsGroup();
  }

  /**
   * Factory method to create a ButtonsGroup that holds the provided buttons
   *
   * @param buttons The set of {@link org.dominokit.domino.ui.button.IsButton} components to be
   *     appended to the ButtonsGroup
   * @return A {@link org.dominokit.domino.ui.button.group.ButtonsGroup}
   */
  public static ButtonsGroup create(IsButton<?>... buttons) {
    return new ButtonsGroup(buttons);
  }
}
