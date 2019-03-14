package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Repository<ID, T extends RepositoryEntry<ID>> {
    final protected Map<ID, T> entries;

    public Repository() {
        entries = Collections.synchronizedMap(new LinkedHashMap<>());
    }

    public Optional<T> findById(ID id) {
        return Optional.ofNullable(entries.get(id));
    }

    public List<T> findAll() {
        return new ArrayList<>(entries.values());
    }

    public T save(T entry) {
        return entries.put(entry.getId(), entry);
    }
}
