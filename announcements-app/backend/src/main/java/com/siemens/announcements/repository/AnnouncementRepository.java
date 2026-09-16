package com.siemens.announcements.repository;
import com.siemens.announcements.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {}
