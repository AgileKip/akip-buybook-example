package org.agilekip.tutorials.buybook.process.buyBookBinding;

import org.agilekip.tutorials.buybook.repository.BuyBookBindingRepository;
import org.agilekip.tutorials.buybook.service.BuyBookService;
import org.agilekip.tutorials.buybook.service.dto.BuyBookBindingDTO;
import org.agilekip.tutorials.buybook.service.dto.BuyBookDTO;
import org.agilekip.tutorials.buybook.service.mapper.BuyBookBindingMapper;
import org.akip.repository.TaskInstanceRepository;
import org.akip.service.TaskInstanceService;
import org.akip.service.dto.TaskInstanceDTO;
import org.akip.service.mapper.TaskInstanceMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskSelectBookService {

    private final TaskInstanceService taskInstanceService;

    private final BuyBookService buyBookService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final BuyBookBindingRepository buyBookBindingRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final TaskSelectBookMapper taskSelectBookMapper;

    private final BuyBookBindingMapper buyBookBindingMapper;

    public TaskSelectBookService(
        TaskInstanceService taskInstanceService,
        BuyBookService buyBookService,
        TaskInstanceRepository taskInstanceRepository,
        BuyBookBindingRepository buyBookBindingRepository,
        TaskInstanceMapper taskInstanceMapper,
        TaskSelectBookMapper taskSelectBookMapper,
        BuyBookBindingMapper buyBookBindingMapper
    ) {
        this.taskInstanceService = taskInstanceService;
        this.buyBookService = buyBookService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.buyBookBindingRepository = buyBookBindingRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.taskSelectBookMapper = taskSelectBookMapper;
        this.buyBookBindingMapper = buyBookBindingMapper;
    }

    public TaskSelectBookContextDTO loadContext(Long taskInstanceId) {
        TaskInstanceDTO taskInstanceDTO = taskInstanceRepository
            .findById(taskInstanceId)
            .map(taskInstanceMapper::toDTOLoadTaskContext)
            .orElseThrow();

        BuyBookBindingDTO buyBookBinding = buyBookBindingRepository
            .findByProcessInstanceId(taskInstanceDTO.getProcessInstance().getId())
            .map(taskSelectBookMapper::toBuyBookBindingDTO)
            .orElseThrow();

        TaskSelectBookContextDTO taskSelectBookContext = new TaskSelectBookContextDTO();
        taskSelectBookContext.setTaskInstance(taskInstanceDTO);
        taskSelectBookContext.setBuyBookBinding(buyBookBinding);

        return taskSelectBookContext;
    }

    public TaskSelectBookContextDTO claim(Long taskInstanceId) {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(TaskSelectBookContextDTO taskSelectBookContext) {
        BuyBookDTO buyBookDTO = buyBookService.findOne(taskSelectBookContext.getBuyBookBinding().getBuyBook().getId()).orElseThrow();
        buyBookDTO.setPurpose(taskSelectBookContext.getBuyBookBinding().getBuyBook().getPurpose());
        buyBookDTO.setBook(taskSelectBookContext.getBuyBookBinding().getBuyBook().getBook());
        buyBookService.save(buyBookDTO);
    }

    public void complete(TaskSelectBookContextDTO taskSelectBookContext) {
        save(taskSelectBookContext);
        BuyBookBindingDTO buyBookBinding = buyBookBindingRepository
            .findByProcessInstanceId(taskSelectBookContext.getBuyBookBinding().getProcessInstance().getId())
            .map(buyBookBindingMapper::toDto)
            .orElseThrow();
        taskInstanceService.complete(taskSelectBookContext.getTaskInstance(), buyBookBinding);
    }
}
