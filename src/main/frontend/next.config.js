/** @type {import('next').NextConfig} */
const proxy = require("http-proxy-middleware");

module.exports = {
  devProxy: {
    "/api/v1": {
      target: "https://localhost:443/api/v1",
      changeOrigin: true,
      pathRewrite: {
        "^/api/v1": "",
      },
    },
  },
  reactStrictMode: true,
  experimental:{
    appDir: true
  },
}
