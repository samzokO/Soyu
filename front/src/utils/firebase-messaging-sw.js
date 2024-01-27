import { initializeApp } from 'firebase/app';
import { getAnalytics } from 'firebase/analytics';
import { getMessaging, getToken, onMessage } from 'firebase/messaging';
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: process.env.REACT_APP_FCM_APIKEY,
  authDomain: process.env.REACT_APP_FCM_AUTHDOMAIN,
  projectId: process.env.REACT_APP_FCM_PROJECTID,
  storageBucket: process.env.REACT_APP_FCM_STORAGEBUCKET,
  messagingSenderId: process.env.REACT_APP_FCM_MESSAGINGSENDERID,
  appId: process.env.REACT_APP_FCM_APPID,
  measurementId: process.env.REACT_APP_FCM_MEASUREMENTID,
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);

// analytics는 뭘까? 확인 필요
const analytics = getAnalytics(app);

const messaging = getMessaging(app);

async function requestPermission() {
  console.log('권한 요청 중...');

  const permission = await Notification.requestPermission();
  if (permission === 'denied') {
    console.log('알림 권한 허용 안됨');
    return;
  }

  console.log('알림 권한이 허용됨');

  const token = await getToken(messaging, {
    vapidKey: process.env.REACT_APP_FCM_VAPID_KEY,
  });

  if (token) console.log('token: ', token);
  else console.log('Can not get Token');

  onMessage(messaging, (payload) => {
    console.log('메시지가 도착했습니다.', payload);
    // ...
  });
}

requestPermission();
