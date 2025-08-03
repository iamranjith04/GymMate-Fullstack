import { useState } from "react";
import { ArrowLeft, Calendar, Dumbbell, TrendingUp, User, Save, X, AlertCircle } from 'lucide-react'

function Login(props) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [passError, setPassError] = useState("");

  const handleSubmit = async () => {
    if (password.length < 5) {
      setPassError("Password must be at least 5 characters long");
      return;
    }

    setPassError("");

    const loginData = {
      email: email,
      password: password,
    };

      const response = await fetch("https://gym-mate-backend-1.onrender.com/api/ranjith/user/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(loginData),
        });

        if (!response.ok) {
        throw new Error("Network response was not OK");
        }

        const resultText = await response.text();
        const success = resultText === "true";

        if (success) {
            alert("Login successful");
            props.setlogin();

        } else {
            alert("Login failed");
        }

  
}

  return (
    <>
    <div className="app-header">
              <div className="app-title">
                <Dumbbell className="icon" />
                <h1>Gym Mate</h1>
              </div>
              <div className="user-greeting">
                <User className="icon" />
                <span>Hello, Ranjith!</span>
              </div>
    </div>
    <div className="LoginContainer">
      <h2>Login</h2>
      <input
        type="email"
        placeholder="Email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />
      <br />
      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      <br />
      <span>{passError}</span>
      <br />
      <button type="submit" onClick={handleSubmit}>
        Submit
      </button>
    </div>
    </>
  );
}

export default Login;
