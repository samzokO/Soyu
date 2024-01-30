import { styled } from 'styled-components';
import Naver from '../../assets/social-signin/naver.png';

function NaverLogin() {
  return (
    <a href="/login">
      <SNaverLogin src={Naver} alt="Naver Login" />
    </a>
  );
}

export default NaverLogin;

const SNaverLogin = styled.img`
  width: inherit;
`;
