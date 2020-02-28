import styled from "styled-components";
import Card from "react-bootstrap/Card";

const StyledCard = styled(Card)`
  max-width: 475px;
  margin: 5vh auto;

  .card-title {
    margin-bottom: 1.25rem;
    text-align: center;
    letter-spacing: 0.5px;
  }

  .card-body {
    display: flex;
    flex-direction: column;

    & > form > button {
      width: 100%;
    }
  }

  .card-footer {
    text-align: center;
  }
`;

export default StyledCard;
