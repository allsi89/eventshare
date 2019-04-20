package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.entities.Role;
import com.allsi.eventshare.domain.models.service.RoleServiceModel;
import com.allsi.eventshare.errors.AuthorisationNotFoundException;
import com.allsi.eventshare.repository.RoleRepository;
import com.allsi.eventshare.service.role.RoleService;
import com.allsi.eventshare.service.role.RoleServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class RoleServiceTests {
  private static final String AUTHORITY = "authority";
  private static final String CORP = "ROLE_CORP";

  @Mock
  private RoleRepository roleRepository;

  @Spy
  private ModelMapper modelMapper;

  private RoleService roleService;

  @Before
  public void init() {
    this.roleService = new RoleServiceImpl(this.roleRepository, this.modelMapper);
  }

  @Test
  public void seedRolesInDb_withNoRolesInDb_returnsCorrect(){

    when(this.roleRepository.count())
        .thenReturn(Long.valueOf(0));

    this.roleService.seedRolesInDb();

    verify(this.roleRepository, atLeast(5)).save(any());
  }

  @Test
  public void seedRolesInDb_withRolesInDb_returnsCorrect() {
    when(this.roleRepository.count())
        .thenReturn(Long.valueOf(5));

    this.roleService.seedRolesInDb();

    verify(this.roleRepository, never()).save(any());
  }

  @Test
  public void findByAuthority_withCorrectAuthority_returnsCorrect() {
    Role expected = new Role(AUTHORITY);
    when(this.roleRepository.findByAuthority(AUTHORITY))
        .thenReturn(java.util.Optional.of(expected));

    RoleServiceModel actual = this.roleService.findByAuthority(AUTHORITY);

    Assert.assertEquals(expected.getAuthority(), actual.getAuthority());
  }

  @Test(expected = AuthorisationNotFoundException.class)
  public void findByAuthority_withNotCorrectAuthority_throwsException() {
    this.roleService.findByAuthority(AUTHORITY);
  }

  @Test
  public void findAllRolesNotCorp_withCorrectData_returnsCorrect() {
    List<RoleServiceModel> roles = this.roleService
        .getAllRolesNotCorp();

    for (RoleServiceModel role : roles) {
      Assert.assertNotEquals(role.getAuthority(), CORP);
    }
  }

  @Test
  public void findAllAuthoritiesNotCorp_withEmptyDb_returnsCorrect() {
    List<Role> roles = new ArrayList<>();

    when(this.roleRepository.findAllByAuthorityNot(CORP))
        .thenReturn(roles);

    List<RoleServiceModel> serviceModels = this.roleService.getAllRolesNotCorp();

    Assert.assertTrue(serviceModels.isEmpty());
  }

}
