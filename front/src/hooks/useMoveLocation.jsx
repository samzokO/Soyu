import { useNavigate } from 'react-router-dom';

function useMoveLocation(uri) {
  const navigate = useNavigate();
  return () => navigate(uri);
}

export default useMoveLocation;
