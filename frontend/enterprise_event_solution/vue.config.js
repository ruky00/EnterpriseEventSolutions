const { defineConfig } = require("@vue/cli-service")

module.exports = defineConfig({
  devServer: {
    proxy: {
      '^/api': {
        target: 'https://127.0.0.1:8443/',
        ws: true,
        changeOrigin: true
      },
    }
  },
})
