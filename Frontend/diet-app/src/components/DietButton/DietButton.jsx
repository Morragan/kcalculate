import Button from "react-bootstrap/Button";
import styled from "styled-components";

const DietButton = styled(Button)`
  background: ${props => props.color || "#ffa33f"};
  border-color: ${props => props.color || "#ffa33f"};
  &&:hover {
    background: ${props => props.color || "#ffa33f"} !important;
    border-color: ${props => props.color || "#ffa33f"} !important;
    filter: brightness(107%);
  }
  &&:active {
    background: ${props => props.color || "#ffa33f"};
    border-color: ${props => props.color || "#ffa33f"};
  }
  &&:focus {
    background: ${props => props.color || "#ffa33f"} !important;
    border-color: ${props => props.color || "#ffa33f"} !important;
    filter: brightness(93%);
  }
  &&:disabled {
    background: ${props => props.color || "#ffa33f"};
    border-color: ${props => props.color || "#ffa33f"};
    filter: grayscale(30%);
  }
`;

export default DietButton;
