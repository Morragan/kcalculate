import React, { Component } from "react";
import styled from "styled-components";
import { connect } from "react-redux";
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import Image from "react-bootstrap/Image";
import { LinkContainer } from "react-router-bootstrap";
import { navbarModes } from "../../constants";
import {
  setUserLoggedStatus,
  saveUserData
} from "../../actions/accountActions";
import { deleteDataFromCookie } from "../../api/TokenAPI";
import { setAddMealModalVisibility } from "../../actions/UIActions";

const StyledNavbar = styled(Navbar)`
  position: sticky;
  top: 0;
  background-color: #ffa33f;
  color: white;
  padding: 0;
  & > .navbar-brand {
    padding: 0;
  }
`;
/*background-color: #4cbb17; /* Kelly */
// {
//   /* <color name="colorPrimary">#ffa33f</color>
//     <color name="colorPrimaryDark">#f78914</color>
//     <color name="colorPrimaryLight">#ffd66f</color>
//     <color name="colorAccent">#ffb34f</color> */
// }

const StyledNavLink = styled(Nav.Link)`
  color: #fafffa !important;
  font-weight: 600;
  &:hover {
    color: white !important;
    text-shadow: 0px 0px 1px white;
  }
`;

const LogoBackground = styled.div`
  padding: 6px 24px;
  -moz-border-radius: 50% / 250px;
  -webkit-border-radius: 40% / 250px;
  border-radius: 50% / 250px;
  background-color: rgba(255, 214, 111, 1);
  z-index: 100;
  position: relative;

  -webkit-box-shadow: 0px 0px 28px 3px rgba(255, 214, 111, 1);
  -moz-box-shadow: 0px 0px 28px 3px rgba(255, 214, 111, 1);
  box-shadow: 0px 0px 28px 3px rgba(255, 214, 111, 1);
`;

class DietNavbar extends Component {
  logout = () => {
    deleteDataFromCookie("access_token");
    deleteDataFromCookie("refresh_token");
    deleteDataFromCookie("expiration");
    this.props.setUserLoggedStatus(false);
    this.props.clearUserData();
  };

  getNavbarContent = () => {
    switch (this.props.mode) {
      case navbarModes.LOGGED_IN:
        return (
          <>
            <StyledNavLink onClick={this.props.showAddMealModal}>
              Add meal
            </StyledNavLink>
            <LinkContainer to="/profile">
              <StyledNavLink>Profile</StyledNavLink>
            </LinkContainer>

            <StyledNavLink onClick={this.logout}>Log out</StyledNavLink>
          </>
        );
      case navbarModes.LOGGED_OUT:
        return (
          <>
            <LinkContainer to="/login">
              <StyledNavLink>Log in</StyledNavLink>
            </LinkContainer>
            <LinkContainer to="/register">
              <StyledNavLink>Sign up</StyledNavLink>
            </LinkContainer>
          </>
        );
      case navbarModes.EMPTY:
        return <></>;
      default:
        return <></>;
    }
  };

  render() {
    let navbarContent = this.getNavbarContent();
    return (
      <StyledNavbar variant="dark" fixed="top">
        <LinkContainer to="/">
          <Navbar.Brand>
            <LogoBackground>
              <Image src="navlogo.png" />
            </LogoBackground>
          </Navbar.Brand>
        </LinkContainer>
        <Navbar.Collapse>
          <Nav className="ml-auto">{navbarContent}</Nav>
        </Navbar.Collapse>
      </StyledNavbar>
    );
  }
}

const mapStateToProps = state => {
  return {
    mode: state.UI.navbarMode
  };
};

const mapDispatchToProps = dispatch => {
  return {
    setUserLoggedStatus: isLoggedIn =>
      dispatch(setUserLoggedStatus(isLoggedIn)),
    showAddMealModal: () => dispatch(setAddMealModalVisibility(true)),
    clearUserData: () => dispatch(saveUserData({}))
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(DietNavbar);
