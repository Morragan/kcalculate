import { accountActions } from "../constants";

export function saveUserData(userData) {
  return {
    type: accountActions.saveUserData,
    payload: userData
  };
}

export function setUserLoggedStatus(isLoggedIn) {
  return {
    type: accountActions.setUserLoggedStatus,
    payload: isLoggedIn
  };
}
