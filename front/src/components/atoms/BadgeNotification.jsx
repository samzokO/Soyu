import styled from 'styled-components';
import theme from '../../styles/theme';
import Condition from '../../styles/BadgeCondition';

/** 숫자뱃지 status로 사용가능, 값 고정, 파라미터 필수
 * @params (number) type
 * 0: 파란색
 * 1: 초록색
 * 2: 노란색
 * 3: 회색
 * 4: 빨간색
 * 5: No Badge
 * @params (number) number - 숫자 입력, 99개 넘어가면 99+로 표시
 */
function BadgeNotification({ status, number }) {
  return (
    <SBadgeContainer type={status}>
      {number > 99 ? '99+' : number}
    </SBadgeContainer>
  );
}

const SBadgeContainer = styled.span`
  background-color: ${(props) => Condition[props.type].bgColor};
  color: ${(props) => Condition[props.type].textColor};
  ${theme.font.Body2};
  padding: 2px 10px;
  display: inline-flex;
  justify-content: center;
  align-items: center;
  border-radius: 20px;
`;

export default BadgeNotification;
