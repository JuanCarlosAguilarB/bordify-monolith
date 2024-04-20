package com.bordify.controllers.board;

import com.bordify.dtos.TopicListDTO;
import com.bordify.exceptions.ApiExceptionResponse;
import com.bordify.models.Board;
import com.bordify.models.User;
import com.bordify.services.BoardService;
import com.bordify.services.TopicService;
import com.bordify.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller for board management operations.
 */
@RestController
@Tag(name = "Board", description = "Board management operations")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;

    @Autowired
    private TopicService topicService;


    /**
     * Creates a new board for the authenticated user.
     *
     * @param boardRequest The request containing the name of the board to be created.
     * @param auth The authentication object containing information about the authenticated user.
     * @return A ResponseEntity with the created board.
     */
    @Operation(summary = "Create a new board", description = "Creates a new board for the authenticated user", tags = { "Board" })
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "User authenticated successfully", content = {
//                    @Content(mediaType = "application/json", schema = @Schema(implementation = com.bordify.controllers.CreateBoardResponse.class)) }),
//    })
    @PostMapping("/boards/")
    public ResponseEntity<Board> createBoard(
            @RequestBody BoardRequest boardRequest, Authentication auth) {

        // Extract userId of token
        String username = auth.getName();
        UUID userId = userService.getUserByUsername(username).getId();

        Board board = Board.builder()
                .name(boardRequest.getName())
                .userId(userId)
                .build();


        boardService.createBoard(
                board
        );

        Board createBoardResponse = Board.builder()
                .name(board.getName())
                .id(board.getId())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(createBoardResponse);
    }

    /**
     * Deletes a board by its id.
     *
     * @param boardId The id of the board to be deleted.
     * @return A ResponseEntity with no content.
     */
    @Operation(summary = "Delete a board", description = "Deletes a board by its id", tags = { "Board" })
    @DeleteMapping("/boards/{boardId}/")
    public ResponseEntity<?> deleteBoard(@PathVariable UUID boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.ok().build();
    }


    /**
     * Get all topics of a board.
     *
     * @param boardId The id of the board to retrieve topics for.
     * @param pageable The pagination information.
     * @param auth The authentication object containing information about the authenticated user.
     * @return A ResponseEntity with the topics of the board.
     */
    @Operation(summary = "Get all topics of a board",
            description = "Lists all topics of a board for a given board",
            tags = { "Board" })
    @GetMapping("/boards/{boardId}/topics/")
    public ResponseEntity<?> getTopicsOfBoard(
            @PathVariable UUID boardId,
            Pageable pageable,
            Authentication auth) {


        // verify that owner of the board is the one requesting the topics
        String username = auth.getName();
        User user = userService.getUserByUsername(username);

        boolean isUserOwnerOfBoard = boardService.isUserOwnerOfBoard(boardId, user.getId());

        if (!isUserOwnerOfBoard){

            ApiExceptionResponse response = ApiExceptionResponse.builder()
                    .status(HttpStatus.FORBIDDEN.value())
                    .error(HttpStatus.FORBIDDEN.getReasonPhrase())
                    .message("User is not the owner of the board")
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        Page<TopicListDTO> topics = topicService.getTopicsOfBoard(boardId, pageable);



        return ResponseEntity.ok(topics);
    }

    /**
     * List all boards.
     *
     * @param pageable The pagination information.
     * @param auth The authentication object containing information about the authenticated user.
     * @return A ResponseEntity with the list of boards.
     */
    @GetMapping("/boards/")
    @Operation(summary = "List boards", description = "List all boards", tags = { "Board" })
    public ResponseEntity<?> listBoards(Pageable pageable, Authentication auth) {

        String username = auth.getName();

        User user = userService.getUserByUsername(username);

        return ResponseEntity.ok(boardService.listBoards(pageable, user.getId()));
    }

    /**
     * Handle a partial update of a board.
     *
     * @param id The id of the board to update.
     * @param boardRequest The request body containing the updated board data.
     * @return A ResponseEntity with the updated board.
     */
    @PatchMapping("/boards/{id}/")
    public ResponseEntity<?> handler(@PathVariable UUID id, @RequestBody BoardRequest boardRequest) {

        Board board = new Board().builder()
                .id(id)
                .name(boardRequest.getName())
                .build();

        Board boardUpdated = boardService.update(board);

        return ResponseEntity.ok(boardUpdated);
    }


    /**
     * Update a board.
     *
     * @param id The id of the board to update.
     * @param boardRequest The request body containing the updated board data.
     * @return A ResponseEntity with the updated board.
     */
    @PutMapping("/boards/{id}/")
    public ResponseEntity<?> updateBoard(@PathVariable UUID id, @RequestBody BoardRequest boardRequest) {

        Board board = new Board().builder()
                .id(id)
                .name(boardRequest.getName())
                .build();

        Board boardUpdated = boardService.update(board);

        return ResponseEntity.ok(boardUpdated);
    }

}
