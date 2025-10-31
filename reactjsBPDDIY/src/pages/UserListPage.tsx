
import React, { useState, useEffect } from "react";
import axios from "axios";
import type { User } from "../types/user";

const UserListPage: React.FC = () => {

  const [users, setUsers] = useState<User[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string>("");

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const response = await axios.get<User[]>(
          import.meta.env.VITE_API_URL
        );
        setUsers(response.data);
      } catch (err: any) {
        setError("Gagal mengambil data user.");
      } finally {
        setLoading(false); 
      }
    };

    fetchUsers();
  }, []); 

  if (loading) return <p className="p-4">⏳ Loading data...</p>;
  if (error) return <p className="p-4 text-red-600">❌ {error}</p>;

  return (
    <div className="p-4">
      <h2 className="text-xl font-bold mb-4">Daftar Pengguna</h2>
      <ul>
        {users.map((user) => (
          <li key={user.id} className="border-b py-2">
            {user.name} - {user.email}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default UserListPage;
