import styled from 'styled-components';

function ImageCircle({ url }) {
  return <SCircle $url={url} />;
}

const SCircle = styled.div`
  width: 40px;
  height: 40px;
  background-color: red;
  border-radius: 20px;
  background-image: url('/HJ.jpg');
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
`;

export default ImageCircle;
