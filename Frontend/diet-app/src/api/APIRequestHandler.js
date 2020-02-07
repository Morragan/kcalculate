import { apiUrl } from "./APIUtils";
import { saveDataToCookie, getDataFromCookie } from "./TokenAPI";

class APIRequestHandler {
  requestsQueue = [];
  isPaused = false;

  queueRequest = (resolve, reject, address, options) =>
    this.requestsQueue.push({ resolve, reject, address, options });

  executeRequests = async () => {
    const accessToken = getDataFromCookie("access_token");

    this.requestsQueue.forEach(({ resolve, reject, address, options }) => {
      const newOptions = options;
      newOptions.headers["Authorization"] = "Bearer " + accessToken;
      fetch(address, options).then(async response => {
        if (response.ok) resolve(await response.json());
        else reject(await response.text());
      });
    });
    this.requestsQueue = [];
  };

  failRequests = message => {
    this.requestsQueue.forEach(({ reject }) => {
      reject(message);
    });
    this.requestsQueue = [];
  };

  refreshToken = async () => {
    const refreshToken = getDataFromCookie("refresh_token");
    const refreshOptions = {
      body: JSON.stringify({ refreshToken }),
      method: "POST",
      mode: "cors",
      headers: { "Content-Type": "application/json" }
    };
    const refreshAddress = apiUrl + "account/refresh-token";
    const refreshResponse = await fetch(refreshAddress, refreshOptions);

    if (!refreshResponse.ok) this.failRequests(await refreshResponse.text());
    else {
      const token = await refreshResponse.json();
      saveDataToCookie("expiration", token.expiration);
      saveDataToCookie("access_token", token.accessToken);
      saveDataToCookie("refresh_token", token.refreshToken);
    }
  };

  executeFetch = async (address, options, isAuthorized) => {
    let promiseResolve;
    let promiseReject;
    const promise = new Promise(async (resolve, reject) => {
      promiseResolve = resolve;
      promiseReject = reject;
    });

    if (!isAuthorized) {
      fetch(address, options).then(async response => {
        if (response.ok) promiseResolve(await response.json());
        else promiseReject(await response.text());
      });
      return promise;
    }

    if (this.isPaused) {
      this.queueRequest(promiseResolve, promiseReject, address, options);
      return promise;
    }

    fetch(address, options).then(async response => {
      if (response.ok) promiseResolve(await response.json());
      else if (response.status === 401) {
        this.queueRequest(promiseResolve, promiseReject, address, options);

        if (!this.isPaused) {
          this.isPaused = true;
          await this.refreshToken();
          this.isPaused = false;
          await this.executeRequests();
        }
      } else promiseReject(await response.text());
    });

    return promise;
  };
}

export default APIRequestHandler;
