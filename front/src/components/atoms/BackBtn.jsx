import { Link } from 'react-router-dom';

function BackBtn({ children }) {
  return <Link to="back">{children}</Link>;
}

export default BackBtn;
