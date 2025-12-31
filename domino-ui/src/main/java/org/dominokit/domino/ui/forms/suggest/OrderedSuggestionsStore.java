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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;
import org.dominokit.domino.ui.IsElement;

/**
 * A suggestions store implementation that maintains ordered suggestions using a map structure. This
 * class allows you to manage and filter suggestions based on search criteria.
 *
 * @param <T> The type of values associated with the suggestions.
 * @param <E> The element type used for suggestions.
 * @param <O> The type of the option element within the store.
 */
public class OrderedSuggestionsStore<T, E extends IsElement<?>, O extends Option<T, E, O>>
    extends AbstractSuggestionsStore<T, E, O, OrderedSuggestionsStore<T, E, O>> {

  private final Map<String, O> suggestions = new TreeMap<>();

  /** Creates an empty OrderedSuggestionsStore. */
  public OrderedSuggestionsStore() {
    this(new ArrayList<>());
  }

  /**
   * Creates an OrderedSuggestionsStore with the specified initial suggestions.
   *
   * @param suggestions The initial collection of suggestions to add to the store.
   */
  public OrderedSuggestionsStore(Collection<O> suggestions) {
    suggestions.forEach(s -> this.suggestions.put(s.getKey(), s));
  }

  /**
   * Creates a new OrderedSuggestionsStore instance.
   *
   * @return A new OrderedSuggestionsStore instance.
   */
  public static <T, E extends IsElement<?>, O extends Option<T, E, O>>
      OrderedSuggestionsStore<T, E, O> create() {
    return new OrderedSuggestionsStore<>();
  }

  /**
   * Creates a new OrderedSuggestionsStore instance with the specified suggestions.
   *
   * @param suggestions The initial collection of suggestions to add to the store.
   * @return A new OrderedSuggestionsStore instance with the provided suggestions.
   */
  public static <T, E extends IsElement<?>, O extends Option<T, E, O>>
      OrderedSuggestionsStore<T, E, O> create(List<O> suggestions) {
    return new OrderedSuggestionsStore<>(suggestions);
  }

  /**
   * Creates a new OrderedSuggestionsStore instance with an option mapper and a collection of items.
   *
   * @param optionMapper A function to map items to options.
   * @param items The collection of items to add to the store.
   * @return A new OrderedSuggestionsStore instance with the provided option mapper and items.
   */
  public static <T, E extends IsElement<?>, O extends Option<T, E, O>>
      OrderedSuggestionsStore<T, E, O> create(
          Function<T, Optional<O>> optionMapper, Collection<T> items) {
    OrderedSuggestionsStore<T, E, O> store = create(optionMapper);
    store.addItem(items);
    return store;
  }

  /**
   * Creates a new OrderedSuggestionsStore instance with an option mapper and an array of items.
   *
   * @param optionMapper A function to map items to options.
   * @param items The array of items to add to the store.
   * @return A new OrderedSuggestionsStore instance with the provided option mapper and items.
   */
  public static <T, E extends IsElement<?>, O extends Option<T, E, O>>
      OrderedSuggestionsStore<T, E, O> create(Function<T, Optional<O>> optionMapper, T... items) {
    return create(optionMapper, Arrays.asList(items));
  }

  /**
   * Creates a new OrderedSuggestionsStore instance with an option mapper.
   *
   * @param optionMapper A function to map items to options.
   * @return A new OrderedSuggestionsStore instance with the provided option mapper.
   */
  public static <T, E extends IsElement<?>, O extends Option<T, E, O>>
      OrderedSuggestionsStore<T, E, O> create(Function<T, Optional<O>> optionMapper) {
    OrderedSuggestionsStore<T, E, O> store = new OrderedSuggestionsStore<>();
    store.setOptionMapper(optionMapper);
    return store;
  }

  @Override
  protected OrderedSuggestionsStore<T, E, O> getThis() {
    return this;
  }

  @Override
  protected Collection<O> getSuggestionsCollection() {
    return suggestions.values();
  }

  /**
   * Adds a single suggestion to the store. The suggestion is associated with a unique key.
   *
   * @param suggestion The suggestion to add.
   * @return This OrderedSuggestionsStore instance with the added suggestion.
   */
  @Override
  public OrderedSuggestionsStore<T, E, O> addSuggestion(O suggestion) {
    suggestions.put(suggestion.getKey(), suggestion);
    return this;
  }

  /**
   * Removes a specific option from the store based on its unique key.
   *
   * @param option The option to remove.
   * @return This OrderedSuggestionsStore instance with the specified option removed.
   */
  @Override
  public OrderedSuggestionsStore<T, E, O> removeOption(O option) {
    findOption(option).ifPresent(found -> suggestions.remove(found.getKey()));
    return this;
  }

  /**
   * Finds an option in the store by its unique key.
   *
   * @param key The unique key of the option to find.
   * @return An Optional containing the found option, or empty if not found.
   */
  @Override
  public Optional<O> findOptionByKey(String key) {
    return Optional.ofNullable(suggestions.get(key));
  }

  /**
   * Sets the suggestions in the store to the provided collection of suggestions. This operation
   * replaces all existing suggestions in the store.
   *
   * @param suggestions The collection of suggestions to set.
   * @return This OrderedSuggestionsStore instance with the updated suggestions.
   */
  public OrderedSuggestionsStore<T, E, O> setSuggestions(Collection<O> suggestions) {
    this.suggestions.clear();
    addSuggestions(suggestions);
    return this;
  }

  /**
   * Retrieves a map containing all the suggestions in the store, where each suggestion is
   * associated with its unique key.
   *
   * @return A map of suggestions with their keys.
   */
  public Map<String, O> getSuggestions() {
    return suggestions;
  }
}
