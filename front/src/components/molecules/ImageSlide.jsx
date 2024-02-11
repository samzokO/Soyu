import styled from 'styled-components';

function ImageSlide({ data }) {
  return <Container />;
  return (
    <SContainer>
      {data && data.map((item) => <SImg key={item} src={item} alt="사진" />)}
    </SContainer>
  );
}

const SImg = styled.img`
  width: 100px;
  height: 100px;
  background-color: blue;
`;

const SContainer = styled.div`
  width: 100%;
  height: 300px;
  /* background-color: red; */
  color: black;
`;

export default ImageSlide;
