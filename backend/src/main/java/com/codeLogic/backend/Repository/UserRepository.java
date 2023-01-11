package com.codeLogic.backend.Repository;

import com.codeLogic.backend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
