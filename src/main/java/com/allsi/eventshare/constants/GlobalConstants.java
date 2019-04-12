package com.allsi.eventshare.constants;

public class GlobalConstants {

  /*
  Home/Index-related routes
   */

  public static final String INDEX_ROUTE ="/";
  public static final String HOME_ROUTE ="/home";

   /*
  Home/Index-related routes
   */

   public static final String INDEX_VIEW = "index";
   public static final String HOME_VIEW = "home";

  /*
  Organisation-related routes
   */
  public static final String ADD_ORG_ROUTE = "/organisation/add";
  public static final String EDIT_ORG_ROUTE = "/organisation/edit";
  public static final String CHANGE_PIC_ORG_ROUTE = "/organisation/details/change-picture";
  public static final String OWNER_ORG_DETAILS_ROUTE = "/organisation/details";

  /*
  Organisation-related views
   */

  public static final String OWNER_ORG_DETAILS_VIEW = "organisation-view";
  public static final String ADD_ORG_VIEW = "add-organisation";
  public static final String EDIT_ORG_VIEW = "edit-organisation";
  public static final String DELETE_ORG_VIEW = "delete-organisation";


  /*
  User-related routes
   */

  public static final String USER_LOGIN_ROUTE = "/users/login";
  public static final String USER_REGISTER_ROUTE = "/users/register";
  public static final String USER_PROFILE_ROUTE = "/users/profile";
  public static final String USER_EDIT_PROFILE_ROUTE = "/users/profile/edit";
  public static final String USER_CHANGE_PASSWORD_ROUTE = "/users/password/edit";
  public static final String USER_CHANGE_PROFILE_PICTURE_ROUTE = "/users/profile/change-picture";

  /*
  User-related views
   */

  public static final String USER_LOGIN_VIEW = "login";
  public static final String USER_REGISTER_VIEW = "register";
  public static final String USER_PROFILE_VIEW = "profile-view";
  public static final String USER_EDIT_PROFILE_VIEW = "edit-profile";
  public static final String USER_CHANGE_PASSWORD_VIEW = "edit-password";

  /*
  Event-related routes
   */

  public static final String ADD_EVENT_ROUTE = "/events/add";
  public static final String EDIT_EVENT_ROUTE = "/events";
  public static final String DELETE_EVENT_ROUTE = "/events";
  public static final String CREATED_EVENTS_ROUTE = "/events";
  public static final String ATTENDING_EVENTS_ROUTE = "/events/my-events/attending"; //json
  public static final String OWNER_ALL_EVENTS_ROUTE = "/events/my-events";
  public static final String
      OWNER_EVENT_DETAILS_ROUTE = "/events/my-events/created/"; // + eventId
  public static final String CHANGE_EVENT_PICTURE_ROUTE ="/events/all-pictures/"; // + eventId

  /*
 Event-related views
  */

  public static final String ADD_EVENT_VIEW = "add-event";
  public static final String EDIT_EVENT_VIEW = "";
  public static final String DELETE_EVENT_VIEW = "";
  public static final String CREATED_EVENTS_VIEW = "";
  public static final String ATTENDING_EVENTS_VIEW = "attendee-event-view";
  public static final String OWNER_ALL_EVENTS_VIEW = "user-events";
  public static final String OWNER_EVENT_DETAILS_VIEW = "creator-event-view";
  public static final String CHANGE_EVENT_PICTURE_VIEW = "";

}
