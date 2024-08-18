const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port : 3000,
    // chageOrigin: true,
    // logLevel: 'debug',
    // proxy: 'http://localhost:8080',
  }
})
