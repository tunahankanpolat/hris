/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        "job-posts-background":"#f3f2ef",
        "obss-blue":"#0a66c2",
        "obss-gray":"#191919",
      }
    },
  },
  plugins: [],
}

