package com.bordify.persistence;


import com.bordify.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class TaskRepositoryShould {


    @Autowired
    private TaskRepository taskRepository;


    public void shouldFindTaskById() {



    }

}
