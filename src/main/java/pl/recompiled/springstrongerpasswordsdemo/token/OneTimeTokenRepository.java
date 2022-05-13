package pl.recompiled.springstrongerpasswordsdemo.token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;

interface OneTimeTokenRepository extends JpaRepository<OneTimeToken, String> {

    void deleteByCreatedAtBefore(Instant expiry);
}
