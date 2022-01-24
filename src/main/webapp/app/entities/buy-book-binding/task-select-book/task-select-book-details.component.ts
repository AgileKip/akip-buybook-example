import { Component, Vue, Inject } from 'vue-property-decorator';

import TaskSelectBookService from './task-select-book.service';
import { TaskSelectBookContext } from './task-select-book.model';

@Component
export default class TaskSelectBookDetailsComponent extends Vue {
  private taskSelectBookService: TaskSelectBookService = new TaskSelectBookService();
  private taskContext: TaskSelectBookContext = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.retrieveContext(to.params.taskInstanceId);
      }
    });
  }

  public retrieveContext(taskInstanceId) {
    this.taskSelectBookService.loadContext(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
