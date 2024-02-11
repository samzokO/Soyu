import { useState } from 'react';
import { loadImg } from '../api/apis';

function useLoadImg() {
  const [data, setData] = useState([]);
  const loadImage = (list) => {
    list.forEach((item, index) => {
      const { savePath, saveName } = item;
      loadImg(savePath, saveName)
        .then((res) => {
          const newPrev = [...data];
          console.log(res.data.data);
          const blob = new Blob([res.data.data], { type: 'image/jpeg' });
          const blobURL = URL.createObjectURL(blob);
          newPrev[index] = blobURL;
          setData(newPrev);
        })
        .catch((e) => {
          console.log(e);
        });
    });
  };
  return [data, loadImage];
}
export default useLoadImg;
