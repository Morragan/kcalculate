import { socialActions } from "../constants";

const initialState = { requestedFriends: [], receivedFriends: [] };

const socialReducer = (state = initialState, action) => {
  switch (action.type) {
    case socialActions.saveFriends:
      return Object.assign({}, state, action.payload);
    default:
      return state;
  }
};

export default socialReducer;
