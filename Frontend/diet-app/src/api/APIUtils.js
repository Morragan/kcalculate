import { getDataFromCookie } from "./TokenAPI";
import APIRequestHandler from "./APIRequestHandler";

const apiRequestHandler = new APIRequestHandler();

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
  const address = apiUrl + controller + "/" + action;
  if (authorized) {
    const token = getDataFromCookie("access_token");
    fetchOptions.headers["Authorization"] = "Bearer " + token;
  }
  return apiRequestHandler.executeFetch(address, fetchOptions, authorized);
};
