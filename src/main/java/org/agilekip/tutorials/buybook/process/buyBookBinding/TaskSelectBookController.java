package org.agilekip.tutorials.buybook.process.buyBookBinding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/buy-book-binding/task-select-book")
public class TaskSelectBookController {

    private final Logger log = LoggerFactory.getLogger(TaskSelectBookController.class);

    private final TaskSelectBookService taskSelectBookService;

    public TaskSelectBookController(TaskSelectBookService taskSelectBookService) {
        this.taskSelectBookService = taskSelectBookService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskSelectBookContextDTO> loadContext(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskSelectBookContextDTO taskSelectBookContext = taskSelectBookService.loadContext(id);
        return ResponseEntity.ok(taskSelectBookContext);
    }

    @GetMapping("/{id}/claim")
    public ResponseEntity<TaskSelectBookContextDTO> claim(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskSelectBookContextDTO taskSelectBookContext = taskSelectBookService.claim(id);
        return ResponseEntity.ok(taskSelectBookContext);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@RequestBody TaskSelectBookContextDTO taskSelectBookContext) {
        log.debug("REST request to complete BuyBookBinding.TaskSelectBook {}", taskSelectBookContext.getTaskInstance().getId());
        taskSelectBookService.complete(taskSelectBookContext);
        return ResponseEntity.noContent().build();
    }
}
