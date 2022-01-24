import { Component, Vue, Inject } from 'vue-property-decorator';

import StoreService from '@/entities/store/store.service';
import { IStore } from '@/shared/model/store.model';

import TaskSelectPickUpStoreService from './task-select-pick-up-store.service';
import { TaskSelectPickUpStoreContext } from './task-select-pick-up-store.model';

const validations: any = {
  taskContext: {
    buyBookBinding: {
      buyBook: {
        purpose: {},
      },
    },
  },
};

@Component({
  validations,
})
export default class TaskSelectPickUpStoreExecuteComponent extends Vue {
  private taskSelectPickUpStoreService: TaskSelectPickUpStoreService = new TaskSelectPickUpStoreService();
  private taskContext: TaskSelectPickUpStoreContext = {};

  @Inject('storeService') private storeService: () => StoreService;

  public stores: IStore[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.claimTaskInstance(to.params.taskInstanceId);
      }
      vm.initRelationships();
    });
  }

  public claimTaskInstance(taskInstanceId) {
    this.taskSelectPickUpStoreService.claim(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public complete() {
    this.taskSelectPickUpStoreService.complete(this.taskContext).then(res => {
      this.$router.go(-1);
    });
  }

  public initRelationships(): void {
    this.storeService()
      .retrieve()
      .then(res => {
        this.stores = res.data;
      });
  }
}
