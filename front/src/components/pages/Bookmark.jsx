import styled from 'styled-components';
import { Link } from 'react-router-dom';
import BottomNav from '../molecules/BottomNav';
import BookmarkTab from '../molecules/BookmarkTab';
import color from '../../styles/color';
import HeartIcon from '../../assets/icons/Icon_24/HeartIcon';
import font from '../../styles/font';

function Bookmark() {
  return (
    <>
      <div>HEAD</div>
      <BookmarkTab />
      <SMainWrap>
        <ul>
          <li>
            <Link to="/">
              <SArticle>
                <SImg src="" alt="물품 이미지" />
                <div>
                  <SGoodsTitle>에어팟 프로 판매합니다</SGoodsTitle>
                  <SFlexWrap>
                    <SBody>직거래살인마</SBody>
                    <SBody>2시간 전</SBody>
                  </SFlexWrap>
                  <SFlexWrap>
                    <p>100,000원</p>
                    <SFlexWrap>
                      <HeartIcon />
                      <p>3</p>
                    </SFlexWrap>
                  </SFlexWrap>
                </div>
              </SArticle>
            </Link>
          </li>
        </ul>
      </SMainWrap>
      <BottomNav />
    </>
  );
}
export default Bookmark;
const SMainWrap = styled.main`
  padding: 0 16px;
`;
const SArticle = styled.article`
  border-bottom: 1px solid ${color.grayScale200};
  padding: 14px;
  display: flex;
  gap: 25px;
`;
const SImg = styled.img`
  width: 60px;
  height: 60px;
  border-radius: 5px;
  border: 1px solid black;
`;
const SFlexWrap = styled.div`
  display: flex;
  gap: 5px;
`;
const SGoodsTitle = styled.h2`
  margin-bottom: 5px;
  ${font.Title}
`;
const SBody = styled.p`
  ${font.Body1}
  margin-right: 10px;
`;
