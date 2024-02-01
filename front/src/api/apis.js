import API from './config';

export const getRoomList = () => API.get('/chats', 'CONFIG');

export const getChatList = (roomId) =>
  API.post(`/chat/${roomId}`, 'data', 'config');

export const getNaverCode = (code, state) =>
  API.post('/naver/login', {
    authorizationCode: `${code}`,
    state: `${state}`,
  });
