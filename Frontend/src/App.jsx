import React, { useState, useEffect } from 'react';
import { ChevronLeft, Plus, Calendar, User, LogOut, Dumbbell } from 'lucide-react';
import Home from './Components/Home.jsx';
import Login from './Components/Login.jsx';
import Register from './Components/Register.jsx';
import Workout from './Components/Workout.jsx'; 
const API_BASE = import.meta.env.VITE_API_BASE;

const setAuthToken = (token) => {
  localStorage.setItem('jwt_token', token);
};

const getAuthToken = () => {
  return localStorage.getItem('jwt_token');
};

const removeAuthToken = () => {
  localStorage.removeItem('jwt_token');
};

const getAuthHeaders = () => {
  const token = getAuthToken();
  return {
    'Content-Type': 'application/json',
    ...(token && { 'Authorization': `Bearer ${token}` })
  };
};

export default function App() {
  const [currentPage, setCurrentPage] = useState('login');
  const [user, setUser] = useState(null);

  useEffect(() => {
    const token = getAuthToken();
    if (token) {
      const email = localStorage.getItem('user_email');
      if (email) {
        setUser({ email });
        setCurrentPage('home');
      }
    }
  }, []);

  const handleLogin = (email) => {
    setUser({ email });
    localStorage.setItem('user_email', email);
    setCurrentPage('home');
  };

  const handleLogout = () => {
    removeAuthToken();
    localStorage.removeItem('user_email');
    setUser(null);
    setCurrentPage('login');
  };

  const navigate = (page) => {
    setCurrentPage(page);
  };

  return (
    <>
    {currentPage === 'login' && (
        <Login navigate={navigate} onLogin={handleLogin} />
      )}
      {currentPage === 'register' && (
        <Register navigate={navigate} />
      )}
      {currentPage === 'home' && (
        <Home navigate={navigate} user={user} onLogout={handleLogout} />
      )}
      {currentPage === 'workout' && (
        <Workout navigate={navigate} user={user} onLogout={handleLogout} />
      )}
    </>
  );
}