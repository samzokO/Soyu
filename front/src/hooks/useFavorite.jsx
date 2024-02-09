import { useState } from 'react';
import { favoriteOnOff } from '../api/apis';

function useFavorite(stationId) {
  const [data, setData] = useState();
  const changer = (bool) => {
    setData(bool);
  };
  const changeState = () => {
    favoriteOnOff(stationId).then(() => {
      changer(!data);
    });
  };

  return [changeState, data, changer];
}

export default useFavorite;
