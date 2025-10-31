import React from "react";
import { useAuth} from "../context/AuthContext";
import { Navigate, useLocation } from "react-router-dom";


interface ProtectedRouteProps {
    children: React.ReactElement;
}


const ProtectedRoute: React.FC<ProtectedRouteProps> = ({ children }) => {
    const { token } = useAuth();
    const location = useLocation();

    if (!token) {
        return <Navigate to="/login" state={{ from: location }} replace />;
    }

    return children;

};

export default ProtectedRoute;