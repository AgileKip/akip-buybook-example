import { Component, Vue, Inject } from 'vue-property-decorator';

import TaskAddShippingInfoService from './task-add-shipping-info.service';
import { TaskAddShippingInfoContext } from './task-add-shipping-info.model';

@Component
export default class TaskAddShippingInfoDetailsComponent extends Vue {
  private taskAddShippingInfoService: TaskAddShippingInfoService = new TaskAddShippingInfoService();
  private taskContext: TaskAddShippingInfoContext = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.retrieveContext(to.params.taskInstanceId);
      }
    });
  }

  public retrieveContext(taskInstanceId) {
    this.taskAddShippingInfoService.loadContext(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
