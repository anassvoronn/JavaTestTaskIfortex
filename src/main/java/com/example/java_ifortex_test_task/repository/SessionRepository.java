package com.example.java_ifortex_test_task.repository;

import com.example.java_ifortex_test_task.entity.DeviceType;
import com.example.java_ifortex_test_task.entity.Session;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    @Query(value = "SELECT * " +
            "FROM sessions " +
            "WHERE device_type = :#{#deviceType.getCode()} " +
            "ORDER BY started_at_utc ASC LIMIT 1", nativeQuery = true)
    Session getFirstDesktopSession(DeviceType deviceType);

    @Query(value = "SELECT s.* " +
            "FROM sessions s " +
            "LEFT JOIN users u ON s.user_id = u.id " +
            "WHERE u.deleted = false AND s.ended_at_utc IS NOT NULL AND s.ended_at_utc < :endDate " +
            "ORDER BY s.started_at_utc DESC", nativeQuery = true)
    List<Session> getSessionsFromActiveUsersEndedBefore2025(LocalDateTime endDate);

    @Converter(autoApply = true)
    class DeviceTypeConverter implements AttributeConverter<DeviceType, Integer> {
        //Не используется в текущем коде
        @Override
        public Integer convertToDatabaseColumn(DeviceType attribute) {
            return attribute.getCode();
        }

        @Override
        public DeviceType convertToEntityAttribute(Integer dbData) {
            return DeviceType.fromCode(dbData);
        }
    }
}