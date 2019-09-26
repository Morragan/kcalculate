import React, { Component } from "react";
import { connect } from "react-redux";
import { Route, Switch, Redirect } from "react-router-dom";
import Home from "./components/Home/Home.jsx";
import Login from "./components/Authorization/Login.jsx";
import Register from "./components/Authorization/Register.jsx";
import UserPage from "./components/UserPage/UserPage.jsx";
import UserProfile from "./components/UserProfile/UserProfile.jsx";

const mapStateToProps = state => {
  return {
    isUserLoggedIn: state.account.isUserLoggedIn
  };
};

const ProtectedRoute = connect(mapStateToProps)(
  ({ component: Component, ...rest }) => {
    return (
      <Route
        {...rest}
        render={props => {
          return rest.isUserLoggedIn ? (
            <Component {...props} />
          ) : (
            <Redirect to="/login" />
          );
        }}
      />
    );
  }
);

class Routes extends Component {
  render() {
    return (
      <Switch>
        <Route exact path="/" component={Home} />
        <Route exact path="/login" component={Login} />
        <Route exact path="/register" component={Register} />
        <ProtectedRoute exact path="/user-page" component={UserPage} />
        <ProtectedRoute exact path="/profile" component={UserProfile} />
      </Switch>
    );
  }
}

export default connect(mapStateToProps)(Routes);
