import styled from 'styled-components';
import theme from '../../styles/theme';
import Condition from '../../styles/BadgeCondition';

const BadgeContainer = styled.span`
  background-color: ${(props) => Condition[props.status].bgColor};
  color: ${(props) => Condition[props.status].textColor};
  ${theme.font.Body2};
  padding: 2px 10px;
  display: inline-flex;
  justify-content: center;
  align-items: center;
  border-radius: 20px;
`;
/** 숫자뱃지 status로 사용가능, 값 고정, 파라미터 필수
 * @params (number) status
 * 1: 파란색
 * 2: 초록색
 * 3: 노란색
 * 4: 회색
 * 5: 빨간색
 * @params (number) number - 숫자 입력, 99개 넘어가면 99+로 표시
 */
function BadgeNotification({ status, number }) {
  return (
    <BadgeContainer status={status}>
      {number > 99 ? '99+' : number}
    </BadgeContainer>
  );
}

export default BadgeNotification;
