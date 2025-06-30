import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { Login } from './components/Login.jsx'
import { AuthProvider } from './components/AuthContext.jsx'; 
import App from './components/App.jsx';

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <AuthProvider>
      <App />
    </AuthProvider>
  </StrictMode>
)


