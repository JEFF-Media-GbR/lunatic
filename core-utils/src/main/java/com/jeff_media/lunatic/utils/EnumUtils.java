package com.jeff_media.lunatic.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Enum related methods
 */
public class EnumUtils {

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    private static final Map<Class<? extends Enum<?>>, Set<String>> ENUM_CACHE = new HashMap<>();
    private static final Map<Class<? extends Enum<?>>, List<? extends Enum<?>>> ENUM_ARRAY_CACHE = new HashMap<>();
    private static final Map<Class<? extends Enum<?>>, EnumMap<?, ?>> NEXT_ENUMS = new HashMap<>();

    /**
     * Gets a Collection of the given Enum constants by their names. Enum constants that aren't found will be
     * accepted by the given consumer. Case is ignored for Bukkit enums.
     *
     * @param enumClazz        the enum class
     * @param list             the list of enum names
     * @param collector        the collector to use
     * @param notFoundConsumer the consumer to use for not found enums
     * @param <E>              the enum type
     * @param <C>              the collection type
     * @return the collection of enums
     */
    public static <E extends Enum<E>, C extends Collection<E>> C getEnumsFromList(final Class<E> enumClazz,
                                                                                  final List<String> list,
                                                                                  final Collector<? super E, ?, C> collector,
                                                                                  final Consumer<String> notFoundConsumer) {
        return list.stream().map(entry -> {
            final Optional<E> result = getIfPresent(enumClazz,
                    enumClazz.getName().startsWith("org.bukkit") ? entry.toUpperCase(Locale.ROOT) : entry);
            if (result.isEmpty()) {
                notFoundConsumer.accept(entry);
                return null;
            }
            return result.get();
        }).filter(Objects::nonNull).collect(collector);
    }

    /**
     * Gets an {@link Optional} of a given Enum by its name
     *
     * @param enumClazz the enum class
     */
    public static <E extends Enum<E>> Optional<E> getIfPresent(final Class<E> enumClazz, final String value) {
        final Set<String> enumSet = ENUM_CACHE.computeIfAbsent(enumClazz, EnumUtils::toStringSet);
        return Optional.ofNullable(enumSet.contains(value) ? Enum.valueOf(enumClazz, value) : null);
    }

    /**
     * Gets an EnumSet of the given Enum constants by a list of regex patterns. Example:
     * <pre>
     * materials:
     * - "^((.+)_)*CHEST$" # matches CHEST, TRAPPED_CHEST, etc
     * - "^((.+)_)*SHULKER_BOX$" # matches SHULKER_BOX, RED_SHULKER_BOX, etc
     * - "^BARREL$" # matches only BARREL
     * </pre>
     *
     * @param enumClazz the enum class
     * @param list      the list of regex patterns
     * @param collector the collector to use
     * @param <E>       the enum type
     * @param <C>       the collection type
     * @return the collection of enums
     */
    public static <E extends Enum<E>, C extends Collection<E>> C getEnumsFromRegexList(final Class<E> enumClazz,
                                                                                       final List<String> list,
                                                                                       final Collector<? super E, ?, C> collector) {
        Set<String> enumz = toStringSet(enumClazz);
        return enumz.stream()
                .filter(entry -> list.stream().anyMatch(pattern -> Pattern.matches(pattern, entry)))
                .map(entry -> getIfPresent(enumClazz, entry).orElse(null))
                .filter(Objects::nonNull)
                .collect(collector);
    }

    /**
     * Gets a random value of the given Enum class. Unlike calling values() on an element instance,
     * or calling getEnumConstants() on an enum class, this will cache the delivered array and
     * doesn't have to create a new one everytime.
     *
     * @param enumClazz the enum class
     * @param <E>       the enum type
     * @return the random enum value
     */
    public static <E extends Enum<E>> E getRandomElement(final Class<E> enumClazz) {
        final List<E> values = getValues(enumClazz);
        return values.get(RANDOM.nextInt(values.size()));
    }

    /**
     * Returns all elements of the given enum class. Unlike calling values() on an element instance,
     * or calling getEnumConstants() on an enum class, this will cache the delivered array and
     * doesn't have to create a new one everytime.
     * The returned list is unmodifiable.
     *
     * @param enumClazz the enum class
     * @param <E>       the enum type
     * @return the list of enum values
     */
    @SuppressWarnings("unchecked")
    public static <E extends Enum<E>> List<E> getValues(final Class<E> enumClazz) {
        return (List<E>) ENUM_ARRAY_CACHE.computeIfAbsent(enumClazz,
                Enum -> Collections.unmodifiableList(Arrays.asList(enumClazz.getEnumConstants())));
    }

    /**
     * Gets the next element of the given enum class by its ordinal. If the given element is the last element
     * of the enum class, it will return null if wrapAround is false, otherwise it will return the first element.
     * The next element of each element is cached and does not require to call values() all the time.
     * If wrapAround is true, the method will never return null.
     * <p>
     * For example, if your enum class has three declared values A, B and C, then calling this method on A will
     * return B, calling it on B will return C, and calling it on C will return A if wrapAround is true, otherwise null.
     */
    @SuppressWarnings("unchecked")
    @Contract("_, true -> !null")
    public static <E extends Enum<E>> E getNextElement(
            @NotNull
            final E e, boolean wrapAround) {
        final Class<E> enumClazz = (Class<E>) e.getClass();
        if (!wrapAround && e.ordinal() == getValues(enumClazz).size() - 1) {
            return null;

        }
        final EnumMap<E, E> nextEnums =
                (EnumMap<E, E>) NEXT_ENUMS.computeIfAbsent(enumClazz, __ -> new EnumMap<E, E>(enumClazz));
        E next = nextEnums.get(e);
        if (next == null) {
            final int ordinal = e.ordinal();
            final List<E> values = getValues(enumClazz);
            next = values.get((ordinal + 1) % values.size());
            nextEnums.put(e, next);
        }
        return next;
    }

    private static Set<String> toStringSet(final Class<? extends Enum<?>> enumClazz) {
        return Arrays.stream(enumClazz.getEnumConstants()).map(Enum::toString).collect(Collectors.toSet());
    }

}


