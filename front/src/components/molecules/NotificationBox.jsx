import styled from 'styled-components';

function NotificationBox({ children }) {
  return <SContainer>{children}</SContainer>;
}

export default NotificationBox;

const SContainer = styled.li`
  list-style: none;
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 10px;
  padding: 10px 20px;
  border-radius: 7px;
  transition: all 0.3s cubic-bezier(0, 0, 0.5, 1);
  box-shadow: 2px 4px 12px rgba(0, 0, 0, 0.08);
  &:hover {
    box-shadow: 2px 4px 16px rgba(0, 0, 0, 0.16);
    cursor: pointer;
  }
`;
