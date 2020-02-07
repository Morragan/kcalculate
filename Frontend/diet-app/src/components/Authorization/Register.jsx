import React, { Component } from "react";
import styled from "styled-components";
import { setNavbarMode } from "../../actions/UIActions";
import { connect } from "react-redux";
import { navbarModes } from "../../constants";
import Card from "react-bootstrap/Card";
import Form from "react-bootstrap/Form";
import Carousel from "react-bootstrap/Carousel";
import InputGroup from "react-bootstrap/InputGroup";
import Button from "react-bootstrap/Button";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCheck } from "@fortawesome/free-solid-svg-icons";
import { faFacebook } from "@fortawesome/free-brands-svg-icons";
import FacebookLogin from "react-facebook-login/dist/facebook-login-render-props";
import GoogleLogin from "react-google-login";
import config from "./config";
import { register, checkNicknameTaken } from "../../api/AccountAPI";
import { toast } from "react-toastify";
import { Redirect, Link } from "react-router-dom";

const StyledCard = styled(Card)`
  max-width: 500px;
  margin: 5vh auto;
  .card-body {
    display: flex;
    flex-direction: column;
  }
`;

const StyledCarousel = styled(Carousel)``;

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

const StyledButtonRegisterManually = styled.button`
  border: none;
  background: #4cbb17;
  color: #e0eee0;
  height: 43px;
  &:hover {
    color: white;
    text-shadow: 0px 0px 1px white;
  }
`;

class Register extends Component {
  state = {
    carouselActiveIndex: 0,
    email: "",
    password: "",
    password2: "",
    nickname: "",
    height: 0,
    weight: 0,
    gender: 1,
    redirect: false
  };
  componentDidMount() {
    this.props.setNavbarMode(navbarModes.EMPTY);
    // TODO: autofocus na pola
  }

  responseFacebook = response => {
    console.log(response);
  };

  responseGoogle = response => {
    console.log(response);
  };

  handleCarouselSelect = (selectedIndex, e) => {
    this.setState({ carouselActiveIndex: selectedIndex });
  };

  handleChange = e => {
    this.setState({ [e.target.name]: e.target.value });
  };

  checkNicknameTaken = () => {
    checkNicknameTaken({ nickname: this.state.nickname })
      .then(data => {
        //TODO: ustawić style
      })
      .catch(reason => console.log("check nickname", reason));
  };

  handleRegisterSubmit = async () => {
    await register({
      email: this.state.email,
      password: this.state.password,
      nickname: this.state.nickname,
      weightKg: this.state.weight,
      heightCm: this.state.height,
      gender: this.state.gender
    })
      .then(data => {
        toast.success(this.state.nickname + " successfully registered");
      })
      .catch(reason => {
        toast.error(`Registration failed\n${reason}`);
        this.setState({ carouselActiveIndex: 0 });
      });
  };

  render() {
    if (this.state.redirect) return <Redirect to="/login" />;
    return (
      <StyledCarousel
        interval={null}
        controls={true}
        activeIndex={this.state.carouselActiveIndex}
        onSelect={this.handleCarouselSelect}
      >
        <Carousel.Item>
          <StyledCard>
            <Card.Body>
              <Card.Title>Rejestracja - krok 1 z 3</Card.Title>
              <Card.Text>Wybierz metodę rejestracji</Card.Text>
              <StyledGoogleLogin
                clientId={config.GOOGLE_CLIENT_ID}
                onSuccess={this.responseGoogle}
                onFailure={this.responseGoogle}
                buttonText="Zarejestruj się przez Google"
              />
              <br />
              <FacebookLogin
                appId={config.FACEBOOK_APP_ID}
                autoload
                callback={this.responseFacebook}
                fields="name,email,picture"
                render={renderProps => (
                  <StyledFacebookLogin onClick={renderProps.onClick}>
                    <FontAwesomeIcon icon={faFacebook} size="lg" />
                    <p>Zarejestruj się przez Facebook</p>
                  </StyledFacebookLogin>
                )}
              />
              <br />
              <StyledButtonRegisterManually
                onClick={() => {
                  this.setState({ carouselActiveIndex: 1 });
                }}
              >
                Zarejestruj się klasycznie
              </StyledButtonRegisterManually>
            </Card.Body>
            <Card.Footer>
              Masz już konto? <Link to="/login">Zaloguj się</Link>
            </Card.Footer>
          </StyledCard>
        </Carousel.Item>
        <Carousel.Item>
          <StyledCard>
            <Card.Body>
              <Card.Title>Rejestracja - Krok 2 z 3</Card.Title>
              <Form
                onSubmit={e => {
                  e.preventDefault();
                  this.setState({ carouselActiveIndex: 2 });
                }}
              >
                <Form.Group>
                  <Form.Label>Email</Form.Label>
                  <Form.Control
                    required
                    type="email"
                    placeholder="Podaj adres email"
                    value={this.state.email}
                    name="email"
                    onChange={this.handleChange}
                    autoFocus={this.state.carouselActiveIndex === 1}
                  />
                  <Form.Text className="text-muted">
                    Nigdy nie udostępnimy twojego adresu email osobom trzecim
                  </Form.Text>
                </Form.Group>
                <Form.Group>
                  <Form.Label>Nazwa użytkownika</Form.Label>
                  <InputGroup>
                    <Form.Control
                      required
                      type="text"
                      placeholder="Wybierz swoją nazwę użytkownika"
                      value={this.state.nickname}
                      name="nickname"
                      onChange={this.handleChange}
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
                  <Form.Label>Hasło</Form.Label>
                  <Form.Control
                    required
                    type="password"
                    placeholder="Wybierz swoje hasło"
                    value={this.state.password}
                    name="password"
                    onChange={this.handleChange}
                  />
                  <Form.Text className="text-muted">
                    Hasło musi mieć co najmniej 8 znaków, w tym co najmniej
                    jedną wielką literę i jeden znak specjalny
                    {/*Zrobić sprawdzanie tego xD*/}
                  </Form.Text>
                </Form.Group>
                <Form.Group>
                  <Form.Label>Potwierdź hasło</Form.Label>
                  <Form.Control
                    required
                    type="password"
                    placeholder="Ponownie wpisz wybrane hasło"
                    value={this.state.password2}
                    name="password2"
                    onChange={this.handleChange}
                    isInvalid={
                      !!this.state.password2 &&
                      this.state.password !== this.state.password2
                    }
                  />
                </Form.Group>
                <Button type="submit">Hajże</Button>
              </Form>
            </Card.Body>
          </StyledCard>
        </Carousel.Item>
        <Carousel.Item>
          <StyledCard>
            <Card.Body>
              <Card.Title>Rejestracja - Krok 3 z 3</Card.Title>
              <Form
                onSubmit={e => {
                  e.preventDefault();
                  this.handleRegisterSubmit();
                }}
              >
                <Form.Group>
                  <Form.Label>Waga</Form.Label>
                  <InputGroup>
                    <Form.Control
                      required
                      type="number"
                      placeholder="Wpisz swoją wagę"
                      value={this.state.weight}
                      name="weight"
                      onChange={this.handleChange}
                      autoFocus={this.state.carouselActiveIndex === 2}
                    />
                    <InputGroup.Append>
                      <InputGroup.Text>kg</InputGroup.Text>
                    </InputGroup.Append>
                  </InputGroup>
                </Form.Group>
                <Form.Group>
                  <Form.Label>Wzrost</Form.Label>
                  <InputGroup>
                    <Form.Control
                      required
                      type="number"
                      placeholder="Wpisz swój wzrost"
                      value={this.state.height}
                      name="height"
                      onChange={this.handleChange}
                    />
                    <InputGroup.Append>
                      <InputGroup.Text>cm</InputGroup.Text>
                    </InputGroup.Append>
                  </InputGroup>
                </Form.Group>
                <Form.Group>
                  <Form.Label>Płeć biologiczna</Form.Label>
                  <Form.Control required as="select">
                    <option value="1">Kobieta</option>
                    <option value="2">Mężczyzna</option>
                  </Form.Control>
                </Form.Group>

                <Button type="submit">Hajże</Button>
              </Form>
            </Card.Body>
          </StyledCard>
        </Carousel.Item>
      </StyledCarousel>
    );
  }
}

const mapStateToProps = state => {
  return {};
};

const mapDispatchToProps = dispatch => {
  return {
    setNavbarMode: mode => {
      dispatch(setNavbarMode(mode));
    }
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Register);
