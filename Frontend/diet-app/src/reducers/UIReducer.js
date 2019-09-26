import { UIActions, navbarModes } from "../constants";

const initialState = {
  navbarMode: navbarModes.LOGGED_OUT,
  addMealModalVisibility: false
};

const UIReducer = (state = initialState, action) => {
  switch (action.type) {
    case UIActions.setNavbarMode:
      return Object.assign({}, state, {
        navbarMode: action.payload
      });
    case UIActions.setAddMealModalVisibility:
      return Object.assign({}, state, {
        addMealModalVisibility: action.payload
      });
    default:
      return state;
  }
};

export default UIReducer;
