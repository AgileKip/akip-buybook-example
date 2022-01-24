import { Component, Vue, Inject } from 'vue-property-decorator';

import TaskSelectDeliveryService from './task-select-delivery.service';
import { TaskSelectDeliveryContext } from './task-select-delivery.model';

const validations: any = {
  taskContext: {
    buyBookBinding: {
      buyBook: {
        purpose: {},
        deliveryHome: {},
      },
    },
  },
};

@Component({
  validations,
})
export default class TaskSelectDeliveryExecuteComponent extends Vue {
  private taskSelectDeliveryService: TaskSelectDeliveryService = new TaskSelectDeliveryService();
  private taskContext: TaskSelectDeliveryContext = {};
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.claimTaskInstance(to.params.taskInstanceId);
      }
    });
  }

  public claimTaskInstance(taskInstanceId) {
    this.taskSelectDeliveryService.claim(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public complete() {
    this.taskSelectDeliveryService.complete(this.taskContext).then(res => {
      this.$router.go(-1);
    });
  }

  public initRelationships(): void {}
}
