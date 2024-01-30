import styled from 'styled-components';
import theme from '../../../styles/theme';

const Palette = {
  black: theme.color.grayScale500,
  gray: theme.color.grayScale300,
};

const Location = styled.svg`
    width: 25px;
    height: 24px;
    fill: ${(props) => Palette[props.active]}
    viewBox: 0 0 24 24;
    xmlns: http://www.w3.org/2000/svg;
  `;

const Path = styled.path`
  fill: ${(props) => Palette[props.active]};
`;

function LocationIcon({ active }) {
  return (
    <Location active={active}>
      <g clipPath="url(#clip0_539_145)">
        <Path
          d="M12.5 2C8.63 2 5.5 5.13 5.5 9C5.5 12.7317 9.03669 17.7265 11.0823 20.3034C11.8189 21.2314 13.1811 21.2314 13.9177 20.3034C15.9633 17.7265 19.5 12.7317 19.5 9C19.5 5.13 16.37 2 12.5 2ZM12.5 11.5C11.12 11.5 10 10.38 10 9C10 7.62 11.12 6.5 12.5 6.5C13.88 6.5 15 7.62 15 9C15 10.38 13.88 11.5 12.5 11.5Z"
          active={active}
        />
      </g>
      <defs>
        <clipPath id="clip0_539_145">
          <rect
            width="24"
            height="24"
            fill="white"
            transform="translate(0.5)"
          />
        </clipPath>
      </defs>
    </Location>
  );
}

export default LocationIcon;
