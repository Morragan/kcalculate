import { mealsHistoryActions } from "../constants";

export const saveMealEntries = meals => {
  return {
    type: mealsHistoryActions.saveMealEntries,
    payload: meals
  };
};
