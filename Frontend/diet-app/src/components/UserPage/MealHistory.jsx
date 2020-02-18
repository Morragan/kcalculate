import React, { Component } from "react";
import styled from "styled-components";
import Jumbotron from "react-bootstrap/Jumbotron";
import Accordion from "react-bootstrap/Accordion";
import Card from "react-bootstrap/Card";
import { areDatesTheSameDay } from "../../constants";

const StyledJumbotron = styled(Jumbotron)`
  margin-top: 15px;
  height: 70vh;
  -webkit-box-shadow: 1px 1px 5px 0px rgba(0, 0, 0, 0.75);
  -moz-box-shadow: 1px 1px 5px 0px rgba(0, 0, 0, 0.75);
  box-shadow: 1px 1px 5px 0px rgba(0, 0, 0, 0.75);
  background-color: #fbf9f9;
  padding: unset;
  display: flex;
  flex-direction: column;
`;

const StyledAccordion = styled(Accordion)`
  margin-bottom: 4px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.125);

  & > .card > .card-header {
    background-color: #ffb44f;
    font-weight: 500;
    color: white;
    display: flex;
    justify-content: space-between;

    span:first-child {
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
  }
`;

const JumbotronHeader = styled.div`
  min-height: 48px;
  background-color: #ffa33f;
  border: 1px solid #c2c2c2;
  border-bottom: 2px solid #c2c2c2;
  border-top-left-radius: 0.3rem;
  border-top-right-radius: 0.3rem;
  display: flex;

  & > span {
    margin: auto auto;
    text-align: center;
    color: white;
    font-size: 17px;
    font-weight: 600;
  }
`;

const JumbotronBody = styled.div`
  padding: 0rem 1rem;
  overflow-y: auto;

  .accordion:first-child {
    margin-top: 1rem;
  }

  .accordion:last-child {
    margin-bottom: 1rem;
  }

  .accordion {
    margin-bottom: 0.4rem;
  }

  ::-webkit-scrollbar {
    width: 10px;
  }

  ::-webkit-scrollbar-track {
    background: #f1f1f1;
    border-bottom-right-radius: 8px;
    border-top-right-radius: 8px;
  }

  ::-webkit-scrollbar-thumb {
    background: #ffb44f;
    border-radius: 8px;
  }
`;

const StyledMessage = styled.p`
  text-align: center;
  font-weight: 500;
  font-size: 22px;
  margin-top: 10px;
`;

const StyledCardBody = styled(Card.Body)`
  display: grid;
  grid-template-areas:
    "title title title title title title"
    "kcal kcal kcal weight weight weight"
    "carbs carbs fat fat protein protein";
  justify-content: space-between;
  padding: 0.5rem 1rem;

  .title {
    grid-area: title;
    text-align: center;
    font-size: 18px;
    margin-bottom: 5px;
  }

  .kcal {
    grid-area: kcal;
  }

  .weight {
    grid-area: weight;
    text-align: end;
  }

  .carbs {
    grid-area: carbs;
  }

  .fat {
    grid-area: fat;
    text-align: center;
  }

  .protein {
    grid-area: protein;
    text-align: end;
  }
`;

class MealHistory extends Component {
  convertTimeToLocal(date) {
    const newDate = new Date(date);
    const offset = date.getTimezoneOffset() / 60;
    const hours = date.getHours();
    newDate.setHours(hours - offset);
    return newDate;
  }

  render() {
    const mealsFromSelectedDay = this.props.meals.filter(meal =>
      areDatesTheSameDay(this.props.selectedDate, new Date(meal.date))
    );
    if (
      mealsFromSelectedDay.length === 0 &&
      areDatesTheSameDay(this.props.selectedDate, new Date())
    )
      return (
        <StyledJumbotron>
          <JumbotronHeader>
            <span>Meals history</span>
          </JumbotronHeader>
          <StyledMessage>Time for breakfast!</StyledMessage>
        </StyledJumbotron>
      );
    else if (mealsFromSelectedDay.length === 0)
      return (
        <StyledJumbotron>
          <JumbotronHeader>
            <span>Meals history</span>
          </JumbotronHeader>
          <StyledMessage>Nothing here!</StyledMessage>
        </StyledJumbotron>
      );
    return (
      <StyledJumbotron>
        <JumbotronHeader>
          <span>Meals history</span>
        </JumbotronHeader>
        <JumbotronBody>
          {mealsFromSelectedDay
            .sort((a, b) => new Date(b.date) - new Date(a.date))
            .map(meal => {
              let newMeal = Object.assign({}, meal);
              if (meal.date[meal.date.length - 1] === "Z")
                newMeal.date = meal.date.slice(0, -1);
              return newMeal;
            })
            .map((meal, index) => (
              <StyledAccordion defaultActiveKey="0" key={index}>
                <Card>
                  <Accordion.Toggle as={Card.Header} variant="success">
                    <span>{meal.name}</span>
                    <span>
                      {this.convertTimeToLocal(
                        new Date(meal.date)
                      ).toLocaleTimeString()}
                    </span>
                  </Accordion.Toggle>
                  <Accordion.Collapse>
                    <StyledCardBody>
                      <h1 className="title">Total nutrients eaten:</h1>
                      <span className="weight">Weight: {meal.weightGram}g</span>
                      <span className="kcal">Calories: {meal.kcal}</span>
                      <span className="carbs">
                        Carbs: {meal.carbs.toFixed(2)}g
                      </span>
                      <span className="fat">Fat: {meal.fat.toFixed(2)}g</span>
                      <span className="protein">
                        Protein: {meal.protein.toFixed(2)}g
                      </span>
                    </StyledCardBody>
                  </Accordion.Collapse>
                </Card>
              </StyledAccordion>
            ))}
        </JumbotronBody>
      </StyledJumbotron>
    );
  }
}

export default MealHistory;
