import React, { useState, useEffect, useRef } from "react";
import styled from "styled-components";
import Button from "react-bootstrap/Button";
import ListGroup from "react-bootstrap/ListGroup";
import Image from "react-bootstrap/Image";
import DietButton from "../DietButton/DietButton";
import avatarPlaceholder from "../../assets/img_avatar.png";

const StyledListGroupItem = styled(ListGroup.Item)`
  display: grid;
  grid-template-areas: "image name buttons";
  grid-template-columns: 70px 1fr 200px;
  width: 90%;
  margin: auto;

  p {
    margin-left: 42px;
  }

  img {
    height: 100%;
    min-height: 100px;
    object-fit: contain;
  }

  button {
    width: 200px;
    height: 38px;
    margin: 0 auto;
  }

  .buttons {
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
  }
`;

const FriendsListElementAccepted = props => {
  const [loading, changeLoading] = useState(false);
  const isMounted = useRef(false);

  useEffect(() => {
    isMounted.current = true;
    return () => (isMounted.current = false);
  }, []);

  const completeLoading = () => isMounted.current && changeLoading(false);

  return (
    <StyledListGroupItem>
      {props.friend.avatarLink ? (
        <Image src={props.friend.avatarLink} alt="Friend image" />
      ) : (
        <Image src={avatarPlaceholder} alt="Friend image" />
      )}

      <p>{props.friend.nickname}</p>
      <Button
        variant="danger"
        onClick={() => {
          changeLoading(true);
          props.handleDeleteFriend(props.friend.id, completeLoading);
        }}
        disabled={loading}
      >
        Remove
      </Button>
    </StyledListGroupItem>
  );
};

const FriendsListElementRequested = props => {
  const [loading, changeLoading] = useState(false);
  const isMounted = useRef(false);

  useEffect(() => {
    isMounted.current = true;
    return () => (isMounted.current = false);
  }, []);

  const completeLoading = () => isMounted.current && changeLoading(false);

  return (
    <StyledListGroupItem>
      {props.friend.avatarLink ? (
        <Image src={props.friend.avatarLink} alt="Friend image" />
      ) : (
        <Image src={avatarPlaceholder} alt="Friend image" />
      )}
      <p>{props.friend.nickname}</p>
      <div className="buttons">
        <DietButton disabled color="#ffd66f">
          Request sent...
        </DietButton>
        <Button
          variant="danger"
          disabled={loading}
          onClick={() => {
            changeLoading(true);
            props.handleDeleteFriend(props.friend.id, completeLoading);
          }}
        >
          Cancel friend request
        </Button>
      </div>
    </StyledListGroupItem>
  );
};

const FriendsListElementRequesting = props => {
  const [loading, changeLoading] = useState(false);
  const isMounted = useRef(false);

  useEffect(() => {
    isMounted.current = true;
    return () => (isMounted.current = false);
  }, []);

  const completeLoading = () => isMounted.current && changeLoading(false);

  return (
    <StyledListGroupItem>
      {props.friend.avatarLink ? (
        <Image src={props.friend.avatarLink} alt="Friend image" />
      ) : (
        <Image src={avatarPlaceholder} alt="Friend image" />
      )}
      <p>{props.friend.nickname}</p>
      <div className="buttons">
        <DietButton
          onClick={() => {
            changeLoading(true);
            props.handleAcceptFriendRequest(props.friend.id, completeLoading);
          }}
          disabled={loading}
        >
          Accept
        </DietButton>
        <Button
          variant="danger"
          onClick={() => {
            changeLoading(true);
            props.handleDeleteFriend(props.friend.id, completeLoading);
          }}
          disabled={loading}
        >
          Reject
        </Button>
      </div>
    </StyledListGroupItem>
  );
};

const FriendsListElementBlocked = props => {
  const [loading, changeLoading] = useState(false);
  const isMounted = useRef(false);

  useEffect(() => {
    isMounted.current = true;
    return () => (isMounted.current = false);
  }, []);

  const completeLoading = () => isMounted.current && changeLoading(false);

  return (
    <StyledListGroupItem>
      {props.friend.avatarLink ? (
        <Image src={props.friend.avatarLink} alt="Friend image" />
      ) : (
        <Image src={avatarPlaceholder} alt="Friend image" />
      )}
      <p>{props.friend.nickname}</p>
      <Button
        onClick={() => {
          changeLoading(true);
          props.handleDeleteFriend(props.friend.id, completeLoading);
        }}
        disabled={loading}
      >
        Unblock
      </Button>
    </StyledListGroupItem>
  );
};

const FriendsListElementFound = props => {
  const [loading, changeLoading] = useState(false);
  const isMounted = useRef(false);

  useEffect(() => {
    isMounted.current = true;
    return () => (isMounted.current = false);
  }, []);

  const completeLoading = () => isMounted.current && changeLoading(false);

  return (
    <StyledListGroupItem>
      {props.friend.avatarLink ? (
        <Image src={props.friend.avatarLink} alt="Friend image" />
      ) : (
        <Image src={avatarPlaceholder} alt="Friend image" />
      )}
      <p>{props.friend.nickname}</p>
      <div className="buttons">
        <DietButton
          onClick={() => {
            changeLoading(true);
            props.handleSendFriendRequest(props.friend.id, completeLoading);
          }}
          disabled={loading}
        >
          Send friend request
        </DietButton>
        <Button
          variant="danger"
          onClick={() => {
            changeLoading(true);
            props.handleBlockUser(props.friend.id, completeLoading);
          }}
          disabled={loading}
        >
          Block
        </Button>
      </div>
    </StyledListGroupItem>
  );
};

const FriendsListElement = {
  Accepted: FriendsListElementAccepted,
  Requested: FriendsListElementRequested,
  Requesting: FriendsListElementRequesting,
  Blocked: FriendsListElementBlocked,
  Found: FriendsListElementFound
};

export default FriendsListElement;
