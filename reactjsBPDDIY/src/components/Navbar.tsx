import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const Navbar: React.FC = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  }
  return (
    <nav className="bg-gray-800 text-white p-4">
      <ul className="flex justify-between items-center">
        {/* Link Kiri */}
        <div className="flex space-x-4">
          <li>
            <Link to="/" className="hover:text-gray-300">
              Home
            </Link>
          </li>
          {user && ( // <-- Hanya tampilkan jika sudah login
            <li>
              <Link to="/users" className="hover:text-gray-300">
                Daftar Pengguna
              </Link>
            </li>
          )}
        </div>

        {/* Link Kanan */}
        <div className="flex space-x-4">
          {user ? (
            // Jika sudah login
            <>
              <li className="text-gray-400">Halo, {user.email}</li>
              <li>
                <button
                  onClick={handleLogout}
                  className="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded"
                >
                  Logout
                </button>
              </li>
            </>
          ) : (
            // Jika belum login
            <>
              <li>
                <Link to="/login" className="hover:text-gray-300">
                  Login
                </Link>
              </li>
              <li>
                <Link to="/register" className="hover:text-gray-300">
                  Register
                </Link>
              </li>
            </>
          )}
        </div>
      </ul>
    </nav>
  );
};

export default Navbar;
