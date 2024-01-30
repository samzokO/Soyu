import styled from 'styled-components';
import theme from '../../styles/theme';
import Condition from '../../styles/BadgeCondition';

const BadgeContainer = styled.div`
  background-color: ${(props) => Condition[props.status].bgColor};
  color: ${(props) => Condition[props.status].textColor};
  ${theme.font.Badge};
  width: 47px;
  height: 26px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 7px;
`;
/** 뱃지 status로 사용가능, 값 고정
 * @params (number) status
 * 1: 예약중
 * 2: DP
 * 3: DP 예정
 * 4: 판매완료
 * 5: 판매대기
 */
function Badge({ status }) {
  return (
    <BadgeContainer status={status}>{Condition[status].content}</BadgeContainer>
  );
}

export default Badge;
