package com.example.apiWithDb.repository;

import com.example.apiWithDb.entities.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendaceRepository extends JpaRepository<AttendanceRecord, Long> {
    List<AttendanceRecord> findAllByEmployeeId(Long employeeId);

    @Query("SELECT a FROM AttendanceRecord a WHERE a.employee.department.id = :departmentId AND a.recordDate = :attendanceDate")
    List<AttendanceRecord> findAttendanceRecordsByDepartmentIdAndDate(@Param("departmentId") Long departmentId,
                                                                      @Param("attendanceDate") LocalDate attendanceDate);

    @Query("SELECT ar FROM AttendanceRecord ar WHERE ar.recordDate = ?1 AND ar.employee.id = ?2")
    Optional<AttendanceRecord> findByDateAndEmployeeId(LocalDate date, Long employeeId);

}

