package org.agilekip.tutorials.buybook.process.buyBookBinding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/buy-book-binding/task-add-shipping-info")
public class TaskAddShippingInfoController {

    private final Logger log = LoggerFactory.getLogger(TaskAddShippingInfoController.class);

    private final TaskAddShippingInfoService taskAddShippingInfoService;

    public TaskAddShippingInfoController(TaskAddShippingInfoService taskAddShippingInfoService) {
        this.taskAddShippingInfoService = taskAddShippingInfoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskAddShippingInfoContextDTO> loadContext(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskAddShippingInfoContextDTO taskAddShippingInfoContext = taskAddShippingInfoService.loadContext(id);
        return ResponseEntity.ok(taskAddShippingInfoContext);
    }

    @GetMapping("/{id}/claim")
    public ResponseEntity<TaskAddShippingInfoContextDTO> claim(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskAddShippingInfoContextDTO taskAddShippingInfoContext = taskAddShippingInfoService.claim(id);
        return ResponseEntity.ok(taskAddShippingInfoContext);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@RequestBody TaskAddShippingInfoContextDTO taskAddShippingInfoContext) {
        log.debug("REST request to complete BuyBookBinding.TaskAddShippingInfo {}", taskAddShippingInfoContext.getTaskInstance().getId());
        taskAddShippingInfoService.complete(taskAddShippingInfoContext);
        return ResponseEntity.noContent().build();
    }
}
