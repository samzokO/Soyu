import API, { ImgAPI } from './config';

export const getRoomList = () => API.get('/chats', 'CONFIG');

export const getChatList = (roomId) =>
  API.post(`/chat/${roomId}`, 'data', 'config');

export const getNaverCode = (code, state) =>
  API.post('/naver/login', {
    authorizationCode: `${code}`,
    state: `${state}`,
  });

export const getItemList = () => API.get('/item/items');

export const getItem = (item) => API.get(`/item/${item}`);

export const getKeyword = (keyword) =>
  API.post(`/item/keyword`, { keyword: `${keyword}` });

export const getCategory = (category) => API.get(`/item/category/${category}`);

export const updateStatus = () =>
  API.put('/item/status', {
    itemId: `6`,
    itemStatus: `WITHDRAW`,
  });

export const postImg = (data, img) => {
  const formData = new FormData();
  formData.append('image', img);
  formData.append(
    'itemCreateRequest',
    new Blob(
      [
        JSON.stringify({
          title: data.title,
          content: data.content,
          price: data.price,
          itemCategories: data.itemCategories,
        }),
      ],
      { type: 'application/json' },
    ),
  );
  ImgAPI.post('item', formData);
};
