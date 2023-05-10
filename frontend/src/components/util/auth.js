import axios from "axios";

// generic function to make an authorized request to the API.
// returns a promise that resolves to the response.
async function authorizedGet(user, url) {
  const token = user && (await user.getIdToken());
  const headers = token ? { Authorization: `Bearer ${token}` } : {};

  return axios.get(url, { headers });
}

export default authorizedGet;
