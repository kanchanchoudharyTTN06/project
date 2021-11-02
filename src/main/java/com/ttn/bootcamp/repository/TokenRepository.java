
package com.ttn.bootcamp.repository;

import com.ttn.bootcamp.domains.User.User;
import com.ttn.bootcamp.token.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<AuthToken, Integer> {

    Optional<AuthToken> findByToken(String confirmationToken);

    Optional<AuthToken> findTokenByUserId(long userId);

    void deleteByUser(User user);
}

