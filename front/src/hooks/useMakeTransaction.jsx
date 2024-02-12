import { useNavigate } from 'react-router-dom';
import { makeChat } from '../api/apis';

function useMakeTransaction() {
  const navigate = useNavigate();

  const onMoveHandler = (itemId, buyerId, sellerId) => {
    makeChat({ itemId, buyerId, sellerId }).then(({ data }) => {
      navigate(`/chat/${data.data.chatId}`, { state: { itemId } });
    });
  };

  return onMoveHandler;
}

export default useMakeTransaction;
