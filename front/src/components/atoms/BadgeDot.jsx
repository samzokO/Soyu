import styled from 'styled-components';
import theme from '../../styles/theme';

const Condition = {
  1: {
    bgColor: theme.color.action,
  },
  2: {
    bgColor: theme.color.success,
  },
  3: {
    bgColor: theme.color.warning,
  },
  4: {
    bgColor: theme.color.delete,
  },
};

const BadgeContainer = styled.div`
  background-color: ${(props) => Condition[props.status].bgColor};
  width: 8px;
  height: 8px;
  border-radius: 4px;
`;
/** 숫자뱃지 status로 사용가능, 값 고정, 파라미터 필수
 * @params (number) status
 * 1: 파란색
 * 2: 초록색
 * 3: 노란색
 * 4: 빨간색
 * @params (number) number - 숫자 입력, 99개 넘어가면 99+로 표시
 */
function BadgeDot({ status }) {
  return <BadgeContainer status={status} />;
}

export default BadgeDot;
