package com.hospital.Repository;

import com.hospital.Entity.Type.AuthProviderType;
import com.hospital.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByProviderIdAndProviderType(String providerId, AuthProviderType providerType);
    Optional<User> findByUsername(String username);

}