import { combineReducers } from "redux";
import accountReducer from "./accountReducer";
import UIReducer from "./UIReducer";
import mealsHistoryReducer from "./mealsHistoryReducer";
import mealsReducer from "./mealsReducer";
import socialReducer from "./socialReducer";

const rootReducer = combineReducers({
  account: accountReducer,
  UI: UIReducer,
  mealsHistory: mealsHistoryReducer,
  meals: mealsReducer,
  social: socialReducer
});

export default rootReducer;
