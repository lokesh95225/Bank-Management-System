import { useEffect, useState } from "react";
import DataTable from "../components/DataTable";
import FormField from "../components/FormField";
import PageHeader from "../components/PageHeader";
import api, { getErrorMessage } from "../lib/api";

const amountTemplate = { accountNumber: "", amount: "", description: "" };
const transferTemplate = { sourceAccountNumber: "", targetAccountNumber: "", amount: "", description: "" };

export default function TransactionsPage() {
  const [accounts, setAccounts] = useState([]);
  const [selectedAccount, setSelectedAccount] = useState("");
  const [transactions, setTransactions] = useState([]);
  const [depositForm, setDepositForm] = useState(amountTemplate);
  const [withdrawForm, setWithdrawForm] = useState(amountTemplate);
  const [transferForm, setTransferForm] = useState(transferTemplate);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  const loadAccounts = async () => {
    const accountResponse = await api.get("/accounts/my");
    setAccounts(accountResponse.data);
    if (!selectedAccount && accountResponse.data[0]?.accountNumber) {
      setSelectedAccount(accountResponse.data[0].accountNumber);
    }
    return accountResponse.data;
  };

  const loadTransactions = async (accountNumber) => {
    if (!accountNumber) return;
    const response = await api.get(`/transactions/account/${accountNumber}?page=0&size=20`);
    setTransactions(response.data);
  };

  useEffect(() => {
    (async () => {
      try {
        const loadedAccounts = await loadAccounts();
        if (loadedAccounts[0]?.accountNumber) {
          await loadTransactions(loadedAccounts[0].accountNumber);
        }
      } catch (err) {
        setError(getErrorMessage(err));
      }
    })();
  }, []);

  useEffect(() => {
    if (selectedAccount) {
      loadTransactions(selectedAccount).catch((err) => setError(getErrorMessage(err)));
    }
  }, [selectedAccount]);

  const submitAction = async (endpoint, payload, reset) => {
    setError("");
    setMessage("");
    try {
      await api.post(endpoint, { ...payload, amount: Number(payload.amount) });
      setMessage("Transaction completed successfully.");
      reset();
      await loadAccounts();
      await loadTransactions(selectedAccount || payload.accountNumber || payload.sourceAccountNumber);
    } catch (err) {
      setError(getErrorMessage(err));
    }
  };

  return (
    <div className="space-y-8">
      <PageHeader
        eyebrow="Money movement"
        title="Run deposits, withdrawals, and transfers"
        description="Use this workspace to simulate everyday banking operations and review transaction trails instantly."
      />

      {error ? <p className="rounded-2xl bg-red-50 px-4 py-3 text-sm text-red-600">{error}</p> : null}
      {message ? <p className="rounded-2xl bg-emerald-50 px-4 py-3 text-sm text-emerald-700">{message}</p> : null}

      <div className="grid gap-6 xl:grid-cols-3">
        <form
          className="space-y-4 rounded-[2rem] border border-white/70 bg-white/80 p-6 shadow-soft"
          onSubmit={(event) => {
            event.preventDefault();
            submitAction("/transactions/deposit", depositForm, () => setDepositForm(amountTemplate));
          }}
        >
          <h2 className="font-display text-2xl font-bold text-slate-900">Deposit</h2>
          <FormField label="Account number" value={depositForm.accountNumber} onChange={(event) => setDepositForm({ ...depositForm, accountNumber: event.target.value })} required />
          <FormField label="Amount" type="number" value={depositForm.amount} onChange={(event) => setDepositForm({ ...depositForm, amount: event.target.value })} required />
          <FormField label="Description" value={depositForm.description} onChange={(event) => setDepositForm({ ...depositForm, description: event.target.value })} />
          <button className="w-full rounded-2xl bg-slate-950 px-4 py-3 text-sm font-semibold text-white">Run deposit</button>
        </form>

        <form
          className="space-y-4 rounded-[2rem] border border-white/70 bg-white/80 p-6 shadow-soft"
          onSubmit={(event) => {
            event.preventDefault();
            submitAction("/transactions/withdraw", withdrawForm, () => setWithdrawForm(amountTemplate));
          }}
        >
          <h2 className="font-display text-2xl font-bold text-slate-900">Withdraw</h2>
          <FormField label="Account number" value={withdrawForm.accountNumber} onChange={(event) => setWithdrawForm({ ...withdrawForm, accountNumber: event.target.value })} required />
          <FormField label="Amount" type="number" value={withdrawForm.amount} onChange={(event) => setWithdrawForm({ ...withdrawForm, amount: event.target.value })} required />
          <FormField label="Description" value={withdrawForm.description} onChange={(event) => setWithdrawForm({ ...withdrawForm, description: event.target.value })} />
          <button className="w-full rounded-2xl bg-slate-950 px-4 py-3 text-sm font-semibold text-white">Run withdrawal</button>
        </form>

        <form
          className="space-y-4 rounded-[2rem] border border-white/70 bg-white/80 p-6 shadow-soft"
          onSubmit={(event) => {
            event.preventDefault();
            submitAction("/transactions/transfer", transferForm, () => setTransferForm(transferTemplate));
          }}
        >
          <h2 className="font-display text-2xl font-bold text-slate-900">Transfer</h2>
          <FormField label="Source account" value={transferForm.sourceAccountNumber} onChange={(event) => setTransferForm({ ...transferForm, sourceAccountNumber: event.target.value })} required />
          <FormField label="Target account" value={transferForm.targetAccountNumber} onChange={(event) => setTransferForm({ ...transferForm, targetAccountNumber: event.target.value })} required />
          <FormField label="Amount" type="number" value={transferForm.amount} onChange={(event) => setTransferForm({ ...transferForm, amount: event.target.value })} required />
          <FormField label="Description" value={transferForm.description} onChange={(event) => setTransferForm({ ...transferForm, description: event.target.value })} />
          <button className="w-full rounded-2xl bg-slate-950 px-4 py-3 text-sm font-semibold text-white">Run transfer</button>
        </form>
      </div>

      <section className="space-y-4">
        <div className="flex flex-col gap-3 sm:flex-row sm:items-center sm:justify-between">
          <h2 className="font-display text-2xl font-bold text-slate-900">Transaction history</h2>
          <select
            value={selectedAccount}
            onChange={(event) => setSelectedAccount(event.target.value)}
            className="rounded-2xl border border-slate-200 bg-white px-4 py-3 text-sm"
          >
            <option value="">Select account</option>
            {accounts.map((account) => (
              <option key={account.accountNumber} value={account.accountNumber}>
                {account.accountName} ({account.accountNumber})
              </option>
            ))}
          </select>
        </div>
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
          emptyMessage="Select an account or create a transaction to populate this table."
        />
      </section>
    </div>
  );
}
