package com.example.apiWithDb.mappers;

import com.example.apiWithDb.dto.AttendanceRequestDto;
import com.example.apiWithDb.entities.AttendanceRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class AttendanceMapperTest {

    private AttendanceMapper attendanceMapper;

    @BeforeEach
    void setUp() {
        attendanceMapper = Mappers.getMapper(AttendanceMapper.class);
    }

    @Test
    void testToAttendanceRecord() {
        AttendanceRequestDto attendanceDto = AttendanceRequestDto.builder()
                .id(1L)
                .recordDate(LocalDate.of(2023, 5, 27))
                .dailyTimeWorked(LocalTime.of(8, 0))
                .build();

        AttendanceRecord attendanceRecord = attendanceMapper.toAttendanceRecord(attendanceDto);

        assertNotNull(attendanceRecord);
        assertEquals(attendanceDto.getId(), attendanceRecord.getId());
        assertEquals(attendanceDto.getRecordDate(), attendanceRecord.getRecordDate());
        assertEquals(attendanceDto.getDailyTimeWorked(), attendanceRecord.getDailyTimeWorked());
    }
}