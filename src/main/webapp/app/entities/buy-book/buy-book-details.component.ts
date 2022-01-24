import { Component, Vue, Inject } from 'vue-property-decorator';

import { IBuyBook } from '@/shared/model/buy-book.model';
import BuyBookService from './buy-book.service';

@Component
export default class BuyBookDetails extends Vue {
  @Inject('buyBookService') private buyBookService: () => BuyBookService;
  public buyBook: IBuyBook = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.buyBookId) {
        vm.retrieveBuyBook(to.params.buyBookId);
      }
    });
  }

  public retrieveBuyBook(buyBookId) {
    this.buyBookService()
      .find(buyBookId)
      .then(res => {
        this.buyBook = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
