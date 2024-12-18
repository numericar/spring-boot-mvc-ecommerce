package com.shopme.users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shopme.commons.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

}
