module.exports = {
  transpileDependencies: true,

  pluginOptions: {
    webpackBundleAnalyzer: {
      openAnalyzer: false
    },
  devServer: {
    proxy: {
      '^/api': {
        target: 'https://127.0.0.1:8443/',
        ws: true,
        changeOrigin: true
      },
    }
  },
}
}
