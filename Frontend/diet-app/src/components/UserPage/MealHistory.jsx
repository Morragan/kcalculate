import React, { Component } from "react";
import styled from "styled-components";
import Jumbotron from "react-bootstrap/Jumbotron";
import Accordion from "react-bootstrap/Accordion";
import Card from "react-bootstrap/Card";
import Image from "react-bootstrap/Image";
import { areDatesTheSameDay } from "../../constants";

const StyledJumbotron = styled(Jumbotron)`
  margin-top: 15px;
  overflow-y: auto;
  height: 86vh;
  padding-top: 20px;
  ::-webkit-scrollbar {
    width: 10px;
  }

  ::-webkit-scrollbar-track {
    background: #f1f1f1;
  }

  ::-webkit-scrollbar-thumb {
    background: #4cbb17;
    border-radius: 8px;
  }
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
          <p>Czas na śniadanie!</p>
        </StyledJumbotron>
      );
    else if (mealsFromSelectedDay.length === 0)
      return (
        <StyledJumbotron>
          <p>Nic tu nie ma!</p>
        </StyledJumbotron>
      );
    return (
      <StyledJumbotron>
        {mealsFromSelectedDay.map((meal, index) => (
          <Accordion defaultActiveKey="0" key={index}>
            <Card>
              <Accordion.Toggle as={Card.Header} variant="success">
                {meal.name}
              </Accordion.Toggle>
              <Accordion.Collapse>
                <Card.Body>
                  <Image src={meal.imageLink} />
                </Card.Body>
              </Accordion.Collapse>
            </Card>
          </Accordion>
        ))}
      </StyledJumbotron>
    );
  }
}

export default MealHistory;
