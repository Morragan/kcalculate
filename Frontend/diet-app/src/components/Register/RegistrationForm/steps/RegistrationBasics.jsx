import React, { useState } from "react";
import StyledCard from "./components/StyledCard";
import Card from "react-bootstrap/Card";
import Form from "react-bootstrap/Form";
import InputGroup from "react-bootstrap/InputGroup";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCheck } from "@fortawesome/free-solid-svg-icons";
import DietButton from "../../../DietButton/DietButton";
import { Link } from "react-router-dom";

const RegistrationBasics = props => {
  const [basics, changeBasics] = useState({
    email: "",
    password: "",
    password2: "",
    nickname: "",
    avatarLink: ""
  });

  const handleChange = e => {
    changeBasics({ ...basics, [e.target.name]: e.target.value });
  };

  return (
    <StyledCard>
      <Card.Body>
        <Card.Title>Basics</Card.Title>
        <Form
          onSubmit={e => {
            e.preventDefault();
            props.submitBasics(
              basics.email,
              basics.password,
              basics.nickname,
              basics.avatarLink
            );
            props.handleNext();
          }}
        >
          <Form.Group>
            <Form.Label>Email</Form.Label>
            <InputGroup>
              <Form.Control
                required
                type="email"
                placeholder="Your email address"
                value={basics.email}
                name="email"
                onChange={handleChange}
              />
              <InputGroup.Append>
                <InputGroup.Text>
                  {/* TODO: sprawdzanie czy username jest dostępny */}
                  <FontAwesomeIcon icon={faCheck} size="lg" />
                </InputGroup.Text>
              </InputGroup.Append>
              <Form.Text className="text-muted">
                You will need it if you ever forget your password or want to
                change it.
              </Form.Text>
            </InputGroup>
          </Form.Group>
          <Form.Group>
            <Form.Label>Nickname</Form.Label>
            <InputGroup>
              <Form.Control
                required
                type="text"
                placeholder="Your nickname"
                value={basics.nickname}
                name="nickname"
                onChange={handleChange}
              />
              <InputGroup.Append>
                <InputGroup.Text>
                  {/* TODO: sprawdzanie czy username jest dostępny */}
                  <FontAwesomeIcon icon={faCheck} size="lg" />
                </InputGroup.Text>
              </InputGroup.Append>
            </InputGroup>
          </Form.Group>
          <Form.Group>
            <Form.Label>Password</Form.Label>
            <Form.Control
              required
              type="password"
              placeholder="Your password"
              value={basics.password}
              name="password"
              onChange={handleChange}
            />
            <Form.Text className="text-muted">
              Needs to be at least 8 characters long with at least one uppercase
              letter and one digit
              {/*Zrobić sprawdzanie tego xD*/}
            </Form.Text>
          </Form.Group>
          <Form.Group>
            <Form.Label>Confirm your password</Form.Label>
            <Form.Control
              required
              type="password"
              placeholder="Your password again"
              value={basics.password2}
              name="password2"
              onChange={handleChange}
              isInvalid={
                !!basics.password2 && basics.password !== basics.password2
              }
            />
          </Form.Group>
          <Form.Group>
            <Form.Label>Avatar link (optional)</Form.Label>
            <Form.Control
              placeholder="Online link to your profile picture"
              value={basics.avatarLink}
              name="avatarLink"
              onChange={handleChange}
            />
          </Form.Group>

          <DietButton type="submit" color="#ffa33f">
            Next
          </DietButton>
        </Form>
      </Card.Body>
      <Card.Footer>
        Already have an account? <Link to="/login">Log in</Link>
      </Card.Footer>
    </StyledCard>
  );
};

export default RegistrationBasics;
