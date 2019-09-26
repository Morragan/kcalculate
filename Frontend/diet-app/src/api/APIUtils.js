import { getDataFromCookie } from "./TokenAPI";

export const apiUrl = "https://localhost:44332/api/";

export const fetchApi = (
  controller,
  action,
  method = "GET",
  payload = undefined,
  options = {},
  authorized = true
) => {
  const fetchOptions = {
    ...options,
    body: JSON.stringify(payload),
    method: method,
    mode: "cors",
    headers: { "Content-Type": "application/json" }
  };
  if (authorized) {
    const token = getDataFromCookie("access_token");
    fetchOptions.headers["Authorization"] = "Bearer " + token;
  }
  return fetch(apiUrl + controller + "/" + action, fetchOptions);
};
