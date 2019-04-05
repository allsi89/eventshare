package com.allsi.eventshare.domain.models.view;

public class UserProfileViewModel {
  private String username;
  private String password;
  private String email;
  private String imageUrl;
  private boolean isCorporate;
  private OrganisationViewModel organisation;

  public UserProfileViewModel() {
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public boolean isCorporate() {
    return isCorporate;
  }

  public void setCorporate(boolean corporate) {
    isCorporate = corporate;
  }

  public OrganisationViewModel getOrganisation() {
    return organisation;
  }

  public void setOrganisation(OrganisationViewModel organisation) {
    this.organisation = organisation;
  }
}
