import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, minLength, maxLength } from 'vuelidate/lib/validators';

import { IPublisher, Publisher } from '@/shared/model/publisher.model';
import PublisherService from './publisher.service';

const validations: any = {
  publisher: {
    name: {
      required,
      minLength: minLength(4),
      maxLength: maxLength(80),
    },
  },
};

@Component({
  validations,
})
export default class PublisherUpdate extends Vue {
  @Inject('publisherService') private publisherService: () => PublisherService;
  public publisher: IPublisher = new Publisher();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.publisherId) {
        vm.retrievePublisher(to.params.publisherId);
      }
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.publisher.id) {
      this.publisherService()
        .update(this.publisher)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('buyBookApp.publisher.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.publisherService()
        .create(this.publisher)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('buyBookApp.publisher.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public retrievePublisher(publisherId): void {
    this.publisherService()
      .find(publisherId)
      .then(res => {
        this.publisher = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
