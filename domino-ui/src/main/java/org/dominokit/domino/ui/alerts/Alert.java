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
package org.dominokit.domino.ui.alerts;

import static org.dominokit.domino.ui.utils.Domino.*;

/**
 * Displays a none floating message anywhere in the page, the message can be permanent or
 * dismissible
 *
 * <p>This component can be themed based on context, for example:
 *
 * <ul>
 *   <li>Success
 *   <li>Info
 *   <li>Warning
 *   <li>Error
 * </ul>
 *
 * <p>Example:
 *
 * <pre>
 *     Alert.success()
 *          .appendChild("Well done! ")
 *          .appendChild("You successfully read this important alert message.")
 * </pre>
 *
 * @see org.dominokit.domino.ui.utils.BaseDominoElement
 */
public class Alert extends BaseAlert<Alert> {

  /**
   * Creates an Alert message without assuming a specific context, context can be applied by adding
   * a context css class like <b>dui_success, dui_error, dui_info, .. etc</b>
   */
  public Alert() {
    super();
  }

  /**
   * Factory method to create an Alert message without assuming a specific context, context can be
   * applied by adding a context css class like <b>dui_success, dui_error, dui_info, .. etc</b>
   *
   * @return new Alert instance
   */
  public static Alert create() {
    return new Alert();
  }

  /**
   * Factory method to create an Alert with primary context, primary context will set the message
   * background to the theme primary context color
   *
   * @return new Alert instance
   */
  public static Alert primary() {
    return create().addCss(dui_primary);
  }

  /**
   * Factory method to create an Alert with secondary context, primary context will set the message
   * background to the theme secondary context color
   *
   * @return new Alert instance
   */
  public static Alert secondary() {
    return create().addCss(dui_secondary);
  }

  /**
   * Factory method to create an Alert with dominant context, primary context will set the message
   * background to the theme dominant context color
   *
   * @return new Alert instance
   */
  public static Alert dominant() {
    return create().addCss(dui_dominant);
  }

  /**
   * Factory method to create an Alert with success context, primary context will set the message
   * background to the theme success context color
   *
   * @return new Alert instance
   */
  public static Alert success() {
    return create().addCss(dui_success);
  }

  /**
   * Factory method to create an Alert with info context, primary context will set the message
   * background to the theme info context color
   *
   * @return new Alert instance
   */
  public static Alert info() {
    return create().addCss(dui_info);
  }

  /**
   * Factory method to create an Alert with warning context, primary context will set the message
   * background to the theme warning context color
   *
   * @return new Alert instance
   */
  public static Alert warning() {
    return create().addCss(dui_warning);
  }

  /**
   * Factory method to create an Alert with error context, primary context will set the message
   * background to the theme error context color
   *
   * @return new Alert instance
   */
  public static Alert error() {
    return create().addCss(dui_error);
  }
}
