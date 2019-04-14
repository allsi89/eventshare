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
  public static final String EDIT_EVENT_ROUTE = "/events";
  public static final String DELETE_EVENT_ROUTE = "/events";
  public static final String CREATED_EVENTS_ROUTE = "/events";
  public static final String OWNER_ALL_EVENTS_ROUTE = "/events/my-events";
  public static final String
      OWNER_EVENT_DETAILS_ROUTE = "/events/my-events/created/"; // + eventId
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
  public static final String EDIT_CATEGORY_ROUTE = "/categories/edit";
  public static final String DELETE_CATEGORY_ROUTE = "/categories/delete";
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
}
