import BackIcon from '../../assets/icons/material_24/back.svg';
import useMoveLocation from '../../hooks/useMoveLocation';

function BackBtn() {
  const back = useMoveLocation(-1);
  return (
    <button type="button" onClick={back} aria-label="back">
      <img src={BackIcon} alt="" />
    </button>
  );
}

export default BackBtn;
