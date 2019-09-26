import { socialActions } from "../constants";

export const saveFriends = friends => {
  return { type: socialActions.saveFriends, payload: friends };
};
