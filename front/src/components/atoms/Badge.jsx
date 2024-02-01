import styled from 'styled-components';
import theme from '../../styles/theme';
import Condition from '../../styles/BadgeCondition';

const BadgeContainer = styled.div`
  background-color: ${(props) => Condition[props.type].bgColor};
  color: ${(props) => Condition[props.type].textColor};
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
 * 0: 예약중
 * 1: DP
 * 2: DP 예정
 * 3: 판매완료
 * 4: 판매대기
 * 5: No Badge
 */
function Badge({ type }) {
  return <BadgeContainer type={type}>{Condition[type].content}</BadgeContainer>;
}

export default Badge;
