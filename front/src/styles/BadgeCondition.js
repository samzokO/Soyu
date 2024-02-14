import theme from './theme';

const Condition = [
  {
    bgColor: theme.color.action,
    textColor: theme.color.white,
    content: '예약중',
  },
  {
    bgColor: theme.color.success,
    textColor: theme.color.grayScale500,
    content: 'DP',
  },
  {
    bgColor: theme.color.warning,
    textColor: theme.color.grayScale500,
    content: 'DP예정',
  },
  {
    bgColor: theme.color.cancel,
    textColor: theme.color.white,
    content: '판매완료',
  },
  {
    bgColor: theme.color.delete,
    textColor: theme.color.white,
    content: '회수대기',
  },
  {},
];

export default Condition;
