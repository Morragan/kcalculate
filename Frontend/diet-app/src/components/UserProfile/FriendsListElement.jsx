import React from "react";
import styled from "styled-components";
import Button from "react-bootstrap/Button";
import ListGroup from "react-bootstrap/ListGroup";
import Image from "react-bootstrap/Image";

const StyledListGroupItem = styled(ListGroup.Item)`
  display: grid;
  grid-template:
    "image name button1"
    "image name button2";
  &&:focus {
    background-color: blue;
  }
`;
//TODO: dodać grid areas
const FriendsListElement = {
  Accepted: props => (
    <StyledListGroupItem>
      <Image
        src={props.friend.avatarLink}
        alt="Friend image"
        style={{ gridTemplateAreas: "image" }}
      />
      <p>{props.friend.nickname}</p>
      <Button variant="danger">Usuń</Button>
    </StyledListGroupItem>
  ),
  Requested: props => (
    <StyledListGroupItem>
      <Image
        src={props.friend.imageLink}
        alt="Friend image"
        style={{ gridTemplateAreas: "image" }}
      />
      <p>{props.friend.nickname}</p>
    </StyledListGroupItem>
  ),
  Requesting: props => (
    <StyledListGroupItem>
      <Image
        src={props.friend.imageLink}
        alt="Friend image"
        style={{ gridTemplateAreas: "image" }}
      />
      <p>{props.friend.nickname}</p>
      <Button>Akceptuj</Button>
      <Button>Odrzuć</Button>
    </StyledListGroupItem>
  ),
  Blocked: props => (
    <StyledListGroupItem>
      <p>{props.friend.nickname}</p>
      <Button>Odblokuj</Button>
    </StyledListGroupItem>
  )
};

export default FriendsListElement;
