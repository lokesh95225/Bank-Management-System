import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import FormField from "../components/FormField";
import { useAuth } from "../context/AuthContext";

export default function RegisterPage() {
  const navigate = useNavigate();
  const { register, getErrorMessage } = useAuth();
  const [form, setForm] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    phoneNumber: "",
    address: ""
  });
  const [error, setError] = useState("");
  const [submitting, setSubmitting] = useState(false);

  const handleSubmit = async (event) => {
    event.preventDefault();
    setSubmitting(true);
    setError("");
    try {
      await register(form);
      navigate("/dashboard", { replace: true });
    } catch (err) {
      setError(getErrorMessage(err));
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="page-grid min-h-screen px-4 py-6 md:px-8">
      <div className="glass-panel mx-auto max-w-3xl rounded-[2.5rem] border border-white/60 p-6 shadow-soft md:p-10">
        <p className="text-xs font-semibold uppercase tracking-[0.4em] text-brand-600">Customer onboarding</p>
        <h1 className="mt-4 font-display text-4xl font-extrabold text-slate-900">Create your digital banking profile</h1>
        <p className="mt-3 max-w-2xl text-sm text-slate-500">
          This creates a customer login instantly and signs you into the dashboard.
        </p>

        <form className="mt-8 grid gap-4 md:grid-cols-2" onSubmit={handleSubmit}>
          <FormField label="First name" name="firstName" value={form.firstName} onChange={(event) => setForm({ ...form, firstName: event.target.value })} required />
          <FormField label="Last name" name="lastName" value={form.lastName} onChange={(event) => setForm({ ...form, lastName: event.target.value })} required />
          <FormField label="Email" name="email" type="email" value={form.email} onChange={(event) => setForm({ ...form, email: event.target.value })} required />
          <FormField label="Phone number" name="phoneNumber" value={form.phoneNumber} onChange={(event) => setForm({ ...form, phoneNumber: event.target.value })} />
          <div className="md:col-span-2">
            <FormField
              label="Password"
              name="password"
              type="password"
              value={form.password}
              onChange={(event) => setForm({ ...form, password: event.target.value })}
              placeholder="Use upper, lower, digit, and special character"
              required
            />
          </div>
          <div className="md:col-span-2">
            <FormField label="Address" name="address" value={form.address} onChange={(event) => setForm({ ...form, address: event.target.value })} />
          </div>
          {error ? <p className="rounded-2xl bg-red-50 px-4 py-3 text-sm text-red-600 md:col-span-2">{error}</p> : null}
          <div className="md:col-span-2 flex flex-col gap-3 sm:flex-row sm:items-center sm:justify-between">
            <button
              type="submit"
              disabled={submitting}
              className="rounded-2xl bg-slate-950 px-5 py-3 text-sm font-semibold text-white transition hover:bg-slate-800 disabled:cursor-not-allowed disabled:opacity-70"
            >
              {submitting ? "Creating profile..." : "Create account"}
            </button>
            <Link className="text-sm font-semibold text-brand-600" to="/login">
              Back to login
            </Link>
          </div>
        </form>
      </div>
    </div>
  );
}
