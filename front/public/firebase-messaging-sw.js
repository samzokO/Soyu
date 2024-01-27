importScripts(
  'https://www.gstatic.com/firebasejs/10.7.2/firebase-app-compat.js',
);
importScripts(
  'https://www.gstatic.com/firebasejs/10.7.2/firebase-messaging-compat.js',
);

const firebaseConfig = {
  apiKey: 'AIzaSyBPWZ3BSoIEQROrjk8VYLWrd2ECugVCiXQ',
  authDomain: 'soyu-fe75a.firebaseapp.com',
  projectId: 'soyu-fe75a',
  storageBucket: 'soyu-fe75a.appspot.com',
  messagingSenderId: '592048161601',
  appId: '1:592048161601:web:d2b8c5829193486ec517c0',
  measurementId: 'G-7RP8TBM7H5',
};

firebase.initializeApp(firebaseConfig);
const messaging = firebase.messaging();

messaging.onBackgroundMessage(function (payload) {
  console.log('Received background message ', payload);

  const notificationTitle = payload.notification.title;
  const notificationOptions = {
    body: payload.notification.body,
  };

  self.registration.showNotification(notificationTitle, notificationOptions);
});

self.addEventListener('install', function (e) {
  console.log('fcm sw install..');
  self.skipWaiting();
});

self.addEventListener('activate', function (e) {
  console.log('fcm sw activate..');
});

self.addEventListener('push', function (e) {
  console.log('push: ', e.data.json());
  if (!e.data.json()) return;

  const resultData = e.data.json().notification;
  const notificationTitle = resultData.title;
  const notificationOptions = {
    body: resultData.body,
    icon: resultData.image,
    tag: resultData.tag,
    ...resultData,
  };
  console.log('push: ', { resultData, notificationTitle, notificationOptions });

  self.registration.showNotification(notificationTitle, notificationOptions);
});

self.addEventListener('notificationclick', function (event) {
  console.log('notification click');
  const url = '/';
  event.notification.close();
  event.waitUntil(clients.openWindow(url));
});
