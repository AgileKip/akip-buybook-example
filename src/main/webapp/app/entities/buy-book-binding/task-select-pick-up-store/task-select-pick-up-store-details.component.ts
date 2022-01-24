import { Component, Vue, Inject } from 'vue-property-decorator';

import TaskSelectPickUpStoreService from './task-select-pick-up-store.service';
import { TaskSelectPickUpStoreContext } from './task-select-pick-up-store.model';

@Component
export default class TaskSelectPickUpStoreDetailsComponent extends Vue {
  private taskSelectPickUpStoreService: TaskSelectPickUpStoreService = new TaskSelectPickUpStoreService();
  private taskContext: TaskSelectPickUpStoreContext = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.retrieveContext(to.params.taskInstanceId);
      }
    });
  }

  public retrieveContext(taskInstanceId) {
    this.taskSelectPickUpStoreService.loadContext(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
