export const navbarModes = {
  EMPTY: "NAVBAR_MODE_EMPTY",
  LOGGED_OUT: "NAVBAR_MODE_LOGGED_OUT",
  LOGGED_IN: "NAVBAR_MODE_LOGGED_IN"
};

export const responseCodes = {
  TOKEN_EXPIRED: "RESPONSE_CODE_TOKEN_EXPIRED",
  TOKEN_INVALID: "RESPONSE_CODE_TOKEN_INVALID"
};

/* Action codes */
export const UIActions = {
  setNavbarMode: "UI_ACTIONS_SET_NAVBAR_MODE",
  setAddMealModalVisibility: "UI_ACTIONS_SET_ADD_MEAL_MODAL_VISIBILITY"
};

export const accountActions = {
  saveUserData: "ACCOUNT_ACTIONS_SAVE_USER_DATA",
  setUserLoggedStatus: "ACCOUNT_ACTIONS_SET_USER_LOGGED_STATUS"
};

export const mealsHistoryActions = {
  saveMealEntries: "MEALS_HISTORY_ACTIONS_SAVE_MEAL_ENTRIES"
};

export const mealsActions = {
  saveMeals: "MEALS_ACTIONS_SAVE_MEALS"
};

export const socialActions = {
  saveFriends: "SOCIAL_ACTIONS_SAVE_FRIENDS"
};

export const areDatesTheSameDay = (date1, date2) =>
  date1.getDate() === date2.getDate() &&
  date1.getMonth() === date2.getMonth() &&
  date1.getFullYear() === date2.getFullYear();
