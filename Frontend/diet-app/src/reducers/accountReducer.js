import { accountActions } from "../constants";
import { isTokenExpired } from "../api/TokenAPI";

const initialState = {
  isUserLoggedIn: !isTokenExpired(),
  user: {}
};

const accountReducer = (state = initialState, action) => {
  switch (action.type) {
    case accountActions.saveUserData:
      return Object.assign({}, state, { user: action.payload });
    case accountActions.setUserLoggedStatus:
      return Object.assign({}, state, { isUserLoggedIn: action.payload });
    default:
      return state;
  }
};

export default accountReducer;
