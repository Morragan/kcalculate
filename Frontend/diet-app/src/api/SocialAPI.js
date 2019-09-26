import { fetchApi } from "./APIUtils";

export const getFriends = async () => {
  return await fetchApi("social", "friends");
};

export const requestFriend = async friendId => {
  return await fetchApi("social", "friends", "POST", {
    friendID: friendId
  });
};

export const acceptFriend = async friendId => {
  return await fetchApi("social", "accept-friend", "PUT", {
    friendID: friendId
  });
};

export const deleteFriend = async friendId => {
  return await fetchApi("social", "friends", "DELETE", { friendID: friendId });
};

export const blockUser = async userId => {
  return await fetchApi("social", "block-user", "POST", { userID: userId });
};
