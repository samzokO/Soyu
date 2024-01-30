import styled from 'styled-components';
import theme from '../../../styles/theme';

const Palette = {
  black: theme.color.grayScale500,
  gray: theme.color.grayScale300,
};

const Person = styled.svg`
    width: 24px;
    height: 24px;
    fill: "none"
    strokeWidth:1.5;
    viewBox: 0 0 24 24;
    xmlns: http://www.w3.org/2000/svg;
  `;

const Circle = styled.circle`
  fill: ${(props) => Palette[props.active]};
  stroke: ${(props) => Palette[props.active]};
`;

const Path = styled.path`
  fill: ${(props) => Palette[props.active]};
  stroke: ${(props) => Palette[props.active]};
`;

function PersonIcon({ active }) {
  return (
    <Person>
      <Circle cx="12.375" cy="7" r="4" active={active} />
      <Path
        active={active}
        d="M4.375 19.6C4.375 16.5072 6.88221 14 9.975 14H14.775C17.8678 14 20.375 16.5072 20.375 19.6C20.375 20.3732 19.7482 21 18.975 21H5.775C5.0018 21 4.375 20.3732 4.375 19.6Z"
      />
    </Person>
  );
}

export default PersonIcon;
