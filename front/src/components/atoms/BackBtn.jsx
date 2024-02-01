import { Link } from 'react-router-dom';
import LeftIcon from '../../assets/icons/Icon_24/LeftIcon';

function BackBtn() {
  return (
    <Link to="back">
      <LeftIcon />
    </Link>
  );
}

export default BackBtn;
