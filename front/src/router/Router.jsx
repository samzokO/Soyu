import { createBrowserRouter } from 'react-router-dom';
import App from '../App';
import ErrorPage from '../components/pages/ErrorPage';

const Router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    errorElement: <ErrorPage />,
  },
  {
    path: '/',
    element: <App />,
  },
]);

export default Router;
