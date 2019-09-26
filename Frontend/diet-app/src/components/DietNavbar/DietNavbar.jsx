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
  background-color: #4cbb17; /* Kelly */
  color: white;
`;

const StyledNavLink = styled(Nav.Link)`
  color: #e0eee0 !important;
  &:hover {
    color: white !important;
    text-shadow: 0px 0px 1px white;
  }
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
              Dodaj wpis
            </StyledNavLink>
            <LinkContainer to="/profile">
              <StyledNavLink>Profil</StyledNavLink>
            </LinkContainer>

            <StyledNavLink onClick={this.logout}>Wyloguj się</StyledNavLink>
          </>
        );
      case navbarModes.LOGGED_OUT:
        return (
          <>
            <LinkContainer to="/login">
              <StyledNavLink>Zaloguj się</StyledNavLink>
            </LinkContainer>
            <LinkContainer to="/register">
              <StyledNavLink>Zarejestruj się</StyledNavLink>
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
            <Image src="logo" />
            DietApp
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

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DietNavbar);
