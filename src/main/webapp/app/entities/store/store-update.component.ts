import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, minLength, maxLength } from 'vuelidate/lib/validators';

import { IStore, Store } from '@/shared/model/store.model';
import StoreService from './store.service';

const validations: any = {
  store: {
    name: {
      required,
      minLength: minLength(4),
      maxLength: maxLength(80),
    },
    address: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class StoreUpdate extends Vue {
  @Inject('storeService') private storeService: () => StoreService;
  public store: IStore = new Store();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.storeId) {
        vm.retrieveStore(to.params.storeId);
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
    if (this.store.id) {
      this.storeService()
        .update(this.store)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('buyBookApp.store.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.storeService()
        .create(this.store)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('buyBookApp.store.created', { param: param.id });
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

  public retrieveStore(storeId): void {
    this.storeService()
      .find(storeId)
      .then(res => {
        this.store = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
