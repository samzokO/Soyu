import { useState } from 'react';
import { makeReservation } from '../api/apis';

function useReservation() {
  const [data, setData] = useState();
  const tryReservation = (lockerId, itemId) => {
    makeReservation(lockerId, itemId).then((res) => {
      setData(res);
    });
  };
  return [data, tryReservation];
}

export default useReservation;
