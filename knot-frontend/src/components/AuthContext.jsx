import React, { createContext, useState, useContext, useEffect } from 'react';
import { jwtDecode } from 'jwt-decode';

const AuthContext = createContext();

export function AuthProvider({ children }) {
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [loading, setLoading] = useState(true);
    const [user, setUser] = useState({
        token: '',
        role: '',
        dataUser: {
            username: '',
            password: ''
        }
    });
    useEffect(() => {
        const token = localStorage.getItem('token');
        if (token) {
            try {
                const decodedToken = jwtDecode(token);
                if (decodedToken.exp * 1000 > Date.now()) {
                    setUser({
                        token,
                        role: decodedToken.role,
                        dataUser: {
                            username: decodedToken.sub || '',
                            password: ''
                        }
                    });
                    setIsAuthenticated(true); 
                } else {
                    localStorage.removeItem('token');
                }
            } catch (error) {
                console.error('Error decoding token:', error);
                localStorage.removeItem('token');
            }
        }
        setLoading(false); 
    }, []);

    const login = (token, role, dataUser) => {
        setUser({
            token,
            role,
            dataUser
        });
        setIsAuthenticated(true); 
        localStorage.setItem('token', token);
    };

    const logout = () => {
        setUser({
            token: '',
            role: '',
            dataUser: {
                username: '',
                password: ''
            }
        });
        setIsAuthenticated(false); 
        localStorage.removeItem('token');
    };

    return (
        <AuthContext.Provider value={{ 
            user, 
            isAuthenticated, 
            loading,         
            login, 
            logout 
        }}>
            {children}
        </AuthContext.Provider>
    );
}

export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
};