package ca.concordia.b4dis.utils;

/**
 * Void input - T output Lambda method.
 */
public interface Provider<T> {
    T invoke();
}