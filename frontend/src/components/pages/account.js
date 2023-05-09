import React from "react";
import "../../App.css";
import Footer from "../footer";

import useUser from "../hooks/useUser";

export default function accountPage() {
  // get user state from useUser hook
  const { user, isLoading } = useUser();

  return (
    <>
      <h1 className="signup"> Hello Account </h1>
      <br />
      <p className="signup-heading">
        {isLoading
          ? "Loading..."
          : user
          ? `Hello ${user.email}`
          : "You are not logged in"}
      </p>
      <Footer />
    </>
  );
}
