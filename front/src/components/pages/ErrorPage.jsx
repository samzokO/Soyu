import { useRouteError } from 'react-router-dom';
import { styled } from 'styled-components';

export default function ErrorPage() {
  const error = useRouteError();
  console.error(error);

  return (
    <SErrorDiv>
      <h1>Oops!</h1>
      <p>Sorry, an unexpected error has occurred.</p>
      <p>
        <i>{error.statusText || error.message}</i>
      </p>
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
