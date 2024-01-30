import { styled } from 'styled-components';
import Google from '../../assets/social-signin/google.svg';

function GoogleLogin() {
  return (
    <a href="/login">
      <SGoogleLogin src={Google} alt="Google Login" />
    </a>
  );
}

export default GoogleLogin;

const SGoogleLogin = styled.img`
  width: inherit;
  min-width: 200px;
`;
