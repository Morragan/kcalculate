import React, { useState, useEffect } from "react";
import styled from "styled-components";
import Card from "react-bootstrap/Card";
import Form from "react-bootstrap/Form";
import Table from "react-bootstrap/Table";
import StyledCard from "./components/StyledCard";
import DietButton from "../../../DietButton/DietButton";

const ButtonsContainer = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  width: 100%;

  & > button {
    width: 45%;
  }
`;

const RegistrationResult = props => {
  const [result, changeResult] = useState({
    calorieLimitLower: 0,
    calorieLimit: 0,
    calorieLimitUpper: 0,
    carbsLimitLower: 0,
    carbsLimit: 0,
    carbsLimitUpper: 0,
    fatLimitLower: 0,
    fatLimit: 0,
    fatLimitUpper: 0,
    proteinLimitLower: 0,
    proteinLimit: 0,
    proteinLimitUpper: 0
  });

  const calculateGoals = () => {
    // Mifflin/St Jeor equation
    const genderFactor = props.gender === 1 ? -5 : 161;
    const bmr =
      10 * props.weight + 6.25 * props.height - 5 * props.age - genderFactor;

    let weightGoalFactor, activityLevelFactor;
    switch (props.weightGoal) {
      case 1:
        weightGoalFactor = 0.85;
        break;
      case 2:
        weightGoalFactor = 1;
        break;
      default:
        weightGoalFactor = 1.15;
        break;
    }
    switch (props.activityLevel) {
      case 1:
        activityLevelFactor = 1.3;
        break;
      case 2:
        activityLevelFactor = 1.55;
        break;
      case 3:
        activityLevelFactor = 1.65;
        break;
      case 4:
        activityLevelFactor = 1.8;
        break;
      default:
        activityLevelFactor = 2;
        break;
    }

    const calorieLimit = (bmr * weightGoalFactor * activityLevelFactor).toFixed(
      0
    );
    const calorieLimitLower = (calorieLimit * 0.95).toFixed(0);
    const calorieLimitUpper = (calorieLimit * 1.05).toFixed(0);

    const kcalInCarbGram = 4;
    const kcalInFatGram = 9;
    const kcalInProteinGram = 4;

    const carbsLimit = ((calorieLimit * 0.5) / kcalInCarbGram).toFixed(1);
    const carbsLimitLower = ((calorieLimit * 0.45) / kcalInCarbGram).toFixed(1);
    const carbsLimitUpper = ((calorieLimit * 0.65) / kcalInCarbGram).toFixed(1);

    const fatLimit = ((calorieLimit * 0.25) / kcalInFatGram).toFixed(1);
    const fatLimitLower = ((calorieLimit * 0.1) / kcalInFatGram).toFixed(1);
    const fatLimitUpper = ((calorieLimit * 0.35) / kcalInFatGram).toFixed(1);

    const proteinLimit = ((calorieLimit * 0.25) / kcalInProteinGram).toFixed(1);
    const proteinLimitLower = (
      (calorieLimit * 0.1) /
      kcalInProteinGram
    ).toFixed(1);
    const proteinLimitUpper = (
      (calorieLimit * 0.35) /
      kcalInProteinGram
    ).toFixed(1);

    changeResult({
      calorieLimitLower,
      calorieLimit,
      calorieLimitUpper,
      carbsLimitLower,
      carbsLimit,
      carbsLimitUpper,
      fatLimitLower,
      fatLimit,
      fatLimitUpper,
      proteinLimitLower,
      proteinLimit,
      proteinLimitUpper
    });
  };

  useEffect(calculateGoals, [
    props.weight,
    props.height,
    props.age,
    props.gender,
    props.weightGoal,
    props.activityLevel
  ]);

  const handleChangeFloat = e => {
    const value = parseFloat(e.target.value) ? parseFloat(e.target.value) : 0;
    changeResult({ ...result, [e.target.name]: value });
  };

  const handleChangeInt = e => {
    const value = parseInt(e.target.value) ? parseInt(e.target.value) : 0;
    changeResult({ ...result, [e.target.name]: value });
  };

  return (
    <StyledCard>
      <Card.Body>
        <Card.Title>Results</Card.Title>
        <Form
          onSubmit={e => {
            e.preventDefault();
            props.finish(
              result.calorieLimitLower,
              result.calorieLimit,
              result.calorieLimitUpper,
              result.carbsLimitLower,
              result.carbsLimit,
              result.carbsLimitUpper,
              result.fatLimitLower,
              result.fatLimit,
              result.fatLimitUpper,
              result.proteinLimitLower,
              result.proteinLimit,
              result.proteinLimitUpper
            );
          }}
        >
          <Form.Text>
            These are your recommended nutrient intake limits. Feel free to
            correct them if you disagree with the estimate.
          </Form.Text>
          <Table>
            <thead>
              <tr>
                <th></th>
                <th>Lower</th>
                <th>Goal</th>
                <th>Upper</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <th>Kcal</th>
                <td>
                  <Form.Control
                    required
                    type="number"
                    onChange={handleChangeInt}
                    value={result.calorieLimitLower}
                    name="calorieLimitLower"
                    step="any"
                    min={1}
                  />
                </td>
                <td>
                  <Form.Control
                    required
                    type="number"
                    onChange={handleChangeInt}
                    value={result.calorieLimit}
                    name="calorieLimit"
                    step="any"
                    min={1}
                  />
                </td>
                <td>
                  <Form.Control
                    required
                    type="number"
                    onChange={handleChangeInt}
                    value={result.calorieLimitUpper}
                    name="calorieLimitUpper"
                    step="any"
                    min={1}
                  />
                </td>
              </tr>
              <tr>
                <th>Carbs</th>
                <td>
                  <Form.Control
                    required
                    type="number"
                    onChange={handleChangeFloat}
                    value={result.carbsLimitLower}
                    name="carbsLimitLower"
                    step="any"
                    min={0}
                  />
                </td>
                <td>
                  <Form.Control
                    required
                    type="number"
                    onChange={handleChangeFloat}
                    value={result.carbsLimit}
                    name="carbsLimit"
                    step="any"
                    min={0}
                  />
                </td>
                <td>
                  <Form.Control
                    required
                    type="number"
                    onChange={handleChangeFloat}
                    value={result.carbsLimitUpper}
                    name="carbsLimitUpper"
                    step="any"
                    min={0}
                  />
                </td>
              </tr>
              <tr>
                <th>Fat</th>
                <td>
                  <Form.Control
                    required
                    type="number"
                    onChange={handleChangeFloat}
                    value={result.fatLimitLower}
                    name="fatLimitLower"
                    step="any"
                    min={0}
                  />
                </td>
                <td>
                  <Form.Control
                    required
                    type="number"
                    onChange={handleChangeFloat}
                    value={result.fatLimit}
                    name="fatLimit"
                    step="any"
                    min={0}
                  />
                </td>
                <td>
                  <Form.Control
                    required
                    type="number"
                    onChange={handleChangeFloat}
                    value={result.fatLimitUpper}
                    name="fatLimitUpper"
                    step="any"
                    min={0}
                  />
                </td>
              </tr>
              <tr>
                <th>Protein</th>
                <td>
                  <Form.Control
                    required
                    type="number"
                    onChange={handleChangeFloat}
                    value={result.proteinLimitLower}
                    name="proteinLimitLower"
                    step="any"
                    min={0}
                  />
                </td>
                <td>
                  <Form.Control
                    required
                    type="number"
                    onChange={handleChangeFloat}
                    value={result.proteinLimit}
                    name="proteinLimit"
                    step="any"
                    min={0}
                  />
                </td>
                <td>
                  <Form.Control
                    required
                    type="number"
                    onChange={handleChangeFloat}
                    value={result.proteinLimitUpper}
                    name="proteinLimitUpper"
                    step="any"
                    min={0}
                  />
                </td>
              </tr>
            </tbody>
          </Table>
          <ButtonsContainer>
            <DietButton onClick={calculateGoals}>Restore estimate</DietButton>
            <DietButton color="#ffa33f" type="submit" disabled={props.loading}>
              {props.loading ? "Loading..." : "Proceed"}
            </DietButton>
          </ButtonsContainer>
        </Form>
      </Card.Body>
    </StyledCard>
  );
};

export default RegistrationResult;
