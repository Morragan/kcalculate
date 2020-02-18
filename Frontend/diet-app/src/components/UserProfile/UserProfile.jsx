import React, { Component } from "react";
import {
  searchUsersByNickname,
  requestFriend,
  acceptFriend,
  deleteFriend,
  blockUser
} from "../../api/SocialAPI";
import { connect } from "react-redux";
import styled from "styled-components";
import Tabs from "react-bootstrap/Tabs";
import Tab from "react-bootstrap/Tab";
import Image from "react-bootstrap/Image";
import Form from "react-bootstrap/Form";
import InputGroup from "react-bootstrap/InputGroup";
import ListGroup from "react-bootstrap/ListGroup";
import { setNavbarMode } from "../../actions/UIActions";
import { navbarModes } from "../../constants";
import FriendsListElement from "./FriendsListElement";
import { Table } from "react-bootstrap";
import DietButton from "../DietButton/DietButton";
import { toast } from "react-toastify";
import { saveFriends } from "../../actions/socialActions";

const StyledImage = styled(Image)`
  float: left;
  margin-left: 1vw;
  margin-right: 3vw;
  margin-top: 1.2vh;
  margin-bottom: 0.5vh;
  width: 300px;
  height: 300px;
  object-fit: contain;
  cursor: pointer;
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
  font-size: 1.2rem;
  color: #43474b;
`;

const StyledTable = styled(Table)`
  width: 70%;
  margin-top: 30px;
`;

const StyledMessage = styled.span`
  font-style: italic;
  margin-left: 12px;
  font-size: 14px;
`;

class UserProfile extends Component {
  state = {
    searchedNicknameFind: "",
    searchedNicknameFriends: "",
    searchedNicknameRequests: "",
    searchedNicknameBlocked: "",
    foundUsers: [],
    searching: false
  };

  componentDidMount() {
    if (!this.props.isUserLoggedIn) return;
    this.props.setNavbarMode(navbarModes.LOGGED_IN);
  }

  handleSearchSubmit = e => {
    e.preventDefault();
    this.setState({ searching: true });
    searchUsersByNickname(this.state.searchedNicknameFind)
      .then(data => {
        if (!data.length) toast.info("No users found");
        else this.setState({ foundUsers: data });
      })
      .catch(error => console.log(error))
      .finally(() => this.setState({ searching: false }));
  };

  handleChange = e => {
    this.setState({ [e.target.name]: e.target.value });
  };

  handleSendFriendRequest = (id, completeLoading) => {
    requestFriend(id)
      .then(data => {
        this.props.saveFriends(data);
        const foundUsers = this.state.foundUsers.filter(user => user.id !== id);
        this.setState({ foundUsers });
      })
      .catch(error => console.log(error))
      .finally(() => completeLoading());
  };

  handleAcceptFriendRequest = (id, completeLoading) => {
    acceptFriend(id)
      .then(data => this.props.saveFriends(data))
      .catch(error => console.log(error))
      .finally(() => completeLoading());
  };

  handleDeleteFriend = (id, completeLoading) => {
    deleteFriend(id)
      .then(data => this.props.saveFriends(data))
      .catch(error => console.log(error))
      .finally(() => completeLoading());
  };

  handleBlockUser = (id, completeLoading) => {
    blockUser(id)
      .then(data => {
        this.props.saveFriends(data);
        const foundUsers = this.state.foundUsers.filter(user => user.id !== id);
        this.setState({ foundUsers });
      })
      .catch(error => console.log(error))
      .finally(() => completeLoading());
  };

  sortFriends = () => {
    const friends = this.props.requestedFriends
      .concat(this.props.receivedFriends)
      .filter(friend => friend.status === 2);
    const pendingRequests = this.props.receivedFriends.filter(
      friend => friend.status === 1
    );
    const requestedFriends = this.props.requestedFriends.filter(
      friend => friend.status === 1
    );
    const blockedUsers = this.props.requestedFriends.filter(
      friend => friend.status === 3
    );

    return { friends, pendingRequests, requestedFriends, blockedUsers };
  };

  render() {
    const {
      friends,
      pendingRequests,
      requestedFriends,
      blockedUsers
    } = this.sortFriends();

    const friendsMapped = friends
      .filter(friend =>
        friend.nickname
          .toLowerCase()
          .includes(this.state.searchedNicknameFriends.toLowerCase())
      )
      .map((friend, index) => (
        <FriendsListElement.Accepted
          friend={friend}
          key={index}
          handleDeleteFriend={this.handleDeleteFriend}
        />
      ));

    const pendingRequestsMapped = pendingRequests
      .filter(friend =>
        friend.nickname
          .toLowerCase()
          .includes(this.state.searchedNicknameRequests.toLowerCase())
      )
      .map((friend, index) => (
        <FriendsListElement.Requesting
          friend={friend}
          key={index}
          handleAcceptFriendRequest={this.handleAcceptFriendRequest}
          handleDeleteFriend={this.handleDeleteFriend}
        />
      ));

    const requestedFriendsMapped = requestedFriends
      .filter(friend =>
        friend.nickname
          .toLowerCase()
          .includes(this.state.searchedNicknameRequests.toLowerCase())
      )
      .map((friend, index) => (
        <FriendsListElement.Requested
          friend={friend}
          key={`2_${index}`}
          handleDeleteFriend={this.handleDeleteFriend}
        />
      ));

    const blockedUsersMapped = blockedUsers
      .filter(friend =>
        friend.nickname
          .toLowerCase()
          .includes(this.state.searchedNicknameBlocked.toLowerCase())
      )
      .map((user, index) => (
        <FriendsListElement.Blocked
          friend={user}
          key={index}
          handleDeleteFriend={this.handleDeleteFriend}
        />
      ));

    return (
      <Tabs defaultActiveKey="profile-info" style={{ marginTop: "24px" }}>
        <Tab title="Profile info" eventKey="profile-info">
          <TabContainer>
            <StyledImage src={this.props.user.avatarLink} alt="User avatar" />
            <StyledP>
              <strong>Nickname:</strong> {this.props.user.nickname}
            </StyledP>
            <StyledP>
              <strong>Email:</strong> {this.props.user.email}
            </StyledP>
            <StyledP>
              <strong>Join date:</strong>{" "}
              {new Date(this.props.user.joinDate).toLocaleDateString()}
            </StyledP>
            <StyledP>
              <strong>Points:</strong> {this.props.user.points}
            </StyledP>
            <StyledP>
              <strong>Streak:</strong> {this.props.user.streak}
            </StyledP>
            <StyledP>
              <strong>Account privacy:</strong>{" "}
              {this.props.user.isPrivate ? "Private" : "Public"}
            </StyledP>
            <StyledP>
              <strong>Sex:</strong>{" "}
              {this.props.user.gender === 1 ? "Female" : "Male"}
            </StyledP>
            <StyledTable bordered hover>
              <thead>
                <tr>
                  <th></th>
                  <th>Lower</th>
                  <th>Goal</th>
                  <th>Upper</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <th>Kcal</th>
                  <td>{this.props.user.calorieLimitLower}</td>
                  <td>{this.props.user.calorieLimit}</td>
                  <td>{this.props.user.calorieLimitUpper}</td>
                </tr>
                <tr>
                  <th>Carbs</th>
                  <td>{this.props.user.carbsLimitLower}</td>
                  <td>{this.props.user.carbsLimit}</td>
                  <td>{this.props.user.carbsLimitUpper}</td>
                </tr>
                <tr>
                  <th>Fat</th>
                  <td>{this.props.user.fatLimitLower}</td>
                  <td>{this.props.user.fatLimit}</td>
                  <td>{this.props.user.fatLimitUpper}</td>
                </tr>
                <tr>
                  <th>Protein</th>
                  <td>{this.props.user.proteinLimitLower}</td>
                  <td>{this.props.user.proteinLimit}</td>
                  <td>{this.props.user.proteinLimitUpper}</td>
                </tr>
              </tbody>
            </StyledTable>
            {/*TODO*/}
            <DietButton>Recalculate nutrient limits</DietButton>
          </TabContainer>
        </Tab>
        <Tab title="Friends" eventKey="friends">
          <TabContainer>
            <Form.Control
              placeholder="Search for your friends"
              value={this.state.searchedNicknameFriends}
              onChange={this.handleChange}
              name="searchedNicknameFriends"
            />
            <hr />
            <ListGroup>
              {friends.length ? (
                friendsMapped
              ) : (
                <StyledMessage>
                  No friends here yet. You can find people in "Find friends" tab
                </StyledMessage>
              )}
            </ListGroup>
          </TabContainer>
        </Tab>
        <Tab title="Find friends" eventKey="find-friends">
          <TabContainer>
            <Form onSubmit={this.handleSearchSubmit}>
              <InputGroup>
                <Form.Control
                  placeholder="Enter searched user's name"
                  required
                  value={this.state.searchedNicknameFind}
                  onChange={this.handleChange}
                  name="searchedNicknameFind"
                />
                <InputGroup.Append>
                  <DietButton type="submit" disabled={this.state.searching}>
                    {this.state.searching ? "Searching..." : "Search"}
                  </DietButton>
                </InputGroup.Append>
              </InputGroup>
            </Form>
            <br />
            <StyledP>Search results</StyledP>
            <hr />
            <ListGroup>
              {this.state.foundUsers.length ? (
                this.state.foundUsers.map((user, index) => (
                  <FriendsListElement.Found
                    friend={user}
                    key={index}
                    handleSendFriendRequest={this.handleSendFriendRequest}
                    handleBlockUser={this.handleBlockUser}
                  />
                ))
              ) : (
                <StyledMessage>
                  Search results will be displayed here
                </StyledMessage>
              )}
            </ListGroup>
          </TabContainer>
        </Tab>
        <Tab title="Friend requests" eventKey="friend-requests">
          <TabContainer>
            <Form.Control
              placeholder="Search for requests"
              value={this.state.searchedNicknameRequests}
              onChange={this.handleChange}
              name="searchedNicknameRequests"
            />
            <hr />
            <ListGroup>
              {pendingRequests.concat(requestedFriends).length ? (
                pendingRequestsMapped.concat(requestedFriendsMapped)
              ) : (
                <StyledMessage>No requests here</StyledMessage>
              )}
            </ListGroup>
          </TabContainer>
        </Tab>
        <Tab title="Blocked users" eventKey="blocked-users">
          <TabContainer>
            <Form.Control
              placeholder="Search for blocked people"
              value={this.state.searchedNicknameBlocked}
              onChange={this.handleChange}
              name="searchedNicknameBlocked"
            />
            <hr />
            <ListGroup>
              {blockedUsers.length ? (
                blockedUsersMapped
              ) : (
                <StyledMessage>You haven't blocked anyone yet</StyledMessage>
              )}
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
  return {
    setNavbarMode: mode => dispatch(setNavbarMode(mode)),
    saveFriends: friends => dispatch(saveFriends(friends))
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(UserProfile);
