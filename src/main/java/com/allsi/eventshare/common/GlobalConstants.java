package com.allsi.eventshare.common;

public class GlobalConstants {

  /*
  Home/Index/Error-related routes
   */

  public static final String INDEX_ROUTE ="/";
  public static final String HOME_ROUTE ="/home";

   /*
  Home/Index-related routes
   */

  public static final String INDEX_VIEW = "index";
  public static final String HOME_VIEW = "home";
  public static final String ERROR_VIEW ="error";

  /*
  Organisation-related routes
   */
  public static final String ADD_ORG_ROUTE = "/organisations/add";
  public static final String EDIT_ORG_ROUTE = "/organisations/edit";
  public static final String CHANGE_PIC_ORG_ROUTE = "/organisations/details/change-picture";
  public static final String OWNER_ORG_DETAILS_ROUTE = "/organisations/details";

  /*
  Organisation-related views
   */

  public static final String OWNER_ORG_DETAILS_VIEW = "organisation/organisation-view";
  public static final String ADD_ORG_VIEW = "organisation/add-organisation";
  public static final String EDIT_ORG_VIEW = "organisation/edit-organisation";
  public static final String DELETE_ORG_VIEW = "organisation/delete-organisation";


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

  public static final String USER_LOGIN_VIEW = "user/login";
  public static final String USER_REGISTER_VIEW = "user/register";
  public static final String USER_PROFILE_VIEW = "user/profile-view";
  public static final String USER_EDIT_PROFILE_VIEW = "user/edit-profile";
  public static final String USER_CHANGE_PASSWORD_VIEW = "user/edit-password";

  /*
  Event-related routes (owner)
   */

  public static final String ADD_EVENT_ROUTE = "/events/add";
  public static final String EDIT_EVENT_ROUTE = "/events/my-events/edit";
  public static final String DELETE_EVENT_ROUTE = "/events/my-events/delete";
  public static final String CREATED_EVENTS_ROUTE = "/events/my-events";
  public static final String OWNER_ALL_EVENTS_ROUTE = "/events/my-events";
  public static final String OWNER_EVENT_DETAILS_ROUTE = "/events/my-events/view/";
  public static final String CHANGE_EVENT_PICTURE_ROUTE ="/events/all-pictures/"; // + eventId

  /*
 Event-related views (owner)
  */

  public static final String ADD_EVENT_VIEW = "event/add-event";
  public static final String EDIT_EVENT_VIEW = "event/edit-event";
  public static final String DELETE_EVENT_VIEW = "event/delete-event";
  public static final String CREATED_EVENTS_VIEW = "";
  public static final String OWNER_ALL_EVENTS_VIEW = "event/user-events";
  public static final String OWNER_EVENT_DETAILS_VIEW = "event/creator-event-view";
  public static final String CHANGE_EVENT_PICTURE_VIEW = "";

  /*
  Category-related routes
   */

  public static final String ADD_CATEGORY_ROUTE = "/categories/add";
  public static final String EDIT_CATEGORY_ROUTE = "/categories/edit/"; // + id
  public static final String DELETE_CATEGORY_ROUTE = "/categories/delete/"; // + id - only post
  public static final String ALL_CATEGORIES_ROUTE = "/categories/all";

  /*
  Category-related views
   */

  public static final String ADD_CATEGORY_VIEW = "category/add-category";
  public static final String EDIT_CATEGORY_VIEW = "category/edit-category";
  public static final String DELETE_CATEGORY_VIEW = "category/delete-category";
  public static final String ALL_CATEGORIES_VIEW = "category/all-categories";

  /*
  Explore-related routes
   */
  public static final String EXPLORE_ROUTE = "/explore/options";
  public static final String EXPLORE_EVENT_ROUTE = "/explore/event/"; //+ id

  public static final String EXPLORE_ORG_EVENTS_ROUTE = "/explore/organisations/"; //+ org.id

  /*
  Explore-related views
   */

  public static final String EXPLORE_VIEW = "explore/explore";
  public static final String EXPLORE_ORG_EVENTS_VIEW = "explore/organisation-events-view";
  public static final String EXPLORE_EVENT_VIEW = "explore/event-guest-view";
  public static final String EXPLORE_COUNTRY_EVENTS_VIEW = "explore/country-events-view";
  public static final String EXPLORE_CATEGORY_EVENTS_VIEW = "explore/category-events-view";
  public static final String EXPLORE_CREATOR_EVENTS_VIEW = "explore/creator-guest-events-view";


  /*
  Admin-related routes
   */

  public static final String ADMIN_ALL_USERS_ROUTE = "/admin/all-users";
  public static final String ADMIN_EDIT_USER_ROUTE = "/admin/users/edit/"; // + id

   /*
  Admin-related views
   */
   public static final String ADMIN_ALL_USERS_VIEW = "/admin/all-users";



   /*
   Binding Models constants and messages
    */

  public static final String EMPTY_ERROR_MESSAGE = "Field cannot be empty!";
  public static final String EMAIL_PATTERN = "^([\\w.-]+)@([\\w-]+)((.(\\w){2,3})+)$";
  public static final String INVALID_EMAIL_MSG = "Please provide a valid email.";
  public static final String BLANK_ERR_MSG = "Input field cannot be blank.";
  public static final String PASS_LENGTH_ERR_MGS = "Password must be 3-16 symbols long.";
  public static final String USERNAME_PATTERN = "^(?![_.])(?!.*[_.]{2})[a-z0-9._]+(?<![_.])$";
  public static final String USERNAME_LENGTH_ERR_MSG = "Username must be 5-15 symbols long.";
  public static final String USERNAME_NOT_ALLOWED_CHARS_ERR_MSG = "Invalid username. " +
      "Allowed symbols are lowercase letters, digits, '_' and '.'";

  public static final String DATE_TIME_FORMAT = "dd-MMM-yyyy hh:mm a";
  public static final String DATE_TIME_STR_TO_FORMAT = "%s %s";





  public static final String ROOT_ADMIN = "ROLE_ROOT";
  public static final String ADMIN = "ROLE_ADMIN";
  public static final String CORP = "ROLE_CORP";
  public static final String MODERATOR = "ROLE_MODERATOR";
  public static final String USER = "ROLE_USER";
}

