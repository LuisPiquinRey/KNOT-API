import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import HomePage from './HomePage';
import { Login } from './Login';
import UnauthorizedPage from './UnauthorizedPage';
import AdminPage from './AdminPage';
import NotFoundPage from './404Page'

import ProtectedRoute from './ProtectedRoute';
import { AuthProvider } from './AuthContext';
export default function App() {
    return (
        <AuthProvider>
            <Router>
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/unauthorized" element={<UnauthorizedPage />} />
                    <Route 
                        path="/admin" 
                        element={
                            <ProtectedRoute allowedRoles={['ADMIN', 'SUPER_ADMIN']}>
                                <AdminPage />
                            </ProtectedRoute>
                        } 
                    />
                    <Route path="*" element={<NotFoundPage />}/>
                </Routes>
            </Router>
        </AuthProvider>
    );
}