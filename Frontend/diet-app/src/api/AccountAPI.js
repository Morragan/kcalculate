import { fetchApi } from "./APIUtils";
import store from "../store";
import { setUserLoggedStatus } from "../actions/accountActions";
import { deleteDataFromCookie } from "./TokenAPI";

export const register = async credentials => {
  return await fetchApi("account", "register", "POST", credentials);
};

export const login = async credentials => {
  return await fetchApi("account", "login", "POST", credentials);
};

export const getUserData = async () => {
  return await fetchApi("account", "");
};

export const checkNicknameTaken = async nickname => {
  return await fetchApi(
    "account",
    "check-nickname-taken",
    "POST",
    nickname,
    {},
    false
  );
};

export const logout = () => {
  deleteDataFromCookie("expiration");
  deleteDataFromCookie("access_token");
  deleteDataFromCookie("refresh_token");
  store.dispatch(setUserLoggedStatus(false));
};
