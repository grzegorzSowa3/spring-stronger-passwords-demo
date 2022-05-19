package pl.recompiled.springstrongerpasswordsdemo.password.validation.passwordlist;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class FrequentPassword implements Persistable<String> {

    @Id
    private String value;
    @Transient
    private boolean isNew;

    static FrequentPassword of(String value) {
        final FrequentPassword password = new FrequentPassword();
        password.isNew = true;
        password.value = value;
        return password;
    }

    @Override
    public String getId() {
        return value;
    }
}
