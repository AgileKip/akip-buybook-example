package org.agilekip.tutorials.buybook.process.buyBookBinding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/buy-book-binding/task-handle-order")
public class TaskHandleOrderController {

    private final Logger log = LoggerFactory.getLogger(TaskHandleOrderController.class);

    private final TaskHandleOrderService taskHandleOrderService;

    public TaskHandleOrderController(TaskHandleOrderService taskHandleOrderService) {
        this.taskHandleOrderService = taskHandleOrderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskHandleOrderContextDTO> loadContext(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskHandleOrderContextDTO taskHandleOrderContext = taskHandleOrderService.loadContext(id);
        return ResponseEntity.ok(taskHandleOrderContext);
    }

    @GetMapping("/{id}/claim")
    public ResponseEntity<TaskHandleOrderContextDTO> claim(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskHandleOrderContextDTO taskHandleOrderContext = taskHandleOrderService.claim(id);
        return ResponseEntity.ok(taskHandleOrderContext);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@RequestBody TaskHandleOrderContextDTO taskHandleOrderContext) {
        log.debug("REST request to complete BuyBookBinding.TaskHandleOrder {}", taskHandleOrderContext.getTaskInstance().getId());
        taskHandleOrderService.complete(taskHandleOrderContext);
        return ResponseEntity.noContent().build();
    }
}
