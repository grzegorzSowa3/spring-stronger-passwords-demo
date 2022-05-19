package pl.recompiled.springstrongerpasswordsdemo.password.validation.passwordlist;

import org.springframework.data.jpa.repository.JpaRepository;

interface FrequentPasswordRepository extends JpaRepository<FrequentPassword, String> {
}
