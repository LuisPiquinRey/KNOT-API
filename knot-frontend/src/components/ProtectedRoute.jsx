import { useAuth } from "./AuthContext";
import { Navigate } from 'react-router-dom';

export default function ProtectedRoute({ children, allowedRoles }) {
    const { isAuthenticated, user, loading } = useAuth();

    console.log('ProtectedRoute - loading:', loading);
    console.log('ProtectedRoute - isAuthenticated:', isAuthenticated);
    console.log('ProtectedRoute - user role:', user.role);
    console.log('ProtectedRoute - allowedRoles:', allowedRoles);

    if (loading) {
        return <div>Loading...</div>;
    }
    if (!isAuthenticated) {
        console.log('Not authenticated, redirecting to login');
        return <Navigate to="/login" replace />;
    }
    if (allowedRoles && allowedRoles.length > 0 && !allowedRoles.includes(user.role)) {
        console.log(`User role "${user.role}" not in allowed roles:`, allowedRoles);
        return <Navigate to="/unauthorized" replace />;
    }

    console.log('Access granted!');
    return children;
}