import LeftIcon from '../../assets/icons/Icon_24/LeftIcon';
import useMoveLocation from '../../hooks/useMoveLocation';

function BackBtn() {
  const back = useMoveLocation(-1);
  return (
    <button type="button" onClick={back} aria-label="dd">
      <LeftIcon />
    </button>
  );
}

export default BackBtn;
