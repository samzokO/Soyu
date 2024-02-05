import axios from 'axios';

const API = axios.create({
  baseURL: process.env.REACT_APP_BASE_URL,
});

API.interceptors.request.use((config) => ({
  ...config,
  headers: {
    Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
  },
}));

export const ImgAPI = axios.create({
  baseURL: process.env.REACT_APP_BASE_URL,
  headers: {
    ContentType: 'multipart/form-data',
    Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
  },
});

export default API;
