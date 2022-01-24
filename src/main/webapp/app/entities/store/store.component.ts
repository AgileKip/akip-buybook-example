import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IStore } from '@/shared/model/store.model';

import StoreService from './store.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Store extends Vue {
  @Inject('storeService') private storeService: () => StoreService;
  private removeId: number = null;

  public stores: IStore[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllStores();
  }

  public clear(): void {
    this.retrieveAllStores();
  }

  public retrieveAllStores(): void {
    this.isFetching = true;

    this.storeService()
      .retrieve()
      .then(
        res => {
          this.stores = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IStore): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeStore(): void {
    this.storeService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('buyBookApp.store.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllStores();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
