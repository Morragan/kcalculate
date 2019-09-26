import { fetchApi } from "./APIUtils";

export const getUserMeals = async () => {
  return await fetchApi("meals", "");
};

export const addUserMeal = async meal => {
  return await fetchApi("meals", "", "POST", meal);
};
