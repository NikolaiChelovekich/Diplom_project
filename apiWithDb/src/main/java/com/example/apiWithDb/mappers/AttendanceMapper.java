package com.example.apiWithDb.mappers;

import com.example.apiWithDb.dto.AttendanceRequestDto;
import com.example.apiWithDb.dto.AttendanceResponseDto;
import com.example.apiWithDb.entities.AttendanceRecord;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    AttendanceRecord toAttendanceRecord(AttendanceRequestDto attendanceDto);
    AttendanceResponseDto toAttendanceResponseDto(AttendanceRecord attendanceRecord);
    List<AttendanceResponseDto> toAttendanceResponseDtoList(List<AttendanceRecord> attendanceRecord);

}