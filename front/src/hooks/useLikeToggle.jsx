import { useEffect, useState } from 'react';
import { getLikeOnOff } from '../api/apis';

function useLikeToggle() {
  const [data, setData] = useState();
  const changer = (itemId) => {
    getLikeOnOff(itemId).then(() => {});
    setData(!data);
  };

  return [data, setData, changer];
}

export default useLikeToggle;
