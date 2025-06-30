import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import HomePage from './HomePage';
import { Login } from './Login';
import UnauthorizedPage from './UnauthorizedPage';
import AdminPage from './AdminPage';

import ProtectedRoute from './ProtectedRoute';
export default function App() {
    return (
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
            </Routes>
        </Router>
    );
}