import { useEffect, useState } from "react";
import PageHeader from "../components/PageHeader";
import StatCard from "../components/StatCard";
import DataTable from "../components/DataTable";
import api, { getErrorMessage } from "../lib/api";
import { useAuth } from "../context/AuthContext";

export default function DashboardPage() {
  const { profile, roles } = useAuth();
  const [accounts, setAccounts] = useState([]);
  const [transactions, setTransactions] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    const load = async () => {
      try {
        const accountResponse = await api.get("/accounts/my");
        setAccounts(accountResponse.data);
        if (accountResponse.data[0]?.accountNumber) {
          const transactionResponse = await api.get(
            `/transactions/account/${accountResponse.data[0].accountNumber}?page=0&size=8`
          );
          setTransactions(transactionResponse.data);
        }
      } catch (err) {
        setError(getErrorMessage(err));
      }
    };
    load();
  }, []);

  const totalBalance = accounts
    .reduce((sum, account) => sum + Number(account.balance || 0), 0)
    .toLocaleString(undefined, { style: "currency", currency: accounts[0]?.currency || "USD" });

  return (
    <div className="space-y-8">
      <PageHeader
        eyebrow="Operations overview"
        title={`Good to see you, ${profile?.firstName || "there"}`}
        description="Your banking cockpit surfaces balances, recent money movement, and role-aware operational access in one place."
      />

      {error ? <p className="rounded-2xl bg-red-50 px-4 py-3 text-sm text-red-600">{error}</p> : null}

      <div className="grid gap-4 md:grid-cols-3">
        <StatCard label="Live balance" value={totalBalance} tone="blue" helper="Combined across your visible accounts" />
        <StatCard label="Accounts" value={String(accounts.length).padStart(2, "0")} tone="mint" helper="Savings and current accounts under your profile" />
        <StatCard label="Roles" value={roles.join(", ") || "Customer"} tone="coral" helper="Access adapts to customer, employee, and admin permissions" />
      </div>

      <section className="grid gap-6 xl:grid-cols-[1.2fr_0.8fr]">
        <div className="space-y-4">
          <h2 className="font-display text-2xl font-bold text-slate-900">Recent activity</h2>
          <DataTable
            columns={[
              { key: "type", label: "Type" },
              { key: "amount", label: "Amount" },
              { key: "sourceAccountNumber", label: "Source" },
              { key: "targetAccountNumber", label: "Target" },
              { key: "createdAt", label: "Created", render: (row) => new Date(row.createdAt).toLocaleString() }
            ]}
            rows={transactions}
            emptyMessage="Make a deposit, withdrawal, or transfer to see activity here."
          />
        </div>

        <div className="rounded-[2rem] bg-slate-950 p-6 text-white shadow-soft">
          <p className="text-xs font-semibold uppercase tracking-[0.35em] text-sky-300">Quick tips</p>
          <h2 className="mt-4 font-display text-3xl font-bold">Get the most from the workspace</h2>
          <ul className="mt-6 space-y-4 text-sm text-slate-300">
            <li>Create a second account to test internal transfers.</li>
            <li>Use the admin console to freeze and unfreeze accounts for support workflows.</li>
            <li>Open Swagger if you want to compare UI flows against the raw API contracts.</li>
          </ul>
        </div>
      </section>
    </div>
  );
}
