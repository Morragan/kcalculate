export const saveDataToCookie = (name, data) => {
  document.cookie = name + "=" + data;
};

export const getDataFromCookie = name => {
  var nameEQ = `${name}=`;
  var ca = document.cookie.split(";");
  for (var i = 0; i < ca.length; i++) {
    var c = ca[i];
    while (c.charAt(0) === " ") c = c.substring(1, c.length);
    if (c.indexOf(nameEQ) === 0) return c.substring(nameEQ.length, c.length);
  }
  return null;
};

export const deleteDataFromCookie = name => {
  document.cookie = `${name}= ; expires = Thu, 01 Jan 1970 00:00:00 GMT`;
};

export const isTokenExpired = () => {
  try {
    const expiration = getDataFromCookie("expiration");
    if (expiration === null) return true;
    if (expiration <= Date.now()) return true;
    else return false;
  } catch (err) {
    console.error("isTokenExpired error", err);
    return true;
  }
};
