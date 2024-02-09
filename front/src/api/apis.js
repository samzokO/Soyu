import API, { ImgAPI } from './config';

export const getRoomList = () => API.get('/chats', 'CONFIG');

export const getChatList = (roomId) =>
  API.post(`/chat/${roomId}`, 'data', 'config');

/** 네이버 소셜 로그인 */
export const getNaverCode = (code, state) =>
  API.post('/naver/login', {
    authorizationCode: `${code}`,
    state: `${state}`,
  });

/** 아이템 리스트 불러오기 */
export const getItemList = () => API.get('/item/items');

/** 내역 리스트 불러오기 */
export const getHistoryList = () => API.get('/history/sale');

/** 아이템 상세정보 불러오기 */
export const getItem = (item) => API.get(`/item/${item}`);

/** 마이페이지 정보 불러오기 */
export const getMine = () => API.get(`/member`);

/** 키워드로 검색 */
export const getKeyword = (keyword) =>
  API.post(`/item/keyword`, { keyword: `${keyword}` });

/** 카테고리별 등록물품 조회하기 */
export const getCategory = (category) => API.get(`/item/category/${category}`);

/** 아이템 아이디로 상태 변경하기 */
export const updateStatus = () =>
  API.put('/item/status', {
    itemId: `6`,
    itemStatus: `WITHDRAW`,
  });

/** 이미지 업로드 */
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

/* 키오스크 */

/** 키오스크 회수 코드 확인 */
export const kioskWithdraw = (code) => {
  API.get(`/kiosk/withdraw/${code}`);
};

/**  키오스크 DP/거래예약 보관시작 코드 확인 */
export const kioskSell = (code) => {
  API.get(`/kiosk/sell/${code}`);
};
/** 키오스크 회수 코드 확인 */
export const kioskBuy = (stationId, code) => {
  API.get(`/kiosk/buy/${stationId}/${code}`);
};

/** 키오스크 물건 구매 결정시에만  */
export const kioskMakePurchase = () => {
  API.get(`/kiosk/dp`);
};

/* 알림 */

/** 로그인시 디바이스토큰 등록 */
export const postFcm = (fcm) => API.post(`/fcm`, { token: `${fcm}` });

/** 알림 내역 조회 */
export const getNotice = () => API.get(`/notice`);

/* 스테이션 */

/** 스테이션 리스트 조회 */
export const getStation = () => API.get(`/station`);

/** 스테이션 즐겨찾기 On / Off */
export const favoriteOnOff = (stationId) => API.post(`/favorite/${stationId}`);

/** 즐겨찾기한 스테이션 조회 */
export const getFavorite = () => API.get(`/favorite`);

/* 좋아요 */

/** 찜 리스트 조회 */
export const getLikes = () => API.get(`/likes`);

/** 찜 On Off */
export const getLikeOnOff = (itemId) => API.post(`/likes/${itemId}`);
