package pl.recompiled.springstrongerpasswordsdemo.token;

import java.util.Optional;
import java.util.UUID;

public interface OneTimeTokenProvider {

    String generate(UUID userId);
    Optional<UUID> use(String token); //returns user id if token was valid, deletes token
}
