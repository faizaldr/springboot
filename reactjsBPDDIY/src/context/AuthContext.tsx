import React, {
  createContext,
  useState,
  useEffect,
  useContext,
  type ReactNode,
} from "react";
import axios from "axios";
import type { AuthUser } from "../types/user"; // Kita akan tambahkan tipe data ini

// Tentukan tipe untuk data yang akan ada di context
interface AuthContextType {
  user: AuthUser | null;
  token: string | null;
  login: (email: string, password: string) => Promise<void>;
  register: (email: string, password: string, role: string) => Promise<void>;
  logout: () => void;
  loading: boolean;
}

// Buat Context
const AuthContext = createContext<AuthContextType | undefined>(undefined);

// URL API dari .env
const AUTH_API_URL = import.meta.env.VITE_AUTH_API_URL;

// Buat Provider Component
export const AuthProvider: React.FC<{ children: ReactNode }> = ({
  children,
}) => {
  const [user, setUser] = useState<AuthUser | null>(null);
  const [token, setToken] = useState<string | null>(null);
  const [loading, setLoading] = useState<boolean>(true); // Loading untuk cek status login awal

  // Cek localStorage saat komponen pertama kali dimuat
  useEffect(() => {
    try {
      const storedToken = localStorage.getItem("authToken");
      const storedUser = localStorage.getItem("authUser");

      if (storedToken && storedUser) {
        setToken(storedToken);
        setUser(JSON.parse(storedUser));
      }
    } catch (error) {
      console.error("Gagal memuat data auth dari localStorage", error);
      localStorage.clear(); // Bersihkan jika data korup
    } finally {
      setLoading(false);
    }
  }, []);

  // Fungsi Login
  const login = async (email: string, password: string) => {
    const response = await axios.post<{ user: AuthUser; token: string }>(
      `${AUTH_API_URL}login`,
      { email, password }
    );

    const { user, token } = response.data;

    setToken(token);
    setUser(user);

    localStorage.setItem("authToken", token);
    localStorage.setItem("authUser", JSON.stringify(user));
  };

  // Fungsi Register
  const register = async (email: string, password: string, role: string) => {
    await axios.post(`${AUTH_API_URL}register`, {
      email,
      password,
      role,
    });
  };

  // Fungsi Logout
  const logout = () => {
    setUser(null);
    setToken(null);
    localStorage.removeItem("authToken");
    localStorage.removeItem("authUser");
  };

  return (
    <AuthContext.Provider
      value={{ user, token, login, register, logout, loading }}
    >
      {!loading && children}{" "}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error("useAuth harus digunakan di dalam AuthProvider");
  }
  return context;
};
