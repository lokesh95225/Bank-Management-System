export default function StatCard({ label, value, tone = "blue", helper }) {
  const tones = {
    blue: "from-brand-500/15 to-sky-200/30",
    mint: "from-emerald-300/20 to-teal-100/40",
    coral: "from-orange-300/25 to-amber-100/40"
  };

  return (
    <div className={`rounded-3xl border border-white/70 bg-gradient-to-br ${tones[tone]} p-6 shadow-soft`}>
      <p className="text-sm text-slate-500">{label}</p>
      <p className="mt-4 font-display text-3xl font-extrabold text-slate-900">{value}</p>
      {helper ? <p className="mt-2 text-xs text-slate-500">{helper}</p> : null}
    </div>
  );
}
