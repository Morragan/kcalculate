import React, { Component } from "react";
import styled from "styled-components";
import { connect } from "react-redux";
import InputGroup from "react-bootstrap/InputGroup";
import Button from "react-bootstrap/Button";
import { default as BaseDatePicker } from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { areDatesTheSameDay } from "../../constants";

const StyledBaseDatePicker = styled(BaseDatePicker)`
  height: 8vh;
  width: 70vw;
  border-style: solid;
  border-color: #4cbb17;
  border-width: 2.5px 0px;
  text-align: center;
  display: inline-block;
`;

const StyledButton = styled(Button)`
  height: 8vh;
  width: 4.2vw;
  min-width: 50px;
  background: #4cbb17;
  border-color: #4cbb17;
  display: inline-block;
  outline: none;
  &&:hover {
    background: #5dcc28;
    border-color: #5dcc28;
  }
  &&:active {
    background: #4cbb17;
    border-color: #4cbb17;
  }
  &&:focus {
    background: #3baa06;
    border-color: #3baa06;
  }
`;

const StyledButtonBackward = styled(StyledButton)`
  background-color: ${props => {
    if (areDatesTheSameDay(props.userjoindate, props.selecteddate))
      return "grey";
  }};
`;

const StyledButtonForward = styled(StyledButton)`
  background-color: ${props => {
    if (areDatesTheSameDay(new Date(), props.selecteddate)) return "grey";
  }};
`;

const StyledInputGroup = styled(InputGroup)`
  width: 100%;
  font-size: 32px;
  input {
    color: #54585c;
  }
  justify-content: center;
  margin-top: 10px;
  margin-bottom: 10px;
`;

class DatePicker extends Component {
  static defaultProps = {
    userJoinDate: new Date()
  };

  goOneDayForward = () => {
    const { selectedDate } = this.props;

    if (areDatesTheSameDay(selectedDate, new Date())) return;

    const nextDay = new Date(Number(selectedDate));
    nextDay.setDate(selectedDate.getDate() + 1);
    this.props.handleChange(nextDay);
  };

  goOneDayBackward = () => {
    const { selectedDate } = this.props;

    if (areDatesTheSameDay(selectedDate, this.props.userJoinDate)) return;

    const previousDay = new Date(Number(selectedDate));
    previousDay.setDate(selectedDate.getDate() - 1);
    this.props.handleChange(previousDay);
  };

  render() {
    return (
      <div className={this.props.className}>
        <StyledInputGroup size="lg">
          <InputGroup.Prepend>
            <StyledButtonBackward
              variant="success"
              onClick={this.goOneDayBackward}
              userjoindate={this.props.userJoinDate}
              selecteddate={this.props.selectedDate}
            >
              ◁
            </StyledButtonBackward>
          </InputGroup.Prepend>
          <StyledBaseDatePicker
            selected={this.props.selectedDate}
            onChange={this.props.handleChange}
            minDate={this.props.userJoinDate}
            maxDate={new Date()}
            dateFormat="dd MMMM, yyyy"
          />
          <InputGroup.Append>
            <StyledButtonForward
              variant="success"
              onClick={this.goOneDayForward}
              selecteddate={this.props.selectedDate}
              disabled={areDatesTheSameDay(this.props.selectedDate, new Date())}
            >
              ▷
            </StyledButtonForward>
          </InputGroup.Append>
        </StyledInputGroup>
      </div>
    );
  }
}

const mapStateToProps = state => {
  return { userJoinDate: new Date(state.account.user.joinDate) };
};

export default connect(mapStateToProps)(DatePicker);
