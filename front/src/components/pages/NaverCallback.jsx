import styled from 'styled-components';
import useNaverCallback from '../../hooks/useNaverCallback';

function NaverCallback() {
  useNaverCallback();

  return (
    <SErrorDiv>
      <h1>이동중입니다</h1>
      <p>loading...</p>
    </SErrorDiv>
  );
}

const SErrorDiv = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100vh;
  gap: 1em;
`;

export default NaverCallback;
