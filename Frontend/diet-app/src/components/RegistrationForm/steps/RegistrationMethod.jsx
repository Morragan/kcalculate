import React from "react";
import styled from "styled-components";
import StyledCard from "./components/StyledCard";
import { faFacebook } from "@fortawesome/free-brands-svg-icons";
import FacebookLogin from "react-facebook-login/dist/facebook-login-render-props";
import config from "../../../config.json";
import DietButton from "../../DietButton/DietButton";
import GoogleLogin from "react-google-login";
import Card from "react-bootstrap/Card";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Link } from "react-router-dom";

const StyledGoogleLogin = styled(GoogleLogin)`
  border: 1px solid #eee !important;
  position: relative;
  & > div {
    position: absolute;
  }
  & > span {
    width: 100%;
    text-align: center !important;
  }
`;

const StyledFacebookLogin = styled.button`
  width: 100%;
  height: 43px;
  background: #3b5998;
  color: white;
  border: 0px transparent;
  display: inline-flex;
  position: relative;

  &:hover {
    background: #3b5998;
    opacity: 0.6;
  }
  svg {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    margin-left: 3px;
  }
  p {
    margin: auto;
  }
`;

const RegistrationMethod = props => {
  return (
    <StyledCard>
      <Card.Body>
        <Card.Title>Signing up</Card.Title>
        <Card.Text>Choose sign up method</Card.Text>
        <StyledGoogleLogin
          clientId={config.GOOGLE_CLIENT_ID}
          onSuccess={props.callbackGoogle}
          onFailure={props.callbackGoogle}
          buttonText="Sign up with Google"
        />
        <br />
        <FacebookLogin
          appId={config.FACEBOOK_APP_ID}
          autoload
          callback={props.callbackFacebook}
          fields="name,email,picture"
          render={renderProps => (
            <StyledFacebookLogin onClick={renderProps.onClick}>
              <FontAwesomeIcon icon={faFacebook} size="lg" />
              <p>Sign up with Facebook</p>
            </StyledFacebookLogin>
          )}
        />
        <br />
        <DietButton color="#ffa33f" onClick={props.handleNext}>
          Sign up manually
        </DietButton>
      </Card.Body>
      <Card.Footer>
        Already have an account? <Link to="/login">Log in</Link>
      </Card.Footer>
    </StyledCard>
  );
};

export default RegistrationMethod;
