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
package org.dominokit.domino.ui.pagination;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VirtualScrollingPaginationTest {

  @Test
  public void testInitialState() {
    VirtualScrollingPagination pagination = VirtualScrollingPagination.create();
    assertEquals(1, pagination.activePage());
    assertEquals(10, pagination.getPageSize());
    assertEquals(0, pagination.getPagesCount());
    assertEquals(0, pagination.getTotalCount());
  }

  @Test
  public void testUpdatePages() {
    VirtualScrollingPagination pagination = VirtualScrollingPagination.create();
    pagination.updatePages(5, 20);
    assertEquals(5, pagination.getPagesCount());
    assertEquals(20, pagination.getPageSize());
    assertEquals(1, pagination.activePage());
  }

  @Test
  public void testUpdatePagesByTotalCount() {
    VirtualScrollingPagination pagination = VirtualScrollingPagination.create();
    pagination.updatePagesByTotalCount(100, 10);
    assertEquals(10, pagination.getPagesCount());
    assertEquals(100, pagination.getTotalCount());
    assertEquals(1, pagination.activePage());
  }

  @Test
  public void testNavigation() {
    VirtualScrollingPagination pagination = VirtualScrollingPagination.create(10);
    pagination.gotoPage(5);
    assertEquals(5, pagination.activePage());

    pagination.nextPage();
    assertEquals(6, pagination.activePage());

    pagination.previousPage();
    assertEquals(5, pagination.activePage());

    pagination.gotoFirst();
    assertEquals(1, pagination.activePage());

    pagination.gotoLast();
    assertEquals(10, pagination.activePage());
  }

  @Test
  public void testBoundaryConditions() {
    VirtualScrollingPagination pagination = VirtualScrollingPagination.create(10);
    pagination.gotoPage(0);
    assertEquals(1, pagination.activePage());

    pagination.gotoPage(11);
    assertEquals(1, pagination.activePage());

    pagination.gotoLast();
    pagination.nextPage();
    assertEquals(10, pagination.activePage());

    pagination.gotoFirst();
    pagination.previousPage();
    assertEquals(1, pagination.activePage());
  }

  @Test
  public void testChangeListeners() {
    VirtualScrollingPagination pagination = VirtualScrollingPagination.create(10);
    final int[] changeCount = {0};
    pagination.addChangeListener(
        (oldValue, newValue) -> {
          changeCount[0]++;
        });

    pagination.gotoPage(2);
    assertEquals(1, changeCount[0]);

    pagination.pauseChangeListeners();
    pagination.gotoPage(3);
    assertEquals(1, changeCount[0]);

    pagination.resumeChangeListeners();
    pagination.gotoPage(4);
    assertEquals(2, changeCount[0]);

    pagination.gotoPage(4); // No change
    assertEquals(2, changeCount[0]);
  }

  @Test
  public void testUpdatePagesListeners() {
    VirtualScrollingPagination pagination = VirtualScrollingPagination.create();
    final int[] changeCount = {0};
    pagination.addChangeListener(
        (oldValue, newValue) -> {
          changeCount[0]++;
        });

    pagination.updatePages(10);
    assertEquals(1, changeCount[0]);

    pagination.updatePages(10);
    assertEquals(2, changeCount[0]);

    pagination.updatePages(0);
    assertEquals(3, changeCount[0]);
    assertEquals(0, pagination.activePage());
  }

  @Test
  public void testGotoPageByRecordIndex() {
    VirtualScrollingPagination pagination = VirtualScrollingPagination.create();
    pagination.updatePagesByTotalCount(100, 10);
    assertEquals(1, pagination.activePage());

    pagination.gotoPageByRecordIndex(15);
    assertEquals(2, pagination.activePage());

    pagination.gotoPageByRecordIndex(99);
    assertEquals(10, pagination.activePage());

    pagination.gotoPage(1);
    pagination.gotoPageByRecordIndex(-1);
    assertEquals(1, pagination.activePage());

    pagination.gotoPageByRecordIndex(100);
    assertEquals(1, pagination.activePage());
  }
}
