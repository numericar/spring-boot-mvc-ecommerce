package com.shopme.users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shopme.commons.entities.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    
}
