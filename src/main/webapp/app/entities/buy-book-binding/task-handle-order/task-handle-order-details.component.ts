import { Component, Vue, Inject } from 'vue-property-decorator';

import TaskHandleOrderService from './task-handle-order.service';
import { TaskHandleOrderContext } from './task-handle-order.model';

@Component
export default class TaskHandleOrderDetailsComponent extends Vue {
  private taskHandleOrderService: TaskHandleOrderService = new TaskHandleOrderService();
  private taskContext: TaskHandleOrderContext = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.retrieveContext(to.params.taskInstanceId);
      }
    });
  }

  public retrieveContext(taskInstanceId) {
    this.taskHandleOrderService.loadContext(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
