import { UIActions } from "../constants";

export const setNavbarMode = mode => {
  return {
    type: UIActions.setNavbarMode,
    payload: mode
  };
};

export const setAddMealModalVisibility = isModalVisible => {
  return {
    type: UIActions.setAddMealModalVisibility,
    payload: isModalVisible
  };
};
