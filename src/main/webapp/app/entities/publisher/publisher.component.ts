import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPublisher } from '@/shared/model/publisher.model';

import PublisherService from './publisher.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Publisher extends Vue {
  @Inject('publisherService') private publisherService: () => PublisherService;
  private removeId: number = null;

  public publishers: IPublisher[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPublishers();
  }

  public clear(): void {
    this.retrieveAllPublishers();
  }

  public retrieveAllPublishers(): void {
    this.isFetching = true;

    this.publisherService()
      .retrieve()
      .then(
        res => {
          this.publishers = res.data;
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

  public prepareRemove(instance: IPublisher): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePublisher(): void {
    this.publisherService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('buyBookApp.publisher.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPublishers();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
