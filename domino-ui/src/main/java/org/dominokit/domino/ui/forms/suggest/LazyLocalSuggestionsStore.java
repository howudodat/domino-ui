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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import org.dominokit.domino.ui.IsElement;

/**
 * A local suggestions store that maps items to options lazily.
 *
 * @param <T> The type of data associated with the suggestion options.
 * @param <E> The type of UI element that represents the suggestion options.
 * @param <O> The type of suggestion options.
 */
public class LazyLocalSuggestionsStore<T, E extends IsElement<?>, O extends Option<T, E, O>>
    extends AbstractLazySuggestionsStore<T, E, O, LazyLocalSuggestionsStore<T, E, O>> {

  private final List<T> items = new ArrayList<>();

  /**
   * Constructs a {@code LazyLocalSuggestionsStore} with the provided option mapper.
   *
   * @param optionMapper The function to map items to options. Cannot be null.
   */
  public LazyLocalSuggestionsStore(Function<T, Optional<O>> optionMapper) {
    super(optionMapper);
  }

  /**
   * Creates a new {@code LazyLocalSuggestionsStore} with the provided option mapper function.
   *
   * @param <T> The type of data associated with the suggestion options.
   * @param <E> The type of UI element that represents the suggestion options.
   * @param <O> The type of suggestion options.
   * @param optionMapper The function to map items to options.
   * @return A new {@code LazyLocalSuggestionsStore} with the provided option mapper.
   */
  public static <T, E extends IsElement<?>, O extends Option<T, E, O>>
      LazyLocalSuggestionsStore<T, E, O> create(Function<T, Optional<O>> optionMapper) {
    return new LazyLocalSuggestionsStore<>(optionMapper);
  }

  /**
   * Creates a new {@code LazyLocalSuggestionsStore} with the provided option mapper function and
   * initial items.
   *
   * @param <T> The type of data associated with the suggestion options.
   * @param <E> The type of UI element that represents the suggestion options.
   * @param <O> The type of suggestion options.
   * @param optionMapper The function to map items to options.
   * @param items The initial items.
   * @return A new {@code LazyLocalSuggestionsStore} with the provided option mapper and initial
   *     items.
   */
  public static <T, E extends IsElement<?>, O extends Option<T, E, O>>
      LazyLocalSuggestionsStore<T, E, O> create(
          Function<T, Optional<O>> optionMapper, Collection<T> items) {
    LazyLocalSuggestionsStore<T, E, O> store = create(optionMapper);
    store.addItem(items);
    return store;
  }

  /**
   * Creates a new {@code LazyLocalSuggestionsStore} with the provided option mapper function and
   * items.
   *
   * @param <T> The type of data associated with the suggestion options.
   * @param <E> The type of UI element that represents the suggestion options.
   * @param <O> The type of suggestion options.
   * @param optionMapper The function to map items to options.
   * @param items The initial items.
   * @return A new {@code LazyLocalSuggestionsStore} with the provided option mapper and items.
   */
  @SafeVarargs
  public static <T, E extends IsElement<?>, O extends Option<T, E, O>>
      LazyLocalSuggestionsStore<T, E, O> create(Function<T, Optional<O>> optionMapper, T... items) {
    return create(optionMapper, java.util.Arrays.asList(items));
  }

  /** {@inheritDoc} */
  @Override
  protected LazyLocalSuggestionsStore<T, E, O> getThis() {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  protected Collection<O> getSuggestionsCollection() {
    List<O> suggestions = new ArrayList<>();
    for (T item : items) {
      Optional<O> option = getOrCreateOption(item);
      if (option.isPresent()) {
        suggestions.add(option.get());
      }
    }
    return suggestions;
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
  public LazyLocalSuggestionsStore<T, E, O> addItem(T item) {
    items.add(item);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  protected void internalAddSuggestion(O suggestion) {
    if (!items.contains(suggestion.getValue())) {
      items.add(suggestion.getValue());
    }
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
   * @return The list of items in the store.
   */
  public List<T> getItems() {
    return items;
  }
}
