import { Component, Vue, Inject } from 'vue-property-decorator';

import TaskPayBookService from './task-pay-book.service';
import { TaskPayBookContext } from './task-pay-book.model';

const validations: any = {
  taskContext: {
    buyBookBinding: {
      buyBook: {
        purpose: {},
        paymentDetails: {},
      },
    },
  },
};

@Component({
  validations,
})
export default class TaskPayBookExecuteComponent extends Vue {
  private taskPayBookService: TaskPayBookService = new TaskPayBookService();
  private taskContext: TaskPayBookContext = {};
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.claimTaskInstance(to.params.taskInstanceId);
      }
    });
  }

  public claimTaskInstance(taskInstanceId) {
    this.taskPayBookService.claim(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public complete() {
    this.taskPayBookService.complete(this.taskContext).then(res => {
      this.$router.go(-1);
    });
  }

  public initRelationships(): void {}
}
