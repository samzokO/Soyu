const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = (app) => {
  app.use(
    '/ws/chat',
    createProxyMiddleware({
      target: 'http://i10b311.p.ssafy.io:8080/api',
      ws: true,
    }),
  );
  app.use(
    '/api',
    createProxyMiddleware({
      target: 'http://i10b311.p.ssafy.io:8080/api',
      changeOrigin: true,
      pathRewrite: {
        '^/api': '',
      },
    }),
  );
};
