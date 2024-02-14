import axios from 'axios';

const API = axios.create({
  baseURL: '/api',
});

API.interceptors.request.use((config) => {
  const accessToken = localStorage.getItem('accessToken');
  if (accessToken) {
    return {
      ...config,
      headers: {
        Authorization: `Bearer ${accessToken}`,
        ...config.headers,
      },
    };
  }
  return config;
});

API.interceptors.response.use(
  (response) => response,

  // 200번대 응답이 아닐 경우 처리
  async (error) => {
    const { config } = error;
    // 토큰 만료시
    if (error.response?.data.statusCode === 401) {
      const originRequest = config;
      const { url, method, data } = originRequest;
      // 리프레시 토큰 api
      const response = await axios
        .post(
          '/api/member/token',
          {},
          {
            headers: {
              Authorization: `Bearer ${localStorage.getItem('refreshToken')}`,
            },
          },
        )
        .catch(() => {
          window.location.href = '/login';
        });
      // 리프레시 토큰 요청이 성공할 때
      if (response.status === 200) {
        const newAccessToken = response.data.data.accessToken;
        localStorage.setItem('accessToken', response.data.data.accessToken);
        localStorage.setItem('refreshToken', response.data.data.refreshToken);
        axios.defaults.headers.common.Authorization = `Bearer ${newAccessToken}`;
        // 진행중이던 요청 이어서하기
        originRequest.headers.Authorization = `Bearer ${newAccessToken}`;
        const res = axios.create(originRequest);
        switch (method) {
          case 'get':
            return res.get(url);
          case 'post':
            return res.post(url, data);
          case 'put':
            return res.put(url, data);
          case 'patch':
            return res.patch(url, data);
          case 'delete':
            return res.delete(url, data);
          default:
            throw new Error(`${method}`);
        }
        // 리프레시 토큰 요청이 실패할때(리프레시 토큰도 만료되었을때 = 재로그인 안내)
      }
      if (response.status === 401) {
        window.location.href = '/login';
      }
    }
    return error;
  },
);

export const ImgAPI = axios.create({
  // baseURL: process.env.REACT_APP_BASE_URL,
  baseURL: '/api',
  headers: {
    ContentType: 'multipart/form-data',
    Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
  },
});

ImgAPI.interceptors.response.use(
  (response) => response,
  // 200번대 응답이 아닐 경우 처리
  async (error) => {
    const {
      config,
      // response: { status },
    } = error;
    // 토큰 만료시
    if (error.response.data.statusCode === 401) {
      const originRequest = config;
      const { url, method, data } = originRequest;
      // 리프레시 토큰 api
      const response = await axios
        .post(
          '/member/token',
          {},
          {
            headers: {
              Authorization: `Bearer ${localStorage.getItem('refreshToken')}`,
            },
          },
        )
        .catch(() => {
          window.location.href = '/login';
        });
      // 리프레시 토큰 요청이 성공할 때
      if (response.status === 200) {
        const newAccessToken = response.data.data.accessToken;
        localStorage.setItem('accessToken', response.data.data.accessToken);
        localStorage.setItem('refreshToken', response.data.data.refreshToken);
        axios.defaults.headers.common.Authorization = `Bearer ${newAccessToken}`;
        // 진행중이던 요청 이어서하기
        originRequest.headers.Authorization = `Bearer ${newAccessToken}`;
        const res = axios.create(originRequest);
        switch (method) {
          case 'get':
            return res.get(url);
          case 'post':
            return res.post(url, data);
          case 'put':
            return res.put(url, data);
          case 'patch':
            return res.patch(url, data);
          case 'delete':
            return res.delete(url, data);
          default:
            throw new Error(`${method}`);
        }
        // 리프레시 토큰 요청이 실패할때(리프레시 토큰도 만료되었을때 = 재로그인 안내)
      }
      if (response.status === 401) {
        window.location.href = '/login';
      }
    }
    return error;
  },
);

export default API;
