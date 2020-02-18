import React, { memo, useState, useEffect } from "react";
import ProgressBar from "react-bootstrap/ProgressBar";
import styled from "styled-components";

const StyledProgressBar = styled.div`
  width: 100%;
  display: grid;
  grid-template-areas:
    "name name name eaten goal left"
    "bar bar bar bar bar bar";
  grid-template-columns: repeat(3, 1fr) repeat(3, 1.2fr);

  span {
    display: block;
    width: 100%;
    text-align: center;
  }

  .name {
    grid-area: name;
    text-align: start;
    line-height: 48px;
    font-size: 20px;
    margin-left: 8px;
  }

  .eaten {
    grid-area: eaten;
  }

  .goal {
    grid-area: goal;
  }

  .left {
    grid-area: left;
  }

  .bar {
    grid-area: bar;

    .progress-bar {
      background-color: ${props => props.barColor};
    }
  }
`;

const NutrientProgressBar = props => {
  const [color, changeColor] = useState("#ffd66f");

  useEffect(() => {
    if (props.eaten > props.lowerLimit && props.eaten < props.upperLimit) {
      changeColor("#28a745");
    } else if (props.eaten < props.lowerLimit) {
      changeColor("#ffd66f");
    } else {
      changeColor("#dc3545");
    }
  }, [props.eaten, props.percentage, props.lowerLimit, props.upperLimit]);

  return (
    <StyledProgressBar barColor={color}>
      <span className="name">{props.name}</span>
      <div className="eaten">
        <span>Eaten</span>
        <span>{parseFloat(props.eaten.toFixed(2))}</span>
      </div>
      <div className="goal">
        <span>Goal</span>
        <span>{parseFloat(props.goal.toFixed(2))}</span>
      </div>
      <div className="left">
        <span>{props.left < 0 ? "Over limit" : "Left"}</span>
        <span>{parseFloat(Math.abs(props.left).toFixed(2))}</span>
      </div>
      <ProgressBar className="bar" now={props.percentage} />
    </StyledProgressBar>
  );
};

export default memo(NutrientProgressBar);
