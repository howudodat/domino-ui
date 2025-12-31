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

import java.util.*;
import java.util.function.Function;
import org.dominokit.domino.ui.IsElement;

/**
 * A local suggestions store that can be used for managing and filtering suggestion options.
 *
 * @param <T> The type of data associated with the suggestion options.
 * @param <E> The type of UI element that represents the suggestion options.
 * @param <O> The type of suggestion options.
 */
public class LocalSuggestionsStore<T, E extends IsElement<?>, O extends Option<T, E, O>>
    extends AbstractSuggestionsStore<T, E, O, LocalSuggestionsStore<T, E, O>> {

  private List<O> suggestions;

  /** Creates an empty {@code LocalSuggestionsStore}. */
  public LocalSuggestionsStore() {
    this(new ArrayList<>());
  }

  /**
   * Creates a {@code LocalSuggestionsStore} with the provided initial suggestions.
   *
   * @param suggestions The initial list of suggestions.
   */
  public LocalSuggestionsStore(List<O> suggestions) {
    this.suggestions = suggestions;
  }

  /**
   * Creates a new empty {@code LocalSuggestionsStore}.
   *
   * @param <T> The type of data associated with the suggestion options.
   * @param <E> The type of UI element that represents the suggestion options.
   * @param <O> The type of suggestion options.
   * @return A new empty {@code LocalSuggestionsStore}.
   */
  public static <T, E extends IsElement<?>, O extends Option<T, E, O>>
      LocalSuggestionsStore<T, E, O> create() {
    return new LocalSuggestionsStore<>();
  }

  /**
   * Creates a new {@code LocalSuggestionsStore} with the provided initial suggestions.
   *
   * @param <T> The type of data associated with the suggestion options.
   * @param <E> The type of UI element that represents the suggestion options.
   * @param <O> The type of suggestion options.
   * @param suggestions The initial list of suggestions.
   * @return A new {@code LocalSuggestionsStore} with the provided initial suggestions.
   */
  public static <T, E extends IsElement<?>, O extends Option<T, E, O>>
      LocalSuggestionsStore<T, E, O> create(List<O> suggestions) {
    return new LocalSuggestionsStore<>(suggestions);
  }

  /**
   * Creates a new {@code LocalSuggestionsStore} with the provided option mapper function and
   * initial items.
   *
   * @param <T> The type of data associated with the suggestion options.
   * @param <E> The type of UI element that represents the suggestion options.
   * @param <O> The type of suggestion options.
   * @param optionMapper The function to map items to options.
   * @param items The initial items.
   * @return A new {@code LocalSuggestionsStore} with the provided option mapper and initial items.
   */
  public static <T, E extends IsElement<?>, O extends Option<T, E, O>>
      LocalSuggestionsStore<T, E, O> create(
          Function<T, Optional<O>> optionMapper, Collection<T> items) {
    LocalSuggestionsStore<T, E, O> store = create(optionMapper);
    store.addItem(items);
    return store;
  }

  /**
   * Creates a new {@code LocalSuggestionsStore} with the provided option mapper function and items.
   *
   * @param <T> The type of data associated with the suggestion options.
   * @param <E> The type of UI element that represents the suggestion options.
   * @param <O> The type of suggestion options.
   * @param optionMapper The function to map items to options.
   * @param items The initial items.
   * @return A new {@code LocalSuggestionsStore} with the provided option mapper and items.
   */
  public static <T, E extends IsElement<?>, O extends Option<T, E, O>>
      LocalSuggestionsStore<T, E, O> create(Function<T, Optional<O>> optionMapper, T... items) {
    return create(optionMapper, Arrays.asList(items));
  }

  /**
   * Creates a new {@code LocalSuggestionsStore} with the provided option mapper function.
   *
   * @param <T> The type of data associated with the suggestion options.
   * @param <E> The type of UI element that represents the suggestion options.
   * @param <O> The type of suggestion options.
   * @param optionMapper The function to map items to options.
   * @return A new {@code LocalSuggestionsStore} with the provided option mapper.
   */
  public static <T, E extends IsElement<?>, O extends Option<T, E, O>>
      LocalSuggestionsStore<T, E, O> create(Function<T, Optional<O>> optionMapper) {
    LocalSuggestionsStore<T, E, O> store = new LocalSuggestionsStore<>();
    store.setOptionMapper(optionMapper);
    return store;
  }

  @Override
  protected LocalSuggestionsStore<T, E, O> getThis() {
    return this;
  }

  @Override
  protected Collection<O> getSuggestionsCollection() {
    return suggestions;
  }

  /**
   * Adds a suggestion option to the store.
   *
   * @param suggestion The suggestion option to add.
   * @return This {@code LocalSuggestionsStore} for method chaining.
   */
  @Override
  public LocalSuggestionsStore<T, E, O> addSuggestion(O suggestion) {
    suggestions.add(suggestion);
    return this;
  }

  /**
   * Adds a list of suggestion options to the store.
   *
   * @param suggestions The list of suggestion options to add.
   * @return This {@code LocalSuggestionsStore} for method chaining.
   */
  public LocalSuggestionsStore<T, E, O> addSuggestions(List<O> suggestions) {
    this.suggestions.addAll(suggestions);
    return this;
  }

  /**
   * Removes a suggestion option from the store.
   *
   * @param option The suggestion option to remove.
   * @return This {@code LocalSuggestionsStore} for method chaining.
   */
  @Override
  public LocalSuggestionsStore<T, E, O> removeOption(O option) {
    findOption(option).ifPresent(found -> suggestions.remove(found));
    return this;
  }

  /**
   * Finds a suggestion option by its key.
   *
   * @param key The key to search for.
   * @return An optional containing the suggestion option if found, otherwise an empty optional.
   */
  @Override
  public Optional<O> findOptionByKey(String key) {
    return suggestions.stream()
        .filter(menuItem -> Objects.equals(key, menuItem.getKey()))
        .findFirst();
  }

  /**
   * Finds a suggestion option by its index.
   *
   * @param index The index to search for.
   * @return An optional containing the suggestion option if found, otherwise an empty optional.
   */
  public Optional<O> findOptionByIndex(int index) {
    if (index < suggestions.size() && index >= 0) {
      return Optional.of(suggestions.get(index));
    }
    return Optional.empty();
  }

  /**
   * Sets the suggestions for this store.
   *
   * @param suggestions The list of suggestions to set.
   * @return This {@code LocalSuggestionsStore} for method chaining.
   */
  public LocalSuggestionsStore<T, E, O> setSuggestions(List<O> suggestions) {
    this.suggestions = new ArrayList<>(suggestions);
    return this;
  }

  /**
   * Gets the list of suggestions in this store.
   *
   * @return The list of suggestions.
   */
  public List<O> getSuggestions() {
    return suggestions;
  }
}
