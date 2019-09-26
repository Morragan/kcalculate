import React, { Component } from "react";
import { connect } from "react-redux";
import styled from "styled-components";
import Tabs from "react-bootstrap/Tabs";
import Tab from "react-bootstrap/Tab";
import Image from "react-bootstrap/Image";
import Form from "react-bootstrap/Form";
import InputGroup from "react-bootstrap/InputGroup";
import Button from "react-bootstrap/Button";
import ListGroup from "react-bootstrap/ListGroup";
import { setNavbarMode } from "../../actions/UIActions";
import { navbarModes } from "../../constants";
import FriendsListElement from "./FriendsListElement";

const StyledImage = styled(Image)`
  float: left;
  margin-left: 1vw;
  margin-right: 3vw;
  margin-top: 1.5vh;
  margin-bottom: 0.5vh;
  width: 300px;
  height: 300px;
`;

const TabContainer = styled.div`
  padding-left: 3vw;
  padding-right: 3vw;
  padding-top: 2vh;
  padding-bottom: 2vh;
  border: 1px solid #dee2e6;
  border-top: none;
  border-bottom-left-radius: 4px;
  border-bottom-right-radius: 4px;
  background-color: #fcfffc;
`;

const StyledP = styled.p`
  font-size: 1.8rem;
  color: #43474b;
`;

class UserProfile extends Component {
  state = {};

  componentDidMount() {
    if (!this.props.isUserLoggedIn) return;
    this.props.setNavbarMode(navbarModes.LOGGED_IN);
  }

  render() {
    return (
      <Tabs defaultActiveKey="profile-info">
        <Tab title="Informacje o profilu" eventKey="profile-info">
          <TabContainer>
            <StyledImage
              src={this.props.user.avatarLink}
              alt="Avatar użytkownika"
            />
            <StyledP>Nazwa: {this.props.user.nickname}</StyledP>
            <StyledP>Email: {this.props.user.email}</StyledP>
            <StyledP>
              Data dołączenia:{" "}
              {new Date(this.props.user.joinDate).toLocaleDateString()}
            </StyledP>
            <StyledP>Punkty: {this.props.user.points}</StyledP>
            <StyledP>
              Konto prywatne: {this.props.user.isPrivate ? "Tak" : "Nie"}
            </StyledP>
            <StyledP>
              Płeć: {this.props.user.gender === 1 ? "Mężczyzna" : "Kobieta"}
            </StyledP>
            <StyledP>Waga: {this.props.user.weightKg}kg</StyledP>
            <StyledP>Wzrost: {this.props.user.heightCm}cm</StyledP>
            <StyledP>Limit kalorii: {this.props.user.calorieLimit}kcal</StyledP>
          </TabContainer>
        </Tab>
        <Tab title="Znajomi" eventKey="friends">
          <TabContainer>
            <Form>
              <InputGroup>
                <Form.Control placeholder="Wpisz nazwę szukanego użytkownika" />
                <InputGroup.Append>
                  <Button type="submit">Szukaj</Button>
                </InputGroup.Append>
              </InputGroup>
            </Form>
            <br />
            <StyledP>Znajomi</StyledP>
            <hr />
            <ListGroup>
              {this.props.requestedFriends
                .concat(this.props.receivedFriends)
                .map((friend, index) => (
                  <FriendsListElement.Accepted friend={friend} key={index} />
                ))}
            </ListGroup>
          </TabContainer>
        </Tab>
      </Tabs>
    );
  }
}

const mapStateToProps = state => {
  return {
    isUserLoggedIn: state.account.isUserLoggedIn,
    user: state.account.user,
    requestedFriends: state.social.requestedFriends,
    receivedFriends: state.social.receivedFriends
  };
};

const mapDispatchToProps = dispatch => {
  return { setNavbarMode: mode => dispatch(setNavbarMode(mode)) };
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UserProfile);
