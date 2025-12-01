package org.msa.skillforge_backend.user.repository;

import org.msa.skillforge_backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByStudentId(String studentId);

}
