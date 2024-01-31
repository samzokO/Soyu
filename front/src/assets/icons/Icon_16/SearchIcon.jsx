import styled from 'styled-components';

const Search = styled.svg`
    width: 16px;
    height: 16px;
    fill: none;
    strokeWidth:2;
    viewBox: 0 0 16 16;
    xmlns: http://www.w3.org/2000/svg;
  `;

function SearchIcon() {
  return (
    <Search>
      <circle
        cx="6.66667"
        cy="6.66667"
        r="4.66667"
        stroke="#191919"
        strokeWidth="2"
      />
      <path
        d="M14.0002 14L11.3335 11.3333"
        stroke="#191919"
        strokeWidth="2"
        strokeLinecap="round"
      />
    </Search>
  );
}

export default SearchIcon;
