import React, { Component } from "react";
import styled from "styled-components";
import MealHistory from "./MealHistory";
import DayNutrientsSummary from "./DayNutrientsSummary";
import DatePicker from "./DatePicker";
import { connect } from "react-redux";
import { setNavbarMode } from "../../actions/UIActions";
import { saveMealEntries } from "../../actions/mealsHistoryActions";
import { navbarModes } from "../../constants";
import { getMealsHistory } from "../../api/MealsHistoryAPI";

const PageContainer = styled.div`
  display: grid;
  grid-template-columns: 2.75fr 1fr 3fr;
  justify-content: space-between;
`;

const StyledDayNutrientsSummary = styled(DayNutrientsSummary)`
  grid-column: 3;
`;

const StyledDatePicker = styled(DatePicker)`
  grid-column: 1/4;
`;

class UserPage extends Component {
  state = {
    selectedDate: new Date()
  };

  static defaultProps = {
    mealsHistory: [],
    calorieLimit: 2000
  };

  componentDidMount() {
    if (!this.props.isUserLoggedIn) return;
    this.props.setNavbarMode(navbarModes.LOGGED_IN);
    getMealsHistory()
      .then(data => this.props.saveMealEntries(data))
      .catch(reason => console.error("user page", reason));
    this.percentage =
      this.props.mealsHistory.reduce((sum, meal) => sum + meal.kcal, 0) /
      this.props.calorieLimit;
  }

  componentDidUpdate() {
    this.percentage =
      this.props.mealsHistory.reduce((sum, meal) => {
        return sum + meal.kcal;
      }, 0) / this.props.calorieLimit;
  }

  handleDateChange = date => {
    this.setState({
      selectedDate: date
    });
  };

  render() {
    return (
      <PageContainer>
        <StyledDatePicker
          selectedDate={this.state.selectedDate}
          handleChange={this.handleDateChange}
          className="date-picker"
        />
        <MealHistory
          meals={this.props.mealsHistory}
          selectedDate={this.state.selectedDate}
        />
        <StyledDayNutrientsSummary
          mealsHistory={this.props.mealsHistory}
          calorieLimit={this.props.calorieLimit}
          selectedDate={this.state.selectedDate}
          className="day-nutrients-summary"
        />
      </PageContainer>
    );
  }
}
const mapStateToProps = state => {
  return {
    isUserLoggedIn: state.account.isUserLoggedIn,
    mealsHistory: state.mealsHistory.mealsHistory,
    calorieLimit: state.account.user.calorieLimit
  };
};

const mapDispatchToProps = dispatch => {
  return {
    setNavbarMode: mode => dispatch(setNavbarMode(mode)),
    saveMealEntries: mealEntries => dispatch(saveMealEntries(mealEntries))
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(UserPage);
