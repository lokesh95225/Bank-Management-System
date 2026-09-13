const TOKEN_KEY = "banking_auth";

export function loadStoredSession() {
  try {
    const raw = localStorage.getItem(TOKEN_KEY);
    return raw ? JSON.parse(raw) : null;
  } catch {
    return null;
  }
}

export function saveStoredSession(session) {
  localStorage.setItem(TOKEN_KEY, JSON.stringify(session));
}

export function clearStoredSession() {
  localStorage.removeItem(TOKEN_KEY);
}
