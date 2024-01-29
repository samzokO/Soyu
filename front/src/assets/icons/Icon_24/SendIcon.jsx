import styled from 'styled-components';
import theme from '../../../styles/theme';

const Send = styled.svg`
  stroke: ${(props) =>
    props.active ? theme.color.grayScale500 : theme.color.grayScale300};
`;
const IconFill = styled.path`
  fill: ${(props) =>
    props.active ? theme.color.grayScale500 : theme.color.grayScale300};
`;

function SendIcon() {
  return (
    <Send
      xmlns="http://www.w3.org/2000/svg"
      width="25"
      height="24"
      viewBox="0 0 25 24"
      fill="none"
    >
      <path
        d="M20.7111 11.1056L4.65934 3.07967C3.86249 2.68124 2.98171 3.44514 3.26344 4.29032L5.72792 11.6838C5.79635 11.889 5.79635 12.111 5.72792 12.3162L3.26344 19.7097C2.98171 20.5549 3.86249 21.3188 4.65934 20.9203L20.7111 12.8944C21.4482 12.5259 21.4482 11.4741 20.7111 11.1056Z"
        strokeWidth="1.5"
        strokeLinejoin="round"
      />
      <IconFill d="M9.5 12.75C9.91421 12.75 10.25 12.4142 10.25 12C10.25 11.5858 9.91421 11.25 9.5 11.25V12.75ZM5.5 12.75H9.5V11.25H5.5V12.75Z" />
    </Send>
  );
}

export default SendIcon;
