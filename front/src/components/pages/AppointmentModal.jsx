import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { motion } from 'framer-motion';
import { styled } from 'styled-components';
import { getStation, getStationDetail, makeAppointment } from '../../api/apis';
import theme from '../../styles/theme';
import logo from '../../assets/icons/Logo.svg';
import LogoMotion from '../../assets/icons/LogoMotion';
import {
  transitionList,
  transitionListitem,
} from '../../styles/ListTransition';

function AppointmentModal() {
  const [isSelectStation, setIsSelectStation] = useState(false);
  const [stations, setStations] = useState([]);
  const [selectStation, setSelectStation] = useState(null);

  useEffect(() => {
    (async () => {
      const { data } = await getStation();
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
        setLockers(data.data.lockers);
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
        setTimeout(() => navigate(-1), 1200);
      }
    })();
  }, [selectedLocker]);

  if (!isSelectStation)
    return (
      <SCanvas>
        <SModalContaier
          variants={transitionList}
          initial="hidden"
          animate="visible"
        >
          <SSubTitle>보관하고 싶은 스테이션을 선택해 주세요.</SSubTitle>
          <SList>
            {stations &&
              stations?.map((station, index) => (
                <SListItem key={index} variants={transitionListitem}>
                  <SA
                    href="/"
                    onClick={(e) => {
                      e.preventDefault();
                      setSelectStation(station.stationId);
                      setIsSelectStation(true);
                    }}
                  >
                    <SFlexWrap>
                      <SImg src={logo} alt="스테이션" />
                      <div>
                        <h2>{station.name}</h2>
                        <SBody2>{station.address}</SBody2>
                      </div>
                    </SFlexWrap>
                  </SA>
                </SListItem>
              ))}
          </SList>
        </SModalContaier>
      </SCanvas>
    );

  if (!isSelectLocker)
    return (
      <SCanvas>
        <SModalContaier
          variants={transitionList}
          initial="hidden"
          animate="visible"
        >
          <SSubTitle>보관함을 선택해주세요.</SSubTitle>
          <SGridList
            variants={transitionList}
            initial="hidden"
            animate="visible"
          >
            {lockers?.map((locker, index) => (
              <SLockerListItem
                key={index}
                variants={transitionListitem}
                status={locker.status}
              >
                <SA
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
                  <SBody2>{locker.status}</SBody2>
                </SA>
              </SLockerListItem>
            ))}
          </SGridList>
        </SModalContaier>
      </SCanvas>
    );

  return (
    <SCanvas>
      <LogoMotion />
      <SComplete>예약완료</SComplete>
    </SCanvas>
  );
}

export default AppointmentModal;

const SComplete = styled(motion.h1)`
  ${theme.font.Splash}
`;

const SA = styled.a`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
`;

const SImg = styled.img`
  width: 30px;
  height: 30px;
`;

const SListItem = styled(motion.li)`
  padding: 1.5em;
  min-width: 200px;
  min-height: 110px;
  ${theme.box};
`;

const SLockerListItem = styled(SListItem)`
  background-color: ${(props) =>
    props.status === 'AVAILABLE' ? theme.color.action : theme.color.error};
`;

const SGridList = styled(motion.ul)`
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 10px;
`;

const SList = styled(motion.ul)`
  display: flex;
  flex-direction: column;
  gap: 5px;
  margin: 3em 2em;
`;

const SBody2 = styled.h2`
  ${theme.font.Body2};
`;

const SSubTitle = styled.h1`
  ${theme.font.Subtitle}
  position: absolute;
  top: 2em;
  white-space: nowrap;
`;

const SModalContaier = styled(motion.article)`
  width: 80%;
  min-width: 375px;
  max-width: 800px;
  border-radius: 7px;
  display: flex;
  padding: 6em 1em;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  position: relative;
  cursor: none;
  ${theme.box}
`;

const SFlexWrap = styled.div`
  display: flex;
  gap: 1em;
  justify-content: flex-start;
  align-items: center;
`;

const SCanvas = styled.div`
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
`;
