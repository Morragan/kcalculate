import React, { useState } from "react";
import StyledCard from "./components/StyledCard";
import Card from "react-bootstrap/Card";
import Form from "react-bootstrap/Form";
import DietButton from "../../../DietButton/DietButton";

const RegistrationQuiz = props => {
  const [quiz, changeQuiz] = useState({ weightGoal: 2, activityLevel: 2 });

  const handleChange = e => {
    changeQuiz({ ...quiz, [e.target.name]: parseInt(e.target.value) });
  };

  return (
    <StyledCard>
      <Card.Body>
        <Card.Title>Questionnaire</Card.Title>
        <Form
          onSubmit={e => {
            e.preventDefault();
            props.submitQuiz(quiz.weightGoal, quiz.activityLevel);
            props.handleNext();
          }}
        >
          <Form.Label>Your goal is to</Form.Label>
          <Form.Check
            type="radio"
            name="weightGoal"
            label="Lose weight"
            value={1}
            onChange={handleChange}
          />
          <Form.Check
            type="radio"
            name="weightGoal"
            label="Maintain weight"
            value={2}
            onChange={handleChange}
            defaultChecked
          />
          <Form.Check
            type="radio"
            name="weightGoal"
            label="Gain weight"
            value={3}
            onChange={handleChange}
          />
          <Form.Label>Your daily activity level is</Form.Label>
          <Form.Check
            type="radio"
            name="activityLevel"
            label="Very low"
            value={1}
            onChange={handleChange}
          />
          <Form.Text className="text-muted">
            Sitting job and no exercising
          </Form.Text>
          <Form.Check
            type="radio"
            name="activityLevel"
            label="Low"
            value={2}
            onChange={handleChange}
            defaultChecked
          />
          <Form.Text className="text-muted">
            Job that involves moving around, some exercising
          </Form.Text>
          <Form.Check
            type="radio"
            name="activityLevel"
            label="Moderate"
            value={3}
            onChange={handleChange}
          />
          <Form.Text className="text-muted">
            Job that involves moving around a lot, regular exercises
          </Form.Text>
          <Form.Check
            type="radio"
            name="activityLevel"
            label="High"
            value={4}
            onChange={handleChange}
          />
          <Form.Text className="text-muted">
            Physical-oriented job, regular sports or exercises
          </Form.Text>
          <Form.Check
            type="radio"
            name="activityLevel"
            label="Very high"
            value={5}
            onChange={handleChange}
          />
          <Form.Text className="text-muted">
            For heavy-duty workers and people that work out every day
          </Form.Text>
          <DietButton color="#ffa33f" type="submit">
            Next
          </DietButton>
        </Form>
      </Card.Body>
    </StyledCard>
  );
};

export default RegistrationQuiz;
