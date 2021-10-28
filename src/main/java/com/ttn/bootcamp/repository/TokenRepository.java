
package com.ttn.bootcamp.repository;

import com.ttn.bootcamp.token.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<AuthToken, Integer> {

    AuthToken findByToken(String confirmationToken);

    AuthToken findTokenByUserId(int id);
}

