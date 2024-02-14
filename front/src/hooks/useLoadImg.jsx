import { useEffect, useState } from 'react';
import { loadImg } from '../api/apis';

/** loadImage에
 * savePath, saveName을 가진 객체 또는 객체리스트를 넣으면
 * 이미지 링크를 data로 반환하는 함수
 */
function useLoadImg() {
  const [data, setData] = useState([]);
  const loadImage = (list) => {
    // 들어온 값을 무조건 리스트로 변환
    const itemList = Array.isArray(list) ? list : [list];
    itemList.forEach((item, index) => {
      const { savePath, saveName } = item;
      if (savePath && saveName) {
        loadImg(savePath, saveName)
          .then((res) => {
            const newPrev = [...data];
            const blob = new Blob([res.data], { type: 'image/jpeg' });
            const blobURL = URL.createObjectURL(blob);
            newPrev[index] = blobURL;
            setData(newPrev);
          })
          .catch(() => {
            setData();
          });
      }
    });
  };

  useEffect(() => {
    console.log();
    return () => {
      data.forEach((item) => {
        URL.revokeObjectURL(item);
      });
    };
  }, []);
  return [data, loadImage];
}
export default useLoadImg;
