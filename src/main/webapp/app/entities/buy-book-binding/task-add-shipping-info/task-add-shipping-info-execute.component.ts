import { Component, Vue, Inject } from 'vue-property-decorator';

import TaskAddShippingInfoService from './task-add-shipping-info.service';
import { TaskAddShippingInfoContext } from './task-add-shipping-info.model';

const validations: any = {
  taskContext: {
    buyBookBinding: {
      buyBook: {
        purpose: {},
        deliveryAddress: {},
      },
    },
  },
};

@Component({
  validations,
})
export default class TaskAddShippingInfoExecuteComponent extends Vue {
  private taskAddShippingInfoService: TaskAddShippingInfoService = new TaskAddShippingInfoService();
  private taskContext: TaskAddShippingInfoContext = {};
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.claimTaskInstance(to.params.taskInstanceId);
      }
    });
  }

  public claimTaskInstance(taskInstanceId) {
    this.taskAddShippingInfoService.claim(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public complete() {
    this.taskAddShippingInfoService.complete(this.taskContext).then(res => {
      this.$router.go(-1);
    });
  }

  public initRelationships(): void {}
}
