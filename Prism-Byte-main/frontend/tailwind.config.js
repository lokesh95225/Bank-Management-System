/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,jsx}"],
  theme: {
    extend: {
      colors: {
        ink: "#0f172a",
        tide: "#e0f2fe",
        mint: "#d1fae5",
        coral: "#ffe4d6",
        sand: "#f8f2e8",
        brand: {
          50: "#eff6ff",
          100: "#dbeafe",
          500: "#2563eb",
          600: "#1d4ed8",
          700: "#1e40af",
          900: "#172554"
        }
      },
      fontFamily: {
        sans: ["Space Grotesk", "Segoe UI", "sans-serif"],
        display: ["Manrope", "Segoe UI", "sans-serif"]
      },
      boxShadow: {
        soft: "0 30px 80px rgba(15, 23, 42, 0.12)"
      }
    }
  },
  plugins: []
};
