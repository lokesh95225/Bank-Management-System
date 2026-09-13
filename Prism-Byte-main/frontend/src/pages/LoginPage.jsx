import { Landmark, ShieldCheck, TrendingUp } from "lucide-react";
import { useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import FormField from "../components/FormField";
import { useAuth } from "../context/AuthContext";

const credentials = [
  { label: "Admin", email: "admin@prismbyte.com", password: "Admin@12345" },
  { label: "Employee", email: "employee@prismbyte.com", password: "Employee@12345" },
  { label: "Customer", email: "customer@prismbyte.com", password: "Customer@12345" }
];

export default function LoginPage() {
  const navigate = useNavigate();
  const location = useLocation();
  const { login, getErrorMessage } = useAuth();
  const [form, setForm] = useState({ email: "customer@prismbyte.com", password: "Customer@12345" });
  const [error, setError] = useState("");
  const [submitting, setSubmitting] = useState(false);

  const handleSubmit = async (event) => {
    event.preventDefault();
    setSubmitting(true);
    setError("");
    try {
      await login(form.email, form.password);
      navigate(location.state?.from?.pathname || "/dashboard", { replace: true });
    } catch (err) {
      setError(getErrorMessage(err));
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="page-grid min-h-screen px-4 py-6 md:px-8">
      <div className="mx-auto grid min-h-[calc(100vh-3rem)] max-w-7xl gap-6 lg:grid-cols-[1.1fr_0.9fr]">
        <section className="hidden rounded-[2.5rem] bg-slate-950 p-10 text-white shadow-soft lg:block">
          <div className="flex h-full flex-col justify-between">
            <div>
              <p className="text-xs font-semibold uppercase tracking-[0.4em] text-sky-300">Prismbyte Banking</p>
              <h1 className="mt-6 max-w-xl font-display text-6xl font-extrabold leading-tight">
                A sharper control room for modern banking operations.
              </h1>
              <p className="mt-6 max-w-lg text-lg text-slate-300">
                Track balances, move funds, monitor transaction activity, and manage back-office workflows in one place.
              </p>
            </div>

            <div className="grid gap-4 md:grid-cols-3">
              <div className="rounded-3xl bg-white/10 p-5">
                <Landmark className="text-sky-300" />
                <p className="mt-4 font-semibold">Multi-account banking</p>
                <p className="mt-2 text-sm text-slate-300">Savings and current accounts with transaction history.</p>
              </div>
              <div className="rounded-3xl bg-white/10 p-5">
                <ShieldCheck className="text-emerald-300" />
                <p className="mt-4 font-semibold">Role-based security</p>
                <p className="mt-2 text-sm text-slate-300">Separate customer, employee, and admin experiences.</p>
              </div>
              <div className="rounded-3xl bg-white/10 p-5">
                <TrendingUp className="text-orange-300" />
                <p className="mt-4 font-semibold">Operational clarity</p>
                <p className="mt-2 text-sm text-slate-300">Make decisions faster with one clean dashboard.</p>
              </div>
            </div>
          </div>
        </section>

        <section className="glass-panel flex rounded-[2.5rem] border border-white/60 p-6 shadow-soft md:p-10">
          <div className="m-auto w-full max-w-md">
            <p className="text-xs font-semibold uppercase tracking-[0.4em] text-brand-600">Welcome back</p>
            <h2 className="mt-4 font-display text-4xl font-extrabold text-slate-900">Sign in to your workspace</h2>
            <p className="mt-3 text-sm text-slate-500">Use one of the seeded roles below or your registered customer account.</p>

            <div className="mt-6 grid gap-3">
              {credentials.map((credential) => (
                <button
                  key={credential.label}
                  type="button"
                  onClick={() => setForm({ email: credential.email, password: credential.password })}
                  className="rounded-2xl border border-slate-200 bg-white px-4 py-3 text-left transition hover:border-brand-300 hover:bg-brand-50"
                >
                  <p className="text-sm font-semibold text-slate-900">{credential.label}</p>
                  <p className="text-xs text-slate-500">{credential.email}</p>
                </button>
              ))}
            </div>

            <form className="mt-8 space-y-4" onSubmit={handleSubmit}>
              <FormField
                label="Email"
                name="email"
                value={form.email}
                onChange={(event) => setForm({ ...form, email: event.target.value })}
                type="email"
                required
              />
              <FormField
                label="Password"
                name="password"
                value={form.password}
                onChange={(event) => setForm({ ...form, password: event.target.value })}
                type="password"
                required
              />
              {error ? <p className="rounded-2xl bg-red-50 px-4 py-3 text-sm text-red-600">{error}</p> : null}
              <button
                type="submit"
                disabled={submitting}
                className="w-full rounded-2xl bg-slate-950 px-4 py-3 text-sm font-semibold text-white transition hover:bg-slate-800 disabled:cursor-not-allowed disabled:opacity-70"
              >
                {submitting ? "Signing in..." : "Sign in"}
              </button>
            </form>

            <p className="mt-6 text-sm text-slate-500">
              New customer?{" "}
              <Link className="font-semibold text-brand-600" to="/register">
                Create an account
              </Link>
            </p>
          </div>
        </section>
      </div>
    </div>
  );
}
