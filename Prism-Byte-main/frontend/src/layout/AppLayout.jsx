import { CreditCard, Home, LogOut, ShieldCheck, UserCircle, Wallet } from "lucide-react";
import { NavLink } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const navItems = [
  { to: "/dashboard", label: "Overview", icon: Home },
  { to: "/accounts", label: "Accounts", icon: Wallet },
  { to: "/transactions", label: "Transactions", icon: CreditCard },
  { to: "/profile", label: "Profile", icon: UserCircle }
];

export default function AppLayout({ children }) {
  const { profile, roles, logout } = useAuth();
  const canAccessAdmin = roles.includes("ADMIN") || roles.includes("EMPLOYEE");

  return (
    <div className="page-grid min-h-screen px-4 py-4 md:px-6">
      <div className="mx-auto grid min-h-[calc(100vh-2rem)] max-w-7xl gap-4 lg:grid-cols-[280px_1fr]">
        <aside className="glass-panel rounded-[2rem] border border-white/60 p-6 shadow-soft">
          <div className="space-y-6">
            <div>
              <p className="text-xs font-semibold uppercase tracking-[0.4em] text-brand-600">Prismbyte</p>
              <h1 className="mt-3 font-display text-3xl font-extrabold text-slate-900">Banking Studio</h1>
              <p className="mt-2 text-sm text-slate-500">Modern operations for customers, employees, and admins.</p>
            </div>

            <div className="rounded-3xl bg-slate-900 p-5 text-white">
              <p className="text-xs uppercase tracking-[0.25em] text-white/60">Signed in as</p>
              <p className="mt-2 font-display text-xl font-bold">
                {profile ? `${profile.firstName} ${profile.lastName}` : "Workspace user"}
              </p>
              <p className="mt-1 text-sm text-white/70">{profile?.email}</p>
              <div className="mt-4 flex flex-wrap gap-2">
                {roles.map((role) => (
                  <span key={role} className="rounded-full bg-white/10 px-3 py-1 text-xs font-medium">
                    {role}
                  </span>
                ))}
              </div>
            </div>

            <nav className="space-y-2">
              {navItems.map((item) => {
                const Icon = item.icon;
                return (
                  <NavLink
                    key={item.to}
                    to={item.to}
                    className={({ isActive }) =>
                      `flex items-center gap-3 rounded-2xl px-4 py-3 text-sm font-medium transition ${
                        isActive
                          ? "bg-brand-600 text-white shadow-lg shadow-brand-600/20"
                          : "text-slate-600 hover:bg-white hover:text-slate-900"
                      }`
                    }
                  >
                    <Icon size={18} />
                    {item.label}
                  </NavLink>
                );
              })}

              {canAccessAdmin ? (
                <NavLink
                  to="/admin"
                  className={({ isActive }) =>
                    `flex items-center gap-3 rounded-2xl px-4 py-3 text-sm font-medium transition ${
                      isActive
                        ? "bg-emerald-600 text-white shadow-lg shadow-emerald-600/20"
                        : "text-slate-600 hover:bg-white hover:text-slate-900"
                    }`
                  }
                >
                  <ShieldCheck size={18} />
                  Admin Console
                </NavLink>
              ) : null}
            </nav>
          </div>

          <button
            type="button"
            onClick={logout}
            className="mt-10 flex w-full items-center justify-center gap-2 rounded-2xl border border-slate-200 bg-white px-4 py-3 text-sm font-semibold text-slate-700 transition hover:border-slate-300 hover:bg-slate-50"
          >
            <LogOut size={16} />
            Sign out
          </button>
        </aside>

        <main className="glass-panel rounded-[2rem] border border-white/60 p-6 shadow-soft md:p-8">
          {children}
        </main>
      </div>
    </div>
  );
}
