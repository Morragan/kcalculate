import React, { useState } from "react";
import StyledCard from "./components/StyledCard";
import Card from "react-bootstrap/Card";
import Form from "react-bootstrap/Form";
import InputGroup from "react-bootstrap/InputGroup";
import DietButton from "../../../DietButton/DietButton";

const RegistrationMeasurements = props => {
  const [measurements, changeMeasurements] = useState({
    weight: 1,
    height: 1,
    gender: 1,
    age: 1
  });

  const handleChange = e => {
    const value = parseFloat(e.target.value) ? parseFloat(e.target.value) : 1;
    changeMeasurements({
      ...measurements,
      [e.target.name]: value
    });
  };

  return (
    <StyledCard>
      <Card.Body>
        <Card.Title>Signing up</Card.Title>
        <Form
          onSubmit={e => {
            e.preventDefault();
            props.submitMeasurements(
              measurements.weight,
              measurements.height,
              measurements.gender,
              measurements.age
            );
            props.handleNext();
          }}
        >
          <Form.Group>
            <Form.Label>Weight</Form.Label>
            <InputGroup>
              <Form.Control
                required
                type="number"
                placeholder="Your weight"
                value={measurements.weight}
                name="weight"
                onChange={handleChange}
                step="any"
                min={1}
              />
              <InputGroup.Append>
                <InputGroup.Text>kg</InputGroup.Text>
              </InputGroup.Append>
            </InputGroup>
          </Form.Group>
          <Form.Group>
            <Form.Label>Height</Form.Label>
            <InputGroup>
              <Form.Control
                required
                type="number"
                placeholder="Your height"
                value={measurements.height}
                name="height"
                onChange={handleChange}
                step="any"
                min={1}
              />
              <InputGroup.Append>
                <InputGroup.Text>cm</InputGroup.Text>
              </InputGroup.Append>
            </InputGroup>
          </Form.Group>
          <Form.Group>
            <Form.Label>Biological sex</Form.Label>
            <Form.Control
              required
              as="select"
              onChange={handleChange}
              value={measurements.gender}
              name="gender"
            >
              <option value="1">Female</option>
              <option value="2">Male</option>
            </Form.Control>
          </Form.Group>
          <Form.Group>
            <Form.Label>Age</Form.Label>
            <Form.Control
              required
              type="number"
              placeholder="Your age"
              value={measurements.age}
              name="age"
              onChange={handleChange}
              step={1}
              min={1}
            />
          </Form.Group>
          <DietButton color="#ffa33f" type="submit">
            Next
          </DietButton>
        </Form>
      </Card.Body>
    </StyledCard>
  );
};

export default RegistrationMeasurements;
