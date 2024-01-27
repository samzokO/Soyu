package com.ssafy.soyu.message.repository;
import com.ssafy.soyu.chat.entity.Chat;
import com.ssafy.soyu.message.entity.Message;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

  List<Message> findMessagesByChat(Chat chat);
}
