import { fetchApi } from "./APIUtils";

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
