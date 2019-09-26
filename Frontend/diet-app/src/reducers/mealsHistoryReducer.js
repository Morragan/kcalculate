import { mealsHistoryActions } from "../constants";

const initialState = { mealsHistory: [] };

const mealsHistoryReducer = (state = initialState, action) => {
  switch (action.type) {
    case mealsHistoryActions.saveMealEntries:
      return Object.assign({}, state, { mealsHistory: action.payload });
    default:
      return state;
  }
};

export default mealsHistoryReducer;
