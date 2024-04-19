package com.bordify.services;

import com.bordify.dtos.TopicListDTO;
import com.bordify.exceptions.EntityNotFound;
import com.bordify.models.Topic;
import com.bordify.repositories.TopicRepository;
import com.bordify.utils.UpdateFieldsOfClasses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TopicService {


    @Autowired
    TopicRepository topicRepository;

    @Autowired
    TaskService taskService;
    public Topic update(Topic topic) {

        ensureTopicExist(topic);

        Topic topicToUpdate = topicRepository.findById(topic.getId()).get();

        UpdateFieldsOfClasses.updateFields(topicToUpdate, topic);

        topicRepository.save(topicToUpdate);

        Topic topicResponse = Topic.builder()
                .id(topicToUpdate.getId())
                .name(topicToUpdate.getName())
                .colorId(topicToUpdate.getColorId())
                .boardId(topicToUpdate.getBoardId())
                .build();

        return topicResponse;

    }

    public void ensureTopicExist(Topic topic) {
        if (!topicRepository.existsById(topic.getId())) {
            throw new EntityNotFound("Topic not found");
        }
    }



    public Page<TopicListDTO> getTopicsOfBoard(UUID boardId, Pageable pageable) {

        List<TopicListDTO> topics = topicRepository.findByBoardIdCustom(boardId, pageable);
        System.out.println("topics: " + topics);
        int pageNumber = 0;
        int pageSize = 5;

//        Pageable pageable1 = PageRequest.of(pageNumber, pageSize, Sort.by("id").descending());
        Pageable pageableTask = PageRequest.of(pageNumber, pageSize);


        for (TopicListDTO topic : topics) {
            topic.setTasks(taskService.getTaskForBoard(topic.getId(), pageableTask));
        }

        return new PageImpl<>(topics, pageable, topics.size());
    }

    public void deleteTopic(UUID id) {
        topicRepository.deleteById(id);
    }

    public void createTopic(Topic topic) {
        topicRepository.save(topic);
    }

}
