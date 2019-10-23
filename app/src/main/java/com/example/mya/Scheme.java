package com.example.mya;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

public class Scheme {
    private final SecureRandom random;
    private final int n;
    private final int k;

    /**
     *
     * n numero de participantes
     * k numero minimo para recuperar
     */
    public Scheme(SecureRandom random, int n, int k) {
        this.random = random;
        checkArgument(k > 1, "K must be > 1");
        checkArgument(n >= k, "N must be >= K");
        checkArgument(n <= 255, "N must be <= 255");
        this.n = n;
        this.k = k;
    }

    /**
     *
     * split para generar los fragmentos
     *
     *
     */
    public Map<Integer, byte[]> split(byte[] secret) {
        // genera los valores
        final byte[][] values = new byte[n][secret.length];
        for (int i = 0; i < secret.length; i++) {
            final byte[] p = Herramientas.generate(random, k - 1, secret[i]);
            for (int x = 1; x <= n; x++) {
                values[x - 1][i] = Herramientas.eval(p, (byte) x);
            }
        }

        //
        final Map<Integer, byte[]> parts = new HashMap<>(n());
        for (int i = 0; i < values.length; i++) {
            parts.put(i + 1, values[i]);
        }
        return Collections.unmodifiableMap(parts);
    }


    public byte[] join(Map<Integer, byte[]> parts) {
        checkArgument(parts.size() > 0, "No parts provided");
        //final int[] lengths = parts.values().stream().mapToInt(v -> v.length).distinct().toArray();

        //checkArgument(lengths.length == 1, "Varying lengths of part values");
        //final byte[] secret = new byte[lengths[0]];
        final byte[] secret = new byte[16];
        for (int i = 0; i < secret.length; i++) {
            final byte[][] points = new byte[parts.size()][2];
            int j = 0;
            for (Map.Entry<Integer, byte[]> part : parts.entrySet()) {
                points[j][0] = part.getKey().byteValue();
                points[j][1] = part.getValue()[i];
                j++;
            }
            secret[i] = Herramientas.interpolate(points);
        }
        return secret;
    }

    /**
     * The number of parts the scheme will generate when splitting a secret.
     *
     * @return {@code N}
     */
    public int n() {
        return n;
    }

    /**
     * The number of parts the scheme will require to re-create a secret.
     *
     * @return {@code K}
     */
    public int k() {
        return k;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Scheme)) {
            return false;
        }
        final Scheme scheme = (Scheme) o;
        return n == scheme.n && k == scheme.k && Objects.equals(random, scheme.random);
    }

    @Override
    public int hashCode() {
        return Objects.hash(random, n, k);
    }
/*
    @Override
    public String toString() {
        return new StringJoiner(", ", Scheme.class.getSimpleName() + "[", "]")
                .add("random=" + random)
                .add("n=" + n)
                .add("k=" + k)
                .toString();
    }*/

    private static void checkArgument(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }
}
