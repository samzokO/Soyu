import { useEffect, useState } from 'react';
import { getRooms } from '../api/apis';

function useLoadRooms() {
  const [rooms, setRooms] = useState([]);

  useEffect(() => {
    (async () => {
      const { data } = await getRooms();
      setRooms(data.data);
    })();
  }, []);

  return rooms;
}
export default useLoadRooms;
