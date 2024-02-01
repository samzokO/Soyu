import { createBrowserRouter } from 'react-router-dom';
import App from '../App';
import ErrorPage from '../components/pages/ErrorPage';
import Login from '../components/pages/Login';
import Bookmark from '../components/pages/Bookmark';
import MyPage from '../components/pages/MyPage';
import BuyList from '../components/pages/BuyList';
import ChatList from '../components/pages/ChatList';

const Router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    errorElement: <ErrorPage />,
  },
  {
    path: '/login',
    element: <Login />,
  },
  {
    path: '/bookmark',
    element: <Bookmark />,
  },
  {
    path: '/mypage',
    element: <MyPage />,
  },
  {
    path: '/mypage/buylist',
    element: <BuyList />,
  },
  {
    path: '/chat',
    element: <ChatList />,
  },
]);

export default Router;
