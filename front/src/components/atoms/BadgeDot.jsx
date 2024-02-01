import styled from 'styled-components';
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
function BadgeDot({ status }) {
  return <SBadgeContainer type={status} />;
}

const SBadgeContainer = styled.div`
  background-color: ${(props) => Condition[props.type].bgColor};
  width: 8px;
  height: 8px;
  border-radius: 4px;
`;

export default BadgeDot;
