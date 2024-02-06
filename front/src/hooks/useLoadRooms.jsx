import { useEffect, useState } from 'react';
import { getRoomList } from '../api/apis';

function useLoadRooms() {
  const [rooms, setRooms] = useState([]);

  useEffect(() => {
    setRooms(getRoomList);
  }, []);

  return rooms;
}
export default useLoadRooms;
