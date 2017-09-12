package com.foldik.split.persistence;

import com.foldik.split.persistence.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByName(String name);
}
