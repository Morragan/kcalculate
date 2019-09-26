import { mealsActions } from "../constants";

export const saveMeals = meals => {
  return {
    type: mealsActions.saveMeals,
    payload: meals
  };
};
