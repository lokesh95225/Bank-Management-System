import { useEffect, useMemo, useState } from "react";
import DataTable from "../components/DataTable";
import FormField from "../components/FormField";
import PageHeader from "../components/PageHeader";
import StatCard from "../components/StatCard";
import api, { getErrorMessage } from "../lib/api";

const initialForm = {
  accountName: "",
  accountType: "SAVINGS",
  currency: "USD"
};

export default function AccountsPage() {
  const [accounts, setAccounts] = useState([]);
  const [form, setForm] = useState(initialForm);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const [submitting, setSubmitting] = useState(false);

  const loadAccounts = async () => {
    try {
      const response = await api.get("/accounts/my");
      setAccounts(response.data);
    } catch (err) {
      setError(getErrorMessage(err));
    }
  };

  useEffect(() => {
    loadAccounts();
  }, []);

  const totalBalance = useMemo(
    () =>
      accounts
        .reduce((sum, account) => sum + Number(account.balance || 0), 0)
        .toLocaleString(undefined, { style: "currency", currency: accounts[0]?.currency || "USD" }),
    [accounts]
  );

  const handleSubmit = async (event) => {
    event.preventDefault();
    setSubmitting(true);
    setError("");
    setSuccess("");
    try {
      await api.post("/accounts", form);
      setSuccess("Account created successfully.");
      setForm(initialForm);
      await loadAccounts();
    } catch (err) {
      setError(getErrorMessage(err));
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="space-y-8">
      <PageHeader
        eyebrow="Account management"
        title="Build and monitor account portfolios"
        description="Create savings and current accounts, keep an eye on balances, and prepare accounts for transfers."
      />

      <div className="grid gap-4 md:grid-cols-3">
        <StatCard label="Total balance" value={totalBalance} tone="blue" />
        <StatCard label="Open accounts" value={String(accounts.length).padStart(2, "0")} tone="mint" />
        <StatCard
          label="Active accounts"
          value={String(accounts.filter((account) => account.status === "ACTIVE").length).padStart(2, "0")}
          tone="coral"
        />
      </div>

      <section className="grid gap-6 xl:grid-cols-[0.95fr_1.05fr]">
        <form className="space-y-4 rounded-[2rem] border border-white/70 bg-white/80 p-6 shadow-soft" onSubmit={handleSubmit}>
          <div>
            <h2 className="font-display text-2xl font-bold text-slate-900">Create a new account</h2>
            <p className="mt-2 text-sm text-slate-500">Create another account instantly under the signed-in customer profile.</p>
          </div>

          <FormField
            label="Account name"
            name="accountName"
            value={form.accountName}
            onChange={(event) => setForm({ ...form, accountName: event.target.value })}
            required
          />

          <label className="block space-y-2">
            <span className="text-sm font-medium text-slate-700">Account type</span>
            <select
              className="w-full rounded-2xl border border-slate-200 bg-white px-4 py-3 text-sm text-slate-900 outline-none transition focus:border-brand-500 focus:ring-4 focus:ring-brand-100"
              value={form.accountType}
              onChange={(event) => setForm({ ...form, accountType: event.target.value })}
            >
              <option value="SAVINGS">Savings</option>
              <option value="CURRENT">Current</option>
            </select>
          </label>

          <FormField
            label="Currency"
            name="currency"
            value={form.currency}
            onChange={(event) => setForm({ ...form, currency: event.target.value.toUpperCase() })}
            required
          />

          {error ? <p className="rounded-2xl bg-red-50 px-4 py-3 text-sm text-red-600">{error}</p> : null}
          {success ? <p className="rounded-2xl bg-emerald-50 px-4 py-3 text-sm text-emerald-700">{success}</p> : null}

          <button
            type="submit"
            disabled={submitting}
            className="w-full rounded-2xl bg-slate-950 px-4 py-3 text-sm font-semibold text-white transition hover:bg-slate-800 disabled:cursor-not-allowed disabled:opacity-70"
          >
            {submitting ? "Creating account..." : "Create account"}
          </button>
        </form>

        <div className="space-y-4">
          <h2 className="font-display text-2xl font-bold text-slate-900">Your account portfolio</h2>
          <DataTable
            columns={[
              { key: "accountNumber", label: "Account number" },
              { key: "accountName", label: "Account name" },
              { key: "accountType", label: "Type" },
              { key: "balance", label: "Balance", render: (row) => `${row.currency} ${row.balance}` },
              { key: "status", label: "Status" }
            ]}
            rows={accounts}
            emptyMessage="No accounts yet. Create one to start moving money."
          />
        </div>
      </section>
    </div>
  );
}
