import com.example.apiWithDb.dto.AttendanceDto;
import com.example.apiWithDb.entities.AttendanceRecord;
import com.example.apiWithDb.entities.Department;
import com.example.apiWithDb.entities.Employee;
import com.example.apiWithDb.entities.User;
import com.example.apiWithDb.exception.AppException;
import com.example.apiWithDb.mappers.AttendanceMapper;
import com.example.apiWithDb.repository.AttendaceRepository;
import com.example.apiWithDb.repository.DepartmentRepository;
import com.example.apiWithDb.repository.employeeRepository;
import com.example.apiWithDb.service.UserService;
import com.example.apiWithDb.service.impl.AttendanceServiceimpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AttendanceServiceimplTest {

    @Mock
    private AttendaceRepository attendaceRepository;

    @Mock
    private employeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private UserService userService;

    @Mock
    private AttendanceMapper attendanceMapper;

    @InjectMocks
    private AttendanceServiceimpl attendanceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateAttendanceRecord_NullDto() {
        Authentication authentication = mock(Authentication.class);
        assertThrows(AppException.class, () -> attendanceService.createAttendanceRecord(null, authentication));
    }

    @Test
    void testCreateAttendanceRecord_NullRecordDate() {
        AttendanceDto attendanceDto = new AttendanceDto(); // Создаем объект без установки recordDate
        Authentication authentication = mock(Authentication.class);
        assertThrows(AppException.class, () -> attendanceService.createAttendanceRecord(attendanceDto, authentication));
    }


//    @Test
//    void testDeleteAttendanceRecord_Success() {
//        LocalDate attendanceDate = LocalDate.now();
//        Authentication authentication = mock(Authentication.class);
//        when(userService.findUserByToken(authentication)).thenReturn(new User("username"));
//
//        Employee employee = new Employee();
//        when(employeeRepository.findByLogin("username")).thenReturn(Optional.of(employee));
//
//        when(attendaceRepository.findByDateAndEmployeeId(attendanceDate, employee.getId())).thenReturn(Optional.of(new AttendanceRecord()));
//
//        assertEquals("Success", attendanceService.deleteAttendanceRecord(attendanceDate, authentication));
//        verify(attendaceRepository, times(1)).delete(any(AttendanceRecord.class));
//    }

//    @Test
//    void testGetAttendanceRecord_Success() {
//        LocalDate attendanceDate = LocalDate.now();
//        Authentication authentication = mock(Authentication.class);
//        when(userService.findUserByToken(authentication)).thenReturn(new User("username"));
//
//        Employee employee = new Employee();
//        when(employeeRepository.findByLogin("username")).thenReturn(Optional.of(employee));
//
//        AttendanceRecord attendanceRecord = new AttendanceRecord();
//        when(attendaceRepository.findByDateAndEmployeeId(attendanceDate, employee.getId())).thenReturn(Optional.of(attendanceRecord));
//
//        assertEquals(attendanceRecord, attendanceService.getAttendanceRecord(attendanceDate, authentication));
//    }
//
//    @Test
//    void testGetAllDepartmentAttendanceRecords_Success() {
//        Long departmentId = 1L;
//        LocalDate attendanceDate = LocalDate.now();
//
//        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(new Department()));
//        when(attendaceRepository.findAttendanceRecordsByDepartmentIdAndDate(departmentId, attendanceDate)).thenReturn(List.of(new AttendanceRecord()));
//
//        assertEquals(1, attendanceService.getAllDepartmentAttendanceRecords(departmentId, attendanceDate).size());
//    }
}
