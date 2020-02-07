import Button from "react-bootstrap/Button";
import styled from "styled-components";

const DietButton = styled(Button)`
  background: ${props => props.color};
  border-color: ${props => props.color};
  &&:hover {
    background: ${props => props.color} !important;
    border-color: ${props => props.color} !important;
    filter: brightness(107%);
  }
  &&:active {
    background: ${props => props.color};
    border-color: ${props => props.color};
  }
  &&:focus {
    background: ${props => props.color} !important;
    border-color: ${props => props.color} !important;
    filter: brightness(93%);
  }
  &&:disabled {
    background: ${props => props.color};
    border-color: ${props => props.color};
    filter: grayscale(30%);
  }
`;

export default DietButton;
