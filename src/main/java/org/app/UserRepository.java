package org.app;

import java.util.Optional;

public interface UserRepository {
    Optional<User> create(User user);
    Optional<User> getById(Long id);
    Optional<User> update(User user);
}
