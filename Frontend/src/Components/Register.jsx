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

export default function Register({ navigate }) {
  const [formData, setFormData] = useState({
    email: '',
    name: '',
    age: '',
    password: '',
    confirmPassword: ''
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async () => {
    setError('');

    if (formData.password !== formData.confirmPassword) {
      setError('Passwords do not match');
      return;
    }

    setLoading(true);

    try {
      const response = await fetch(`${API_BASE}/login/newLogin`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          email: formData.email,
          name: formData.name,
          age: parseInt(formData.age),
          password: formData.password
        })
      });

      if (response.ok) {
        const token = await response.text();
        alert('Registration successful! Please login.');
        setAuthToken(token);
        navigate('Home');
      } else {
        const errorMsg = await response.text();
        setError(errorMsg);
      }
    } catch (err) {
      setError('Failed to connect to server');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="register-container">
      <div className="register-card">
        <button
          onClick={() => navigate('login')}
          className="back-button"
        >
          <ChevronLeft className="back-icon" />
          Back to Login
        </button>

        <h2 className="register-title">Create Account</h2>

        <div>
          <div className="input-group">
            <label className="input-label">Email</label>
            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              className="text-input"
              required
            />
          </div>

          <div className="input-group">
            <label className="input-label">Name</label>
            <input
              type="text"
              name="name"
              value={formData.name}
              onChange={handleChange}
              className="text-input"
              required
            />
          </div>

          <div className="input-group">
            <label className="input-label">Age</label>
            <input
              type="number"
              name="age"
              value={formData.age}
              onChange={handleChange}
              className="text-input"
              required
              min="1"
            />
          </div>

          <div className="input-group">
            <label className="input-label">Password</label>
            <input
              type="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              className="text-input"
              required
            />
          </div>

          <div className="input-group">
            <label className="input-label">Confirm Password</label>
            <input
              type="password"
              name="confirmPassword"
              value={formData.confirmPassword}
              onChange={handleChange}
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
            className="register-button"
          >
            {loading ? 'Creating Account...' : 'Sign Up'}
          </button>
        </div>
      </div>
    </div>
  );
}