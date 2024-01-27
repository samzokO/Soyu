import { initializeApp } from 'firebase/app';
import { getAnalytics } from 'firebase/analytics';
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
const analytics = getAnalytics(app);
