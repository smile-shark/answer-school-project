const { defineConfig } = require('@vue/cli-service')
module.exports = {
  devServer: {
    port: 8081,
    proxy: {
      '/javaSever': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        pathRewrite: { '^/javaSever': '' },
      }
    }
  }
}
