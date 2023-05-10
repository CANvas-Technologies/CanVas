import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { getAuth, signInWithEmailAndPassword } from "firebase/auth";
import useUser from "../hooks/useUser";
import { LinearProgress, Button, Link, Input } from "@mui/material";

const LoginPage = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const navigate = useNavigate();

  const logIn = async () => {
    try {
      await signInWithEmailAndPassword(getAuth(), email, password);
    } catch (e) {
      setError(e.message);
    }
  };

  // are we already logged in? redirect to /account
  const { user, isLoading } = useUser();

  if (isLoading) {
    return <LinearProgress />;
  }

  if (user) {
    navigate("/account");
  }

  // with mui
  return (
    <center>
      <h1>Log In</h1>
      {error && <p className="error">{error}</p>}
      <Input
        placeholder="Your email address"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />
      <br />
      <Input
        type="password"
        placeholder="Your password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      <br />
      <br />
      <Button onClick={logIn}>Log In</Button>
      <br />
      <Link to="/sign-up">Don't have an account? Create one here</Link>
    </center>
  );
};

export default LoginPage;
