import { motion } from 'framer-motion';
import { styled } from 'styled-components';
import theme from '../../styles/theme';

function KeypadButton({ children, onClick, variants }) {
  return (
    <SButton type="button" onClick={onClick} variants={variants}>
      {children}
    </SButton>
  );
}

const SButton = styled(motion.button)`
  width: 100px;
  height: 70px;
  border-radius: 7px;
  display: flex;
  justify-content: center;
  align-items: center;
  border: 1px solid #e2e2e2;
  ${theme.font.Headline};
  ${theme.box};
`;

export default KeypadButton;
