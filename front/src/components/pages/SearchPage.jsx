import { styled } from 'styled-components';
import { MainContainerWithoutNav } from '../../styles/Maincontainer';
import LocalHeader from '../molecules/LocalHeader';
import BackBtn from '../atoms/BackBtn';
import useSearch from '../../hooks/useSearch';
import ItemList from '../molecules/ItemList';

function SearchPage() {
  const [Search, Handler, Key, Changer] = useSearch();
  const handleSubmit = () => {
    Changer();
  };

  const handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      e.preventDefault();
      handleSubmit();
    }
  };
  const changeValue = (e) => {
    Handler(e.target.value);
  };

  return (
    <MainContainerWithoutNav>
      <LocalHeader>
        <BackBtn />
        <SForm onSubmit={handleSubmit}>
          <SBox
            type="text"
            id="Search"
            value={Key}
            onChange={changeValue}
            placeholder="검색어를 입력해주세요"
            onKeyDown={handleKeyPress}
          />
        </SForm>
      </LocalHeader>
      {Search && (
        <div>
          <ItemList title="검색결과" data={Search} />
        </div>
      )}
    </MainContainerWithoutNav>
  );
}

const SBox = styled.input`
  width: 100%;
  padding: 5px 15px;
  border-radius: 7px;
  border: 1px solid ${({ theme }) => theme.color.grayScale400};
  &::placeholder {
    ${({ theme }) => theme.font.Body2};
  }
`;

const SForm = styled.form`
  width: 100%;
`;

export default SearchPage;
