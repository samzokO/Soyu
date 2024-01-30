import styled from 'styled-components';
import theme from '../../styles/theme';

const Condition = {
  1: {
    bgColor: theme.color.action,
    textColor: theme.color.white,
    content: '예약중',
  },
  2: {
    bgColor: theme.color.success,
    textColor: theme.color.grayScale500,
    content: 'DP',
  },
  3: {
    bgColor: theme.color.warning,
    textColor: theme.color.grayScale500,
    content: 'DP예정',
  },
  4: {
    bgColor: theme.color.cancel,
    textColor: theme.color.white,
    content: '판매완료',
  },
  5: {
    bgColor: theme.color.delete,
    textColor: theme.color.white,
    content: '판매대기',
  },
};

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
