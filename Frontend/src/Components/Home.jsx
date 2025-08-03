import { useState } from 'react'
import { ArrowLeft, Calendar, Dumbbell, TrendingUp, User, Save, X, AlertCircle } from 'lucide-react'

function Home(){
  const [currentView, setCurrentView] = useState('home')
  const [selectedMuscle, setSelectedMuscle] = useState('')
  const [selectedWorkout, setSelectedWorkout] = useState('')
  const [selectedDate, setSelectedDate] = useState(new Date().toISOString().split('T')[0])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  
  const [muscleWorkouts, setMuscleWorkouts] = useState([])
  const [lastWorkoutDate, setLastWorkoutDate] = useState('')
  const [lastSessionDetails, setLastSessionDetails] = useState([])
  const [Day,SetDay]=useState("");
  
  const [sets, setSets] = useState([
    { id: 1, value: '', placeholder: 'e.g., 1 Set 15 rep * 25kg' },
    { id: 2, value: '', placeholder: 'e.g., 2 Set 12 rep * 30kg' },
    { id: 3, value: '', placeholder: 'e.g., 3 Set 10 rep * 32.5kg' }
  ])

  const muscleGroups = [
    { name: 'Leg', icon: '🦵', color: 'chest' },
    { name: 'Bicep', icon: '💪', color: 'shoulder' },
    { name: 'Tricep', icon: 'T', color: 'lats' },
    { name: 'Chest', icon: 'C', color: 'chest' },
    { name: 'Lats', icon: 'L', color: 'lats' },
    { name: 'Shoulder', icon: '🏋️', color: 'shoulder' },
    { name: 'Cardio', icon: '🏃', color: 'chest' }
    
  ]

  const handleMuscleSelect = async (muscleName) => {
    setSelectedMuscle(muscleName)
    setLoading(true)
    setError('')
    
    try {
      const workoutsResponse = await fetch(`https://gym-mate-backend-1.onrender.com/api/ranjith/muscle/${muscleName}`)
      if (!workoutsResponse.ok) throw new Error('Failed to fetch workouts')
      const workoutsData = await workoutsResponse.json()
      setMuscleWorkouts(workoutsData)
      
      const lastDateResponse = await fetch(`https://gym-mate-backend-1.onrender.com/api/ranjith/muscle/${muscleName}/last-date`)
      if (!lastDateResponse.ok) throw new Error('Failed to fetch last workout date')
      const lastDateData = await lastDateResponse.text()
      setLastWorkoutDate(lastDateData)
      
      setCurrentView('workouts')
    } catch (err) {
      setError('Failed to load workouts. Please try again.')
      console.error('Error fetching muscle data:', err)
    } finally {
      setLoading(false)
    }
  }

  const handleWorkoutSelect = async (workoutName) => {
    setSelectedWorkout(workoutName)
    setLoading(true)
    setError('')
    
    try {
      const lastSessionResponse = await fetch(
        `https://gym-mate-backend-1.onrender.com/api/ranjith/muscleName/${selectedMuscle}/${lastWorkoutDate}/${workoutName}`
      )
      if (!lastSessionResponse.ok) throw new Error('Failed to fetch last session data')
      const lastSessionData = await lastSessionResponse.json()
      setLastSessionDetails(lastSessionData)
      setCurrentView('input')
    } catch (err) {
      setError('No previous session data found for this workout.')
      setLastSessionDetails([])
      setCurrentView('input')
      console.error('Error fetching last session data:', err)
    } finally {
      setLoading(false)
    }
  }

  const handleSetChange = (setId, value) => {
    setSets(prev => prev.map(set => 
      set.id === setId ? { ...set, value } : set
    ))
  }

  const handleSubmit = async () => {
    if (!selectedDate) {
      setError('Please select a date')
      return
    }

    const filledSets = sets.filter(set => set.value.trim() !== '')
    if (filledSets.length === 0) {
      setError('Please enter at least one set')
      return
    }

    setLoading(true)
    setError('')

    try {
      const workoutData = {
        workoutName: selectedWorkout,
        sets: filledSets.map(set => set.value)
      }

      const response = await fetch(
        `https://gym-mate-backend-1.onrender.com/api/ranjith/muscleName/${selectedMuscle}/date/${selectedDate}`,
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(workoutData)
        }
      )

      if (!response.ok) {
        throw new Error('Failed to save workout')
      }
      
      setSets(prev => prev.map(set => ({ ...set, value: '' })))
      alert('Workout saved successfully!')
      setCurrentView('home')
    } catch (err) {
      setError('Failed to save workout. Please try again.')
      console.error('Error saving workout:', err)
    } finally {
      setLoading(false)
    }
  }

  const navigateBack = () => {
    if (currentView === 'workouts') {
      setCurrentView('home')
    } else if (currentView === 'input') {
      setCurrentView('workouts')
    }
  }

  const ErrorMessage = ({ message }) => (
    <div className="error-message">
      <AlertCircle className="icon" />
      <span>{message}</span>
    </div>
  )

  const LoadingSpinner = () => (
    <div className="loading-container">
      <div className="loading-spinner"></div>
    </div>
  )

  return (
    <div className="app-container">
      <div className="app-wrapper">
        
        {currentView === 'home' && (
          <div className="page-content">
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

            <div className="form-group">
              <label className="form-label">
                <Calendar className="icon" />
                Workout Date
              </label>
              <input
                type="date"
                value={selectedDate}
                onChange={(e) => {setSelectedDate(e.target.value)} }
                className="form-input"
              />
              <h1>{new Date(selectedDate).toLocaleDateString("en-US", { weekday: "long" })}</h1>
            </div>

            {error && <ErrorMessage message={error} />}

            <div>
              <h3 className="section-title">Select Muscle Group</h3>
              {muscleGroups.map((muscle) => (
                <button
                  key={muscle.name}
                  onClick={() => handleMuscleSelect(muscle.name)}
                  disabled={loading}
                  className="muscle-button"
                >
                  <div className="muscle-button-content">
                    <div className={`muscle-icon ${muscle.color}`}>
                      {muscle.icon}
                    </div>
                    <div className="muscle-info text-left">
                      <h4>{muscle.name}</h4>
                      <p>Target {muscle.name.toLowerCase()} muscles</p>
                    </div>
                  </div>
                </button>
              ))}
            </div>
          </div>
        )}

        {currentView === 'workouts' && (
          <div className="page-content">
            <div className="nav-header">
              <button
                onClick={navigateBack}
                className="back-button"
              >
                <ArrowLeft className="w-5 h-5" />
              </button>
              <div className="nav-title">
                <h2>{selectedMuscle} Workouts</h2>
                {lastWorkoutDate && (
                  <p className="nav-subtitle">Last trained: {lastWorkoutDate} {new Date(lastWorkoutDate).toLocaleDateString("en-US", { weekday: "long" })}</p>
                )}
              </div>
            </div>

            {error && <ErrorMessage message={error} />}
            
            {loading ? (
              <LoadingSpinner />
            ) : (
              <div>
                {muscleWorkouts.map((workout, index) => (
                  <button
                    key={index}
                    onClick={() => handleWorkoutSelect(workout)}
                    className="workout-button"
                  >
                    <div className="workout-button-content">
                      <span>{workout}</span>
                      <TrendingUp className="icon" />
                    </div>
                  </button>
                ))}
              </div>
            )}
          </div>
        )}

        {currentView === 'input' && (
          <div className="page-content">
            <div className="nav-header">
              <button
                onClick={navigateBack}
                className="back-button"
              >
                <ArrowLeft className="w-5 h-5" />
              </button>
              <div className="nav-title">
                <h2>{selectedWorkout}</h2>
              </div>
            </div>

            {error && <ErrorMessage message={error} />}

            {lastSessionDetails.length > 0 && (
              <div className="previous-session">
                <h3>Previous Session ({lastWorkoutDate}):</h3>
                <div className="previous-session-details">
                  {lastSessionDetails.map((detail, index) => (
                    <p key={index} className="previous-session-item">{detail}</p>
                  ))}
                </div>
              </div>
            )}

            <div className="sets-section">
              <h3>Today's Sets:</h3>
              {sets.map((set) => (
                <div key={set.id} className="set-input-group">
                  <label>Set {set.id}</label>
                  <input
                    type="text"
                    value={set.value}
                    onChange={(e) => handleSetChange(set.id, e.target.value)}
                    placeholder={set.placeholder}
                    className="form-input"
                  />
                </div>
              ))}
            </div>

            <button
              onClick={handleSubmit}
              disabled={loading}
              className="primary-button"
            >
              {loading ? (
                <div className="loading-spinner-small"></div>
              ) : (
                <Save className="w-4 h-4" />
              )}
              {loading ? 'Saving...' : 'Save Workout'}
            </button>
          </div>
        )}
      </div>
    </div>
  )
}

export default Home;