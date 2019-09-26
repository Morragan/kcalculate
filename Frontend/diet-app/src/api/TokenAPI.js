import { fetchApi } from "./APIUtils";
import store from "../store";
import { setUserLoggedStatus } from "../actions/accountActions";

//TODO: wywoływać
export const refreshAccessToken = async email => {
  const token = getDataFromCookie("refresh_token");
  fetchApi("account", "refreshtoken", "POST", { token, email })
    .then(response => {
      if (!response.ok) throw response;
      return response.json();
    })
    .then(data => {
      saveDataToCookie("expiration", data.expiration);
      saveDataToCookie("access_token", data.accessToken);
      saveDataToCookie("refresh_token", data.refreshToken);
      store.dispatch(setUserLoggedStatus(true));
    })
    .catch(reason => console.log("refresh token", reason));
};

export const saveDataToCookie = (name, data) => {
  document.cookie = name + "=" + data;
};

export const getDataFromCookie = name => {
  var nameEQ = `${name}=`;
  var ca = document.cookie.split(";");
  for (var i = 0; i < ca.length; i++) {
    var c = ca[i];
    while (c.charAt(0) === " ") c = c.substring(1, c.length);
    if (c.indexOf(nameEQ) === 0) return c.substring(nameEQ.length, c.length);
  }
  return null;
};

export const deleteDataFromCookie = name => {
  document.cookie = `${name}= ; expires = Thu, 01 Jan 1970 00:00:00 GMT`;
};

export const isTokenExpired = () => {
  try {
    const expiration = getDataFromCookie("expiration");
    if (expiration === null) return true;
    if (expiration <= Date.now() / 1000) return true;
    else return false;
  } catch (err) {
    console.error("isTokenExpired error", err);
    return true;
  }
};
