import { useState } from 'react';

function useManageTab() {
  const [active, setActive] = useState('');
  const onClickTabHandler = (name) => {
    setActive(name);
  };
  return [active, onClickTabHandler];
}
export default useManageTab;
