package com.dreammusic.repository;

import com.dreammusic.entity.DreamRecord;
import com.dreammusic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DreamRecordRepository extends JpaRepository<DreamRecord, Long> {
    List<DreamRecord> findByUserOrderByCreatedAtDesc(User user);
    List<DreamRecord> findByUserIdOrderByCreatedAtDesc(Long userId);
}
