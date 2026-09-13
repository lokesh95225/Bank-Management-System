import { useEffect, useState } from "react";
import FormField from "../components/FormField";
import PageHeader from "../components/PageHeader";
import { useAuth } from "../context/AuthContext";
import api, { getErrorMessage } from "../lib/api";

export default function ProfilePage() {
  const { profile, refreshProfile } = useAuth();
  const [form, setForm] = useState({
    firstName: profile?.firstName || "",
    lastName: profile?.lastName || "",
    phoneNumber: profile?.phoneNumber || "",
    address: profile?.address || ""
  });
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  useEffect(() => {
    setForm({
      firstName: profile?.firstName || "",
      lastName: profile?.lastName || "",
      phoneNumber: profile?.phoneNumber || "",
      address: profile?.address || ""
    });
  }, [profile]);

  const handleSubmit = async (event) => {
    event.preventDefault();
    setError("");
    setMessage("");
    try {
      await api.put("/users/me", form);
      await refreshProfile();
      setMessage("Profile updated successfully.");
    } catch (err) {
      setError(getErrorMessage(err));
    }
  };

  return (
    <div className="space-y-8">
      <PageHeader
        eyebrow="Profile"
        title="Keep identity and contact data current"
        description="Update your customer profile information without leaving the workspace."
      />

      <div className="grid gap-6 xl:grid-cols-[0.9fr_1.1fr]">
        <div className="rounded-[2rem] bg-slate-950 p-6 text-white shadow-soft">
          <p className="text-xs uppercase tracking-[0.35em] text-sky-300">Identity snapshot</p>
          <h2 className="mt-4 font-display text-3xl font-bold">
            {profile?.firstName} {profile?.lastName}
          </h2>
          <p className="mt-2 text-slate-300">{profile?.email}</p>
          <div className="mt-6 space-y-3 text-sm text-slate-300">
            <p>Roles: {profile?.roles?.join(", ")}</p>
            <p>Phone: {profile?.phoneNumber || "Not set"}</p>
            <p>Address: {profile?.address || "Not set"}</p>
          </div>
        </div>

        <form className="space-y-4 rounded-[2rem] border border-white/70 bg-white/80 p-6 shadow-soft" onSubmit={handleSubmit}>
          <FormField label="First name" value={form.firstName} onChange={(event) => setForm({ ...form, firstName: event.target.value })} required />
          <FormField label="Last name" value={form.lastName} onChange={(event) => setForm({ ...form, lastName: event.target.value })} required />
          <FormField label="Phone number" value={form.phoneNumber} onChange={(event) => setForm({ ...form, phoneNumber: event.target.value })} />
          <FormField label="Address" value={form.address} onChange={(event) => setForm({ ...form, address: event.target.value })} />
          {error ? <p className="rounded-2xl bg-red-50 px-4 py-3 text-sm text-red-600">{error}</p> : null}
          {message ? <p className="rounded-2xl bg-emerald-50 px-4 py-3 text-sm text-emerald-700">{message}</p> : null}
          <button className="rounded-2xl bg-slate-950 px-5 py-3 text-sm font-semibold text-white">Save profile</button>
        </form>
      </div>
    </div>
  );
}
