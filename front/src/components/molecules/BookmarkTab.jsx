import styled from 'styled-components';
import useManageTab from '../../hooks/useManageTab';
// children을 이용한 합성 패턴으로 리팩토링하기
function BookmarkTab() {
  const [state, handler] = useManageTab();
  return (
    <STab>
      <SBookmark onClick={() => handler('bookmark')} current={state}>
        북마크
      </SBookmark>
      <SHeart onClick={() => handler('heart')} current={state}>
        찜
      </SHeart>
    </STab>
  );
}
export default BookmarkTab;
const STab = styled.nav`
  width: 100vw;
  display: flex;
`;
const SButton = styled.button`
  width: inherit;
  padding: 10px;
`;
const SBookmark = styled(SButton)`
  border-bottom: ${(props) =>
    props.current === 'bookmark' ? '1px solid #4827E9' : ''};
`;
const SHeart = styled(SButton)`
  border-bottom: ${(props) =>
    props.current === 'heart' ? '1px solid #4827E9' : ''};
`;
