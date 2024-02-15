import { useEffect } from 'react';

import styled from 'styled-components';
import theme from '../../styles/theme';
import useLoadImg from '../../hooks/useLoadImg';

function NotificationBox({ notice }) {
  const noticeType = {
    DP_SELL: 'DP된 물품이 판매되었습니다.',
    RESERVE_SELL: '거래 예약된 물품이 판매되었습니다.',
    RESERVE_CANCEL: '거래 예약이 취소되었습니다.',
    CONVERT: '거래 예약이 취소되었습니다.',
    LIKE: '누군가 물품을 찜하였습니다.',
    DP_POSSIBLE: '물품이 DP 조건을 충족하였습니다.',
    CHAT_CREATE: '당신의 물품에 관심이 있는 구매자가 채팅방을 개설하였습니다.',
    RESERVE: '소유박스의 예약이 완료되었습니다.',
    BUY: '판매자가 물품을 소유박스에 보관하였습니다.',
    WITHDRAW: '물품의 회수 신청이 완료되었습니다.',
    WITHDRAW_EXPIRED:
      '물품의 회수 시간이 만료되었습니다. 소유 관계자가 물건을 회수합니다.',
    DISCOUNT: 'DP 중인 물품에 할인이 적용되었습니다.',
    WILL_DISCOUNT: 'DP 중인 물품이 12시간 뒤 할인이 적용됩니다.',
    BUYER_CANCEL: '구매자가 물품을 구매하지 않았습니다.',
    CHANGE_STATUS:
      '거래 물품을 DP로 전환 하실건지 결정해주세요. 회수 하실 거면 회수 코드를 입력해주세요.',
    SELLER_CANCEL: '판매자가 물품의 거래를 취소하였습니다.',
  };

  const updateTimeStamp = (timestamp) => {
    // 경과한 시간 계산 (1초 = 1000)
    const timeElapsed = Math.floor((new Date() - new Date(timestamp)) / 1000);

    if (timeElapsed < 60) {
      return `방금 전`;
    }
    if (timeElapsed < 60 * 60) {
      const minutes = Math.floor(timeElapsed / 60);
      return `${minutes}분 전`;
    }
    if (timeElapsed < 60 * 60 * 24) {
      const hours = Math.floor(timeElapsed / (60 * 60));
      return `${hours}시간 전`;
    }
    if (timeElapsed < 60 * 60 * 24 * 7) {
      const days = Math.floor(timeElapsed / (60 * 60 * 24));
      return `${days}일 전`;
    }

    // 일주일 이상 지난 아이템에 대해서는 YYYY-MM-DD로 표기
    const date = new Date(timestamp).toString().slice(0, 10);
    return date;
  };

  const [img, loadImg] = useLoadImg();

  useEffect(() => {
    loadImg(notice.imageResponses);
  }, []);

  return (
    <SContainer>
      <SIMG src={img} alt="알림 사진" />
      <SContent>{noticeType[notice.type]}</SContent>
      <STime>{updateTimeStamp(notice.localDateTime)}</STime>
    </SContainer>
  );
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

const SIMG = styled.img`
  width: 40px;
  height: 40px;
  /* background-color: blue; */
  border-radius: 7px;
`;

const SContent = styled.div`
  ${theme.font.Body1}
  width: 80%;
`;

const STime = styled.div`
  ${theme.font.Body2}
  white-space: nowrap;
`;
