package pl.recompiled.springstrongerpasswordsdemo.token;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Optional;
import java.util.UUID;

import static java.util.concurrent.TimeUnit.SECONDS;

@Component
@RequiredArgsConstructor
class SecureRandomAlphanumericOneTimeTokenProvider implements OneTimeTokenProvider {

    private final SecureRandom random = new SecureRandom();
    private final TemporalAmount tokenLifespan = Duration.ofMinutes(5);

    private final OneTimeTokenRepository repository;

    @Override
    public String generate(UUID userId) {
        final Integer tokenLength = 24;
        OneTimeToken token = OneTimeToken.random(this.random, userId, tokenLength);
        return repository.save(token).getToken();
    }

    @Override
    public Optional<UUID> use(String token) {
        try {
            Optional<OneTimeToken> oneTimeToken = repository.findById(token);
            oneTimeToken.ifPresent(repository::delete);
            return oneTimeToken.map(it -> {
                if (isValid(it)) {
                    return it.getUserId();
                } else {
                    return null;
                }
            });
        } finally {
            repository.deleteById(token);
        }
    }

    private Boolean isValid(OneTimeToken token) {
        return token.getCreatedAt().plus(tokenLifespan).isBefore(Instant.now());
    }

    @Scheduled(fixedRate = 30, timeUnit = SECONDS)
    private void cleanOutdatedTokens() {
        repository.deleteByCreatedAtBefore(Instant.now().minus(tokenLifespan));
    }
}
