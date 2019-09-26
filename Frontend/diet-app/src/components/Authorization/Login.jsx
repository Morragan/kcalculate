import React, { Component } from "react";
import styled from "styled-components";
import { connect } from "react-redux";
import { navbarModes } from "../../constants";
import { Redirect } from "react-router-dom";
import Card from "react-bootstrap/Card";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import { login } from "../../api/AccountAPI";
import { toast } from "react-toastify";
import { setNavbarMode } from "../../actions/UIActions";
import { saveDataToCookie } from "../../api/TokenAPI";
import {
  setUserLoggedStatus,
  saveUserData
} from "../../actions/accountActions";
import { getUserData } from "../../api/AccountAPI";
import { Link } from "react-router-dom";
import { getFriends } from "../../api/SocialAPI";
import { saveFriends } from "../../actions/socialActions";

const StyledCard = styled(Card)`
  max-width: 500px;
  margin: 5vh auto;
  .card-body {
    display: flex;
    flex-direction: column;
  }
`;

class Login extends Component {
  state = { nickname: "", password: "", redirect: false };
  componentDidMount() {
    this.props.setNavbarMode(navbarModes.EMPTY);
  }
  componentWillUnmount() {
    this.setState = (state, callback) => {
      return;
    };
  }
  handleChange = e => {
    this.setState({ [e.target.name]: e.target.value });
  };

  handleLoginSubmit = async () => {
    await login({
      nickname: this.state.nickname,
      password: this.state.password
    })
      .then(response => {
        if (response.ok) {
          toast.success(
            `Pomyślnie zalogowano użytkownika ${this.state.nickname}`
          );
          return response.json().then(data => {
            saveDataToCookie("expiration", data.expiration);
            saveDataToCookie("access_token", data.accessToken);
            saveDataToCookie("refresh_token", data.refreshToken);
            this.props.setUserLoggedStatus(true);
            this.setState({ redirect: true });
          });
        } else {
          return response.text().then(response => {
            throw response;
          });
        }
      })
      .catch(reason => {
        toast.error(`Nie udało się zalogować użytkownika\n${reason}`);
      });

    getUserData()
      .then(response => {
        if (!response.ok) throw response;
        return response.json();
      })
      .then(data => this.props.saveUserData(data))
      .catch(reason => console.error("getUserData", reason));

    getFriends()
      .then(response => {
        if (!response.ok) throw response;
        return response.json();
      })
      .then(data => {
        console.log("FRIENDS", data);
        this.props.saveFriends(data);
      })
      .catch(reason => console.error(reason));
  };

  render() {
    if (this.state.redirect) return <Redirect to="/user-page" />;
    return (
      <StyledCard>
        <Card.Body>
          <Card.Title>Logowanie</Card.Title>
          <Form
            onSubmit={e => {
              e.preventDefault();
              this.handleLoginSubmit();
            }}
          >
            <Form.Group>
              <Form.Label>Nazwa użytkownika</Form.Label>
              <Form.Control
                required
                type="text"
                placeholder="Wpisz swoją nazwę użytkownika"
                value={this.state.nickname}
                name="nickname"
                onChange={this.handleChange}
              />
            </Form.Group>
            <Form.Group>
              <Form.Label>Hasło</Form.Label>
              <Form.Control
                required
                type="password"
                placeholder="Wpisz swoje hasło"
                value={this.state.password}
                name="password"
                onChange={this.handleChange}
              />
            </Form.Group>
            <Button type="submit">Zaloguj się</Button>
          </Form>
        </Card.Body>
        <Card.Footer>
          Nie masz konta? <Link to="/register">Zarejestruj się</Link>
        </Card.Footer>
      </StyledCard>
    );
  }
}

const mapStateToProps = state => {
  return {};
};

const mapDispatchToProps = dispatch => {
  return {
    saveUserData: userData => dispatch(saveUserData(userData)),
    saveFriends: friends => dispatch(saveFriends(friends)),
    setNavbarMode: mode => dispatch(setNavbarMode(mode)),
    setUserLoggedStatus: isLoggedIn => dispatch(setUserLoggedStatus(isLoggedIn))
  };
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Login);
