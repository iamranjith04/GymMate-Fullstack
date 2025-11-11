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

export default function Workout({ navigate, user, onLogout }) {
  const [selectedMuscle, setSelectedMuscle] = useState('Chest');
  const [selectedDate, setSelectedDate] = useState(new Date().toISOString().split('T')[0]);
  const [workoutList, setWorkoutList] = useState([]);
  const [savedWorkouts, setSavedWorkouts] = useState([]);
  const [lastWorkoutDate, setLastWorkoutDate] = useState(null);
  const [currentWorkout, setCurrentWorkout] = useState({
    workoutName: '',
    set1Weight: '',
    set1Reps: '',
    set2Weight: '',
    set2Reps: '',
    set3Weight: '',
    set3Reps: ''
  });
  const [loading, setLoading] = useState(false);

  const muscles = ['Chest', 'Lats', 'Bicep', 'Tricep', 'Shoulder', 'Leg', 'Cardio'];

  useEffect(() => {
    fetchWorkoutList();
    fetchSavedWorkouts();
    fetchLastWorkoutDate();
  }, [selectedMuscle, selectedDate]);

  const handleAuthError = (response) => {
    if (response.status === 401) {
      alert('Session expired. Please login again.');
      onLogout();
      return true;
    }
    return false;
  };

  const fetchWorkoutList = async () => {
    try {
      const response = await fetch(`${API_BASE}/api/User/ListWorkout`, {
        method: 'POST',
        headers: getAuthHeaders(),
        body: JSON.stringify({ muscleName: selectedMuscle })
      });

      if (handleAuthError(response)) return;

      const data = await response.json();
      setWorkoutList(data);
    } catch (err) {
      console.error('Failed to fetch workout list', err);
    }
  };

  const fetchSavedWorkouts = async () => {
    try {
      const response = await fetch(`${API_BASE}/api/User/getWorkouts`, {
        method: 'POST',
        headers: getAuthHeaders(),
        body: JSON.stringify({
          email: user.email,
          date: selectedDate,
          muscleName: selectedMuscle
        })
      });

      if (handleAuthError(response)) return;

      if (response.status === 204) {
        setSavedWorkouts([]);
        return;
      }

      const data = await response.json();
      setSavedWorkouts(data || []);
    } catch (err) {
      console.error('Failed to fetch saved workouts', err);
      setSavedWorkouts([]);
    }
  };

  const fetchLastWorkoutDate = async () => {
    try {
      const response = await fetch(`${API_BASE}/api/User/getLastWorkoutDate`, {
        method: 'POST',
        headers: getAuthHeaders(),
        body: JSON.stringify({
          email: user.email,
          muscleName: selectedMuscle
        })
      });

      if (handleAuthError(response)) return;

      if (response.status === 204) {
        setLastWorkoutDate(null);
        return;
      }

      const data = await response.json();
      setLastWorkoutDate(data.lastWorkoutDate || null);
    } catch (err) {
      console.error('Failed to fetch last workout date', err);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setCurrentWorkout({ ...currentWorkout, [name]: value });
  };

  const handleSaveWorkout = async () => {
    setLoading(true);

    try {
      const response = await fetch(`${API_BASE}/api/User/addWorkout`, {
        method: 'POST',
        headers: getAuthHeaders(),
        body: JSON.stringify({
          email: user.email,
          date: selectedDate,
          muscleName: selectedMuscle,
          workoutName: currentWorkout.workoutName,
          set1Weight: parseFloat(currentWorkout.set1Weight),
          set1Reps: parseInt(currentWorkout.set1Reps),
          set2Weight: parseFloat(currentWorkout.set2Weight),
          set2Reps: parseInt(currentWorkout.set2Reps),
          set3Weight: parseFloat(currentWorkout.set3Weight),
          set3Reps: parseInt(currentWorkout.set3Reps)
        })
      });

      if (handleAuthError(response)) return;

      const result = await response.json();

      if (response.ok && result.message) {
        alert('Workout saved successfully!');
        setCurrentWorkout({
          workoutName: '',
          set1Weight: '',
          set1Reps: '',
          set2Weight: '',
          set2Reps: '',
          set3Weight: '',
          set3Reps: ''
        });
        fetchSavedWorkouts();
        fetchLastWorkoutDate();
      } else {
        alert('Failed to save workout: ' + (result.error || 'Unknown error'));
      }
    } catch (err) {
      alert('Error connecting to server');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="page-container">
      <div className="header">
        <div className="header-narrow">
          <button
            onClick={() => navigate('home')}
            className="back-link"
          >
            <ChevronLeft className="back-icon" />
            Back to Home
          </button>
          <h1 className="page-title">Log Workout</h1>
        </div>
      </div>

      <div className="main-content">
        <div className="form-grid">
          <div className="form-card">
            <label className="form-label">Date</label>
            <input
              type="date"
              value={selectedDate}
              onChange={(e) => setSelectedDate(e.target.value)}
              className="date-input"
            />
          </div>

          <div className="form-card">
            <label className="form-label">Muscle Group</label>
            <select
              value={selectedMuscle}
              onChange={(e) => setSelectedMuscle(e.target.value)}
              className="select-input"
            >
              {muscles.map((muscle) => (
                <option key={muscle} value={muscle}>
                  {muscle}
                </option>
              ))}
            </select>
          </div>
        </div>

        {lastWorkoutDate && (
          <div className="info-banner">
            <p className="info-text">
              Last {selectedMuscle} workout: <strong>{lastWorkoutDate}</strong>
            </p>
          </div>
        )}

        <div className="content-card">
          <h3 className="card-title">Add New Exercise</h3>
          <div>
            <div className="input-group">
              <label className="input-label">Exercise</label>
              <select
                name="workoutName"
                value={currentWorkout.workoutName}
                onChange={handleInputChange}
                className="select-input"
                required
              >
                <option value="">Select Exercise</option>
                {workoutList.map((workout) => (
                  <option key={workout} value={workout}>
                    {workout}
                  </option>
                ))}
              </select>
            </div>

            <div className="set-grid">
              <div>
                <label className="input-label">Set 1 Weight (kg)</label>
                <input
                  type="number"
                  step="0.1"
                  name="set1Weight"
                  value={currentWorkout.set1Weight}
                  onChange={handleInputChange}
                  className="number-input"
                  required
                />
              </div>
              <div>
                <label className="input-label">Set 1 Reps</label>
                <input
                  type="number"
                  name="set1Reps"
                  value={currentWorkout.set1Reps}
                  onChange={handleInputChange}
                  className="number-input"
                  required
                />
              </div>
            </div>

            <div className="set-grid">
              <div>
                <label className="input-label">Set 2 Weight (kg)</label>
                <input
                  type="number"
                  step="0.1"
                  name="set2Weight"
                  value={currentWorkout.set2Weight}
                  onChange={handleInputChange}
                  className="number-input"
                  required
                />
              </div>
              <div>
                <label className="input-label">Set 2 Reps</label>
                <input
                  type="number"
                  name="set2Reps"
                  value={currentWorkout.set2Reps}
                  onChange={handleInputChange}
                  className="number-input"
                  required
                />
              </div>
            </div>

            <div className="set-grid">
              <div>
                <label className="input-label">Set 3 Weight (kg)</label>
                <input
                  type="number"
                  step="0.1"
                  name="set3Weight"
                  value={currentWorkout.set3Weight}
                  onChange={handleInputChange}
                  className="number-input"
                  required
                />
              </div>
              <div>
                <label className="input-label">Set 3 Reps</label>
                <input
                  type="number"
                  name="set3Reps"
                  value={currentWorkout.set3Reps}
                  onChange={handleInputChange}
                  className="number-input"
                  required
                />
              </div>
            </div>

            <button
              onClick={handleSaveWorkout}
              disabled={loading}
              className="save-button"
            >
              {loading ? 'Saving...' : 'Save Exercise'}
            </button>
          </div>
        </div>

        {savedWorkouts.length > 0 && (
          <div className="content-card">
            <h3 className="card-title">Today's Exercises</h3>
            {savedWorkouts.map((workout, index) => (
              <div key={index} className="workout-item">
                <h4 className="exercise-name">{workout.workoutName}</h4>
                <div className="sets-grid">
                  <div>
                    <p className="set-label">Set 1</p>
                    <p className="set-value">{workout.set1Weight}kg × {workout.set1Reps}</p>
                  </div>
                  <div>
                    <p className="set-label">Set 2</p>
                    <p className="set-value">{workout.set2Weight}kg × {workout.set2Reps}</p>
                  </div>
                  <div>
                    <p className="set-label">Set 3</p>
                    <p className="set-value">{workout.set3Weight}kg × {workout.set3Reps}</p>
                  </div>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}