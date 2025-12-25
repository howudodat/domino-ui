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
package org.dominokit.domino.ui.datatable.store;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.dominokit.domino.ui.datatable.events.BodyScrollEvent;
import org.dominokit.domino.ui.datatable.plugins.pagination.BodyScrollPlugin;
import org.junit.Test;

public class LocalListScrollingDataSourceTest {

  @Test
  public void testLoadData() {
    List<Integer> data = new ArrayList<>();
    for (int i = 0; i < 50; i++) {
      data.add(i);
    }

    LocalListScrollingDataSource<Integer> dataSource = new LocalListScrollingDataSource<>(data, 10);
    final List<Integer> loadedData = new ArrayList<>();
    dataSource.onDataChanged(
        event -> {
          loadedData.clear();
          loadedData.addAll(event.getNewData());
        });

    dataSource.load();
    assertEquals(10, loadedData.size());
    assertEquals(Integer.valueOf(0), loadedData.get(0));
    assertEquals(Integer.valueOf(9), loadedData.get(loadedData.size() - 1));
  }

  @Test
  public void testInitialLoadedPages() {
    List<Integer> data = new ArrayList<>();
    for (int i = 0; i < 50; i++) {
      data.add(i);
    }

    LocalListScrollingDataSource<Integer> dataSource =
        new LocalListScrollingDataSource<>(data, 10, 2);
    final List<Integer> loadedData = new ArrayList<>();
    dataSource.onDataChanged(
        event -> {
          loadedData.clear();
          loadedData.addAll(event.getNewData());
        });

    dataSource.load();
    assertEquals(20, loadedData.size());
    assertEquals(Integer.valueOf(0), loadedData.get(0));
    assertEquals(Integer.valueOf(19), loadedData.get(loadedData.size() - 1));
  }

  @Test
  public void testScrollingAppend() {
    List<Integer> data = new ArrayList<>();
    for (int i = 0; i < 50; i++) {
      data.add(i);
    }

    LocalListScrollingDataSource<Integer> dataSource = new LocalListScrollingDataSource<>(data, 10);
    final List<Integer> loadedData = new ArrayList<>();
    final boolean[] appendCalled = {false};
    dataSource.onDataChanged(
        event -> {
          if (event.isAppend()) {
            appendCalled[0] = true;
          }
          loadedData.addAll(event.getNewData());
        });

    dataSource.load();
    assertEquals(10, loadedData.size());

    dataSource.handleEvent(new BodyScrollEvent(BodyScrollPlugin.ScrollPosition.BOTTOM));

    assertEquals(20, loadedData.size());
    assertEquals(true, appendCalled[0]);
    assertEquals(Integer.valueOf(10), loadedData.get(10));
    assertEquals(Integer.valueOf(19), loadedData.get(19));
  }

  @Test
  public void testProgrammaticPaginationUpdate() {
    List<Integer> data = new ArrayList<>();
    for (int i = 0; i < 50; i++) {
      data.add(i);
    }

    LocalListScrollingDataSource<Integer> dataSource = new LocalListScrollingDataSource<>(data, 10);
    final List<Integer> loadedData = new ArrayList<>();
    dataSource.onDataChanged(
        event -> {
          if (!event.isAppend()) {
            loadedData.clear();
          }
          loadedData.addAll(event.getNewData());
        });

    dataSource.load();
    assertEquals(10, loadedData.size());
    assertEquals(Integer.valueOf(0), loadedData.get(0));

    dataSource.getPagination().gotoPage(2);

    assertEquals(20, loadedData.size());
    assertEquals(Integer.valueOf(0), loadedData.get(0));
    assertEquals(Integer.valueOf(19), loadedData.get(19));

    dataSource.getPagination().gotoPage(3);
    assertEquals(30, loadedData.size());
    assertEquals(Integer.valueOf(29), loadedData.get(29));

    dataSource.getPagination().gotoPage(2);
    assertEquals(20, loadedData.size());
    assertEquals(Integer.valueOf(0), loadedData.get(0));
    assertEquals(Integer.valueOf(19), loadedData.get(19));
  }

  @Test
  public void testJumpToPageLoadsAllPreviousPages() {
    List<Integer> data = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      data.add(i);
    }

    LocalListScrollingDataSource<Integer> dataSource = new LocalListScrollingDataSource<>(data, 10);
    final List<Integer> loadedData = new ArrayList<>();
    dataSource.onDataChanged(
        event -> {
          if (!event.isAppend()) {
            loadedData.clear();
          }
          loadedData.addAll(event.getNewData());
        });

    dataSource.load();
    assertEquals(10, loadedData.size());
    assertEquals(Integer.valueOf(0), loadedData.get(0));
    assertEquals(Integer.valueOf(9), loadedData.get(9));

    dataSource.getPagination().gotoPage(4);

    assertEquals(40, loadedData.size());
    assertEquals(Integer.valueOf(0), loadedData.get(0));
    assertEquals(Integer.valueOf(39), loadedData.get(39));
  }
}
