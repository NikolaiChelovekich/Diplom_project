package com.example.apiWithDb.service;

import com.example.apiWithDb.entities.Company;
import com.example.apiWithDb.entities.User;
import com.example.apiWithDb.exception.AppException;
import com.example.apiWithDb.repository.CompanyRepository;
import com.example.apiWithDb.repository.DepartmentRepository;
import com.example.apiWithDb.service.impl.CompanyServiceimpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private CompanyServiceimpl companyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateCompany_Success() {
        // Setup
        Company company = new Company();
        Authentication authentication = mock(Authentication.class);
        User user = new User();
        when(userService.findUserByToken(authentication)).thenReturn(user);

        // Test
        String result = companyService.createCompany(company, authentication);

        // Verify
        assertEquals("Success", result);
        verify(companyRepository, times(1)).save(company);
    }

    @Test
    void testUpdateCompany_CompanyNotFound() {
        // Setup
        Company company = new Company();
        company.setId(1L);
        Authentication authentication = mock(Authentication.class);
        User user = new User();
        user.setId(1L);
        when(userService.findUserByToken(authentication)).thenReturn(user);
        when(companyRepository.findById(1L)).thenReturn(Optional.empty());

        // Test and Verify
        assertThrows(AppException.class, () -> companyService.updateCompany(company, authentication));
        verify(companyRepository, never()).save(company);
        verify(userService, times(1)).findUserByToken(authentication);
        verify(companyRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateCompany_NotAuthorized() {
        // Setup
        Company company = new Company();
        company.setId(1L);
        Authentication authentication = mock(Authentication.class);
        User user = new User();
        user.setId(2L); // Different user
        when(userService.findUserByToken(authentication)).thenReturn(user);
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));

        // Test & Verify
        assertThrows(AppException.class, () -> companyService.updateCompany(company, authentication));
        verify(companyRepository, never()).save(company);
    }

    @Test
    public void testDeleteCompany_Success() {
        // Setup
        Long companyId = 1L;
        Authentication authentication = mock(Authentication.class);
        User user = new User();
        user.setId(1L);
        Company company = new Company();
        company.setUser(user);

        when(userService.findUserByToken(authentication)).thenReturn(user);
        when(companyRepository.findById(companyId)).thenReturn(Optional.of(company));

        // Test
        String result = companyService.deleteCompany(companyId, authentication);

        // Verify
        assertEquals("Success", result);
        verify(companyRepository, times(1)).deleteCompanyByIdAndUserId(companyId, user.getId());
    }


    @Test
    void testGetCompanyForAdmin_Success() {
        // Setup
        Long companyId = 1L;
        Authentication authentication = mock(Authentication.class);
        User user = new User();
        user.setId(1L);
        Company company = new Company();
        company.setUser(user);
        when(userService.findUserByToken(authentication)).thenReturn(user);
        when(companyRepository.findById(companyId)).thenReturn(Optional.of(company));

        // Test
        Company result = companyService.getCompanyForAdmin(companyId, authentication);

        // Verify
        assertNotNull(result);
        assertEquals(company, result);
    }


    @Test
    void testGetCompanyForEmployee_Success() {
        // Setup
        Authentication authentication = mock(Authentication.class);
        User user = new User();
        user.setId(1L);
        Company company = new Company();
        company.setUser(user);
        when(userService.findUserByToken(authentication)).thenReturn(user);
        when(companyRepository.findCompanyByEmployeeLogin(user.getLogin())).thenReturn(Optional.of(company));

        // Test
        Company result = companyService.getCompanyForEmployee(authentication);

        // Verify
        assertNotNull(result);
        assertEquals(company, result);
    }

    @Test
    void testGetAllCompanies_Success() {
        // Setup
        Authentication authentication = mock(Authentication.class);
        User user = new User();
        user.setId(1L);
        List<Company> companies = new ArrayList<>();
        companies.add(new Company());
        companies.add(new Company());
        when(userService.findUserByToken(authentication)).thenReturn(user);
        when(companyRepository.findAllCompaniesByUserId(user.getId())).thenReturn(companies);

        // Test
        List<Company> result = companyService.getAllCompanies(authentication);

        // Verify
        assertNotNull(result);
        assertEquals(companies.size(), result.size());
    }
}