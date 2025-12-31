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
package org.dominokit.domino.ui.utils;

import com.google.gwt.junit.client.GWTTestCase;

public class MatchHighlighterTest extends GWTTestCase {

  @Override
  public String getModuleName() {
    return "org.dominokit.domino.ui.DominoUI";
  }

  public void testShouldReturnEmptyStringWhenSourceIsNull() {
    assertEquals("", MatchHighlighter.highlight(null, "match"));
  }

  public void testShouldReturnSourceWhenPartIsNull() {
    String source = "Domino UI";

    assertEquals(source, MatchHighlighter.highlight(source, null));
  }

  public void testShouldReturnSourceWhenPartIsEmpty() {
    String source = "Domino UI";

    assertEquals(source, MatchHighlighter.highlight(source, ""));
  }

  public void testShouldReturnSourceWhenSourceIsEmpty() {
    assertEquals("", MatchHighlighter.highlight("", "domino"));
  }

  public void testShouldReturnSourceWhenNoMatchFound() {
    String source = "Domino UI";

    assertEquals(source, MatchHighlighter.highlight(source, "test"));
  }

  public void testShouldHighlightMatchWithSpecialCharacters() {
    String highlighted = MatchHighlighter.highlight("İrfan", "an");

    assertEquals("İrf<mark>an</mark>", highlighted);
  }

  public void testShouldHighlightMatchIgnoringCase() {
    String highlighted = MatchHighlighter.highlight("DominoUI", "dom");

    assertEquals("<mark>Dom</mark>inoUI", highlighted);
  }

  public void testShouldHighlightAllOccurrencesMatchingFirstDetectedCase() {
    String highlighted = MatchHighlighter.highlight("foo foo Foo foo", "foo");

    assertEquals("<mark>foo</mark> <mark>foo</mark> Foo <mark>foo</mark>", highlighted);
  }

  public void testShouldHighlightEntireSourceWhenPartEqualsSource() {
    assertEquals("<mark>match</mark>", MatchHighlighter.highlight("match", "match"));
  }

  public void testShouldReturnSourceWhenPartLongerThanSource() {
    assertEquals("short", MatchHighlighter.highlight("short", "a bit longer"));
  }
}
