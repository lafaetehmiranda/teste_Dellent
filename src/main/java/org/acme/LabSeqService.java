package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.cache.CacheResult;
import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class LabSeqService {

    private final Map<Integer, BigInteger> cache = new ConcurrentHashMap<>();

    public LabSeqService() {
        cache.put(0, BigInteger.ZERO);
        cache.put(1, BigInteger.ONE);
        cache.put(2, BigInteger.ZERO);
        cache.put(3, BigInteger.ONE);
    }

    @CacheResult(cacheName = "labseq-cache")
    public BigInteger calculate(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Index must be non-negative.");
        }

        if (cache.containsKey(n)) {
            return cache.get(n);
        }

        int maxComputed = 3;
        
        for (int i = 4; i <= n; i++) {
            if (!cache.containsKey(i)) {
                BigInteger l_n_4 = cache.get(i - 4);
                BigInteger l_n_3 = cache.get(i - 3);
                cache.put(i, l_n_4.add(l_n_3));
            }
        }

        return cache.get(n);
    }
}
