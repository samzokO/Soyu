import { initializeApp } from 'firebase/app';
import { getMessaging, getToken, onMessage } from 'firebase/messaging';

const firebaseConfig = {
  apiKey: String(process.env.REACT_APP_FCM_APIKEY),
  authDomain: String(process.env.REACT_APP_FCM_AUTHDOMAIN),
  projectId: String(process.env.REACT_APP_FCM_PROJECTID),
  storageBucket: String(process.env.REACT_APP_FCM_STORAGEBUCKET),
  messagingSenderId: String(process.env.REACT_APP_FCM_MESSAGINGSENDERID),
  appId: String(process.env.REACT_APP_FCM_APPID),
  measurementId: String(process.env.REACT_APP_FCM_MEASUREMENTID),
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const messaging = getMessaging(app);

/** 클라이언트에 알림권한 요청하는 함수
 * @author 호진
 * @param nothing
 * @returns 디바이스 토큰
 */
async function requestPermission() {
  console.log('권한 요청 중...');

  const permission = await Notification.requestPermission();
  if (permission === 'denied') {
    console.log('알림 권한 불허');
    return;
  }

  console.log('알림 권한이 허용됨');
  getToken(messaging, {
    vapidKey: process.env.REACT_APP_FCM_VAPID_KEY,
  })
    .then((currentToken) => {
      if (currentToken) {
        localStorage.setItem('fcmToken', currentToken);
      } else {
        // Show permission request UI
        console.log(
          'No registration token available. Request permission to generate one.',
        );
        // ...
      }
    })
    .catch((err) => {
      console.log('An error occurred while retrieving token. ', err);
      // ...
    });

  onMessage(messaging, (payload) => {
    console.log('메시지가 도착했습니다.', payload);
  });
}

requestPermission();
