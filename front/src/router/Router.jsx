import { createBrowserRouter } from 'react-router-dom';
import App from '../App';
import ErrorPage from '../components/pages/ErrorPage';
import Login from '../components/pages/Login';
import Bookmark from '../components/pages/Bookmark';

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
]);

export default Router;
