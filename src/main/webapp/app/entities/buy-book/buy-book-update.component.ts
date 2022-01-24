import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import BookService from '@/entities/book/book.service';
import { IBook } from '@/shared/model/book.model';

import StoreService from '@/entities/store/store.service';
import { IStore } from '@/shared/model/store.model';

import { IBuyBook, BuyBook } from '@/shared/model/buy-book.model';
import BuyBookService from './buy-book.service';

const validations: any = {
  buyBook: {
    purpose: {
      required,
    },
    description: {},
    date: {},
    deliveryHome: {},
    deliveryAddress: {},
    paymentDetails: {},
    purchaseReview: {},
  },
};

@Component({
  validations,
})
export default class BuyBookUpdate extends Vue {
  @Inject('buyBookService') private buyBookService: () => BuyBookService;
  public buyBook: IBuyBook = new BuyBook();

  @Inject('bookService') private bookService: () => BookService;

  public books: IBook[] = [];

  @Inject('storeService') private storeService: () => StoreService;

  public stores: IStore[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.buyBookId) {
        vm.retrieveBuyBook(to.params.buyBookId);
      }
      vm.initRelationships();
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
    if (this.buyBook.id) {
      this.buyBookService()
        .update(this.buyBook)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('buyBookApp.buyBook.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.buyBookService()
        .create(this.buyBook)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('buyBookApp.buyBook.created', { param: param.id });
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

  public retrieveBuyBook(buyBookId): void {
    this.buyBookService()
      .find(buyBookId)
      .then(res => {
        this.buyBook = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.bookService()
      .retrieve()
      .then(res => {
        this.books = res.data;
      });
    this.storeService()
      .retrieve()
      .then(res => {
        this.stores = res.data;
      });
  }
}
