import styled from 'styled-components';
import { motion } from 'framer-motion';
import { MainContainerWithNav } from '../../styles/Maincontainer';
import BackBtn from '../atoms/BackBtn';
import LocalHeader from '../molecules/LocalHeader';
import theme from '../../styles/theme';
import BottomNav from '../molecules/BottomNav';
import useStationList from '../../hooks/useStationList';
import StationItem from '../atoms/StationListItem';

function Map() {
  const data = useStationList();
  const list = {
    hidden: {
      opacity: 0,
    },
    visible: {
      opacity: 1,
      transition: {
        when: 'beforeChildren',
        staggerChildren: 0.1,
      },
    },
  };

  const listitem = {
    hidden: { opacity: 0, y: 50 },
    visible: { opacity: 1, y: 0 },
  };
  return (
    <div>
      <LocalHeader>
        <BackBtn />
        소유박스 지도
        <div />
      </LocalHeader>
      <MainContainerWithNav>
        <SHeadLine>소유박스는 어디에 있을까요?</SHeadLine>
        <SList variants={list} initial="hidden" animate="visible">
          {data &&
            data.map((item) => (
              <StationItem
                variants={listitem}
                key={item.stationId}
                data={item}
              />
            ))}
        </SList>
      </MainContainerWithNav>
      <BottomNav />
    </div>
  );
}

const SList = styled(motion.ul)`
  display: flex;
  flex-direction: column;
  gap: 10px;
`;

const SHeadLine = styled.div`
  ${theme.font.Subtitle}
  padding: 10px 0px;
`;

export default Map;
