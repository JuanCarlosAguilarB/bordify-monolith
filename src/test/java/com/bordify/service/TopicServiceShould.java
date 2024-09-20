package com.bordify.service;

import com.bordify.dtos.TopicListDTO;
import com.bordify.exceptions.EntityNotFound;
import com.bordify.models.Board;
import com.bordify.models.Color;
import com.bordify.models.Topic;
import com.bordify.models.User;
import com.bordify.persistence.models.BoardFactory;
import com.bordify.persistence.models.ColorFactory;
import com.bordify.persistence.models.TopicFactory;
import com.bordify.persistence.models.UserFactory;
import com.bordify.repositories.TopicRepository;
import com.bordify.services.TopicService;
import com.bordify.shared.infrastucture.controlles.UnitTestBaseClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

public class TopicServiceShould extends UnitTestBaseClass {

    @Mock
    TopicRepository repository;

    @InjectMocks
    TopicService service;

    User user;
    Board board;
    Color color;

    @BeforeEach
    public void setup(){
        this.user = UserFactory.getRandomUser();
        this.board = BoardFactory.getRandomBoard(this.user);
        this.color = ColorFactory.getRandomColor();
    }

    @DisplayName("should create a Topic")
    @Test
    public void shoudCreateATopic() {

        Topic topic = TopicFactory.getRandomTopic(color, board);

        when(repository.save(topic)).thenReturn(topic);

        service.createTopic(topic);

        Mockito.verify(repository,Mockito.times(1)).save(topic);
    }



    @DisplayName("should delete a Topic")
    @Test
    public void shouldDeleteATopic() {

        Topic topic = TopicFactory.getRandomTopic(color, board);
        UUID idTopic = topic.getId();

        service.deleteTopic(idTopic);

        Mockito.verify(repository,Mockito.times(1)).deleteById(idTopic);
    }


    @DisplayName("should retrieve topics of a board")
    @Test
    public void shouldRetrieveTopicsOfABoard() {

        UUID boardId = board.getId();
        Pageable pageable = Pageable.unpaged();

        List<TopicListDTO> topics = new ArrayList<>();

        when(repository.findByBoardIdCustom(boardId,pageable)).thenReturn(topics);

        service.getTopicsOfBoard(boardId,pageable);

        Mockito.verify(repository,Mockito.times(1)).findByBoardIdCustom(boardId,pageable);
    }


    @DisplayName("should update a topic")
    @Test
    public void shouldUpdateATopic() {

        Topic topic = TopicFactory.getRandomTopic(color, board);

        when(repository.existsById(Mockito.any())).thenReturn(true);
        when(repository.findById(topic.getId())).thenReturn(Optional.of(topic));

        service.update(topic);

        Mockito.verify(repository,Mockito.times(1)).existsById(topic.getId());
        Mockito.verify(repository,Mockito.times(1)).findById(topic.getId());
        Mockito.verify(repository,Mockito.times(1)).save(Mockito.any());
    }

    @DisplayName("shouldn't update a non existent topic")
    @Test
    public void shouldntUpdateANonExistentTopic() {

        Topic topic = TopicFactory.getRandomTopic(color, board);

        when(repository.existsById(Mockito.any())).thenReturn(false);

        Assertions.assertThrows(EntityNotFound.class, ()->{
            service.update(topic);
        });

        Mockito.verify(repository,Mockito.times(1)).existsById(topic.getId());
    }

}
