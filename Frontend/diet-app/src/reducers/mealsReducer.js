import { mealsActions } from "../constants";

const initialState = { meals: [] };

const mealsReducer = (state = initialState, action) => {
  switch (action.type) {
    case mealsActions.saveMeals:
      return Object.assign({}, state, { meals: action.payload });
    default:
      return state;
  }
};

export default mealsReducer;
