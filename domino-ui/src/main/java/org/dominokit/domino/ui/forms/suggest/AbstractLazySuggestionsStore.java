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
import static java.util.Objects.nonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import org.dominokit.domino.ui.IsElement;

/**
 * An abstract base class for lazy suggestions stores. Lazy stores do not map items to options
 * immediately when added. Instead, they map them on demand when filtering or finding suggestions.
 *
 * @param <T> The type of data associated with the suggestion options.
 * @param <E> The type of UI element that represents the suggestion options.
 * @param <O> The type of suggestion options.
 * @param <S> The type of the store itself, for fluent API.
 */
public abstract class AbstractLazySuggestionsStore<
        T,
        E extends IsElement<?>,
        O extends Option<T, E, O>,
        S extends AbstractLazySuggestionsStore<T, E, O, S>>
    extends AbstractSuggestionsStore<T, E, O, S> {

  protected final Map<T, O> cache = new HashMap<>();

  /**
   * Constructs an {@code AbstractLazySuggestionsStore} with the provided option mapper.
   *
   * @param optionMapper The function to map items to options. Cannot be null.
   * @throws IllegalArgumentException If the option mapper is null.
   */
  public AbstractLazySuggestionsStore(Function<T, Optional<O>> optionMapper) {
    if (isNull(optionMapper)) {
      throw new IllegalArgumentException("Option mapper cannot be null for lazy stores");
    }
    setOptionMapper(optionMapper);
  }

  /**
   * Gets or creates an option for the provided item using the option mapper.
   *
   * @param item The item to map.
   * @return An optional containing the mapped option, or empty if mapping failed.
   */
  protected Optional<O> getOrCreateOption(T item) {
    if (cache.containsKey(item)) {
      return Optional.ofNullable(cache.get(item));
    }
    Optional<O> option = optionMapper.apply(item);
    option.ifPresent(o -> cache.put(item, o));
    return option;
  }

  /** {@inheritDoc} */
  @Override
  public S addSuggestion(O suggestion) {
    if (nonNull(suggestion)) {
      cache.put(suggestion.getValue(), suggestion);
      internalAddSuggestion(suggestion);
    }
    return getThis();
  }

  /**
   * Internal method to add a suggestion to the underlying storage.
   *
   * @param suggestion The suggestion to add.
   */
  protected abstract void internalAddSuggestion(O suggestion);

  /** {@inheritDoc} */
  @Override
  public S removeOption(O option) {
    if (nonNull(option)) {
      cache.remove(option.getValue());
      internalRemoveOption(option);
    }
    return getThis();
  }

  /**
   * Internal method to remove an option from the underlying storage.
   *
   * @param option The option to remove.
   */
  protected abstract void internalRemoveOption(O option);

  /** {@inheritDoc} */
  @Override
  public S removeAllOptions() {
    cache.clear();
    internalRemoveAllOptions();
    return getThis();
  }

  /** Internal method to remove all options/items from the underlying storage. */
  protected abstract void internalRemoveAllOptions();
}
