package net.droth.strinder.core.mapper;

public interface Mapper<S, T> {
    T map(final S source);
}
