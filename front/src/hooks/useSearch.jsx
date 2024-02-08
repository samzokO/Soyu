import { useState, useEffect } from 'react';
import { getKeyword } from '../api/apis';

function useSearch() {
  // 검색결과
  const [data, setData] = useState('');
  // 검색할 키워드
  const [key, setKey] = useState('');
  // 훅 내부 키워드
  const [value, setValue] = useState('');

  // 훅 내부 키워드 업데이트
  const keychanger = (k) => {
    setValue(k);
  };

  // 저장된 훅 내부 키워드로 검색
  const changer = () => {
    if (value) setKey(value);
  };

  useEffect(() => {
    if (key) {
      getKeyword(key).then((response) => {
        const result = response.data.data;
        setData(result);
      });
    } else {
      setData([]);
    }
  }, [key]);

  return [data, keychanger, value, changer];
}
export default useSearch;
