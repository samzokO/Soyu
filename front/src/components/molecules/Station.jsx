import styled from 'styled-components';
import font from '../../styles/font';

function Station() {
  return (
    <article>
      <STitle>덕명동 소유박스</STitle>
      <SUl>
        <li>
          <SImg src="#" alt="#" />
        </li>
        <li>
          <SImg src="#" alt="#" />
        </li>
        <li>
          <SImg src="#" alt="#" />
        </li>
        <li>
          <SImg src="#" alt="#" />
        </li>
      </SUl>
    </article>
  );
}

export default Station;

const STitle = styled.h2`
  ${font.Title}
  margin: 30px 0 16px;
`;

const SImg = styled.img`
  width: 150px;
  height: 150px;
  border: 1px solid black;
  border-radius: 5px;
  margin-right: 10px;
`;

const SUl = styled.ul`
  display: flex;
  overflow: scroll;
`;
