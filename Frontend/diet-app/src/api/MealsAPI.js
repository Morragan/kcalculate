import { fetchApi } from "./APIUtils";

export const getUserMeals = async () => {
  return await fetchApi("meals", "");
};

export const addUserMeal = async meal => {
  return await fetchApi("meals", "", "POST", meal);
};

export const findMealsByName = async name => {
  return await fetchApi("meals", `meal-name/${name}`);
};

export const findMealsByBarcode = async barcode => {
  return await fetchApi("meals", `meal-barcode/${barcode}`);
};
