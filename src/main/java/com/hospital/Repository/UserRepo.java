package com.hospital.Repository;

import com.hospital.Entity.Type.AuthProviderType;
import com.hospital.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserRepo extends JpaRepository<Users,Integer> {

    Users findByUsername(String username);

    Optional<Users> findByProviderIdAndProviderType(String providerId, AuthProviderType providerType);
}
