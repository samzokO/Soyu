import { styled } from 'styled-components';
import Kakao from '../../assets/social-signin/kakao.png';

function KakaoLogin() {
  return (
    <a href="/login">
      <SKakaoLogin src={Kakao} alt="Kakao Login" />
    </a>
  );
}

export default KakaoLogin;

const SKakaoLogin = styled.img`
  width: inherit;
`;
