/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import BuyBookComponent from '@/entities/buy-book/buy-book.vue';
import BuyBookClass from '@/entities/buy-book/buy-book.component';
import BuyBookService from '@/entities/buy-book/buy-book.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('BuyBook Management Component', () => {
    let wrapper: Wrapper<BuyBookClass>;
    let comp: BuyBookClass;
    let buyBookServiceStub: SinonStubbedInstance<BuyBookService>;

    beforeEach(() => {
      buyBookServiceStub = sinon.createStubInstance<BuyBookService>(BuyBookService);
      buyBookServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<BuyBookClass>(BuyBookComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          buyBookService: () => buyBookServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      buyBookServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllBuyBooks();
      await comp.$nextTick();

      // THEN
      expect(buyBookServiceStub.retrieve.called).toBeTruthy();
      expect(comp.buyBooks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      buyBookServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeBuyBook();
      await comp.$nextTick();

      // THEN
      expect(buyBookServiceStub.delete.called).toBeTruthy();
      expect(buyBookServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
