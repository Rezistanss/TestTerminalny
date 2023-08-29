package zadanie3;

import zadanie3.Exceptions.NullElementException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

class MinMaxService {
    public static <T extends Comparable<T>> MinMax<T> getMinMax(List<T> elements) {
        if (elements.isEmpty()) {
            throw new IllegalArgumentException("List cannot be null or empty.");
        }
        return new MinMax<>(getMinValue(elements), getMaxValue(elements));
    }

    private static <T extends Comparable<T>> T getMinValue(List<T> elements) {
        return Optional.of(elements)
                .map(list -> list.stream()
                        .filter(Objects::nonNull)
                        .min(Comparable::compareTo)
                        .orElseThrow(() -> new NullElementException("Element cannot be null")))
                .orElseThrow(() -> new NullElementException("Element cannot be null"));
    }

    private static <T extends Comparable<T>> T getMaxValue(List<T> elements) {
        return Optional.of(elements)
                .map(list -> list.stream()
                        .filter(Objects::nonNull)
                        .max(Comparable::compareTo)
                        .orElseThrow(() -> new NullElementException("Element cannot be null")))
                .orElseThrow(() -> new NullElementException("Element cannot be null"));
    }
}
