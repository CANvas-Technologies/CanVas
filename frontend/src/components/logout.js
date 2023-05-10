import { getAuth, signOut } from "firebase/auth";

function logOut() {
  const auth = getAuth();
  signOut(auth);
}

export default logOut;
