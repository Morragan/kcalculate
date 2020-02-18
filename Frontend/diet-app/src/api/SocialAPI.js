import { fetchApi } from "./APIUtils";

export const getFriends = async () => {
  return await fetchApi("social", "friends");
};

export const requestFriend = async friendId => {
  return await fetchApi("social", `friends/${friendId}`, "POST");
};

export const acceptFriend = async friendId => {
  return await fetchApi("social", `accept-friend/${friendId}`, "PUT");
};

export const deleteFriend = async friendId => {
  return await fetchApi("social", `friends/${friendId}`, "DELETE");
};

export const blockUser = async userId => {
  return await fetchApi("social", `block-user/${userId}`, "POST");
};

export const searchUsersByNickname = async nickname => {
  return await fetchApi("social", `search/?nickname=${nickname}`, "GET");
};
