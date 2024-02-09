import styled from 'styled-components';
import { MainContainerWithNav } from '../../styles/Maincontainer';
import BackBtn from '../atoms/BackBtn';
import LocalHeader from '../molecules/LocalHeader';
import theme from '../../styles/theme';
import BottomNav from '../molecules/BottomNav';
import useStationList from '../../hooks/useStationList';
import StationItem from '../atoms/StationListItem';

function Map() {
  const data = useStationList();
  return (
    <div>
      <LocalHeader>
        <BackBtn />
        소유박스 지도
        <div />
      </LocalHeader>
      <MainContainerWithNav>
        <SHeadLine>소유박스는 어디에 있을까요?</SHeadLine>
        <SList>
          {data &&
            data.map((item) => (
              <StationItem key={item.stationId} data={item} />
            ))}
        </SList>
      </MainContainerWithNav>
      <BottomNav />
    </div>
  );
}

const SList = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 10px;
`;

const SHeadLine = styled.div`
  ${theme.font.Subtitle}
  padding: 10px 0px;
`;

export default Map;
