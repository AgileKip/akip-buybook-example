import { Component, Vue, Inject } from 'vue-property-decorator';

import { IBuyBookBinding, BuyBookBinding } from '@/shared/model/buy-book-binding.model';

import { ProcessInstance, ProcessDefinitionService } from 'akip-vue-community';

import { BuyBook } from '@/shared/model/buy-book.model';
import BuyBookBindingService from './buy-book-binding.service';

const validations: any = {
  buyBookBinding: {
    buyBook: {
      purpose: {},
      description: {},
      date: {},
    },
  },
};

@Component({
  validations,
})
export default class StartFormInitComponent extends Vue {
  @Inject('buyBookBindingService') private buyBookBindingService: () => BuyBookBindingService;

  private processDefinitionService: ProcessDefinitionService = new ProcessDefinitionService();

  public bpmnProcessDefinitionId: string = 'procidBuyBook';
  public buyBookBinding: IBuyBookBinding = new BuyBookBinding();

  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.initStartForm();
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

    this.buyBookBindingService()
      .create(this.buyBookBinding)
      .then(param => {
        this.isSaving = false;
        this.$router.go(-1);
        const message = this.$t('buyBookApp.startForm.created', { param: param.id });
        this.$root.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Success',
          variant: 'success',
          solid: true,
          autoHideDelay: 5000,
        });
      });
  }

  public initStartForm(): void {
    this.buyBookBinding.buyBook = new BuyBook();
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.processDefinitionService.find(this.bpmnProcessDefinitionId).then(res => {
      this.buyBookBinding.processInstance = new ProcessInstance();
      this.buyBookBinding.processInstance.processDefinition = res;
    });
  }
}
