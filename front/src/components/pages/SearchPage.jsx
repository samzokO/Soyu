import TextField from '../molecules/TextField';
import { MainContainerWithoutNav } from '../../styles/Maincontainer';
import LocalHeader from '../molecules/LocalHeader';
import BackBtn from '../atoms/BackBtn';
import SearchIcon from '../../assets/icons/material_16/search.svg';

function SearchPage() {
  return (
    <MainContainerWithoutNav>
      <LocalHeader>
        <BackBtn />
        <TextField
          type="text"
          id="Search"
          placeholder="검색어를 입력해주세요"
          image={SearchIcon}
        />
      </LocalHeader>
      <div>리스트</div>
    </MainContainerWithoutNav>
  );
}

export default SearchPage;
