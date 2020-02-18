import React, { useState, useEffect } from "react";
import styled from "styled-components";
import {
  CircularProgressbarWithChildren,
  buildStyles
} from "react-circular-progressbar";
import "react-circular-progressbar/dist/styles.css";
import { areDatesTheSameDay } from "../../constants";
import NutrientProgressBar from "./NutrientProgressBar";

const StyledCircularProgressbarWithChildren = styled(
  CircularProgressbarWithChildren
)``;

const PageContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
`;

const KcalLeftContainer = styled.div`
  text-align: center;

  span {
    display: block;
  }

  span:first-child {
    font-weight: 600;
    font-size: 38px;
    color: ${props => props.color};
  }
`;

const KcalInfoContainer = styled.div`
  margin-top: -40px;
  width: 70%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;

  & > div {
    text-align: center;
    span {
      display: block;
    }
  }
`;

const DayNutrientsSummary = props => {
  const [mealsFromSelectedDate, changeMealsFromSelectedDate] = useState([]);
  const [kcalEaten, changeKcalEaten] = useState(0);
  const [kcalLeft, changeKcalLeft] = useState(0);
  const [kcalProgress, changeKcalProgress] = useState(0);
  const [carbsEaten, changeCarbsEaten] = useState(0);
  const [carbsLeft, changeCarbsLeft] = useState(0);
  const [carbsProgress, changeCarbsProgress] = useState(0);
  const [fatEaten, changeFatEaten] = useState(0);
  const [fatLeft, changeFatLeft] = useState(0);
  const [fatProgress, changeFatProgress] = useState(0);
  const [proteinEaten, changeProteinEaten] = useState(0);
  const [proteinLeft, changeProteinLeft] = useState(0);
  const [proteinProgress, changeProteinProgress] = useState(0);
  const [barColor, changeBarColor] = useState("#ffd66f");

  useEffect(() => {
    const meals = props.mealsHistory.filter(meal =>
      areDatesTheSameDay(props.selectedDate, new Date(meal.date))
    );
    changeMealsFromSelectedDate(meals);
  }, [props.mealsHistory, props.selectedDate]);

  useEffect(() => {
    const kcalSum = mealsFromSelectedDate.reduce(
      (sum, meal) => sum + meal.kcal,
      0
    );
    changeKcalEaten(kcalSum || 0);

    const carbsSum = mealsFromSelectedDate.reduce(
      (sum, meal) => sum + meal.carbs,
      0
    );
    changeCarbsEaten(carbsSum);

    const fatSum = mealsFromSelectedDate.reduce(
      (sum, meal) => sum + meal.fat,
      0
    );
    changeFatEaten(fatSum);

    const proteinSum = mealsFromSelectedDate.reduce(
      (sum, meal) => sum + meal.protein,
      0
    );
    changeProteinEaten(proteinSum);
  }, [mealsFromSelectedDate]);

  useEffect(() => {
    const progress = (kcalEaten / props.user.calorieLimit) * 100;
    changeKcalProgress(progress);
  }, [kcalEaten, props.user.calorieLimit]);

  useEffect(() => {
    const progress = (carbsEaten / props.user.carbsLimit) * 100;
    changeCarbsProgress(progress);
  }, [carbsEaten, props.user.carbsLimit]);

  useEffect(() => {
    const progress = (fatEaten / props.user.fatLimit) * 100;
    changeFatProgress(progress);
  }, [fatEaten, props.user.fatLimit]);

  useEffect(() => {
    const progress = (proteinEaten / props.user.proteinLimit) * 100;
    changeProteinProgress(progress);
  }, [proteinEaten, props.user.proteinLimit]);

  useEffect(() => {
    const left = props.user.calorieLimit - kcalEaten;
    changeKcalLeft(left);
  }, [kcalEaten, props.user.calorieLimit]);

  useEffect(() => {
    const left = props.user.carbsLimit - carbsEaten;
    changeCarbsLeft(left);
  }, [carbsEaten, props.user.carbsLimit]);

  useEffect(() => {
    const left = props.user.fatLimit - fatEaten;
    changeFatLeft(left);
  }, [fatEaten, props.user.fatLimit]);

  useEffect(() => {
    const left = props.user.proteinLimit - proteinEaten;
    changeProteinLeft(left);
  }, [proteinEaten, props.user.proteinLimit]);

  useEffect(() => {
    if (
      kcalEaten > props.user.calorieLimitLower &&
      kcalEaten < props.user.calorieLimitUpper
    ) {
      changeBarColor("#28a745");
    } else if (kcalEaten < props.user.calorieLimitLower) {
      changeBarColor("#ffd66f");
    } else {
      changeBarColor("#dc3545");
    }
  }, [kcalEaten, props.user.calorieLimitLower, props.user.calorieLimitUpper]);

  return (
    <PageContainer className={`${props.className}`}>
      <StyledCircularProgressbarWithChildren
        value={kcalProgress}
        styles={buildStyles({
          pathColor: barColor,
          trailColor: "#e9ecef"
        })}
      >
        <KcalLeftContainer color={barColor}>
          <span>{parseFloat(Math.abs(kcalLeft || 0).toFixed(2))}kcal</span>
          <span>{kcalLeft < 0 ? "Over limit" : "Left"}</span>
        </KcalLeftContainer>
      </StyledCircularProgressbarWithChildren>
      <KcalInfoContainer>
        <div>
          <span>Goal</span>
          <span>{props.user.calorieLimit}</span>
        </div>
        <div>
          <span>Eaten</span>
          <span>{kcalEaten}</span>
        </div>
      </KcalInfoContainer>
      <hr style={{ width: "100%" }} />
      <NutrientProgressBar
        name="Carbs"
        eaten={carbsEaten}
        goal={props.user.carbsLimit || 0}
        left={carbsLeft || 0}
        percentage={carbsProgress}
        lowerLimit={props.user.carbsLimitLower}
        upperLimit={props.user.carbsLimitUpper}
      />
      <NutrientProgressBar
        name="Fat"
        eaten={fatEaten}
        goal={props.user.fatLimit || 0}
        left={fatLeft || 0}
        percentage={fatProgress}
        lowerLimit={props.user.fatLimitLower}
        upperLimit={props.user.fatLimitUpper}
      />
      <NutrientProgressBar
        name="Protein"
        eaten={proteinEaten}
        goal={props.user.proteinLimit || 0}
        left={proteinLeft || 0}
        percentage={proteinProgress}
        lowerLimit={props.user.proteinLimitLower}
        upperLimit={props.user.proteinLimitUpper}
      />
    </PageContainer>
  );
};

export default DayNutrientsSummary;
