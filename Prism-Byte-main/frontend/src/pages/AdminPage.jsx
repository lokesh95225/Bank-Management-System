import { useEffect, useState } from "react";
import DataTable from "../components/DataTable";
import PageHeader from "../components/PageHeader";
import api, { getErrorMessage } from "../lib/api";

export default function AdminPage() {
  const [users, setUsers] = useState([]);
  const [transactions, setTransactions] = useState([]);
  const [accountNumber, setAccountNumber] = useState("");
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  const loadData = async () => {
    const [userResponse, transactionResponse] = await Promise.all([
      api.get("/admin/users"),
      api.get("/admin/transactions?page=0&size=20")
    ]);
    setUsers(userResponse.data);
    setTransactions(transactionResponse.data);
  };

  useEffect(() => {
    loadData().catch((err) => setError(getErrorMessage(err)));
  }, []);

  const runAdminAction = async (path, successMessage) => {
    setError("");
    setMessage("");
    try {
      await api.patch(path);
      setMessage(successMessage);
      await loadData();
    } catch (err) {
      setError(getErrorMessage(err));
    }
  };

  return (
    <div className="space-y-8">
      <PageHeader
        eyebrow="Back office"
        title="Admin and employee operations"
        description="Review users, inspect system-wide transaction activity, and freeze or unfreeze accounts when support or risk workflows require it."
      />

      <div className="rounded-[2rem] border border-white/70 bg-white/80 p-6 shadow-soft">
        <div className="flex flex-col gap-3 md:flex-row md:items-center">
          <input
            className="flex-1 rounded-2xl border border-slate-200 bg-white px-4 py-3 text-sm"
            placeholder="Enter account number to freeze or unfreeze"
            value={accountNumber}
            onChange={(event) => setAccountNumber(event.target.value)}
          />
          <button
            className="rounded-2xl bg-red-600 px-5 py-3 text-sm font-semibold text-white"
            onClick={() => runAdminAction(`/admin/accounts/${accountNumber}/freeze`, "Account frozen successfully.")}
          >
            Freeze account
          </button>
          <button
            className="rounded-2xl bg-emerald-600 px-5 py-3 text-sm font-semibold text-white"
            onClick={() => runAdminAction(`/admin/accounts/${accountNumber}/unfreeze`, "Account unfrozen successfully.")}
          >
            Unfreeze account
          </button>
        </div>
        {error ? <p className="mt-4 rounded-2xl bg-red-50 px-4 py-3 text-sm text-red-600">{error}</p> : null}
        {message ? <p className="mt-4 rounded-2xl bg-emerald-50 px-4 py-3 text-sm text-emerald-700">{message}</p> : null}
      </div>

      <section className="space-y-4">
        <h2 className="font-display text-2xl font-bold text-slate-900">Users</h2>
        <DataTable
          columns={[
            { key: "fullName", label: "Name" },
            { key: "email", label: "Email" },
            { key: "roles", label: "Roles", render: (row) => row.roles.join(", ") },
            { key: "enabled", label: "Enabled" },
            { key: "locked", label: "Locked" }
          ]}
          rows={users}
          emptyMessage="User records will appear here."
        />
      </section>

      <section className="space-y-4">
        <h2 className="font-display text-2xl font-bold text-slate-900">Recent system transactions</h2>
        <DataTable
          columns={[
            { key: "reference", label: "Reference" },
            { key: "type", label: "Type" },
            { key: "amount", label: "Amount" },
            { key: "sourceAccountNumber", label: "Source" },
            { key: "targetAccountNumber", label: "Target" },
            { key: "createdAt", label: "Created", render: (row) => new Date(row.createdAt).toLocaleString() }
          ]}
          rows={transactions}
          emptyMessage="Transaction records will appear here."
        />
      </section>
    </div>
  );
}
