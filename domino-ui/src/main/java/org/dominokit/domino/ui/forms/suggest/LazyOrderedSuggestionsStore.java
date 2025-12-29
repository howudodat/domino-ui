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
package org.dominokit.domino.ui.forms.suggest;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Function;
import org.dominokit.domino.ui.IsElement;

/**
 * A suggestions store implementation that maintains ordered suggestions and maps items to options
 * lazily. Suggestions are ordered by their keys.
 *
 * @param <T> The type of values associated with the suggestions.
 * @param <E> The element type used for suggestions.
 * @param <O> The type of the option element within the store.
 */
public class LazyOrderedSuggestionsStore<T, E extends IsElement<?>, O extends Option<T, E, O>>
    extends AbstractLazySuggestionsStore<T, E, O, LazyOrderedSuggestionsStore<T, E, O>> {

  private final Set<T> items = new LinkedHashSet<>();

  /**
   * Constructs a {@code LazyOrderedSuggestionsStore} with the provided option mapper.
   *
   * @param optionMapper The function to map items to options. Cannot be null.
   */
  public LazyOrderedSuggestionsStore(Function<T, Optional<O>> optionMapper) {
    super(optionMapper);
  }

  /**
   * Creates a new {@code LazyOrderedSuggestionsStore} instance with an option mapper.
   *
   * @param <T> The type of values associated with the suggestions.
   * @param <E> The element type used for suggestions.
   * @param <O> The type of the option element within the store.
   * @param optionMapper A function to map items to options.
   * @return A new {@code LazyOrderedSuggestionsStore} instance with the provided option mapper.
   */
  public static <T, E extends IsElement<?>, O extends Option<T, E, O>>
      LazyOrderedSuggestionsStore<T, E, O> create(Function<T, Optional<O>> optionMapper) {
    return new LazyOrderedSuggestionsStore<>(optionMapper);
  }

  /**
   * Creates a new {@code LazyOrderedSuggestionsStore} instance with an option mapper and initial
   * items.
   *
   * @param <T> The type of values associated with the suggestions.
   * @param <E> The element type used for suggestions.
   * @param <O> The type of the option element within the store.
   * @param optionMapper A function to map items to options.
   * @param items The initial items.
   * @return A new {@code LazyOrderedSuggestionsStore} instance with the provided option mapper and
   *     items.
   */
  public static <T, E extends IsElement<?>, O extends Option<T, E, O>>
      LazyOrderedSuggestionsStore<T, E, O> create(
          Function<T, Optional<O>> optionMapper, Collection<T> items) {
    LazyOrderedSuggestionsStore<T, E, O> store = create(optionMapper);
    store.addItem(items);
    return store;
  }

  /**
   * Creates a new {@code LazyOrderedSuggestionsStore} instance with an option mapper and an array
   * of items.
   *
   * @param <T> The type of values associated with the suggestions.
   * @param <E> The element type used for suggestions.
   * @param <O> The type of the option element within the store.
   * @param optionMapper A function to map items to options.
   * @param items The array of items to add to the store.
   * @return A new {@code LazyOrderedSuggestionsStore} instance with the provided option mapper and
   *     items.
   */
  @SafeVarargs
  public static <T, E extends IsElement<?>, O extends Option<T, E, O>>
      LazyOrderedSuggestionsStore<T, E, O> create(
          Function<T, Optional<O>> optionMapper, T... items) {
    return create(optionMapper, java.util.Arrays.asList(items));
  }

  /** {@inheritDoc} */
  @Override
  protected LazyOrderedSuggestionsStore<T, E, O> getThis() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  protected Collection<O> getSuggestionsCollection() {
    List<O> all = new ArrayList<>();
    for (T item : items) {
      Optional<O> option = getOrCreateOption(item);
      if (option.isPresent()) {
        all.add(option.get());
      }
    }
    all.sort(Comparator.comparing(Option::getKey));
    return all;
  }

  /** {@inheritDoc} */
  @Override
  public void filter(String searchValue, SuggestionsHandler<T, E, O> suggestionsHandler) {
    List<O> filteredSuggestions = new ArrayList<>();
    for (T item : items) {
      getOrCreateOption(item)
          .ifPresent(
              suggestion -> {
                if (filterItem(searchValue, suggestion)) {
                  filteredSuggestions.add(suggestion);
                }
              });
    }
    filteredSuggestions.sort(Comparator.comparing(Option::getKey));
    suggestionsHandler.onSuggestionsReady(filteredSuggestions);
  }

  /** {@inheritDoc} */
  @Override
  public void find(T searchValue, Consumer<O> handler) {
    if (isNull(searchValue)) {
      handler.accept(null);
      return;
    }
    for (T item : items) {
      if (Objects.equals(item, searchValue)) {
        getOrCreateOption(item).ifPresent(handler::accept);
        return;
      }
    }
    handler.accept(null);
  }

  /** {@inheritDoc} */
  @Override
  public Optional<O> findOptionByKey(String key) {
    for (T item : items) {
      Optional<O> option = getOrCreateOption(item);
      if (option.isPresent() && Objects.equals(key, option.get().getKey())) {
        return option;
      }
    }
    return Optional.empty();
  }

  /** {@inheritDoc} */
  @Override
  public LazyOrderedSuggestionsStore<T, E, O> addItem(T item) {
    items.add(item);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  protected void internalAddSuggestion(O suggestion) {
    items.add(suggestion.getValue());
  }

  /** {@inheritDoc} */
  @Override
  protected void internalRemoveOption(O option) {
    items.remove(option.getValue());
  }

  /** {@inheritDoc} */
  @Override
  protected void internalRemoveAllOptions() {
    items.clear();
  }

  /**
   * Retrieves a map containing all the suggestions in the store, where each suggestion is
   * associated with its unique key. This method will trigger mapping of all items.
   *
   * @return A map of suggestions with their keys.
   */
  public Map<String, O> getSuggestions() {
    Map<String, O> map = new TreeMap<>();
    for (T item : items) {
      getOrCreateOption(item).ifPresent(o -> map.put(o.getKey(), o));
    }
    return map;
  }

  /** @return The set of items in the store. */
  public Set<T> getItems() {
    return items;
  }
}
