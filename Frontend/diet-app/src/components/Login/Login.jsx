import React, { Component } from "react";
import styled from "styled-components";
import { connect } from "react-redux";
import { navbarModes } from "../../constants";
import { Redirect } from "react-router-dom";
import Card from "react-bootstrap/Card";
import Form from "react-bootstrap/Form";
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
import DietButton from "../DietButton/DietButton";

const StyledCard = styled(Card)`
  max-width: 375px;
  margin: 5vh auto;
  .card-body {
    display: flex;
    flex-direction: column;
  }

  .card-title {
    margin-bottom: 1.25rem;
    text-align: center;
    letter-spacing: 1.3px;
  }

  .card-footer {
    text-align: center;
  }

  button {
    margin: auto auto;
    display: block;
    width: 99%;
    border-radius: 20px;
  }
`;

class Login extends Component {
  state = { nickname: "", password: "", redirect: false, isLoading: false };
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
    this.setState({ isLoading: true });
    await login({
      nickname: this.state.nickname,
      password: this.state.password
    })
      .then(data => {
        toast.success(`Login successful: ${this.state.nickname}`);
        saveDataToCookie("expiration", data.expiration);
        saveDataToCookie("access_token", data.accessToken);
        saveDataToCookie("refresh_token", data.refreshToken);
        this.props.setUserLoggedStatus(true);
        this.setState({ redirect: true, isLoading: false });
      })
      .catch(reason => {
        this.setState({ isLoading: false });
        toast.error(`Login failed\n${reason}`);
      });

    getUserData()
      .then(data => this.props.saveUserData(data))
      .catch(reason => console.error("getUserData", reason));

    getFriends()
      .then(data => this.props.saveFriends(data))
      .catch(reason => console.error(reason));
  };

  render() {
    if (this.state.redirect) return <Redirect to="/user-page" />;
    return (
      <StyledCard>
        <Card.Body>
          <Card.Title>LOGIN</Card.Title>
          <Form
            onSubmit={e => {
              e.preventDefault();
              this.handleLoginSubmit();
            }}
          >
            <Form.Group>
              <Form.Control
                required
                type="text"
                placeholder="Nickname"
                value={this.state.nickname}
                name="nickname"
                onChange={this.handleChange}
              />
            </Form.Group>
            <Form.Group>
              <Form.Control
                required
                type="password"
                placeholder="Password"
                value={this.state.password}
                name="password"
                onChange={this.handleChange}
              />
            </Form.Group>
            <DietButton
              type="submit"
              color="#ffa33f"
              disabled={this.state.isLoading}
            >
              {this.state.isLoading ? "Loading..." : "Login"}
            </DietButton>
          </Form>
        </Card.Body>
        <Card.Footer>
          Don't have an account? <Link to="/register">Sign up</Link>
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

export default connect(mapStateToProps, mapDispatchToProps)(Login);
