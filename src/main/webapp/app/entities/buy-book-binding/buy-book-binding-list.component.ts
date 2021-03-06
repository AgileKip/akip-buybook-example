import { Component, Vue, Inject } from 'vue-property-decorator';
import { IBuyBookBinding } from '@/shared/model/buy-book-binding.model';

import { ProcessDefinition, ProcessDefinitionService } from 'akip-vue-community';

import BuyBookBindingService from './buy-book-binding.service';

@Component
export default class BuyBookBindingListComponent extends Vue {
  @Inject('buyBookBindingService') private buyBookBindingService: () => BuyBookBindingService;

  private processDefinitionService: ProcessDefinitionService = new ProcessDefinitionService();

  public bpmnProcessDefinitionId: string = 'procidBuyBook';
  public processDefinition: ProcessDefinition = new ProcessDefinition();
  public buyBookBindingList: IBuyBookBinding[] = [];

  public isFetchingProcessDefinition = false;
  public isFetchingProcessInstances = false;

  public mounted(): void {
    this.init();
  }

  public init(): void {
    this.retrieveProcessDefinition();
    this.retrieveProcessInstances();
  }

  public retrieveProcessDefinition() {
    this.isFetchingProcessDefinition = true;
    this.processDefinitionService.find(this.bpmnProcessDefinitionId).then(
      res => {
        this.processDefinition = res;
        this.isFetchingProcessDefinition = false;
      },
      err => {
        this.isFetchingProcessDefinition = false;
      }
    );
  }

  public retrieveProcessInstances(): void {
    this.isFetchingProcessInstances = true;
    this.buyBookBindingService()
      .retrieve()
      .then(
        res => {
          this.buyBookBindingList = res.data;
          this.isFetchingProcessInstances = false;
        },
        err => {
          this.isFetchingProcessInstances = false;
        }
      );
  }

  get isFetching(): boolean {
    return this.isFetchingProcessDefinition && this.isFetchingProcessInstances;
  }

  public handleSyncList(): void {
    this.retrieveProcessInstances();
  }

  public previousState(): void {
    this.$router.go(-1);
  }
}
