import styled from 'styled-components';
import theme from '../../../styles/theme';

const Heart = styled.svg`
    width: 24px;
    height: 24px;
    margin: 0 auto;
    fill: ${(props) => (props.active ? theme.color.grayScale500 : 'red')};
    viewBox: 0 0 24 24;
    xmlns: http://www.w3.org/2000/svg;
  `;

const Path = styled.path`
  fill: ${(props) =>
    props.active ? theme.color.grayScale500 : theme.color.grayScale300};
`;

function HeartIcon({ active }) {
  console.log(active ? theme.color.grayScale500 : theme.color.grayScale300);
  console.log(theme.color.grayScale500);
  return (
    <Heart fill="none">
      <g clipPath="url(#clip0_539_145)">
        <Path d="M12.5 21.35L11.05 20.03C5.9 15.36 2.5 12.28 2.5 8.5C2.5 5.42 4.92 3 8 3C9.74 3 11.41 3.81 12.5 5.09C13.59 3.81 15.26 3 17 3C20.08 3 22.5 5.42 22.5 8.5C22.5 12.28 19.1 15.36 13.95 20.04L12.5 21.35Z" />
      </g>
      <defs>
        <clipPath id="clip0_539_139">
          <rect
            width="24"
            height="24"
            fill="white"
            transform="translate(0.5)"
          />
        </clipPath>
      </defs>
    </Heart>
  );
}

export default HeartIcon;
