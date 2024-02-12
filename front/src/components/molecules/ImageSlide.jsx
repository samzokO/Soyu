import styled from 'styled-components';

function ImageSlide({ data }) {
  return (
    <SContainer>
      {data && data.map((item) => <SImg key={item} src={item} alt="사진" />)}
    </SContainer>
  );
}

const SImg = styled.div`
  width: 100%;
  height: 100%;
  background-image: url(${(props) => props.src});
  background-repeat: no-repeat;
  background-position: 50%;
  background-size: contain;
`;

const SContainer = styled.div`
  width: 100%;
  height: 300px;
  color: black;
  display: flex;
`;

export default ImageSlide;
