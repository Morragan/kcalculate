import { fetchApi } from "./APIUtils";

export const getMealsHistory = async () => {
  return await fetchApi("mealentries", "history");
};

export const recordMealEntry = async mealEntry => {
  return await fetchApi("mealentries", "", "POST", mealEntry);
};
