package org.agilekip.tutorials.buybook.process.buyBookBinding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/buy-book-binding/task-pay-book")
public class TaskPayBookController {

    private final Logger log = LoggerFactory.getLogger(TaskPayBookController.class);

    private final TaskPayBookService taskPayBookService;

    public TaskPayBookController(TaskPayBookService taskPayBookService) {
        this.taskPayBookService = taskPayBookService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskPayBookContextDTO> loadContext(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskPayBookContextDTO taskPayBookContext = taskPayBookService.loadContext(id);
        return ResponseEntity.ok(taskPayBookContext);
    }

    @GetMapping("/{id}/claim")
    public ResponseEntity<TaskPayBookContextDTO> claim(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskPayBookContextDTO taskPayBookContext = taskPayBookService.claim(id);
        return ResponseEntity.ok(taskPayBookContext);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@RequestBody TaskPayBookContextDTO taskPayBookContext) {
        log.debug("REST request to complete BuyBookBinding.TaskPayBook {}", taskPayBookContext.getTaskInstance().getId());
        taskPayBookService.complete(taskPayBookContext);
        return ResponseEntity.noContent().build();
    }
}
