import React, { Component } from "react";
import { navbarModes } from "../../constants";
import { setNavbarMode } from "../../actions/UIActions";
import { connect } from "react-redux";
import { Redirect } from "react-router-dom";

class Home extends Component {
  componentDidMount() {
    this.props.setNavbarMode(navbarModes.LOGGED_OUT);
  }
  render() {
    if (this.props.isUserLoggedIn) return <Redirect to="/user-page" />;
    else return <Redirect to="/login" />;
  }
}

const mapStateToProps = state => {
  return {
    isUserLoggedIn: state.account.isUserLoggedIn
  };
};

const mapDispatchToProps = dispatch => {
  return {
    setNavbarMode: mode => {
      dispatch(setNavbarMode(mode));
    }
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Home);
