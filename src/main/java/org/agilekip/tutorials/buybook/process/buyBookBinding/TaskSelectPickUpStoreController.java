package org.agilekip.tutorials.buybook.process.buyBookBinding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/buy-book-binding/task-select-pick-up-store")
public class TaskSelectPickUpStoreController {

    private final Logger log = LoggerFactory.getLogger(TaskSelectPickUpStoreController.class);

    private final TaskSelectPickUpStoreService taskSelectPickUpStoreService;

    public TaskSelectPickUpStoreController(TaskSelectPickUpStoreService taskSelectPickUpStoreService) {
        this.taskSelectPickUpStoreService = taskSelectPickUpStoreService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskSelectPickUpStoreContextDTO> loadContext(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskSelectPickUpStoreContextDTO taskSelectPickUpStoreContext = taskSelectPickUpStoreService.loadContext(id);
        return ResponseEntity.ok(taskSelectPickUpStoreContext);
    }

    @GetMapping("/{id}/claim")
    public ResponseEntity<TaskSelectPickUpStoreContextDTO> claim(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskSelectPickUpStoreContextDTO taskSelectPickUpStoreContext = taskSelectPickUpStoreService.claim(id);
        return ResponseEntity.ok(taskSelectPickUpStoreContext);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@RequestBody TaskSelectPickUpStoreContextDTO taskSelectPickUpStoreContext) {
        log.debug(
            "REST request to complete BuyBookBinding.TaskSelectPickUpStore {}",
            taskSelectPickUpStoreContext.getTaskInstance().getId()
        );
        taskSelectPickUpStoreService.complete(taskSelectPickUpStoreContext);
        return ResponseEntity.noContent().build();
    }
}
