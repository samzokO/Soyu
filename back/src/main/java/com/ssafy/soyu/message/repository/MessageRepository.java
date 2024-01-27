package com.ssafy.soyu.message.repository;
import com.ssafy.soyu.chat.Chat;
import com.ssafy.soyu.message.Message;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends JpaRepository<Message, Long> {

  List<Message> findMessagesByChat(Chat chat);
}
