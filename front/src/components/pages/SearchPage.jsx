import { useState } from 'react';
import TextField from '../molecules/TextField';
import { MainContainerWithoutNav } from '../../styles/Maincontainer';
import LocalHeader from '../molecules/LocalHeader';
import BackBtn from '../atoms/BackBtn';
import SearchIcon from '../../assets/icons/material_16/search.svg';
import useSearch from '../../hooks/useSearch';
import ItemList from '../molecules/ItemList';

function SearchPage() {
  const [searchValue, setSearchValue] = useState('');
  const Search = useSearch({ searchValue });

  const handleSearchChange = (event) => {
    setSearchValue(event.target.value);
  };

  return (
    <MainContainerWithoutNav>
      <LocalHeader>
        <BackBtn />
        <TextField
          type="text"
          id="Search"
          placeholder="검색어를 입력해주세요"
          $image={SearchIcon}
          value={searchValue}
          onChange={handleSearchChange}
        />
      </LocalHeader>
      {Search[0] && (
        <div>
          <ItemList title="검색결과" data={Search[0]} />
        </div>
      )}
    </MainContainerWithoutNav>
  );
}

export default SearchPage;
