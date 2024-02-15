import styled from 'styled-components';
import defaultImage from '../../assets/icons/material_24/default_account.svg';

function ImageCircle({ img }) {
  return <SCircle img={img} />;
}

const SCircle = styled.div`
  width: 40px;
  height: 40px;
  background-color: red;
  border-radius: 20px;
  background-image: url('https://source.unsplash.com/random/250x250/?character');
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
`;

export default ImageCircle;
