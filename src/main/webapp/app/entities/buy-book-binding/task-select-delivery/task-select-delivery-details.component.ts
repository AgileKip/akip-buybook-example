import { Component, Vue, Inject } from 'vue-property-decorator';

import TaskSelectDeliveryService from './task-select-delivery.service';
import { TaskSelectDeliveryContext } from './task-select-delivery.model';

@Component
export default class TaskSelectDeliveryDetailsComponent extends Vue {
  private taskSelectDeliveryService: TaskSelectDeliveryService = new TaskSelectDeliveryService();
  private taskContext: TaskSelectDeliveryContext = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.retrieveContext(to.params.taskInstanceId);
      }
    });
  }

  public retrieveContext(taskInstanceId) {
    this.taskSelectDeliveryService.loadContext(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
