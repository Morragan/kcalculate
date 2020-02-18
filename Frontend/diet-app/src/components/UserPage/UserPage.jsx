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
  grid-template-areas:
    "date date date"
    "history . summary";
  justify-content: space-between;

  @media (max-width: 860px) {
    grid-template-areas:
      "date"
      "summary"
      "history";
    grid-template-columns: auto;
  }
`;

const StyledDayNutrientsSummary = styled(DayNutrientsSummary)`
  grid-area: summary;
`;

const StyledDatePicker = styled(DatePicker)`
  grid-area: date;
`;

class UserPage extends Component {
  state = {
    selectedDate: new Date()
  };

  static defaultProps = {
    mealsHistory: [],
    user: { calorieLimit: 2000, carbsLimit: 10, fatLimit: 10, proteinLimit: 10 }
  };

  componentDidMount() {
    if (!this.props.isUserLoggedIn) return;
    this.props.setNavbarMode(navbarModes.LOGGED_IN);
    getMealsHistory()
      .then(data => this.props.saveMealEntries(data))
      .catch(reason => console.error("user page", reason));
  }

  componentDidUpdate() {}

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
          user={this.props.user}
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
    user: state.account.user
  };
};

const mapDispatchToProps = dispatch => {
  return {
    setNavbarMode: mode => dispatch(setNavbarMode(mode)),
    saveMealEntries: mealEntries => dispatch(saveMealEntries(mealEntries))
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(UserPage);
