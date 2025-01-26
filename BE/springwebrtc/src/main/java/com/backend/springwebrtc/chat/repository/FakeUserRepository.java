package com.backend.springwebrtc.chat.repository;

import com.backend.springwebrtc.chat.domain.entity.FakeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FakeUserRepository extends JpaRepository<FakeUser, Long> {
    Optional<FakeUser> findByNickname(String nickname);
}
