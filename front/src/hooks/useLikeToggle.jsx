import { useEffect, useState } from 'react';
import { getLikeOnOff } from '../api/apis';

function useLikeToggle(likesStatus) {
  const [data, setData] = useState();
  const changer = (itemId) => {
    getLikeOnOff(itemId).then(() => {});
    setData(!data);
  };
  useEffect(() => {
    setData(likesStatus);
  }, []);
  return [data, changer];
}

export default useLikeToggle;
