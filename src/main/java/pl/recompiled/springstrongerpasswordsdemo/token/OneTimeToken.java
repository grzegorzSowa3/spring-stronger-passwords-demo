package pl.recompiled.springstrongerpasswordsdemo.token;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class OneTimeToken implements Persistable<String> {

    @Id
    private String token;
    @Transient
    private boolean isNew;
    private UUID userId;
    private Instant createdAt;

    static OneTimeToken random(SecureRandom random, UUID userId, Integer length) {
        OneTimeToken token = new OneTimeToken();
        token.token = RandomStringUtils.random(length, 0, 0, true, true, null, random);
        token.userId = userId;
        token.isNew = true;
        token.createdAt = Instant.now();
        return token;
    }

    @Override
    public String getId() {
        return token;
    }
}
