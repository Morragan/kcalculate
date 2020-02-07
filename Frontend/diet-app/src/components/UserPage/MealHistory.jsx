import React, { Component } from "react";
import styled from "styled-components";
import Jumbotron from "react-bootstrap/Jumbotron";
import Accordion from "react-bootstrap/Accordion";
import Card from "react-bootstrap/Card";
import { areDatesTheSameDay } from "../../constants";

const StyledJumbotron = styled(Jumbotron)`
  margin-top: 15px;
  overflow-y: auto;
  height: 69vh;
  -webkit-box-shadow: 1px 1px 5px 0px rgba(0, 0, 0, 0.75);
  -moz-box-shadow: 1px 1px 5px 0px rgba(0, 0, 0, 0.75);
  box-shadow: 1px 1px 5px 0px rgba(0, 0, 0, 0.75);
  background-color: #fbf9f9;
  ::-webkit-scrollbar {
    width: 10px;
  }

  ::-webkit-scrollbar-track {
    background: #f1f1f1;
  }

  ::-webkit-scrollbar-thumb {
    background: #ffb44f;
    border-radius: 8px;
  }
`;

const StyledAccordion = styled(Accordion)`
  margin-bottom: 4px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.125);

  & > .card > .card-header {
    background-color: #ffb44f;
    font-weight: 500;
    color: white;
  }
`;

const JumbotronHeader = styled.div`
  height: 3rem;
  background-color: #ffa33f;
  margin-top: -4rem;
  margin-left: -2rem;
  margin-right: -2rem;
  margin-bottom: 1rem;
  border: 1px solid #c2c2c2;
  border-bottom: 2px solid #c2c2c2;
  border-top-left-radius: 0.3rem;
  border-top-right-radius: 0.3rem;
  display: flex;

  & > span {
    margin: auto auto;
    text-align: center;
    color: white;
    font-size: 18;
    font-weight: 600;
    letter-spacing: 1.2;
  }
`;

const StyledMessage = styled.p`
  text-align: center;
  font-weight: 500;
  font-size: 22px;
`;

class MealHistory extends Component {
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
        {mealsFromSelectedDay.map((meal, index) => (
          <StyledAccordion defaultActiveKey="0" key={index}>
            <Card>
              <Accordion.Toggle as={Card.Header} variant="success">
                {meal.name}
              </Accordion.Toggle>
              <Accordion.Collapse>
                <Card.Body>{}</Card.Body>
              </Accordion.Collapse>
            </Card>
          </StyledAccordion>
        ))}
      </StyledJumbotron>
    );
  }
}

export default MealHistory;
