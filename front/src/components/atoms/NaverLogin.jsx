import { styled } from 'styled-components';
import Naver from '../../assets/social-signin/naver.png';

function NaverLogin() {
  const NAVER_CLIENT_ID = process.env.REACT_APP_NAVER_CLIENT_ID;
  const REDIRECT_URI = process.env.REACT_APP_NAVER_REDIRECT_URI;
  const STATE = process.env.REACT_APP_NAVER_STATE;
  const NAVER_AUTH_URL = `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${NAVER_CLIENT_ID}&state=${STATE}&redirect_uri=${REDIRECT_URI}`;

  const Login = () => {
    window.location.href = NAVER_AUTH_URL;
  };

  return (
    <button type="button" onClick={Login}>
      <SNaverLogin src={Naver} alt="Naver Login" />
    </button>
  );
}

export default NaverLogin;

const SNaverLogin = styled.img`
  width: inherit;
`;
