import { useAuth } from "./AuthContext";
import { Navigate } from 'react-router-dom';

export default function ProtectedRoute({ children,allowedRoles }) {
    const { user } = useAuth();
    const token = user.token;
    const role = user.role;

    if (!token) {
        return <Navigate to="/login" />;
    }

    if (allowedRoles && !allowedRoles.includes(role)) {
        return <Navigate to="/unauthorized" />;
    }

    return children;
}