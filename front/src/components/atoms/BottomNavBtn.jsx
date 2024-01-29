import { Link } from 'react-router-dom';
import styled from 'styled-components';
import React from 'react';

const NavBtn = styled(Link)`
  padding: 6px 8px;
  text-align: center;
`;

/** 하단바 네비 버튼 */
function BottomNavBtn({ children }) {
  return <NavBtn>{children}</NavBtn>;
}

export default BottomNavBtn;
