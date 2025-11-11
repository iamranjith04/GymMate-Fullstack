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


export default function LoginPage({ navigate, onLogin }) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async () => {
    setError('');
    setLoading(true);

    try {
      const response = await fetch(`${API_BASE}/login/existingLogin`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password })
      });

      const result = await response.text();
      
      if (response.ok) {
        setAuthToken(result);
        onLogin(email);
      } else {
        setError(result || 'Login failed');
      }
    } catch (err) {
      setError('Failed to connect to server');
    } finally {
      setLoading(false);
    }
  };

  const handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      handleSubmit();
    }
  };

  return (
    <div className="login-container">
      <div className="login-card">
        <div className="logo-container">
          <Dumbbell className="app-logo" />
          <h1 className="app-title">GymMate</h1>
        </div>

        <h2 className="login-title">Welcome Back</h2>

        <div>
          <div className="input-group">
            <label className="input-label">Email</label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              onKeyPress={handleKeyPress}
              className="text-input"
              required
            />
          </div>

          <div className="input-group">
            <label className="input-label">Password</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              onKeyPress={handleKeyPress}
              className="text-input"
              required
            />
          </div>

          {error && (
            <div className="error-message">
              {error}
            </div>
          )}

          <button
            onClick={handleSubmit}
            disabled={loading}
            className="login-button"
          >
            {loading ? 'Logging in...' : 'Login'}
          </button>
        </div>

        <div className="signup-section">
          <span className="signup-text">Don't have an account? </span>
          <button
            onClick={() => navigate('register')}
            className="signup-link"
          >
            Sign Up
          </button>
        </div>
      </div>
    </div>
  );
}