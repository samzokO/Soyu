import styled from 'styled-components';
import theme from '../../../styles/theme';

const Person = styled.svg`
    width: 24px;
    height: 24px;
    fill: ${(props) => (props.active ? theme.color.grayScale500 : theme.color.grayScale300)};
    stroke:${(props) => (props.active ? theme.color.grayScale500 : theme.color.grayScale300)};
    strokeWidth:1.5;
    viewBox: 0 0 24 24;
    xmlns: http://www.w3.org/2000/svg;
  `;

function PersonIcon({ active }) {
  return (
    <Person active={active}>
      <circle cx="12.375" cy="7" r="4" />
      <path d="M4.375 19.6C4.375 16.5072 6.88221 14 9.975 14H14.775C17.8678 14 20.375 16.5072 20.375 19.6C20.375 20.3732 19.7482 21 18.975 21H5.775C5.0018 21 4.375 20.3732 4.375 19.6Z" />
    </Person>
  );
}

export default PersonIcon;
