import React, { useState, useEffect } from "react";
import styled from "styled-components";
import { Row, Col, Form, ListGroup, InputGroup } from "react-bootstrap";

const StyledMealsListElementContent = styled.div`
  display: grid;
  grid-template-areas:
    "name name weight weight"
    "kcal carbs fat protein";
  grid-template-rows: auto;
  grid-template-columns: 1fr 1fr 1fr 1fr;

  & > .meal-name {
    grid-area: name;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  & > .meal-weight {
    grid-area: weight;
  }
  & > .meal-kcal {
    grid-area: kcal;
  }
  & > .meal-carbs {
    grid-area: carbs;
  }
  & > .meal-fat {
    grid-area: fat;
  }
  & > .meal-protein {
    grid-area: protein;
  }
`;

const StyledListGroupItem = styled(ListGroup.Item)`
  &:focus-within {
    background-color: #ffd66f;
    font-weight: 700;
    color: white;
  }
`;

const MealsListItem = props => {
  const [weightGram, changeWeight] = useState(0);
  const [touched, changeTouched] = useState(false);
  const [focused, changeFocused] = useState(false);
  const [invalid, changeInvalid] = useState(false);

  useEffect(() => {
    changeWeight(0);
  }, [props.meal]);

  useEffect(() => {
    if (touched && focused && weightGram === 0) changeInvalid(true);
    else changeInvalid(false);
  }, [touched, focused, weightGram]);

  useEffect(() => {
    if (!touched && weightGram !== 0) changeTouched(true);
  }, [touched, weightGram]);

  const handleFocus = () => {
    changeFocused(true);
    props.saveSelectedMealToState(props.meal, weightGram);
  };

  const handleBlur = () => changeFocused(false);

  const handleSubmit = e => {
    e.preventDefault();
    changeTouched(true);
    if (weightGram === 0) return;
    props.recordMeal(weightGram, props.meal.name, props.meal.nutrients);
  };

  const handleWeightChange = e => {
    const newValue = parseFloat(e.target.value) || 0;
    changeWeight(newValue);
    props.saveSelectedMealToState(props.meal, newValue);
  };

  return (
    <Form onSubmit={handleSubmit}>
      <StyledListGroupItem
        action
        type="button"
        onFocus={handleFocus}
        onBlur={handleBlur}
      >
        <StyledMealsListElementContent>
          <p className="meal-name">{props.meal.name}</p>
          <Form.Group as={Row} className="meal-weight">
            <Form.Label column sm={2}>
              Weight
            </Form.Label>
            <InputGroup as={Col} sm={10}>
              <Form.Control
                required
                type="number"
                isInvalid={invalid}
                value={weightGram.toString()}
                onChange={handleWeightChange}
                name="weightGram"
                min={0}
              />
              <InputGroup.Append>
                <InputGroup.Text>g</InputGroup.Text>
              </InputGroup.Append>
            </InputGroup>
          </Form.Group>
          <p className="meal-kcal">{props.meal.nutrients.kcal}kcal</p>
          <p className="meal-carbs">Carbs: {props.meal.nutrients.carbs}g</p>
          <p className="meal-fat">Fat: {props.meal.nutrients.fat}g</p>
          <p className="meal-protein">
            Protein: {props.meal.nutrients.protein}g
          </p>
        </StyledMealsListElementContent>
      </StyledListGroupItem>
    </Form>
  );
};

export default MealsListItem;
