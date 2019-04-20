package com.allsi.eventshare.service;

import com.allsi.eventshare.domain.entities.Role;
import com.allsi.eventshare.domain.entities.User;
import com.allsi.eventshare.domain.models.service.ImageServiceModel;
import com.allsi.eventshare.domain.models.service.RoleServiceModel;
import com.allsi.eventshare.domain.models.service.UserServiceModel;
import com.allsi.eventshare.errors.IllegalOperationException;
import com.allsi.eventshare.repository.UserRepository;
import com.allsi.eventshare.service.role.RoleService;
import com.allsi.eventshare.service.user.UserService;
import com.allsi.eventshare.service.user.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceTests {
  private static final String USER_ID = "userId";
  private static final String USER_USERNAME = "userUsername";
  private static final String SECOND_USER_USERNAME = "secondUserUsername";
  private static final String USER_PASSWORD = "userPassword";
  private static final String USER_EMAIL = "userEmail";
  private static final String INVALID_ROLE = "invalidRole";
  private static final String ROLE_USER = "ROLE_USER";
  private static final String ROLE_ROOT = "ROLE_ROOT";
  private static final String ROLE_CORP = "ROLE_CORP";

  @Mock
  private UserRepository userRepository;
  @Mock
  private RoleService roleService;

  @Spy
  private BCryptPasswordEncoder encoder;
  @Spy
  private ModelMapper modelMapper;

  private UserService userService;


  @Before
  public void init() {
   this.userService = new UserServiceImpl(this.userRepository,
       this.roleService, this.modelMapper, this.encoder);
  }

  @Test
  public void findByUsername_withCorrectUsername_returnsCorrect() {
    User user = getUser();
    when(this.userRepository.findByUsername(user.getUsername()))
        .thenReturn(java.util.Optional.of(user));

    UserServiceModel serviceModel = this.userService.findUserByUsername(user.getUsername());

    Assert.assertEquals(serviceModel.getUsername(), user.getUsername());
    Assert.assertEquals(serviceModel.getId(), user.getId());
    Assert.assertEquals(serviceModel.getPassword(), user.getPassword());
    Assert.assertEquals(serviceModel.getEmail(), user.getEmail());
  }

  @Test(expected = UsernameNotFoundException.class)
  public void findByUsername_withNotCorrectUsername_throwsException() {
    this.userService.findUserByUsername(USER_USERNAME);
  }

  @Test
  public void register_withCorrectData_returnsCorrect() {
    UserServiceModel serviceModel = new UserServiceModel(){{
      setId(USER_ID);
      setUsername(USER_USERNAME);
      setEmail(USER_EMAIL);
      setPassword(USER_PASSWORD);
    }};

    this.userService.register(serviceModel);

    verify(this.userRepository).save(any());
  }

  @Test(expected = Exception.class)
  public void registerUser_withNotCorrectData_throwsException(){

    UserServiceModel userServiceModel = new UserServiceModel();

    this.userService.register(userServiceModel);
  }

  @Test(expected = Exception.class)
  public void registerUser_withNotCorrectUser_throwsException(){

    this.userService.register(null);
  }

  @Test
  public void editProfile_withCorrectData_returnsCorrect(){
    UserServiceModel serviceModel = new UserServiceModel(){{
      setUsername(USER_USERNAME);
      setEmail(USER_EMAIL);
      setAbout(null);
    }};

    User user = getUser();

    when(this.userRepository.findByUsername(serviceModel.getUsername()))
        .thenReturn(java.util.Optional.of(user));

    this.userService.editUserProfile(serviceModel);

    verify(this.userRepository).save(any());
  }

  @Test(expected = IllegalOperationException.class)
  public void editProfile_withNoCorrectData_throwsException(){
    UserServiceModel serviceModel = new UserServiceModel(){{
      setUsername(USER_USERNAME);
      setEmail(null);
      setAbout(null);
    }};

    User user = getUser();

    when(this.userRepository.findByUsername(serviceModel.getUsername()))
        .thenReturn(java.util.Optional.of(user));

    this.userService.editUserProfile(serviceModel);
  }

  @Test(expected = UsernameNotFoundException.class)
  public void editProfile_withNotValidUser_throwsException(){
    UserServiceModel serviceModel = new UserServiceModel(){{
      setUsername(USER_USERNAME);
      setEmail(USER_EMAIL);
      setAbout(null);
    }};

    this.userService.editUserProfile(serviceModel);
  }

  @Test
  public void editPassword_withCorrectData_returnsCorrect(){
    UserServiceModel serviceModel = new UserServiceModel(){{
      setUsername(USER_USERNAME);
      setPassword(USER_PASSWORD);
    }};

    User user = getUser();

    when(this.userRepository.findByUsername(serviceModel.getUsername()))
        .thenReturn(java.util.Optional.of(user));

    this.userService.editUserPassword(serviceModel, USER_USERNAME, USER_PASSWORD);

    verify(this.userRepository).save(any());
  }

  @Test(expected = UsernameNotFoundException.class)
  public void editPassword_witNotCorrectUser_throwsException(){
    UserServiceModel serviceModel = new UserServiceModel(){{
      setUsername(USER_USERNAME);
      setPassword(USER_PASSWORD);
    }};

    this.userService.editUserPassword(serviceModel, USER_USERNAME, USER_PASSWORD);
  }

  @Test
  public void editUserPicture_withCorrectData_returnsCorrect() throws IOException {
    ImageServiceModel imageServiceModel = mock(ImageServiceModel.class);

    User user = getUser();

    when(this.userRepository.findByUsername(USER_USERNAME))
        .thenReturn(java.util.Optional.of(user));

    this.userService.editUserPicture(USER_USERNAME, imageServiceModel);
  }

  @Test(expected = UsernameNotFoundException.class)
  public void editUserPicture_withNotCorrectUser_throwsException() throws IOException {
    ImageServiceModel imageServiceModel = mock(ImageServiceModel.class);

    this.userService.editUserPicture(USER_USERNAME, imageServiceModel);
  }

  @Test
  public void findAllButRequester_withValidData_returnsCorrect() {
    User user = getUser();
    User second = getUser();
    second.setUsername(SECOND_USER_USERNAME);
    List<User> users = new ArrayList<>() {{
      add(second);
      add(user);
    }};

    when(this.userRepository.findAll())
        .thenReturn(users);

    List<UserServiceModel> serviceModels = this.userService
        .findAllUsersButRequester(USER_USERNAME);

    Assert.assertEquals(1, serviceModels.size());
  }

  @Test
  public void findAllButRequester_withNotValidUser_returnsCorrect(){
    User user = getUser();
    User second = getUser();
    second.setUsername(SECOND_USER_USERNAME);
    List<User> users = new ArrayList<>() {{
      add(second);
      add(user);
    }};

    when(this.userRepository.findAll())
        .thenReturn(users);

    List<UserServiceModel> serviceModels = this.userService
        .findAllUsersButRequester(null);

    Assert.assertEquals(2, serviceModels.size());
  }

  @Test(expected = IllegalOperationException.class)
  public void updateRole_withRootRole_throwsException(){
    User user = getUser();
    when(this.userRepository.findById(user.getId()))
        .thenReturn(java.util.Optional.of(user));

    this.userService.updateRole(user.getId(), ROLE_ROOT);
  }

  @Test(expected = NullPointerException.class)
  public void updateRole_withNullRole_returnsNotUpdated(){
    User user = getUser();

    when(this.userRepository.findById(user.getId()))
        .thenReturn(java.util.Optional.of(user));

    this.userService.updateRole(user.getId(),null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void updateRole_withRoleAndNotValidUser_throwsException(){
    this.userService.updateRole(USER_ID, null);
  }

  @Test(expected = IllegalOperationException.class)
  public void updateRole_withNotValidRoleAndValidUser_throwsException(){
    User user = getUser();
    when(this.userRepository.findById(user.getId()))
        .thenReturn(java.util.Optional.of(user));

    this.userService.updateRole(user.getId(), INVALID_ROLE);
  }

  @Test(expected = IllegalOperationException.class)
  public void updateRoleOnRoot_withValidData_throwsException() {
    User user = getUser();
    user.getRoles().add(new Role(ROLE_ROOT));
    when(this.userRepository.findById(user.getId()))
        .thenReturn(java.util.Optional.of(user));

    this.userService.updateRole(user.getId(), ROLE_ROOT);
  }

  @Test
  public void updateRole_withValidData_returnsCorrect() {
    User user = getUser();

    when(this.userRepository.findById(anyString()))
        .thenReturn(java.util.Optional.of(user));

    when(this.roleService.findByAuthority(anyString()))
        .thenReturn(new RoleServiceModel(ROLE_USER));

    this.userService.updateRole(user.getId(), ROLE_USER);

    verify(this.userRepository).save(any());
  }

  @Test
  public void removeCorpRole_withValidData_returnsCorrect() {
    User user = getUser();
    user.getRoles().add(new Role(ROLE_CORP));

    when(this.userRepository.findByUsername(anyString()))
        .thenReturn(java.util.Optional.of(user));

    this.userService.removeCorpRole(user.getUsername());

    verify(this.userRepository).save(any());
  }

  @Test(expected = UsernameNotFoundException.class)
  public void removeCorpRole_withNotValidData_throwsException() {
    this.userService.removeCorpRole(USER_USERNAME);
  }

  @Test
  public void addCorpRole_withValidData_returnsCorrect() {
    User user = getUser();

    when(this.userRepository.findByUsername(anyString()))
        .thenReturn(java.util.Optional.of(user));

    this.userService.removeCorpRole(user.getUsername());

    verify(this.userRepository).save(any());
  }

  @Test(expected = UsernameNotFoundException.class)
  public void addCorpRole_withNotValidData_throwsException() {
    this.userService.removeCorpRole(USER_USERNAME);
  }

  @Test
  public void loadUserByUsername_withValidData_returnsCorrect() {
    User expected = getUser();

    when(this.userRepository.findByUsername(anyString()))
        .thenReturn(java.util.Optional.of(expected));

    UserDetails actual = this.userService
        .loadUserByUsername(expected.getUsername());

    Assert.assertEquals(expected.getUsername(), actual.getUsername());
  }

  @Test(expected = UsernameNotFoundException.class)
  public void loadUserByUsername_withNotValidData_throwsException() {
    this.userService
        .loadUserByUsername(USER_USERNAME);
  }

  private User getUser() {
    String password = this.encoder.encode(USER_PASSWORD);

    return new User() {{
      setId(USER_ID);
      setUsername(USER_USERNAME);
      setEmail(USER_EMAIL);
      setPassword(password);
    }};
  }
}
