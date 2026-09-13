import { createContext, useContext, useEffect, useState } from "react";
import api, { getErrorMessage } from "../lib/api";
import {
  clearStoredSession,
  loadStoredSession,
  saveStoredSession
} from "../utils/storage";

const AuthContext = createContext(null);

function normalizeRoles(roles = []) {
  return roles.map((role) => role.replace("ROLE_", ""));
}

export function AuthProvider({ children }) {
  const [session, setSession] = useState(loadStoredSession());
  const [profile, setProfile] = useState(null);
  const [loading, setLoading] = useState(Boolean(loadStoredSession()));

  useEffect(() => {
    if (!session?.accessToken) {
      setLoading(false);
      return;
    }

    const loadProfile = async () => {
      try {
        const response = await api.get("/users/me");
        setProfile(response.data);
      } catch {
        logout();
      } finally {
        setLoading(false);
      }
    };

    loadProfile();
  }, [session?.accessToken]);

  const login = async (email, password) => {
    const response = await api.post("/auth/login", { email, password });
    const nextSession = {
      ...response.data,
      roles: normalizeRoles(response.data.roles)
    };
    saveStoredSession(nextSession);
    setSession(nextSession);
    const profileResponse = await api.get("/users/me", {
      headers: { Authorization: `Bearer ${nextSession.accessToken}` }
    });
    setProfile(profileResponse.data);
    return nextSession;
  };

  const register = async (payload) => {
    const response = await api.post("/auth/register", payload);
    const nextSession = {
      ...response.data,
      roles: normalizeRoles(response.data.roles)
    };
    saveStoredSession(nextSession);
    setSession(nextSession);
    const profileResponse = await api.get("/users/me", {
      headers: { Authorization: `Bearer ${nextSession.accessToken}` }
    });
    setProfile(profileResponse.data);
    return nextSession;
  };

  const refreshProfile = async () => {
    if (!session?.accessToken) {
      return null;
    }
    const response = await api.get("/users/me");
    setProfile(response.data);
    return response.data;
  };

  const logout = () => {
    clearStoredSession();
    setSession(null);
    setProfile(null);
  };

  const value = {
    session,
    profile,
    loading,
    isAuthenticated: Boolean(session?.accessToken),
    roles: session?.roles || [],
    login,
    register,
    logout,
    refreshProfile,
    getErrorMessage
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used inside AuthProvider");
  }
  return context;
}
