import React from "react";
import Carousel from "react-bootstrap/Carousel";
import RegistrationMethod from "./steps/RegistrationMethod";
import RegistrationBasics from "./steps/RegistrationBasics";
import RegistrationMeasurements from "./steps/RegistrationMeasurements";
import RegistrationQuiz from "./steps/RegistrationQuiz";
import RegistrationResult from "./steps/RegistrationResult";
import styled from "styled-components";

const StyledCarousel = styled(Carousel)`
  & > .carousel-control-next {
    display: none;
  }
  & > .carousel-control-prev span {
    background-image: url("data:image/svg+xml;charset=utf8,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='%23ffa33fx' viewBox='0 0 8 8'%3E%3Cpath d='M5.25 0l-4 4 4 4 1.5-1.5-2.5-2.5 2.5-2.5-1.5-1.5z'/%3E%3C/svg%3E");
  }
`;

const RegistrationForm = props => {
  const next = () => {
    props.handleCarouselSelect(props.activeIndex + 1);
  };

  return (
    <StyledCarousel
      interval={null}
      controls={true}
      wrap={false}
      activeIndex={props.activeIndex}
      onSelect={props.handleCarouselSelect}
    >
      <Carousel.Item>
        <RegistrationMethod
          callbackFacebook={props.callbackFacebook}
          callbackGoogle={props.callbackGoogle}
          handleNext={next}
        />
      </Carousel.Item>
      <Carousel.Item>
        <RegistrationBasics
          submitBasics={props.handleBasicsSubmit}
          handleNext={next}
        />
      </Carousel.Item>
      <Carousel.Item>
        <RegistrationMeasurements
          submitMeasurements={props.handleMeasurementsSubmit}
          handleNext={next}
        />
      </Carousel.Item>
      <Carousel.Item>
        <RegistrationQuiz
          submitQuiz={props.handleQuizSubmit}
          handleNext={next}
        />
      </Carousel.Item>
      <Carousel.Item>
        <RegistrationResult
          weight={props.weight}
          height={props.height}
          age={props.age}
          gender={props.gender}
          weightGoal={props.weightGoal}
          activityLevel={props.activityLevel}
          finish={props.handleFinish}
          loading={props.loading}
        />
      </Carousel.Item>
    </StyledCarousel>
  );
};

export default RegistrationForm;
