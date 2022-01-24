package org.agilekip.tutorials.buybook.process.buyBookBinding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/buy-book-binding/task-select-delivery")
public class TaskSelectDeliveryController {

    private final Logger log = LoggerFactory.getLogger(TaskSelectDeliveryController.class);

    private final TaskSelectDeliveryService taskSelectDeliveryService;

    public TaskSelectDeliveryController(TaskSelectDeliveryService taskSelectDeliveryService) {
        this.taskSelectDeliveryService = taskSelectDeliveryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskSelectDeliveryContextDTO> loadContext(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskSelectDeliveryContextDTO taskSelectDeliveryContext = taskSelectDeliveryService.loadContext(id);
        return ResponseEntity.ok(taskSelectDeliveryContext);
    }

    @GetMapping("/{id}/claim")
    public ResponseEntity<TaskSelectDeliveryContextDTO> claim(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskSelectDeliveryContextDTO taskSelectDeliveryContext = taskSelectDeliveryService.claim(id);
        return ResponseEntity.ok(taskSelectDeliveryContext);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@RequestBody TaskSelectDeliveryContextDTO taskSelectDeliveryContext) {
        log.debug("REST request to complete BuyBookBinding.TaskSelectDelivery {}", taskSelectDeliveryContext.getTaskInstance().getId());
        taskSelectDeliveryService.complete(taskSelectDeliveryContext);
        return ResponseEntity.noContent().build();
    }
}
