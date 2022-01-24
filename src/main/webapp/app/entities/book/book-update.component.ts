import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { required, numeric, minValue, maxValue } from 'vuelidate/lib/validators';

import UserService from '@/admin/user-management/user-management.service';

import PublisherService from '@/entities/publisher/publisher.service';
import { IPublisher } from '@/shared/model/publisher.model';

import { IBook, Book } from '@/shared/model/book.model';
import BookService from './book.service';

const validations: any = {
  book: {
    name: {
      required,
    },
    description: {},
    pubYear: {
      required,
      numeric,
      min: minValue(1800),
      max: maxValue(2022),
    },
    cover: {},
  },
};

@Component({
  validations,
})
export default class BookUpdate extends mixins(JhiDataUtils) {
  @Inject('bookService') private bookService: () => BookService;
  public book: IBook = new Book();

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];

  @Inject('publisherService') private publisherService: () => PublisherService;

  public publishers: IPublisher[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.bookId) {
        vm.retrieveBook(to.params.bookId);
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
    if (this.book.id) {
      this.bookService()
        .update(this.book)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('buyBookApp.book.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.bookService()
        .create(this.book)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('buyBookApp.book.created', { param: param.id });
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

  public retrieveBook(bookId): void {
    this.bookService()
      .find(bookId)
      .then(res => {
        this.book = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public clearInputImage(field, fieldContentType, idInput): void {
    if (this.book && field && fieldContentType) {
      if (Object.prototype.hasOwnProperty.call(this.book, field)) {
        this.book[field] = null;
      }
      if (Object.prototype.hasOwnProperty.call(this.book, fieldContentType)) {
        this.book[fieldContentType] = null;
      }
      if (idInput) {
        (<any>this).$refs[idInput] = null;
      }
    }
  }

  public initRelationships(): void {
    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
    this.publisherService()
      .retrieve()
      .then(res => {
        this.publishers = res.data;
      });
  }
}
