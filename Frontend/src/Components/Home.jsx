import React, { useState, useEffect } from 'react';
import { ChevronLeft, Plus, Calendar, User, LogOut, Dumbbell } from 'lucide-react';

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

export default function Home({ navigate, user, onLogout }) {
  const [selectedDate, setSelectedDate] = useState(new Date().toISOString().split('T')[0]);

  const handleStartWorkout = () => {
    navigate('workout');
  };

  return (
    <div className="page-container">
      <div className="header">
        <div className="header-content">
          <div className="logo-section">
            <Dumbbell className="logo-icon" />
            <h1 className="app-title">GymMate</h1>
          </div>
          <button
            onClick={onLogout}
            className="logout-button"
          >
            <LogOut className="button-icon" />
            Logout
          </button>
        </div>
      </div>

      <div className="main-content">
        <div className="content-card">
          <div className="card-header">
            <User className="header-icon" />
            <h2 className="card-title">Welcome, {user?.email}</h2>
          </div>
        </div>

        <div className="content-card">
          <div className="card-header">
            <Calendar className="header-icon" />
            <h3 className="card-title">Select Workout Date</h3>
          </div>
          <input
            type="date"
            value={selectedDate}
            onChange={(e) => setSelectedDate(e.target.value)}
            className="date-input"
          />
        </div>

        <div className="content-card">
          <button
            onClick={handleStartWorkout}
            className="start-workout-button"
          >
            <Plus className="button-icon" />
            Start Workout
          </button>
        </div>
      </div>
    </div>
  );
}