import { Component, Vue, Inject } from 'vue-property-decorator';

import TaskPayBookService from './task-pay-book.service';
import { TaskPayBookContext } from './task-pay-book.model';

@Component
export default class TaskPayBookDetailsComponent extends Vue {
  private taskPayBookService: TaskPayBookService = new TaskPayBookService();
  private taskContext: TaskPayBookContext = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.retrieveContext(to.params.taskInstanceId);
      }
    });
  }

  public retrieveContext(taskInstanceId) {
    this.taskPayBookService.loadContext(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
