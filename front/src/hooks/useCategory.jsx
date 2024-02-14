import { useState, useEffect } from 'react';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { getCategory } from '../api/apis';

function useCategory() {
  const [data, setData] = useState('');
  const [result, setResult] = useState('');
  const categoryChanger = (value) => {
    setData(value);
  };
  useEffect(() => {
    if (data)
      getCategory(data)
        .then((response) => {
          if (response.response?.status === 404) {
            throw new Error(response?.response?.data?.message);
          }
          const res = response?.data?.data;
          setResult(res);
        })
        .catch((error) => {
          setResult([]);
          toast.error(`${error}`, {
            position: 'top-center',
          });
        });
  }, [data]);
  return [result, categoryChanger];
}
export default useCategory;
