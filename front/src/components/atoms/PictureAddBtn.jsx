import styled from 'styled-components';
import theme from '../../styles/theme';

function PictureAddBtn({ id, name, onChange }) {
  return (
    <div>
      <SLabel htmlFor="file">+</SLabel>
      <input
        name={name}
        id={id}
        type="file"
        onChange={onChange}
        accept=".jpg, .jpeg"
        style={{ display: 'none' }}
      />
    </div>
  );
}

const SLabel = styled.label`
  padding: 25px;
  background-color: ${theme.color.grayScale200};
  border-radius: 7px;
`;

export default PictureAddBtn;
