import com.example.apiWithDb.entities.Company;
import com.example.apiWithDb.entities.Department;
import com.example.apiWithDb.exception.AppException;
import com.example.apiWithDb.repository.CompanyRepository;
import com.example.apiWithDb.repository.DepartmentRepository;
import com.example.apiWithDb.service.impl.DepartmentServiceimpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private DepartmentServiceimpl departmentService;

    private Company company;
    private Department department;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        company = new Company();
        company.setId(1L);
        company.setCompName("Test Company");

        department = new Department();
        department.setId(1L);
        department.setName("Test Department");
        department.setCompany(company);
    }

    @Test
    void createDepartment_Success() {
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));

        String result = departmentService.createDepartment(department, 1L);

        assertEquals("Success", result);
        verify(departmentRepository, times(1)).save(department);
        assertEquals(company, department.getCompany());
    }

    @Test
    void createDepartment_CompanyNotFound() {
        when(companyRepository.findById(1L)).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> {
            departmentService.createDepartment(department, 1L);
        });

        assertEquals("Company not found", exception.getMessage());
         
        verify(departmentRepository, never()).save(any(Department.class));
    }

    @Test
    void updateDepartment_Success() {
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        department.setName("Updated Department");
        String result = departmentService.updateDepartment(department, 1L);

        assertEquals("Department updated successfully", result);
        verify(departmentRepository, times(1)).save(department);
        assertEquals(company, department.getCompany());
    }

    @Test
    void updateDepartment_CompanyNotFound() {
        when(companyRepository.findById(1L)).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> {
            departmentService.updateDepartment(department, 1L);
        });

        assertEquals("Company not found", exception.getMessage());
         
        verify(departmentRepository, never()).save(any(Department.class));
    }

    @Test
    void updateDepartment_DepartmentNotFound() {
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> {
            departmentService.updateDepartment(department, 1L);
        });

        assertEquals("Department not found", exception.getMessage());
         
        verify(departmentRepository, never()).save(any(Department.class));
    }

    @Test
    void deleteDepartment_Success() {
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        String result = departmentService.deleteDepartment(1L, 1L);

        assertEquals("Department deleted successfully", result);
        verify(departmentRepository, times(1)).delete(department);
    }

    @Test
    void deleteDepartment_CompanyNotFound() {
        when(companyRepository.findById(1L)).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> {
            departmentService.deleteDepartment(1L, 1L);
        });

        assertEquals("Company not found", exception.getMessage());
         
        verify(departmentRepository, never()).delete(any(Department.class));
    }

    @Test
    void deleteDepartment_DepartmentNotFound() {
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> {
            departmentService.deleteDepartment(1L, 1L);
        });

        assertEquals("Department not found", exception.getMessage());
         
        verify(departmentRepository, never()).delete(any(Department.class));
    }

    @Test
    void deleteDepartment_DepartmentNotBelongToCompany() {
        Company otherCompany = new Company();
        otherCompany.setId(2L);
        department.setCompany(otherCompany);

        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        AppException exception = assertThrows(AppException.class, () -> {
            departmentService.deleteDepartment(1L, 1L);
        });

        assertEquals("Department does not belong to the specified company", exception.getMessage());

        verify(departmentRepository, never()).delete(any(Department.class));
    }

    @Test
    void getDepartment_Success() {
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        when(departmentRepository.findByIdAndCompany(1L, company)).thenReturn(Optional.of(department));

        Department result = departmentService.getDepartment(1L, 1L);

        assertEquals(department, result);
    }

    @Test
    void getDepartment_CompanyNotFound() {
        when(companyRepository.findById(1L)).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> {
            departmentService.getDepartment(1L, 1L);
        });

        assertEquals("Company not found", exception.getMessage());
         
    }

    @Test
    void getDepartment_DepartmentNotFound() {
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        when(departmentRepository.findByIdAndCompany(1L, company)).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> {
            departmentService.getDepartment(1L, 1L);
        });

        assertEquals("Department not found", exception.getMessage());
         
    }

    @Test
    void getAllDepartments_Success() {
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        when(departmentRepository.findByCompany(company)).thenReturn(List.of(department));

        List<Department> result = departmentService.getAllDepartments(1L);

        assertEquals(1, result.size());
        assertEquals(department, result.get(0));
    }

    @Test
    void getAllDepartments_CompanyNotFound() {
        when(companyRepository.findById(1L)).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> {
            departmentService.getAllDepartments(1L);
        });

        assertEquals("Company not found", exception.getMessage());
         
    }
}
