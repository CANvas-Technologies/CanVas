import axios from "axios";

// generic function to make an authorized request to the API.
// returns a promise that resolves to the response.
async function authorizedGet(user, url) {
  const token = user && (await user.getIdToken());
  const headers = token ? { Authorization: `Bearer ${token}` } : {};

  return axios.get(url, { headers });
}

async function authorizedPost(user, url, data) {
  const token = user && (await user.getIdToken());
  const headers = token ? { Authorization: `Bearer ${token}` } : {};

  return axios.post(url, data, { headers });
}

export { authorizedGet, authorizedPost };
