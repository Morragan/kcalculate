import React, { Component } from "react";
import styled from "styled-components";
import Routes from "../../Routes";
import DietNavbar from "../DietNavbar/DietNavbar";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.min.css";
import { connect } from "react-redux";
import { getUserData } from "../../api/AccountAPI";
import { saveUserData } from "../../actions/accountActions";
import { saveFriends } from "../../actions/socialActions";
import AddMealModal from "../Modals/AddMealModal";
import { getFriends } from "../../api/SocialAPI";

const RoutesContainer = styled.div`
  margin-left: 12vh;
  margin-right: 12vh;
`;

class App extends Component {
  componentDidMount() {
    if (this.props.isUserLoggedIn) {
      getUserData()
        .then(data => this.props.saveUserData(data))
        .catch(reason => console.error("getUserData", reason));
      getFriends()
        .then(data => {
          console.log("FRIENDS", data);
          this.props.saveFriends(data);
        })
        .catch(reason => console.error(reason));
    }
  }
  render() {
    return (
      <>
        <DietNavbar />
        <RoutesContainer>
          <Routes />
        </RoutesContainer>
        <ToastContainer
          position="bottom-right"
          autoClose={5000}
          closeOnClick
          pauseOnHover
          draggable
        />
        <AddMealModal />
      </>
    );
  }
}

const mapStateToProps = state => {
  return { isUserLoggedIn: state.account.isUserLoggedIn };
};

const mapDispatchToProps = dispatch => {
  return {
    saveUserData: userData => dispatch(saveUserData(userData)),
    saveFriends: friends => dispatch(saveFriends(friends))
  };
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(App);
