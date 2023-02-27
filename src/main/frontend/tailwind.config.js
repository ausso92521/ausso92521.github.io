/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    './pages/**/*.{js,ts,jsx,tsx}',
    './components/**/*.{js,ts,jsx,tsx}',
    './app/**/*.{js,ts,jsx,tsx}',
  ],
  theme: {
    extend: {
      colors: {
        accent: '#8BC34A',
        secondary: '#FFB74D',
        primary: '#64B5F6'
      }
    },
    fontFamily: {
      'display': ['Bubblegum Sans'],
      'body': ['Open Sans'],
    }
  },
  plugins: [],
}
