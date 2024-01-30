import theme from './theme';

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

export default Condition;
