import axios from 'axios';

const API = axios.create({
  baseURL: '<BaseURL>',
});

API.interceptors.request.use((config) => ({
  ...config,
  headers: {
    Authorization: `Bearer <accessToken>`,
  },
}));

export default API;
