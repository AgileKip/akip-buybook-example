import { Component, Vue, Inject } from 'vue-property-decorator';

import { IBuyBookBinding } from '@/shared/model/buy-book-binding.model';
import BuyBookBindingService from './buy-book-binding.service';

@Component
export default class BuyBookBindingDetailsComponent extends Vue {
  @Inject('buyBookBindingService') private buyBookBindingService: () => BuyBookBindingService;
  public buyBookBinding: IBuyBookBinding = {};

  public isFetching: boolean = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.processInstanceId) {
        vm.retrieveBuyBookBinding(to.params.processInstanceId);
      }
    });
  }

  public retrieveBuyBookBinding(buyBookBindingId) {
    this.isFetching = true;
    this.buyBookBindingService()
      .find(buyBookBindingId)
      .then(
        res => {
          this.buyBookBinding = res;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public previousState() {
    this.$router.go(-1);
  }
}
