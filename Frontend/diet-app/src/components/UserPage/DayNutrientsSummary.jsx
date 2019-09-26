import React, { Component } from "react";
import styled from "styled-components";
import { CircularProgressbar } from "react-circular-progressbar";
import "react-circular-progressbar/dist/styles.css";
import ProgressBar from "react-bootstrap/ProgressBar";
import { areDatesTheSameDay } from "../../constants";

const StyledCircularProgressbar = styled(CircularProgressbar)`
  width: 55% !important;
`;

const PageContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
`;

const StyledProgressBar = styled(ProgressBar)`
  width: 100%;
`;

class DayNutrientsSummary extends Component {
  static defaultProps = {
    mealsHistory: [],
    calorieLimit: 2000
  };
  render() {
    const sum = this.props.mealsHistory
      .filter(meal =>
        areDatesTheSameDay(this.props.selectedDate, new Date(meal.date))
      )
      .reduce((sum, meal) => sum + meal.kcal, 0);
    const percentage = (sum / this.props.calorieLimit) * 100;
    return (
      <PageContainer className={`${this.props.className}`}>
        <StyledCircularProgressbar value={percentage} text={`${sum}kcal`} />
        <StyledProgressBar now={percentage} />
      </PageContainer>
    );
  }
}

export default DayNutrientsSummary;
