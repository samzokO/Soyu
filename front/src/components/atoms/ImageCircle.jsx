import styled from 'styled-components';

function ImageCircle({ img }) {
  return <SCircle img={img} />;
}

const SCircle = styled.div`
  width: 40px;
  height: 40px;
  background-color: red;
  border-radius: 20px;
  background-image: url(${(props) => props.img});
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
`;

export default ImageCircle;
