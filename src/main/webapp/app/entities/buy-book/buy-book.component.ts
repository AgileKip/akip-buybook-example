import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IBuyBook } from '@/shared/model/buy-book.model';

import BuyBookService from './buy-book.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class BuyBook extends Vue {
  @Inject('buyBookService') private buyBookService: () => BuyBookService;
  private removeId: number = null;

  public buyBooks: IBuyBook[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllBuyBooks();
  }

  public clear(): void {
    this.retrieveAllBuyBooks();
  }

  public retrieveAllBuyBooks(): void {
    this.isFetching = true;

    this.buyBookService()
      .retrieve()
      .then(
        res => {
          this.buyBooks = res.data;
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

  public prepareRemove(instance: IBuyBook): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeBuyBook(): void {
    this.buyBookService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('buyBookApp.buyBook.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllBuyBooks();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
