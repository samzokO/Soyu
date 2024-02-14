import React from 'react';
import { createPortal } from 'react-dom';

/** 모달을 리액트 포탈을 통해 다른세계로 보내드려요 */
function ModalContainer({ children }) {
  return createPortal(<div>{children}</div>, document.getElementById('modal'));
}

export default ModalContainer;
