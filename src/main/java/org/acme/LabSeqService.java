package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.cache.CacheResult;
import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class LabSeqService {

    // Use a ConcurrentHashMap for caching intermediate values since quarkus @CacheResult 
    // might have too much overhead for recursive calls and BigInteger limits Stack memory.
    // Iterative approach is best for large N like 100000 to prevent StackOverflow.
    
    private final Map<Integer, BigInteger> cache = new ConcurrentHashMap<>();

    public LabSeqService() {
        // Initialize base cases
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

        // Return from cache if already calculated
        if (cache.containsKey(n)) {
            return cache.get(n);
        }

        // To avoid StackOverflowError for large N (e.g., 100000), 
        // calculate iteratively from the lowest missing value up to N.
        
        // Find the starting point to compute iteratively
        int maxComputed = 3;
        // In a real scenario we'd track max computed explicitly, but finding missing is easy:
        // iterate from 4 to N.
        
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
