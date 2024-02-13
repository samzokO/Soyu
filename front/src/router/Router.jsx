import { createBrowserRouter } from 'react-router-dom';
import MainPage from '../components/pages/MainPage';
import ErrorPage from '../components/pages/ErrorPage';
import Login from '../components/pages/Login';
import Bookmark from '../components/pages/Bookmark';
import MyPage from '../components/pages/MyPage';
import BuyList from '../components/pages/BuyList';
import ChatList from '../components/pages/ChatList';
import ChatRoom from '../components/pages/ChatRoom';
import NaverCallback from '../components/pages/NaverCallback';
import SearchPage from '../components/pages/SearchPage';
import ItemAddPage from '../components/pages/ItemAddPage';
import ItemDetailPage from '../components/pages/ItemDetailPage';
import Notification from '../components/pages/Notification';
import Map from '../components/pages/Map';
import Kiosk from '../components/pages/Kiosk';
import Category from '../components/pages/Category';
import Account from '../components/pages/Account';
import AppointmentModal from '../components/pages/AppointmentModal';

const Router = createBrowserRouter([
  {
    path: '/',
    element: <MainPage />,
    errorElement: <ErrorPage />,
  },
  {
    path: '/login',
    element: <Login />,
  },
  {
    path: '/item',
    element: <ItemAddPage />,
  },
  {
    path: '/item/:itemId',
    element: <ItemDetailPage />,
  },
  {
    path: '/search',
    element: <SearchPage />,
  },
  {
    path: '/naver/callback',
    element: <NaverCallback />,
  },
  {
    path: '/notification',
    element: <Notification />,
  },
  {
    path: '/bookmark',
    element: <Bookmark />,
  },
  {
    path: '/bookmark/:heart',
    element: <Bookmark />,
  },
  {
    path: '/category',
    element: <Category />,
  },
  {
    path: '/mypage',
    element: <MyPage />,
  },
  {
    path: '/mypage/:list',
    element: <BuyList />,
  },
  {
    path: '/mypage/account',
    element: <Account />,
  },
  {
    path: '/map',
    element: <Map />,
  },
  {
    path: '/kiosk',
    element: <Kiosk />,
  },
  {
    path: '/chat',
    element: <ChatList />,
  },
  {
    path: '/chat/:chatId',
    element: <ChatRoom />,
  },
  {
    path: '/station/:chatId',
    element: <AppointmentModal />,
  },
]);

export default Router;
