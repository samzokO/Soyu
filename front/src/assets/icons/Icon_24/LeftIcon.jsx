import styled from 'styled-components';
import theme from '../../../styles/theme';

const Left = styled.svg`
    width: 24px;
    height: 24px;
    fill="none"
    stroke:${theme.color.grayScale500};
    strokeWidth:2;
    viewBox: 0 0 24 24;
    xmlns: http://www.w3.org/2000/svg;
  `;

const Path = styled.path`
  stroke: ${theme.color.grayScale500};
  strokewidth: 2;
  stroke-linecap: round;
  stroke-linejoin: round;
`;

function LeftIcon() {
  return (
    <Left>
      <svg fill="none">
        <g clipPath="url(#clip0_539_879)">
          <Path d="M16 4L8 12L16 20" />
        </g>
        <defs>
          <clipPath id="clip0_539_879">
            <rect width="24" height="24" fill="white" />
          </clipPath>
        </defs>
      </svg>
      ;
    </Left>
  );
}

export default LeftIcon;
