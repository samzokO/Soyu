import { useState } from 'react';
import { loadImg } from '../api/apis';

function useLoadImg() {
  const [data, setData] = useState([]);
  const loadImage = (list) => {
    const itemList = Array.isArray(list) ? list : [list];

    itemList.forEach((item, index) => {
      const { savePath, saveName } = item;
      loadImg(savePath, saveName)
        .then((res) => {
          console.log(res);
          const newPrev = [...data];
          const blob = new Blob([res.data], { type: 'image/jpeg' });
          const blobURL = URL.createObjectURL(blob);
          newPrev[index] = blobURL;
          setData(newPrev);
        })
        .catch(() => {});
    });
  };
  return [data, loadImage];
}
export default useLoadImg;
