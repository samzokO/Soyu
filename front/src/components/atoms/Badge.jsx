import styled from 'styled-components';
import theme from '../../styles/theme';
import Condition from '../../styles/BadgeCondition';

/** 뱃지 status로 사용가능, 값 고정
 * @params (number) status
 * 0: 예약중
 * 1: DP
 * 2: DP 예정
 * 3: 판매완료
 * 4: 판매대기
 * 5: No Badge
 */
function Badge({ status }) {
  return (
    <BadgeContainer type={status}>{Condition[status].content}</BadgeContainer>
  );
}

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

export default Badge;
