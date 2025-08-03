import Home from "./Components/Home"
import { useState } from 'react';
import LoginCom from "./Components/Login"
function App() {
  
  const [Login,SetLogin]=useState(()=>{return sessionStorage.getItem('isLoggedIn') === 'true';});
  const handleLogin=()=>{
    SetLogin(true);
    sessionStorage.setItem('isLoggedIn', 'true');
  }

  return (
    <>
      {Login ? <Home /> : <LoginCom setlogin={handleLogin}/>}
    </>
  )
}

export default App