import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { getStation, getStationDetail, makeAppointment } from '../../api/apis';

function AppointmentModal() {
  const [isSelectStation, setIsSelectStation] = useState(false);
  const [stations, setStations] = useState([]);
  const [selectStation, setSelectStation] = useState(null);

  useEffect(() => {
    (async () => {
      const { data } = await getStation();
      console.log(data);
      setStations(data.data);
    })();
  }, []);

  const [isSelectLocker, setIsSelectLocker] = useState(false);
  const [lockers, setLockers] = useState([]);
  const [selectedLocker, setSelectedLocker] = useState(null);

  const navigate = useNavigate();
  const { chatId } = useParams();

  useEffect(() => {
    (async () => {
      if (selectStation != null) {
        const { data } = await getStationDetail(selectStation);
        setLockers(data.data[0].lockers);
      }
    })();
  }, [selectStation]);

  useEffect(() => {
    (async () => {
      if (selectedLocker !== null) {
        await makeAppointment({
          chatId,
          lockerId: selectedLocker,
        });
        setTimeout(() => navigate(-1), 1000);
      }
    })();
  }, [selectedLocker]);

  if (!isSelectStation)
    return (
      <>
        <h1>보관하고 싶은 스테이션을 선택해 주세요.</h1>
        <ul>
          {stations?.map((station, index) => (
            <li key={index}>
              <a
                href="/"
                onClick={(e) => {
                  e.preventDefault();
                  setSelectStation(station.stationId);
                  setIsSelectStation(true);
                }}
              >
                <h2>{station.name}</h2>
                <p>{station.address}</p>
              </a>
            </li>
          ))}
        </ul>
      </>
    );

  if (!isSelectLocker)
    return (
      <>
        <h1>보관함을 선택해주세요.</h1>
        <ul>
          {lockers?.map((locker, index) => (
            <li key={index}>
              <a
                href="/"
                onClick={(e) => {
                  e.preventDefault();
                  if (locker.status === 'AVAILABLE') {
                    setSelectedLocker(locker.lockerId);
                    setIsSelectLocker(true);
                  }
                }}
              >
                <h2>{locker.lockerLocation}번 보관함</h2>
                <p>{locker.status}</p>
              </a>
            </li>
          ))}
        </ul>
      </>
    );

  return <p>선택이 완료되었습니다.</p>;
}

export default AppointmentModal;
