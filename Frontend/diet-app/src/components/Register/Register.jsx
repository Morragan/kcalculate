import React, { Component } from "react";
import { setNavbarMode } from "../../actions/UIActions";
import { connect } from "react-redux";
import { navbarModes } from "../../constants";
import { register, checkNicknameTaken } from "../../api/AccountAPI";
import { toast } from "react-toastify";
import { Redirect } from "react-router-dom";
import RegistrationForm from "./RegistrationForm/RegistrationForm";

class Register extends Component {
  state = {
    formActiveIndex: 0,
    email: "",
    password: "",
    nickname: "",
    avatarLink: "",
    height: 0,
    weight: 0,
    gender: 1,
    age: 0,
    weightGoal: 2,
    activityLevel: 2,
    redirect: false,
    loading: false
  };
  componentDidMount() {
    this.props.setNavbarMode(navbarModes.EMPTY);
  }

  callbackFacebook = response => {
    console.log(response);
  };

  callbackGoogle = response => {
    console.log(response);
  };

  handleCarouselSelect = value => {
    this.setState({ formActiveIndex: value });
  };

  checkNicknameTaken = iconRef => {
    checkNicknameTaken({ nickname: this.state.nickname })
      .then(data => {
        //TODO: ustawić style
      })
      .catch(reason => console.log("check nickname", reason));
  };

  checkEmailTaken = iconRef => {};

  handleBasicsSubmit = (email, password, nickname, avatarLink) => {
    this.setState({ email, password, nickname, avatarLink });
  };

  handleMeasurementsSubmit = (weight, height, gender, age) => {
    this.setState({ weight, height, gender, age });
  };

  handleQuizSubmit = (weightGoal, activityLevel) => {
    this.setState({ weightGoal, activityLevel });
  };

  handleRegisterSubmit = async (
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
  ) => {
    this.setState({ loading: true });
    await register({
      email: this.state.email,
      password: this.state.password,
      nickname: this.state.nickname,
      avatarLink: this.state.avatarLink,
      calorieLimit,
      calorieLimitLower,
      calorieLimitUpper,
      carbsLimit,
      carbsLimitLower,
      carbsLimitUpper,
      fatLimit,
      fatLimitLower,
      fatLimitUpper,
      proteinLimit,
      proteinLimitLower,
      proteinLimitUpper
    })
      .then(() => {
        toast.success(this.state.nickname + " successfully registered");
        this.setState({ redirect: true });
      })
      .catch(reason => {
        toast.error(`Registration failed\n${reason}`);
      })
      .finally(() => this.setState({ loading: false }));
  };

  render() {
    if (this.state.redirect) return <Redirect to="/login" />;
    return (
      <RegistrationForm
        handleCarouselSelect={this.handleCarouselSelect}
        activeIndex={this.state.formActiveIndex}
        callbackFacebook={this.callbackFacebook}
        callbackGoogle={this.callbackGoogle}
        handleBasicsSubmit={this.handleBasicsSubmit}
        handleMeasurementsSubmit={this.handleMeasurementsSubmit}
        handleQuizSubmit={this.handleQuizSubmit}
        handleFinish={this.handleRegisterSubmit}
        weight={this.state.weight}
        height={this.state.height}
        age={this.state.age}
        gender={this.state.gender}
        weightGoal={this.state.weightGoal}
        activityLevel={this.state.activityLevel}
        loading={this.state.loading}
      />
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
