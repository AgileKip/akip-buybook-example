import { Component, Vue, Inject } from 'vue-property-decorator';

import { IStore } from '@/shared/model/store.model';
import StoreService from './store.service';

@Component
export default class StoreDetails extends Vue {
  @Inject('storeService') private storeService: () => StoreService;
  public store: IStore = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.storeId) {
        vm.retrieveStore(to.params.storeId);
      }
    });
  }

  public retrieveStore(storeId) {
    this.storeService()
      .find(storeId)
      .then(res => {
        this.store = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
