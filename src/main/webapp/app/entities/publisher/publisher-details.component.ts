import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPublisher } from '@/shared/model/publisher.model';
import PublisherService from './publisher.service';

@Component
export default class PublisherDetails extends Vue {
  @Inject('publisherService') private publisherService: () => PublisherService;
  public publisher: IPublisher = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.publisherId) {
        vm.retrievePublisher(to.params.publisherId);
      }
    });
  }

  public retrievePublisher(publisherId) {
    this.publisherService()
      .find(publisherId)
      .then(res => {
        this.publisher = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
